package com.cdtn.kltn.dto.client.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationClientDTO {
    private String codeClient;
    private String provinceCode;
    private String districtCode;
    private String wardsCode;
    private String introduces;
    private String phone;
    private Integer typeLoan;
    private String passport;
    private String url;
    private String firstName;
    private String lastName;
}
