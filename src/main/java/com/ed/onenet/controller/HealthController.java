package com.ed.onenet.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/health")
@Slf4j
public class HealthController {

    @GetMapping
    public String getObject(){
        return "{\"status\":\"ok\"}";
    }

}
