package com.cdtn.kltn.service;

import com.cdtn.kltn.dto.base.BaseResponseData;
import com.cdtn.kltn.dto.client.request.RechargeDTO;
import com.cdtn.kltn.dto.client.request.RegistrationClientDTO;
import com.cdtn.kltn.entity.Client;
import com.cdtn.kltn.entity.Image;
import com.cdtn.kltn.entity.User;
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


    @Transactional
    public BaseResponseData saveClient(RegistrationClientDTO registrationClientDTO) {
        Optional<Client> client = clientRepository.findByCodeClient(registrationClientDTO.getCodeClient());
        if (!client.isPresent()) {
            return new BaseResponseData(500, "Client không tồn tại", null);
        } else {
            Optional<User> user = userRepository.findById(client.get().getUserId());
            if (!user.isPresent()) {
                return new BaseResponseData(500, "User không tồn tại", null);
            } else {
                Optional<Image> image = imageRepository.findByCodeClient(client.get().getCodeClient());
                //Set lại thông tin image
                if (!image.isPresent()) {
                    imageRepository.save(Image.builder().codeClient(client.get()
                            .getCodeClient())
                            .codeImage("IMAGE_" + imageService.getIndex()+1)
                            .url(registrationClientDTO.getUrl()).level(1)
                            .build());
                } else {
                    image.get().setUrl(registrationClientDTO.getUrl());
                    imageRepository.save(image.get());
                }
                //Set lại thông tin client
                client.get().setProvinceCode(registrationClientDTO.getProvinceCode());
                client.get().setDistrictCode(registrationClientDTO.getDistrictCode());
                client.get().setWardsCode(registrationClientDTO.getWardsCode());
                client.get().setIntroduces(registrationClientDTO.getIntroduces());
                client.get().setPhone(registrationClientDTO.getPhone());
                client.get().setTypeLoan(registrationClientDTO.getTypeLoan());
                client.get().setPassport(registrationClientDTO.getPassport());
                client.get().setFullName(registrationClientDTO.getFirstName() + " " + registrationClientDTO.getLastName());
                //Set lại thông tin user
                user.get().setFirstName(registrationClientDTO.getFirstName());
                user.get().setLastName(registrationClientDTO.getLastName());
                //Save
                clientRepository.save(client.get());
                userRepository.save(user.get());

                return new BaseResponseData(200, "Đăng ký thành công", null);
            }
        }


    }

    @Transactional
    public BaseResponseData findByCodeClient(String codeClient) {
        Optional<Client> client = clientRepository.findByCodeClient(codeClient);
        return client.isPresent() ? new BaseResponseData(200, "Hiển thị thông tin khách hàng thành công", client) : new BaseResponseData(500, "Hiển thị thông tin khách hàng thất bại", null);
    }

    @Transactional
    public BaseResponseData RechargeClient(RechargeDTO rechargeDTO) {
        Optional<Client> client = clientRepository.findByCodeClient(rechargeDTO.getCodeClient());
        if (!client.isPresent()) {
            return new BaseResponseData(500, "Client không tồn tại", null);
        } else {
            client.get().setMoney(String.valueOf(Long.parseLong(client.get().getMoney()) + rechargeDTO.getMoney()));
            clientRepository.save(client.get());
            return new BaseResponseData(200, "Nạp thẻ thành công", client);
        }
    }

}
