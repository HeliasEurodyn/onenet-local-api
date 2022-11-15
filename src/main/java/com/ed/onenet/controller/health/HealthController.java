package com.ed.onenet.controller.health;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health")
@Slf4j
@Tag(name = "Health Status")
public class HealthController {

    @GetMapping
    public String getObject(){
        return "{\"version\":\"1.3\"}";
    }

}
