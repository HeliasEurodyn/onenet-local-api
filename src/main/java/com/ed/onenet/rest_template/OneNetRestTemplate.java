package com.ed.onenet.rest_template;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.Map;

@Service
public class OneNetRestTemplate {

    private final RestTemplate restTemplate;

    public OneNetRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> update(Map<String, Object> jsonLdParameters,
                                              Map<String, String> headers, String type, String id, String endpoint) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/ld+json");
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(jsonLdParameters, httpHeaders);

        ResponseEntity<Object> response =
        restTemplate.exchange(
                URI.create(endpoint + "/ngsi-ld/v1/entities/urn:ngsi-ld:" + type + ":" + id + "/attrs"),
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<Object>() {
                }
        );

        return jsonLdParameters;
    }

    public Map<String, Object> post(Map<String, Object> jsonLdParameters,
                                            Map<String, String> headers, String endpoint) {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/ld+json");
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(jsonLdParameters, httpHeaders);
        try {
            ResponseEntity<Object> response =
            restTemplate.exchange(
                    URI.create(endpoint +"/createentity/"),
                    HttpMethod.POST,
                    httpEntity,
                    new ParameterizedTypeReference<Object>() {
                    }
            );
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error Accessing DataApp");
        }

        return jsonLdParameters;
    }

    public Boolean checkExistance(String endpoint, String type, String id) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity httpEntity = new HttpEntity(httpHeaders);
            restTemplate.exchange(endpoint + "/ngsi-ld/v1/entities/urn:ngsi-ld:" + type + ":" + id,
                    HttpMethod.GET,
                    httpEntity,
                    new ParameterizedTypeReference<Object>() {
                    }
            );
        } catch (HttpStatusCodeException ex) {
            return false;
        }
        return true;
    }

    public Map<String, Object> retrieveData(String type, String id, String endpoint) {

            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<Map> jsonLdParameters =
                     restTemplate.exchange(endpoint + "/getentity/urn:ngsi-ld:" + type + ":" + id,
                            HttpMethod.GET,
                            httpEntity,
                             new ParameterizedTypeReference<Map>() {}
                    );

        return jsonLdParameters.getBody();
    }

    public Map<String, Object> sourceRegistration(Map<String, Object> jsonLdParameters, String endpoint) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/ld+json");
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(jsonLdParameters, httpHeaders);

        restTemplate.postForObject(
                URI.create(endpoint + "/ngsi-ld/v1/csourceRegistrations/"),
                httpEntity,
                Void.class
        );

        return jsonLdParameters;
    }

    public void dataentityRequesFromProvider(Map<String, Object> dataentityRequestJson ,String endpoint) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/ld+json");
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(dataentityRequestJson, httpHeaders);

        restTemplate.exchange(
                URI.create(endpoint + "/registration"),
                HttpMethod.POST,
                httpEntity,
                Void.class);
    }
}
