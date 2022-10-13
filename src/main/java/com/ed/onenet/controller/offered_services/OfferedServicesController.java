package com.ed.onenet.controller.offered_services;

import com.ed.onenet.dto.list_results.ListResultsDataDTO;
import com.ed.onenet.service.offered_services.OfferedServicesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/offered-services")
@Slf4j
public class OfferedServicesController {

    private final OfferedServicesService offeredServicesService;

    public OfferedServicesController(OfferedServicesService offeredServicesService) {
        this.offeredServicesService = offeredServicesService;
    }

    @GetMapping("list")
    public List<Map<String, Object>> getList(@RequestHeader Map<String, String> headers) {
        return offeredServicesService.getList(headers);
    }

    @GetMapping("page/{page}")
    public ListResultsDataDTO getPage(
            @RequestHeader Map<String, String> headers,
            @PathVariable("page") Long page) {
        return offeredServicesService.getPage(headers, page);
    }

}
