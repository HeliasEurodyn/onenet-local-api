package com.ed.onenet.controller.user;

import com.ed.onenet.dto.user.JwtAuthenticationResponseDTO;
import com.ed.onenet.dto.user.LoginDTO;
import com.ed.onenet.dto.user.UserDTO;
import com.ed.onenet.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
@Tag(name = "User", description = "The first endpoint authenticates a user and the second one provides info about current user ")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Authenticates a user and returns a JWT if authentication was successful.
     *
     * @param loginDTO The email and password of the user to authenticate.
     * @return Returns the JWT.
     */
    @PostMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
    public JwtAuthenticationResponseDTO authenticate(@RequestBody LoginDTO loginDTO) {
        return userService.authenticate(loginDTO);
    }

    @GetMapping(value = "/current")
    public UserDTO getCurrentUser(@RequestHeader Map<String, String> headers) {
        return userService.getCurrentUser(headers);
    }

}
