package com.ed.onenet.controller.provide_data;

import com.ed.onenet.dto.list_results.ListResultsDataDTO;
import com.ed.onenet.dto.provide_data.ProvideDataDTO;
import com.ed.onenet.service.provide_data.ProvideDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/provide-data")
@Slf4j
@Tag(name = "Provide Data", description = "These endpoints retrieve data which have been provided by you to other users (All of them or by {page}). In addition, you can provide data using post method")
public class ProvideDataController {

    private final ProvideDataService provideDataService;

    public ProvideDataController(ProvideDataService provideDataService) {
        this.provideDataService = provideDataService;
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("list")
    public List<Map<String, Object>> getList(@RequestHeader Map<String, String> headers) {
        return provideDataService.getList(headers);
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("page/{page}")
    public ListResultsDataDTO getPage(
            @RequestHeader Map<String, String> headers,
            @PathVariable("page") Long page) {
        return provideDataService.getPage(headers, page);
    }

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public Object post(
            @RequestBody @Valid ProvideDataDTO dto,
            @RequestHeader Map<String, String> headers) {
        return provideDataService.post(dto, headers);
    }

}
