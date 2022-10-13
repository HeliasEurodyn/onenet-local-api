package com.ed.onenet.service.user;

import com.ed.onenet.dto.user.JwtAuthenticationResponseDTO;
import com.ed.onenet.dto.user.LoginDTO;
import com.ed.onenet.dto.user.UserDTO;
import com.ed.onenet.rest_template.user.UserRestTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    private final UserRestTemplate userRestTemplate;

    public UserService(UserRestTemplate userRestTemplate) {
        this.userRestTemplate = userRestTemplate;
    }

    public UserDTO getCurrentUser(Map<String, String> headers) {
        return this.userRestTemplate.getCurrentUser(headers);
    }

    public JwtAuthenticationResponseDTO authenticate(LoginDTO loginDTO) {
        return this.userRestTemplate.authenticate(loginDTO);
    }
}
