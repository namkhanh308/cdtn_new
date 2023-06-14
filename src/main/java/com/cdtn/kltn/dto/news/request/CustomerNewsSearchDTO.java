package com.cdtn.kltn.dto.news.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CustomerNewsSearchDTO {
    private String nameSearch;
    private String provinceCode;
    private String districtCode;
    private String codeTypeProperty;
    private String codeCateTypePropertyCategory;
    private String priceStart;
    private String priceEnd;
    private String areaMinRange;
    private String areaMaxRange;
    private String totalRoom;
    private Integer rangeDaySearch;
    private Integer page;
    private Integer size;
}
