package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.base.BaseResponseData;
import com.cdtn.kltn.service.ClientService;
import com.cdtn.kltn.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/province")
@RequiredArgsConstructor
public class ProvinceController {
    private final ProvinceService provinceService;

    @GetMapping("/findAll")
    public BaseResponseData findAll() {
        return provinceService.findAll();
    }

}
