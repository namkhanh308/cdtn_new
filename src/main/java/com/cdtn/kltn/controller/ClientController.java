package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.dto.client.request.RechargeDTO;
import com.cdtn.kltn.dto.client.request.RegistrationClientDTO;
import com.cdtn.kltn.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/saveClient")
    public ResponseEntity<BaseResponseData> saveClient(@RequestBody @Valid RegistrationClientDTO request) {
        return ResponseEntity.ok(clientService.saveClient(request));
    }
    @GetMapping("/findByCodeClient")
    public ResponseEntity<BaseResponseData> findByCodeClient(@RequestParam String codeClient) {
        return ResponseEntity.ok(clientService.findByCodeClient(codeClient));
    }

    @PostMapping("/recharge")
    public ResponseEntity<BaseResponseData> rechargeClient(@RequestBody RechargeDTO rechargeDTO) {
        return ResponseEntity.ok(clientService.rechargeClient(rechargeDTO));
    }

}
