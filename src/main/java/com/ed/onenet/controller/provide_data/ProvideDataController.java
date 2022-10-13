package com.ed.onenet.controller.provide_data;

import com.ed.onenet.dto.list_results.ListResultsDataDTO;
import com.ed.onenet.dto.provide_data.ProvideDataDTO;
import com.ed.onenet.service.provide_data.ProvideDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/provide-data")
@Slf4j
public class ProvideDataController {

    private final ProvideDataService provideDataService;

    public ProvideDataController(ProvideDataService provideDataService) {
        this.provideDataService = provideDataService;
    }

    @GetMapping("list")
    public List<Map<String, Object>> getList(@RequestHeader Map<String, String> headers) {
        return provideDataService.getList(headers);
    }

    @GetMapping("page/{page}")
    public ListResultsDataDTO getPage(
            @RequestHeader Map<String, String> headers,
            @PathVariable("page") Long page) {
        return provideDataService.getPage(headers, page);
    }

    @PostMapping
    public Object post(
            @RequestBody ProvideDataDTO dto,
            @RequestHeader Map<String, String> headers) {
        return provideDataService.post(dto, headers);
    }

}
