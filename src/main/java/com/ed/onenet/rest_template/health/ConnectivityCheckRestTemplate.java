package com.ed.onenet.rest_template.health;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class ConnectivityCheckRestTemplate {

    @Value("${sofia.uri}")
    private String sofiaUri;

    private final RestTemplate restTemplate;

    public ConnectivityCheckRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Object checkUrlConnectivity(String url) {

        HttpHeaders httpHeaders = new HttpHeaders();
    //    httpHeaders.add("Content-Type", "application/json");
    //    httpHeaders.add("Authorization", headers.get("authorization"));
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<Object> response =
                restTemplate.exchange(url ,
                        HttpMethod.GET,
                        httpEntity,
                        new ParameterizedTypeReference<Object>() {}
                );

        return response.getBody();
    }

    public Map<String, String> checkUrlConnectivityFromCentralRegistry(Map<String, String> headers, String dataAppUrl) {

        String encodedDataAppUrl = Base64.getEncoder().encodeToString(dataAppUrl.getBytes(StandardCharsets.UTF_8));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Authorization", headers.get("authorization"));
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<Map<String, String>> response =
                restTemplate.exchange(this.sofiaUri + "/check-connectivity?data_app_url="+encodedDataAppUrl,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<Map<String, String>>() {
                }
        );

        return response.getBody();
    }

}
