package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.auth.request.AuthenticationRequest;
import com.cdtn.kltn.dto.auth.request.ChangePasswordDTO;
import com.cdtn.kltn.dto.auth.request.RegistrationDTO;
import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponseData> login(@RequestBody @Valid AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
    @PostMapping("/registerAccount")
    public ResponseEntity<BaseResponseData>  login(@RequestBody @Valid RegistrationDTO request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }
    @PostMapping("/changePassword")
    public ResponseEntity<BaseResponseData> changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO){
        try {
            authService.changePassword(changePasswordDTO);
            return ResponseEntity.ok(new BaseResponseData(200, "Thay đổi mật khẩu thành công", null));
        }catch (Exception e){
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @GetMapping("/infoClient")
    public ResponseEntity<BaseResponseData> showClientInfo(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = authorizationHeader.replace("Bearer ", "").trim();
        return ResponseEntity.ok(authService.clientInfo(token));
    }




}
