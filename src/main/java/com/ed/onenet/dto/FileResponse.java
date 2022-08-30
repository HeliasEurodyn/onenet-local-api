package com.ed.onenet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@Accessors(chain = true)
public class FileResponse {

    public FileResponse(String filedata, Boolean retrieved){
        this.filedata = filedata;
        this.retrieved = retrieved;
    }

    public String filedata;

    public Boolean retrieved;

}
