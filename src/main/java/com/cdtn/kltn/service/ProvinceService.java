package com.cdtn.kltn.service;

import com.cdtn.kltn.common.StreamUtil;
import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.entity.Districs;
import com.cdtn.kltn.entity.Province;
import com.cdtn.kltn.repository.districs.DistricsRepository;
import com.cdtn.kltn.repository.province.ProvinceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProvinceService {
    private final ProvinceRepository provinceRepository;
    private final DistricsRepository districsRepository;

    @Transactional
    public BaseResponseData findAll() {
        List<Province> provinceList = provinceRepository.findAll();
        return !provinceList.isEmpty() ?
                new BaseResponseData(200, "Hiển thị danh sách tỉnh, thành thành công", provinceList) :
                new BaseResponseData(500, "Hiển thị danh sách tỉnh, thành thất bại", null);
    }

    public Object findAllProvinceAndDistrict() {
        List<Province> provinceList = provinceRepository.findAll();
        List<Districs> districsList = districsRepository.findAll();

        Map<String, List<Districs> > mapDistrictByProvinceCode =
                StreamUtil.groupingApply(districsList, Districs::getProvinceCode);

        for (Province province : provinceList) {
            List<Districs> districtListItem = mapDistrictByProvinceCode.getOrDefault(province.getProvinceCode(), null);
            province.setDistricsList(districtListItem);
        }
        return provinceList;
    }
}
