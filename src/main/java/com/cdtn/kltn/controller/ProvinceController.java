package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/province")
@RequiredArgsConstructor
public class ProvinceController {
    private final ProvinceService provinceService;

    @GetMapping("/findAll")
    public ResponseEntity<BaseResponseData> findAll() {
        return ResponseEntity.ok(provinceService.findAll());
    }

    @GetMapping("/findAllProvinceAndDistrict")
    public ResponseEntity<BaseResponseData> findAllProvinceAndDistrict() {
        return ResponseEntity.ok(new BaseResponseData(200, "Success", provinceService.findAllProvinceAndDistrict()));
    }
}
