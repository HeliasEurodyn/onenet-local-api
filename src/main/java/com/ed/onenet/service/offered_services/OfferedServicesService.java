package com.ed.onenet.service.offered_services;

import com.ed.onenet.dto.list_results.ListResultsDataDTO;
import com.ed.onenet.rest_template.offered_services.OfferedServicesRestTemplate;
import com.ed.onenet.rest_template.provide_data.ProvideDataRestTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OfferedServicesService {

    private final OfferedServicesRestTemplate offeredServicesRestTemplate;

    public OfferedServicesService(OfferedServicesRestTemplate offeredServicesRestTemplate) {
        this.offeredServicesRestTemplate = offeredServicesRestTemplate;
    }

    public List<Map<String, Object>> getList(Map<String, String> headers) {
        return this.offeredServicesRestTemplate.getList(headers);
    }
    public ListResultsDataDTO getPage(Map<String, String> headers, Long page) {
        return this.offeredServicesRestTemplate.getPage(headers, page);
    }

}
