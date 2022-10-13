package com.ed.onenet.controller;

import com.ed.onenet.dto.FileResponse;
import com.ed.onenet.dto.FormResponse;
import com.ed.onenet.service.EntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/entity")
@Slf4j
public class EntityController {
    private final EntityService entityService;

    public EntityController(EntityService entityService) {
        this.entityService = entityService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public FormResponse postObjectData(@RequestParam("id") String formId,
                                       @RequestBody Map<String, Map<String, Object>> parameters,
                                       @RequestHeader Map<String, String> headers) {
        return this.entityService.postObject(formId, parameters, headers);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public FileResponse getObjectData(@RequestParam("id") String id,
                                      @RequestParam("provider_ecc") String encodedEccUrl,
                                      @RequestParam("consumer_fiware") String encodedConsumerFiwareUrl,
                                      @RequestParam("consumer_data_app") String encodedConsumerDataAppUrl,
                                      @RequestHeader Map<String, String> headers) {
        return this.entityService.decodeUrlsAndGetObjectData(id, encodedEccUrl, encodedConsumerFiwareUrl, encodedConsumerDataAppUrl, headers);
    }

    @GetMapping(path = "/local")
    public FileResponse getLocalData(@RequestParam("id") String id,
                                     @RequestParam("fiware_url") String encodedFiwareUrl,
                                     @RequestHeader Map<String, String> headers) {
        FileResponse fileResponse = this.entityService.getLocalObjectData(id, encodedFiwareUrl, headers);
        return fileResponse;
    }
}
