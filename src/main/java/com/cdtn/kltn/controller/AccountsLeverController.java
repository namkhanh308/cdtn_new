package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.accountlevel.request.AccountsLeverSwitchDTO;
import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.service.AccountsLeverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/accountslever")
@RequiredArgsConstructor
public class AccountsLeverController {

    private final AccountsLeverService accountsLeverService;

    @PostMapping("/switchAccount")
    public ResponseEntity<BaseResponseData> login(@RequestBody @Valid AccountsLeverSwitchDTO accountsLeverSwitchDTO) {
        try {
            accountsLeverService.switchAccountLever(accountsLeverSwitchDTO);
            return ResponseEntity.ok(new BaseResponseData(200, "Chuyển đổi gói tài khoản thành công",null));
        }catch (Exception e){
            return ResponseEntity.ok(new BaseResponseData(500, "Error", e.getMessage()));
        }
    }
}
