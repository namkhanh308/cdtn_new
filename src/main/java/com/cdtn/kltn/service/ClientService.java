package com.cdtn.kltn.service;

import com.cdtn.kltn.dto.auth.request.AuthenticationRequest;
import com.cdtn.kltn.dto.auth.request.RegistrationDTO;
import com.cdtn.kltn.dto.auth.response.AuthenticationResponse;
import com.cdtn.kltn.dto.base.BaseResponseData;
import com.cdtn.kltn.dto.client.RegistrationClientDTO;
import com.cdtn.kltn.entity.Client;
import com.cdtn.kltn.entity.User;
import com.cdtn.kltn.repository.client.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;


    @Transactional
    public BaseResponseData registerClient(RegistrationClientDTO registrationClientDTO) {
        Optional<Client> client = clientRepository.findByCodeClient(registrationClientDTO.getCodeClient());
        if (!client.isPresent()) {
            return new BaseResponseData(500, "Client không tồn tại", null);
        }
        client.get().setFullName(registrationClientDTO.getFullName());
        client.get().setAge(registrationClientDTO.getAge());
        client.get().setProvinceCode(registrationClientDTO.getProvinceCode());
        client.get().setDistrictCode(registrationClientDTO.getDistrictCode());
        client.get().setWardsCode(registrationClientDTO.getWardsCode());
        client.get().setIntroduces(registrationClientDTO.getIntroduces());
        client.get().setPhone(registrationClientDTO.getPhone());
        client.get().setTypeLoan(registrationClientDTO.getTypeLoan());
        client.get().setMoney(registrationClientDTO.getMoney());
        client.get().setPassport(registrationClientDTO.getPassport());

        clientRepository.save(client.get());
        return new BaseResponseData(200, "Đăng ký thành công", null);
    }
}
