package com.ed.onenet.service.consume_data;

import com.ed.onenet.dto.FileResponse;
import com.ed.onenet.dto.list_results.ListResultsDataDTO;
import com.ed.onenet.rest_template.consume_data.ConsumeDataRestTemplate;
import com.ed.onenet.rest_template.custom_query.CustomQueryRestTemplate;
import com.ed.onenet.service.local_functionality.EntityService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ConsumeDataService {

    private final ConsumeDataRestTemplate consumeDataRestTemplate;
    private final EntityService entityService;
    private final CustomQueryRestTemplate customQueryRestTemplate;

    public ConsumeDataService(ConsumeDataRestTemplate consumeDataRestTemplate,
                              EntityService entityService,
                              CustomQueryRestTemplate customQueryRestTemplate) {
        this.consumeDataRestTemplate = consumeDataRestTemplate;
        this.entityService = entityService;
        this.customQueryRestTemplate = customQueryRestTemplate;
    }

    public List<Map<String, Object>> getList(Map<String, String> headers) {
        return this.consumeDataRestTemplate.getList(headers);
    }

    public ListResultsDataDTO getPage(Map<String, String> headers, Long page) {
        return this.consumeDataRestTemplate.getPage(headers, page);
    }

    public FileResponse getObjectData(String id, Map<String, String> headers) {
        List<Map<String, Object>> consumeParameters =
                this.customQueryRestTemplate.getDataObjects("65d27a30-e2e3-4372-b142-8ae82f0ba2f9", Collections.singletonMap("data_send_id", id), headers);
        if (consumeParameters.size() <= 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File cannot be retrieved.");
        }

        String eccUrl = (String) consumeParameters.get(0).get("ecc_url");
        String brokerUrl = (String) consumeParameters.get(0).get("broker_url");
        String dataAppUrl = (String) consumeParameters.get(0).get("data_app_url");

        FileResponse fileResponse = this.entityService.getObjectData(id, eccUrl, brokerUrl, dataAppUrl, headers);

        if (fileResponse.retrieved == true && fileResponse.filedata.contains(",")) {
            String[] parts = fileResponse.filedata.split(",");
            fileResponse.filedata = parts[1];
        }

        return fileResponse;
    }

}
