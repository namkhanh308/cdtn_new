package com.cdtn.kltn.service;

import com.cdtn.kltn.common.Enums;
import com.cdtn.kltn.common.StreamUtil;
import com.cdtn.kltn.dto.CategoryTypeProperty.response.CategoryTypePropertyResponse;
import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.entity.TypeProperty;
import com.cdtn.kltn.repository.typeproperty.TypePropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TypePropertyService {
    private final TypePropertyRepository typePropertyRepository;

    public BaseResponseData findAllByCodeCateTypePropertyCategory(Long codeTypePropertyCategory) {
        List<TypeProperty> typeProperties =
                typePropertyRepository.findAllByCodeCateTypePropertyCategory(codeTypePropertyCategory);
        return !typeProperties.isEmpty()
                ? new BaseResponseData(200, "Hiển thị danh sách loại tài sản thành công", typeProperties)
                : new BaseResponseData(500, "Hiển thị danh sách loại tài sản thất bại", null);
    }

    public BaseResponseData findAllByCodeTypePropertyCategory(Long codeTypePropertyCategory) {
        List<TypeProperty> typeProperties =
                typePropertyRepository.findAllByCodeCateTypePropertyCategoryAndDisplayLevel(codeTypePropertyCategory, 2);
        return !typeProperties.isEmpty()
                ? new BaseResponseData(200, "Hiển thị danh sách loại tài sản thành công", typeProperties)
                : new BaseResponseData(500, "Hiển thị danh sách loại tài sản thất bại", null);
    }

    public Object findAllTypePropertyAndCategoryTypeProperty() {
        List<TypeProperty> typeProperties = typePropertyRepository.findAll();
        Map<Long, List<TypeProperty>> mapListTypePropertyByCodeCateId =
                StreamUtil.groupingApply(typeProperties, TypeProperty::getCodeCateTypePropertyCategory);
        List<CategoryTypePropertyResponse> categoryTypePropertyResponses = new ArrayList<>();
        for (Enums.TypePropertyCategory s : Enums.TypePropertyCategory.values()) {
            categoryTypePropertyResponses.add(CategoryTypePropertyResponse.builder()
                    .codeCateTypePropertyCategory(Enums.TypePropertyCategory.TATCA.equals(s)
                            ? null
                            : Long.valueOf(s.getCode()))
                    .nameCodeCateTypePropertyCategory(s.getName())
                    .build());
        }
        categoryTypePropertyResponses.forEach(category ->
                category.setPropertyList(mapListTypePropertyByCodeCateId.getOrDefault(category.getCodeCateTypePropertyCategory(), null)));
        return categoryTypePropertyResponses;

    }
}
