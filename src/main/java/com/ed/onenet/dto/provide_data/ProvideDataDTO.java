package com.ed.onenet.dto.provide_data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@Accessors(chain = true)
public class ProvideDataDTO {
    String title;
    String description;
    String filename;
    String file;

    @NotEmpty
    @NotNull(message = "Data Offering Id not null")
    String data_offering_id;
    String code;
}
