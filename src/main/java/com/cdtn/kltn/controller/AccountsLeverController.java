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
        return ResponseEntity.ok(accountsLeverService.switchAccountLever(accountsLeverSwitchDTO));
    }
}
