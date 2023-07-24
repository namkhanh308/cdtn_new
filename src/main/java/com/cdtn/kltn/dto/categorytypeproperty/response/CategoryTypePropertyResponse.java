package com.cdtn.kltn.dto.categorytypeproperty.response;

import com.cdtn.kltn.entity.TypeProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryTypePropertyResponse {
    private Long codeCateTypePropertyCategory;
    private String nameCodeCateTypePropertyCategory;
    private List<TypeProperty> propertyList;
}
