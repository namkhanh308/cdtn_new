package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.auth.request.AuthenticationRequest;
import com.cdtn.kltn.dto.auth.request.ChangePasswordDTO;
import com.cdtn.kltn.dto.auth.request.RegistrationDTO;
import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<BaseResponseData> login(@RequestBody @Valid RegistrationDTO request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("/changePassword")
    public ResponseEntity<BaseResponseData> changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO) {
        try {
            authService.changePassword(changePasswordDTO);
            return ResponseEntity.ok(new BaseResponseData(200, "Thay đổi mật khẩu thành công", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @GetMapping("/infoClient")
    public ResponseEntity<BaseResponseData> showClientInfo(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = authorizationHeader.replace("Bearer ", "").trim();
        return ResponseEntity.ok(authService.clientInfo(token));
    }

    @GetMapping("/findAllUser")
    public ResponseEntity<BaseResponseData> findAllUser(@RequestParam String searchName, @RequestParam int page, @RequestParam int size) {
        try {
            Page<?> list = authService.findAllUser(searchName, page, size);
            return ResponseEntity.ok(new BaseResponseData(200, "Hiển thị danh sách tài khoản thành công", list));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @GetMapping("/resetPassword")
    public ResponseEntity<BaseResponseData> resetPassword(@RequestParam Long id) {
        try {
            authService.resetPassword(id);
            return ResponseEntity.ok(new BaseResponseData(200, "Reset mật khẩu thành công", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @GetMapping("/changeStatus")
    public ResponseEntity<BaseResponseData> changeStatus(@RequestParam Long id) {
        try {
            authService.changeStatus(id);
            return ResponseEntity.ok(new BaseResponseData(200, "Reset mật khẩu thành công", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }


    @GetMapping("/detail")
    public ResponseEntity<BaseResponseData> showClientInfo(@RequestParam Long id) {
        return ResponseEntity.ok(authService.clientDetail(id));
    }


}
