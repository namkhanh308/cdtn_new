package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.service.WardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/wards")
@RequiredArgsConstructor
public class WardsController {
    private final WardsService wardsService;

    @RequestMapping("/findAllByDistrictCode")
    public ResponseEntity<BaseResponseData> findAllByDistrictCode(@RequestParam String districtsCode) {
        return ResponseEntity.ok(wardsService.findAllByDistrictCode(districtsCode));
    }

}
