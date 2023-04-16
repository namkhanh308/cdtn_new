package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.auth.request.AuthenticationRequest;
import com.cdtn.kltn.dto.auth.request.RegistrationDTO;
import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.security.JwtFilter;
import com.cdtn.kltn.service.AuthService;
import com.cdtn.kltn.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.repo.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final JwtFilter filter;

    private final JwtService jwtService;

    private final HttpServletRequest request;


    @PostMapping("/login")
    public ResponseEntity<BaseResponseData> login(@RequestBody @Valid AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
    @PostMapping("/registerAccount")
    public ResponseEntity<BaseResponseData>  login(@RequestBody @Valid RegistrationDTO request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @GetMapping("/infoClient")
    public ResponseEntity<BaseResponseData> ShowClientInfo (HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = authorizationHeader.replaceAll("Bearer", "").trim();
        return ResponseEntity.ok(authService.clientInfo(token));
    }


}
