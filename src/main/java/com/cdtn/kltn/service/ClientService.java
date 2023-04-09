package com.cdtn.kltn.service;

import com.cdtn.kltn.dto.base.BaseResponseData;
import com.cdtn.kltn.dto.client.request.RechargeDTO;
import com.cdtn.kltn.dto.client.request.RegistrationClientDTO;
import com.cdtn.kltn.entity.Client;
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

        if(client.get().getMoney() == null){
            client.get().setMoney("0");
        }
        client.get().setFullName(registrationClientDTO.getFullName());
        client.get().setAge(registrationClientDTO.getAge());
        client.get().setProvinceCode(registrationClientDTO.getProvinceCode());
        client.get().setDistrictCode(registrationClientDTO.getDistrictCode());
        client.get().setWardsCode(registrationClientDTO.getWardsCode());
        client.get().setIntroduces(registrationClientDTO.getIntroduces());
        client.get().setPhone(registrationClientDTO.getPhone());
        client.get().setTypeLoan(registrationClientDTO.getTypeLoan());
        client.get().setPassport(registrationClientDTO.getPassport());

        clientRepository.save(client.get());
        return new BaseResponseData(200, "Đăng ký thành công", null);
    }

    @Transactional
    public BaseResponseData findByCodeClient(String codeClient){
       Optional<Client> client = clientRepository.findByCodeClient(codeClient);
       return client.isPresent() ? new BaseResponseData(200, "Hiển thị thông tin khách hàng thành công", client) : new BaseResponseData(500, "Hiển thị thông tin khách hàng thất bại", null);
    }

    @Transactional
    public BaseResponseData RechargeClient(RechargeDTO rechargeDTO){
        Optional<Client> client = clientRepository.findByCodeClient(rechargeDTO.getCodeClient());
        if (!client.isPresent()) {
            return new BaseResponseData(500, "Client không tồn tại", null);
        }else {
            client.get().setMoney(String.valueOf(Long.parseLong(client.get().getMoney()) + rechargeDTO.getMoney()));
            clientRepository.save(client.get());
            return new BaseResponseData(200,"Nạp thẻ thành công",client);
        }
    }

}
