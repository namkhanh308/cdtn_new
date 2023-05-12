package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.dto.client.request.RechargeDTO;
import com.cdtn.kltn.dto.client.request.RegistrationClientDTO;
import com.cdtn.kltn.dto.client.respone.HomeClientResponse;
import com.cdtn.kltn.entity.Client;
import com.cdtn.kltn.repository.client.CustomClientRepositoryImpl;
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

    private final CustomClientRepositoryImpl customClientRepositoryImpl;

    @PostMapping("/saveClient")
    public ResponseEntity<BaseResponseData> saveClient(@RequestBody @Valid RegistrationClientDTO request) {
        try{
            clientService.saveClient(request);
            return ResponseEntity.ok(new BaseResponseData(200, "Success", "Đăng ký thành công"));
        }catch (Exception e){
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }
    @GetMapping("/findByCodeClient")
    public ResponseEntity<BaseResponseData> findByCodeClient(@RequestParam String codeClient) {
        try {
            Client client = clientService.findByCodeClient(codeClient);
            return ResponseEntity.ok(new BaseResponseData(200, "Hiển thị thông tin khách hàng thành công", client));
        }catch (Exception e){
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @PostMapping("/recharge")
    public ResponseEntity<BaseResponseData> rechargeClient(@RequestBody RechargeDTO rechargeDTO) {
        try {
            Client client = clientService.rechargeClient(rechargeDTO);
            return ResponseEntity.ok(new BaseResponseData(200, "Nạp thẻ thành công", client));
        }catch (Exception e){
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @GetMapping("/homeClient")
    public ResponseEntity<BaseResponseData> rechargeClient(@RequestParam String codeClient) {
        try {
            HomeClientResponse homeClientResponse = customClientRepositoryImpl.homeClient(codeClient);
            return ResponseEntity.ok(new BaseResponseData(200, "Hiển thị thông tin trang chủ thành công", homeClientResponse));
        } catch (Exception e){
            return ResponseEntity.ok(new BaseResponseData(500, "Hiển thị thông tin thất bại", null));
        }
    }

}
