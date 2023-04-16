package com.cdtn.kltn.dto.property.request;

import com.cdtn.kltn.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePropertyDTO {
    private String codeProperty;
    private String codeTypeProperty;
    private String codeCateTypePropertyCategory;
    private String nameProperty;
    private String provinceCode;
    private String districtCode;
    private String wardsCode;
    private String codeClient;
    private String areaUse;
    private String usableArea;
    private String landArea;
    private Integer bedCount;
    private Integer livingCount;
    private Integer kitchenCount;
    private Integer law;
    private String priceBuy;
    private String priceLoan;
    private String introduces;
    private String location;
    private List<PropertyImageDTO> imageList;

}
