package com.cdtn.kltn.service;

import com.cdtn.kltn.dto.auth.request.AuthenticationRequest;
import com.cdtn.kltn.dto.auth.request.RegistrationDTO;
import com.cdtn.kltn.dto.auth.response.AuthenticationResponse;
import com.cdtn.kltn.dto.base.BaseResponseData;
import com.cdtn.kltn.entity.Client;
import com.cdtn.kltn.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

//    private final

//    @Transactional
//    public BaseResponseData registerClient(RegistrationDTO registrationDTO) {
//        if (userRepository.findByUserName(registrationDTO.getUsername()).isPresent()) {
//            return new BaseResponseData(500, "User đã tồn tại", null);
//        }
//        User userEntity = User.builder()
//                .userName(registrationDTO.getUsername())
//                .password(passwordEncoder.encode(registrationDTO.getPassword()))
//                .role("ROLE_CLIENT")
//                .statusAccount(1)
//                .build();
//        User user =  userRepository.save(userEntity);
//
//        Client client = Client.builder().userId(user.getId()).isCheckAgency(1).build();
//
//        return new BaseResponseData(200, "Đăng ký thành công", null);
//    }
}
