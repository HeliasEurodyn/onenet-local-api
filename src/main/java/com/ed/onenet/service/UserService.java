package com.ed.onenet.service;

import com.ed.onenet.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;

@Service
public class UserService {

    @Value("${sofia.uri}")
    private String sofiaUri;

    private final RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDTO getCurrentUser(String jwt) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Authorization", jwt);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<UserDTO> response = restTemplate.exchange(this.sofiaUri + "/user/current",
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<UserDTO>() {}
                );

        return response.getBody();
    }

}
