package com.cdtn.kltn.service;

import com.cdtn.kltn.dto.auth.request.AuthenticationRequest;
import com.cdtn.kltn.dto.auth.response.AuthenticationResponse;
import com.cdtn.kltn.entity.User;
import com.cdtn.kltn.exception.StoreException;
import com.cdtn.kltn.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()));

        User user = userRepository.findByUserName(request.getUsername())
                .orElseThrow(() -> new StoreException("User not found by email: " + request.getUsername()));

        String role = userRepository.findUserRoleByUsername(user.getUsername());

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        user.setRole(role);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String jwtToken = jwtService.createToken(authentication);
        String jwtRefreshToken = jwtService.refreshToken(authentication);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(jwtRefreshToken)
                .userId(user.getId())
                .build();
    }
}
