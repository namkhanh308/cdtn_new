package com.cdtn.kltn.service;

import com.cdtn.kltn.common.Enums;
import com.cdtn.kltn.dto.auth.request.AuthenticationRequest;
import com.cdtn.kltn.dto.auth.request.RegistrationDTO;
import com.cdtn.kltn.dto.auth.response.AuthenticationResponse;
import com.cdtn.kltn.dto.auth.response.UserInfo;
import com.cdtn.kltn.dto.base.BaseResponseData;
import com.cdtn.kltn.dto.client.respone.ClientInfoDTO;
import com.cdtn.kltn.entity.Client;
import com.cdtn.kltn.entity.User;
import com.cdtn.kltn.exception.StoreException;
import com.cdtn.kltn.repository.client.ClientRepository;
import com.cdtn.kltn.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.Utils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                request.getUsername(),
//                request.getPassword()));

        User user = userRepository.findByUserName(request.getEmail())
                .orElseThrow(() -> new StoreException("User not found by email: " + request.getEmail()));

        String role = userRepository.findUserRoleByUsername(user.getUsername());

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        user.setRole(role);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        String jwtToken = jwtService.createToken(authentication);
        String jwtRefreshToken = jwtService.refreshToken(authentication);

        Client client = clientRepository.findByUserId(user.getId())
                .orElseThrow(() -> new StoreException("Client is not contains userID: " + user.getId()));

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(jwtRefreshToken)
                .userInfo(UserInfo.builder().userId(user.getId()).codeClient(client.getCodeClient()).typeLoan(client.getTypeLoan()).lastName(user.getLastName()).build())
                .build();
    }
    @Transactional
    public BaseResponseData registerUser(RegistrationDTO registrationDTO) {
        if (userRepository.findByUserName(registrationDTO.getEmail()).isPresent()) {
            return new BaseResponseData(500, "User đã tồn tại", null);
        }
        User userEntity = User.builder()
                .userName(registrationDTO.getEmail())
                .password(passwordEncoder.encode(registrationDTO.getPassword()))
                .firstName(registrationDTO.getFirstName())
                .lastName(registrationDTO.getLastName())
                .role("ROLE_CLIENT")
                .statusAccount(Enums.Status.ACTIVE.getCode())
                .build();
        User user =  userRepository.save(userEntity);

        Client client = Client.builder().userId(user.getId()).typeLoan(Enums.LoanType.TENANT.getCode()).fullName(registrationDTO.getFirstName() + " " + registrationDTO.getLastName()).money("0").build();
        Client client1 = clientRepository.save(client);
        client1.setCodeClient("client." + client1.getId().toString());
        clientRepository.save(client1);
        return new BaseResponseData(200, "Đăng ký thành công", null);
    }

    @Transactional
    public BaseResponseData clientInfo(String token) {
        String email = jwtService.getUserNameByToken(token);
        Optional<User> user = userRepository.findByUserName(email);
        if (!user.isPresent()) {
            return new BaseResponseData(500, "User không tồn tại", null);
        }else {
            Optional<Client> client = clientRepository.findByUserId(user.get().getId());
            if(!client.isPresent()){
                return new BaseResponseData(500, "Client không tồn tại", null);
            }else {
                return new BaseResponseData(200,"Dữ liệu client được trả ra thành công",
                        ClientInfoDTO.builder()
                                .codeClient(client.get().getCodeClient())
                                .userId(client.get().getUserId())
                                .fullName(client.get().getFullName())
                                .provinceCode(client.get().getProvinceCode())
                                .districtCode(client.get().getDistrictCode())
                                .wardsCode(client.get().getWardsCode())
                                .introduces(client.get().getIntroduces())
                                .phone(client.get().getPhone())
                                .typeLoan(client.get().getTypeLoan())
                                .money(client.get().getMoney())
                                .passport(client.get().getPassport())
                                .firstName(user.get().getFirstName())
                                .lastName(user.get().getLastName()).build());
            }
        }
    }

}
