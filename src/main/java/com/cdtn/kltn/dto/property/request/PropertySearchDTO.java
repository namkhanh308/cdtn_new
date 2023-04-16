package com.cdtn.kltn.dto.property.request;

import lombok.Data;

@Data
public class PropertySearchDTO {
    private Integer page;
    private Integer records;
    private String codeProperty;
    private String codeTypeProperty;
    private String nameProperty;

}
