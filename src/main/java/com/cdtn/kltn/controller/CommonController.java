package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.base.BaseResponseData;
import com.cdtn.kltn.service.ClientService;
import com.cdtn.kltn.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/common")
@RequiredArgsConstructor
public class CommonController {
    private final CommonService commonService;

    @GetMapping("/findAllTypeLoan")
    public BaseResponseData findAllTypeLoan() {
        return commonService.getLoanType();
    }
}

