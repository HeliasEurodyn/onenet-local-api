package com.ed.onenet.rest_template.provide_data;

import com.ed.onenet.dto.list_results.ListResultsDataDTO;
import com.ed.onenet.dto.provide_data.ProvideDataDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Service
public class ProvideDataRestTemplate {

    private final RestTemplate restTemplate;
    @Value("${sofia.uri}")
    private String sofiaUri;

    public ProvideDataRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> getList(Map<String, String> headers) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Authorization", headers.get("authorization"));
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(this.sofiaUri + "/datalist/data_provided",
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {
                }
        );

        return response.getBody();
    }

    public ListResultsDataDTO getPage(Map<String, String> headers, Long page) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Authorization", headers.get("authorization"));
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<ListResultsDataDTO> response = restTemplate.exchange(this.sofiaUri + "/datalist/data_provided/page/" + page,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<ListResultsDataDTO>() {
                }
        );

        return response.getBody();
    }

    public String post(Map<String, Map<String, Object>> parameters, Map<String, String> headers) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Authorization", headers.get("authorization"));
        HttpEntity<Map<String, Map<String, Object>>> httpEntity =
                new HttpEntity<Map<String, Map<String, Object>>>(parameters, httpHeaders);

        ResponseEntity<String> response =
                restTemplate.exchange(
                        URI.create(sofiaUri + "/dataset/data_provided"),
                        HttpMethod.POST,
                        httpEntity,
                        new ParameterizedTypeReference<String>() {
                        }
                );

        return response.getBody();
    }
}
