package com.cdtn.kltn.service;

import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.entity.Districs;
import com.cdtn.kltn.repository.districs.DistricsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictsService {
    private final DistricsRepository districsRepository;

    public BaseResponseData findAllByProvinceCode(String provinceCode){
        List<Districs> districsList = districsRepository.findAllByProvinceCode(provinceCode);
        return !districsList.isEmpty() ? new BaseResponseData(200, "Hiển thị danh sách huyện thành công", districsList) : new BaseResponseData(500, "Hiển thị danh sách huyện thất bại", null);
    }
}
