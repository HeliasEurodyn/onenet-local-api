package com.ed.onenet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.Accessors;


@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@Accessors(chain = true)
public class UserDTO {

    private String id;

    private String username;

    private String email;

    private String companyId;

    private String companyName;

    private String companyAddress;

    private String phoneNumber;

}
