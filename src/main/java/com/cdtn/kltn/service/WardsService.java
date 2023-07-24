package com.cdtn.kltn.service;

import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.entity.Wards;
import com.cdtn.kltn.repository.wards.WardsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WardsService {

    private final WardsRepository wardsRepository;

    public BaseResponseData findAllByDistrictCode(String districsCode) {
        List<Wards> districsList = wardsRepository.findAllByDistrictCode(districsCode);
        return !districsList.isEmpty() ?
                new BaseResponseData(200, "Hiển thị danh sách phường, xã thành công", districsList) :
                new BaseResponseData(500, "Hiển thị danh phường xã thất bại", null);
    }

}
