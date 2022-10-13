package com.ed.onenet.controller.consume_data;

import com.ed.onenet.dto.*;
import com.ed.onenet.dto.list_results.ListResultsDataDTO;
import com.ed.onenet.service.consume_data.ConsumeDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/consume-data")
@Slf4j
@Tag(name = "Asset Types", description = "Endpoints collection for Asset Types List.")
public class ConsumeDataController {

    private final ConsumeDataService consumeDataService;

    public ConsumeDataController(ConsumeDataService consumeDataService) {
        this.consumeDataService = consumeDataService;
    }

    @GetMapping("list")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public List<Map<String, Object>> getList(@RequestHeader Map<String, String> headers) {
        return consumeDataService.getList(headers);
    }

    @GetMapping("page/{page}")
    public ListResultsDataDTO getPage(
            @RequestHeader Map<String, String> headers,
            @PathVariable("page") Long page) {
        return consumeDataService.getPage(headers, page);
    }

    @GetMapping("by-id")
    public FileResponse getObjectData(@RequestParam("id") String id,
                                      @RequestHeader Map<String, String> headers) {
        return this.consumeDataService.getObjectData(id, headers);
    }

}
