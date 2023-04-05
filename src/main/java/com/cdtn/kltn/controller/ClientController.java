package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.auth.request.RegistrationDTO;
import com.cdtn.kltn.dto.base.BaseResponseData;
import com.cdtn.kltn.dto.client.RegistrationClientDTO;
import com.cdtn.kltn.entity.Client;
import com.cdtn.kltn.service.AuthService;
import com.cdtn.kltn.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/registerClient")
    public BaseResponseData register(@RequestBody @Valid RegistrationClientDTO request) {
        return clientService.registerClient(request);
    }
    @PostMapping("/test")
    public String login() {
        return "hello";
    }

}
