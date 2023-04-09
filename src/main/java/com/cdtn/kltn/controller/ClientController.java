package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.base.BaseResponseData;
import com.cdtn.kltn.dto.client.request.RechargeDTO;
import com.cdtn.kltn.dto.client.request.RegistrationClientDTO;
import com.cdtn.kltn.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/saveClient")
    public BaseResponseData saveClient(@RequestBody @Valid RegistrationClientDTO request) {
        return clientService.saveClient(request);
    }
    @GetMapping("/findByCodeClient")
    public BaseResponseData findByCodeClient(@RequestParam String codeClient) {
        return clientService.findByCodeClient(codeClient);
    }

    @PostMapping("/recharge")
    public BaseResponseData RechargeClient(@RequestBody RechargeDTO rechargeDTO) {
        return clientService.RechargeClient(rechargeDTO);
    }

}
