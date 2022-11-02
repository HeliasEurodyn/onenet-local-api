package com.ed.onenet.service.provide_data;

import com.ed.onenet.dto.list_results.ListResultsDataDTO;
import com.ed.onenet.dto.provide_data.ProvideDataDTO;
import com.ed.onenet.rest_template.custom_query.CustomQueryRestTemplate;
import com.ed.onenet.rest_template.provide_data.ProvideDataRestTemplate;
import com.ed.onenet.service.EntityService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProvideDataService {

    private final ProvideDataRestTemplate provideDataRestTemplate;
    private final EntityService entityService;
    private final CustomQueryRestTemplate customQueryRestTemplate;

    public ProvideDataService(ProvideDataRestTemplate provideDataRestTemplate,
                              EntityService entityService,
                              CustomQueryRestTemplate customQueryRestTemplate) {
        this.provideDataRestTemplate = provideDataRestTemplate;
        this.entityService = entityService;
        this.customQueryRestTemplate = customQueryRestTemplate;
    }

    public List<Map<String, Object>> getList(Map<String, String> headers) {
        return this.provideDataRestTemplate.getList(headers);
    }

    public ListResultsDataDTO getPage(Map<String, String> headers, Long page) {
        return this.provideDataRestTemplate.getPage(headers, page);
    }

    public Object post(ProvideDataDTO dto, Map<String, String> headers) {
        Map<String, Object> dataSend = new HashMap<>();
        dataSend.put("title", dto.getTitle());
        dataSend.put("description", dto.getDescription());
        dataSend.put("fileName", dto.getFilename());
        dataSend.put("user_code", dto.getCode());
        dataSend.put("data_catalog_data_offerings_id", dto.getData_offering_id());
        dataSend.put("status","pending");
        Map<String, Map<String, Object>> parameters = new HashMap<>();
        parameters.put("data_send", dataSend);

        /* Save Data Offering to Central Registry */
        String id = this.provideDataRestTemplate.post(parameters, headers);
        dataSend.put("id",id);
        dataSend.put("message","data:application/octet-stream;base64," + dto.getFile() );

        /* Get Provider Parameters From Central Registry */
        List<Map<String,Object>> providerParameters =
                this.customQueryRestTemplate.getDataObjects("a716cb83-dc93-4d49-b4ac-92e3185eb715", Collections.EMPTY_MAP, headers);
        if(providerParameters.size() <= 0){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Provider Error");
        }

        /* Save File on Local Premises */
        String providerFiwareUrl = (String) providerParameters.get(0).get("provider_fiware_url");
        this.entityService.postObjectToOnenet(parameters, providerFiwareUrl, headers);

        /* Activate Data Offering to Central Registry */
        Map<String, String> activationParameters = new HashMap<>();
        activationParameters.put("data_send_id", id);
        this.customQueryRestTemplate.activateDataOffering(activationParameters, headers);

        return Collections.singletonMap("id", id);
    }
}
