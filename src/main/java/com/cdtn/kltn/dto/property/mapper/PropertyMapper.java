package com.cdtn.kltn.dto.property.mapper;

import com.cdtn.kltn.common.Enums;
import com.cdtn.kltn.dto.property.request.CreatePropertyDTO;
import com.cdtn.kltn.dto.property.respone.PropertyDetailDataRespone;
import com.cdtn.kltn.entity.Property;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PropertyMapper {
    public Property createProperty(CreatePropertyDTO createPropertyDTO, String codeProperty) {
        return Property.builder()
                .codeTypeProperty(createPropertyDTO.getCodeTypeProperty())
                .codeCateTypePropertyCategory(createPropertyDTO.getCodeCateTypePropertyCategory())
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

    public void updateProperty(CreatePropertyDTO createPropertyDTO, Property property) {
        property.setNameProperty(createPropertyDTO.getNameProperty());
        property.setProvinceCode(createPropertyDTO.getProvinceCode());
        property.setDistrictCode(createPropertyDTO.getDistrictCode());
        property.setWardsCode(createPropertyDTO.getWardsCode());
        property.setDateChange(LocalDateTime.now());
        property.setStatusProperty(Enums.StatusProperty.DACHINHSUA.getCode());
    }

    public CreatePropertyDTO setDataPropertyDetailDTO(PropertyDetailDataRespone propertyDetailDataRespone) {
        return CreatePropertyDTO.builder()
                .codeProperty(propertyDetailDataRespone.getCodeProperty())
                .codeTypeProperty(propertyDetailDataRespone.getCodeTypeProperty())
                .codeCateTypePropertyCategory(propertyDetailDataRespone.getCodeCateTypePropertyCategory())
                .nameProperty(propertyDetailDataRespone.getNameProperty())
                .provinceCode(propertyDetailDataRespone.getProvinceCode())
                .districtCode(propertyDetailDataRespone.getDistrictCode())
                .wardsCode(propertyDetailDataRespone.getWardsCode())
                .codeClient(propertyDetailDataRespone.getCodeClient())
                .areaUse(propertyDetailDataRespone.getAreaUse())
                .usableArea(propertyDetailDataRespone.getUsableArea())
                .landArea(propertyDetailDataRespone.getLandArea())
                .bedCount(propertyDetailDataRespone.getBedCount())
                .livingCount(propertyDetailDataRespone.getLivingCount())
                .kitchenCount(propertyDetailDataRespone.getKitchenCount())
                .law(propertyDetailDataRespone.getLaw())
                .priceBuy(propertyDetailDataRespone.getPriceBuy())
                .priceLoan(propertyDetailDataRespone.getPriceLoan())
                .introduces(propertyDetailDataRespone.getIntroduces())
                .location(propertyDetailDataRespone.getLocation())
                .build();
    }
}
