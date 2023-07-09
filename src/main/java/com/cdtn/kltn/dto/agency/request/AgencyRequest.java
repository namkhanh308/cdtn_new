package com.cdtn.kltn.dto.agency.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgencyRequest {
    private Long id;
    private String url;
    private String nameAgency;
    private String phone;
    private String dateCreate;
    private String provinceCode;
    private String district1st;
}
