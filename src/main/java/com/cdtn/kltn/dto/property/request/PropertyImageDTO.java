package com.cdtn.kltn.dto.property.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyImageDTO {
    private String propertyCode;
    private String codeImage;
    private String url;
}
