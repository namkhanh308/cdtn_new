package com.cdtn.kltn.service;

import com.cdtn.kltn.dto.auth.request.AuthenticationRequest;
import com.cdtn.kltn.dto.auth.request.RegistrationDTO;
import com.cdtn.kltn.dto.auth.response.AuthenticationResponse;
import com.cdtn.kltn.dto.base.BaseResponseData;
import com.cdtn.kltn.entity.Client;
import com.cdtn.kltn.entity.User;
import com.cdtn.kltn.exception.StoreException;
import com.cdtn.kltn.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                request.getUsername(),
//                request.getPassword()));

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
    @Transactional
    public BaseResponseData registerClient(RegistrationDTO registrationDTO) {
        if (userRepository.findByUserName(registrationDTO.getUsername()).isPresent()) {
            return new BaseResponseData(500, "User đã tồn tại", null);
        }
        User userEntity = User.builder()
                .userName(registrationDTO.getUsername())
                .password(passwordEncoder.encode(registrationDTO.getPassword()))
                .role("ROLE_CLIENT")
                .statusAccount(1)
                .build();
        User user =  userRepository.save(userEntity);

        Client client = Client.builder().userId(user.getId()).isCheckAgency(1).build();

        return new BaseResponseData(200, "Đăng ký thành công", null);
    }

}
