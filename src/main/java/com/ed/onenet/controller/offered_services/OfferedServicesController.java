package com.ed.onenet.controller.offered_services;

import com.ed.onenet.dto.list_results.ListResultsDataDTO;
import com.ed.onenet.service.offered_services.OfferedServicesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/offered-services")
@Slf4j
@Tag(name = "Offered Services", description = "These endpoints fetch your offered services (All of them or by {page}).")
public class OfferedServicesController {

    private final OfferedServicesService offeredServicesService;

    public OfferedServicesController(OfferedServicesService offeredServicesService) {
        this.offeredServicesService = offeredServicesService;
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("list")
    public List<Map<String, Object>> getList(@RequestHeader Map<String, String> headers) {
        return offeredServicesService.getList(headers);
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("page/{page}")
    public ListResultsDataDTO getPage(
            @RequestHeader Map<String, String> headers,
            @PathVariable("page") Long page) {
        return offeredServicesService.getPage(headers, page);
    }

}
