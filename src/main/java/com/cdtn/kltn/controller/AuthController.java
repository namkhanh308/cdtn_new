package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.auth.request.AuthenticationRequest;
import com.cdtn.kltn.dto.auth.request.RegistrationDTO;
import com.cdtn.kltn.dto.auth.response.AuthenticationResponse;
import com.cdtn.kltn.dto.base.BaseResponseData;
import com.cdtn.kltn.entity.User;
import com.cdtn.kltn.security.JwtFilter;
import com.cdtn.kltn.service.AuthService;
import com.cdtn.kltn.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
    @PostMapping("/registerAccount")
    public BaseResponseData login(@RequestBody @Valid RegistrationDTO request) {
        return authService.registerUser(request);
    }

    @GetMapping("/infoClient")
    public BaseResponseData ShowClientInfo (HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = authorizationHeader.replaceAll("Bearer", "").trim();
        return authService.clientInfo(token);
    }
   
}
