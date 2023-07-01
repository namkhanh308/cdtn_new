package com.cdtn.kltn.service;

import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.dto.client.mapper.ClientMapper;
import com.cdtn.kltn.dto.client.request.RechargeDTO;
import com.cdtn.kltn.dto.client.request.RegistrationClientDTO;
import com.cdtn.kltn.entity.Client;
import com.cdtn.kltn.entity.Image;
import com.cdtn.kltn.entity.User;
import com.cdtn.kltn.exception.StoreException;
import com.cdtn.kltn.repository.client.ClientRepository;
import com.cdtn.kltn.repository.image.ImageRepository;
import com.cdtn.kltn.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    private final ImageRepository imageRepository;

    private final UserRepository userRepository;

    private final ImageService imageService;

    private final ClientMapper clientMapper;


    @Transactional
    public void saveClient(RegistrationClientDTO registrationClientDTO) {
        Client client = clientRepository.findByCodeClient(registrationClientDTO.getCodeClient())
                .orElseThrow(() -> new StoreException("Client không tồn tại"));
        User user = userRepository.findById(client.getUserId())
                .orElseThrow(() -> new StoreException("User không tồn tại"));
        Optional<Image> image = imageRepository.findByCodeClient(client.getCodeClient());
        //Set lại thông tin image
        if (image.isEmpty()) {
            imageRepository.save(Image.builder().codeClient(client
                    .getCodeClient())
                    .codeImage("IMAGE_" + imageService.getIndex() + 1)
                    .url(registrationClientDTO.getUrl()).level(1)
                    .build());
        } else {
            image.get().setUrl(registrationClientDTO.getUrl());
            imageRepository.save(image.get());
        }
        //Set lại thông tin client
        client = clientMapper.updateClient(client,registrationClientDTO);
        //Set lại thông tin user
        user.setFirstName(registrationClientDTO.getFirstName());
        user.setLastName(registrationClientDTO.getLastName());
        //Save
        clientRepository.save(client);
        userRepository.save(user);
    }

    @Transactional
    public Client findByCodeClient(String codeClient) {
        return clientRepository.findByCodeClient(codeClient)
                .orElseThrow(()-> new StoreException("Hiển thị thông tin khách hàng thất bại"));
    }

    @Transactional
    public Client rechargeClient(RechargeDTO rechargeDTO) {
        Client client = clientRepository.findByCodeClient(rechargeDTO.getCodeClient())
                .orElseThrow(() -> new StoreException("Client không tồn tại"));
        client.setMoney(String.valueOf(Long.parseLong(client.getMoney()) + rechargeDTO.getMoney()));
        clientRepository.save(client);
        return client;
    }

    public void savePhone(String codeClient, String phone) {
        Client client = clientRepository.findByCodeClient(codeClient)
                .orElseThrow(() -> new StoreException("Client không tồn tại"));
        client.setPhone(phone);
        clientRepository.save(client);
    }

}
