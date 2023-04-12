package com.cdtn.kltn.dto.property.mapper;

import com.cdtn.kltn.common.Enums;
import com.cdtn.kltn.dto.property.request.CreatePropertyDTO;
import com.cdtn.kltn.entity.Property;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PropertyMapper {
    public Property createProperty(CreatePropertyDTO createPropertyDTO, String codeProperty){
        return Property.builder()
                .codeTypeProperty(createPropertyDTO.getCodeTypeProperty())
                .nameProperty(createPropertyDTO.getNameProperty())
                .codeProperty(codeProperty)
                .provinceCode(createPropertyDTO.getProvinceCode())
                .districtCode(createPropertyDTO.getDistrictCode())
                .wardsCode(createPropertyDTO.getWardsCode())
                .dateCreate(LocalDateTime.now())
                .codeClient(createPropertyDTO.getCodeClient())
                .typeLoanOrBuy(Enums.TypeLoanOrBuy.LOAN.getCode())
                .statusProperty(Enums.StatusProperty.MOITAO.getCode())
                .build();
    }

    public void updateProperty(CreatePropertyDTO createPropertyDTO, Property property){
        property.setNameProperty(createPropertyDTO.getNameProperty());
        property.setProvinceCode(createPropertyDTO.getProvinceCode());
        property.setDistrictCode(createPropertyDTO.getDistrictCode());
        property.setWardsCode(createPropertyDTO.getWardsCode());
        property.setDateChange(LocalDateTime.now());
        property.setStatusProperty(Enums.StatusProperty.DACHINHSUA.getCode());
    }
}
