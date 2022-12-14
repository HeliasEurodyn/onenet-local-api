package com.ed.onenet.service.health;

import com.ed.onenet.rest_template.custom_query.CustomQueryRestTemplate;
import com.ed.onenet.rest_template.health.ConnectivityCheckRestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ConnectivityCheckService {

    private final CustomQueryRestTemplate customQueryRestTemplate;
    private final ConnectivityCheckRestTemplate connectivityCheckRestTemplate;

    public ConnectivityCheckService(CustomQueryRestTemplate customQueryRestTemplate,
                                    ConnectivityCheckRestTemplate connectivityCheckRestTemplate) {
        this.customQueryRestTemplate = customQueryRestTemplate;
        this.connectivityCheckRestTemplate = connectivityCheckRestTemplate;
    }

    @GetMapping
    public Map<String, String> check(@RequestHeader Map<String, String> headers){

        /* Get Provider Parameters From Central Registry */
        log.debug("1. Get Provider Parameters From Central Registry" );
        List<Map<String,Object>> parameters =
                this.customQueryRestTemplate.getDataObjects("e48046c9-0b94-41d2-9ad4-206f1604b821", Collections.EMPTY_MAP, headers);

        log.debug("response= "+ parameters.toString());

        if(parameters.size() <= 0){
            log.debug("return 1 - Error retrieving your connection settings from central registry");
            return Map.of("code","1",
                    "message", "Error retrieving your connection settings from central registry");
        }

      //  String eccUrl = (String) parameters.get(0).get("ecc_url");
        String dataAppUrl = (String) parameters.get(0).get("data_app_url");

//        if(eccUrl.endsWith("/data")){
//            eccUrl = eccUrl.replace("/data","");
//        }

        try{
            log.debug("2. Check Data App from local api" );
            log.debug("dataAppUrl= "+ dataAppUrl + "/about/version");
            Object o = connectivityCheckRestTemplate.checkUrlConnectivity(dataAppUrl + "/about/version");
        } catch (Exception ex){
            log.debug("return 2 - Data App cannot be found");
            return Map.of("code","2",
                    "message", "Data App cannot be found");
        }

        try{
            log.debug("3. Check Data App Central Registry" );
            return connectivityCheckRestTemplate.checkUrlConnectivityFromCentralRegistry(headers, dataAppUrl);
        } catch (Exception ex){
            log.debug("return 5 - Data App cannot be accessed from the Web");
            return Map.of("code","5",
                    "message", "Data App cannot be accessed from the Web");
        }

//        try{
//            Object o = connectivityCheckRestTemplate.checkUrlConnectivity(eccUrl + "/about/version");
//        } catch (Exception ex){
//            return Map.of("code","3",
//                    "message", "Ecc cannot be found");
////            return "{\"code\":\"3\",\"message\":\"Ecc cannot be found\"}";
//        }

//
//        return Map.of("code","0",
//                "message", "Connection is established!");
    }

}
