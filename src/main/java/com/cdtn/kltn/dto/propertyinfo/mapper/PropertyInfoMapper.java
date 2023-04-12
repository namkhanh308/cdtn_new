package com.cdtn.kltn.dto.propertyinfo.mapper;

import com.cdtn.kltn.dto.property.request.CreatePropertyDTO;
import com.cdtn.kltn.entity.PropertyInfo;
import com.cdtn.kltn.entity.TypeProperty;
import org.springframework.stereotype.Component;

@Component
public class PropertyInfoMapper {

    public PropertyInfo createPropertyInfo(CreatePropertyDTO createPropertyDTO,String codeProperty){
        return PropertyInfo.builder()
                .codeProperty(codeProperty)
                .areaUse(createPropertyDTO.getAreaUse())
                .usableArea(createPropertyDTO.getUsableArea())
                .landArea(createPropertyDTO.getLandArea())
                .bedCount(createPropertyDTO.getBedCount())
                .livingCount(createPropertyDTO.getLivingCount())
                .kitchenCount(createPropertyDTO.getKitchenCount())
                .law(createPropertyDTO.getLaw())
                .priceBuy(createPropertyDTO.getPriceBuy())
                .priceLoan(createPropertyDTO.getPriceLoan())
                .introduces(createPropertyDTO.getIntroduces())
                .location(createPropertyDTO.getLocation())
                .build();
    }

    public void updatePropertyInfo(CreatePropertyDTO createPropertyDTO, PropertyInfo propertyInfo){
        propertyInfo.setAreaUse(createPropertyDTO.getAreaUse());
        propertyInfo.setUsableArea(createPropertyDTO.getUsableArea());
        propertyInfo.setLandArea(createPropertyDTO.getLandArea());
        propertyInfo.setBedCount(createPropertyDTO.getBedCount());
        propertyInfo.setLivingCount(createPropertyDTO.getLivingCount());
        propertyInfo.setKitchenCount(createPropertyDTO.getKitchenCount());
        propertyInfo.setLaw(createPropertyDTO.getLaw());
        propertyInfo.setPriceBuy(createPropertyDTO.getPriceBuy());
        propertyInfo.setPriceLoan(createPropertyDTO.getPriceLoan());
        propertyInfo.setIntroduces(createPropertyDTO.getIntroduces());
        propertyInfo.setLocation(createPropertyDTO.getLocation());
    }
}
