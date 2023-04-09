package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.base.BaseResponseData;
import com.cdtn.kltn.service.DistrictsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/districts")
@RequiredArgsConstructor
public class DistrictsController {
    private final DistrictsService districtsService;

    @GetMapping("/findAllByProvinceCode")
    public BaseResponseData login(@RequestParam String provinceCode) {
        return districtsService.findAllByProvinceCode(provinceCode);
    }

}
