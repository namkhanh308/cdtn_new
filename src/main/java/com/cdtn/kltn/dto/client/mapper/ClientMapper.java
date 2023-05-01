package com.cdtn.kltn.dto.client.mapper;

import com.cdtn.kltn.dto.client.request.RegistrationClientDTO;
import com.cdtn.kltn.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    public Client updateClient(Client client,RegistrationClientDTO registrationClientDTO){
        client.setProvinceCode(registrationClientDTO.getProvinceCode());
        client.setDistrictCode(registrationClientDTO.getDistrictCode());
        client.setWardsCode(registrationClientDTO.getWardsCode());
        client.setIntroduces(registrationClientDTO.getIntroduces());
        client.setPhone(registrationClientDTO.getPhone());
        client.setTypeLoan(registrationClientDTO.getTypeLoan());
        client.setPassport(registrationClientDTO.getPassport());
        client.setFullName(registrationClientDTO.getFirstName() + " " + registrationClientDTO.getLastName());
        return client;
    }
}
