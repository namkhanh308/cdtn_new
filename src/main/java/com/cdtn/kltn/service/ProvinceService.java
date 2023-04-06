package com.cdtn.kltn.service;

import com.cdtn.kltn.dto.base.BaseResponseData;
import com.cdtn.kltn.entity.Province;
import com.cdtn.kltn.repository.province.ProvinceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvinceService {
    private final ProvinceRepository provinceRepository;

    @Transactional
    public BaseResponseData findAll() {
        List<Province> provinceList = provinceRepository.findAll();
        return provinceList.size() > 0 ? new BaseResponseData(200, "Hiển thị danh sách tỉnh, thành thành công", provinceList) : new BaseResponseData(500, "Hiển thị danh sách tỉnh, thành thất bại", null);
    }
}
