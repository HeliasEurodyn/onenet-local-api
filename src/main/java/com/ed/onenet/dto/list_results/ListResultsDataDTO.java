package com.ed.onenet.dto.list_results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@Accessors(chain = true)
public class ListResultsDataDTO {
    List<Map<String, Object>> listContent;
    Long totalPages;
    Long currentPage;
    Long pageSize;
    Long totalRows;
}
