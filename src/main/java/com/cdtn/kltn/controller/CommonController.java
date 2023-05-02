package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.base.response.BaseResponseData;
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

    @GetMapping("/findAllTypePropertyCategory")
    public BaseResponseData findAllTypePropertyCategory() {
        return commonService.getTypePropertyCategory();
    }

    @GetMapping("/findAllLawCategory")
    public BaseResponseData findAllLawCategory() {
        return commonService.getLawCategory();
    }

    @GetMapping("/findAllTypeAccount")
    public BaseResponseData findAllTypeAccount() {
        return commonService.getTypeAccount();
    }

    @GetMapping("/findAllStatusNews")
    public BaseResponseData findAllStatusNews() {
        return commonService.getStatusNews();
    }
}

