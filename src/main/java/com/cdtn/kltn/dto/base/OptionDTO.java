package com.cdtn.kltn.dto.base;

import lombok.Data;

@Data
public class OptionDTO {
    private String value;
    private String name;

    public OptionDTO(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
