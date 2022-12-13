package com.ed.onenet.controller.health;

import com.ed.onenet.service.health.ConnectivityCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/check-connectivity")
@Slf4j
public class ConnectivityCheckController {

    private final ConnectivityCheckService connectivityCheckService;

    public ConnectivityCheckController(ConnectivityCheckService connectivityCheckService) {
        this.connectivityCheckService = connectivityCheckService;
    }

    @GetMapping
    public Map<String, String> check(@RequestHeader Map<String, String> headers){
     log.debug("*** ConnectivityCheckController GET" );
     log.debug("Headers= "+ headers);
     return this.connectivityCheckService.check(headers);
    }

}
