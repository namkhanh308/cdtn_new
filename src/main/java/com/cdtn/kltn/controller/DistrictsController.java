package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.service.DistrictsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/districts")
@RequiredArgsConstructor
public class DistrictsController {
    private final DistrictsService districtsService;

    @GetMapping("/findAllByProvinceCode")
    public ResponseEntity<BaseResponseData> login(@RequestParam String provinceCode) {
        return ResponseEntity.ok(districtsService.findAllByProvinceCode(provinceCode));
    }

}
