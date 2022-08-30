package com.ed.onenet.controller.rest_template;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@Service
public class SofiaRestTemplate {

    private final RestTemplate restTemplate;
    @Value("${sofia.uri}")
    private String sofiaUri;

    public SofiaRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String post(Map<String, Map<String, Object>> parameters,
                       String formId,
                       Map<String, String> headers) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Authorization", headers.get("authorization"));
        HttpEntity<Map<String, Map<String, Object>>> httpEntity =
                new HttpEntity<Map<String, Map<String, Object>>>(parameters, httpHeaders);

        ResponseEntity<String> response =
                restTemplate.exchange(
                        URI.create(sofiaUri + "/form?id=" + formId),
                        HttpMethod.POST,
                        httpEntity,
                        new ParameterizedTypeReference<String>() {
                        }
                );

        return response.getBody();
    }

}
