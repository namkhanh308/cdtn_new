package com.cdtn.kltn.service;

import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.entity.TypeProperty;
import com.cdtn.kltn.repository.typeproperty.TypePropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypePropertyService {
    private final TypePropertyRepository typePropertyRepository;

    public BaseResponseData findAllByCodeCateTypePropertyCategory(Long codeTypePropertyCategory){
        List<TypeProperty> typeProperties = typePropertyRepository.findAllByCodeCateTypePropertyCategory(codeTypePropertyCategory);
        return typeProperties.size() > 0
                ? new BaseResponseData(200, "Hiển thị danh sách loại tài sản thành công", typeProperties)
                : new BaseResponseData(500, "Hiển thị danh sách loại tài sản thất bại", null);
    }

    public BaseResponseData findAllByCodeTypePropertyCategory(Long codeTypePropertyCategory){
        List<TypeProperty> typeProperties = typePropertyRepository.findAllByCodeCateTypePropertyCategoryAndDisplayLevel(codeTypePropertyCategory,2);
        return typeProperties.size() > 0
                ? new BaseResponseData(200, "Hiển thị danh sách loại tài sản thành công", typeProperties)
                : new BaseResponseData(500, "Hiển thị danh sách loại tài sản thất bại", null);
    }
}
