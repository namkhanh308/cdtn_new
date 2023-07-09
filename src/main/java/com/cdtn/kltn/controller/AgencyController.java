package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.dto.client.request.RegistrationClientDTO;
import com.cdtn.kltn.dto.client.respone.HomeClientResponse;
import com.cdtn.kltn.entity.Agency;
import com.cdtn.kltn.repository.Agency.AgencyRepository;
import com.cdtn.kltn.service.AgencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/agency")
@RequiredArgsConstructor
public class AgencyController {

    private final AgencyService agencyService;

    @PostMapping("/saveAgency")
    public ResponseEntity<BaseResponseData> saveClient(@RequestBody Agency agency) {
        try{
            agencyService.saveAgency(agency);
            return ResponseEntity.ok(new BaseResponseData(200, "Success", "Đăng ký môi giới thành công"));
        }catch (Exception e){
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @GetMapping("/findAllAgency")
    public ResponseEntity<BaseResponseData> findAllAgency(@RequestParam String nameSearch, @RequestParam String provinceCode, int page, int size) {
        try {
            Page<?> agencyList  = agencyService.findAllAgency(nameSearch, provinceCode, page, size);
            return ResponseEntity.ok(new BaseResponseData(200, "Hiển thị danh sách môi giới thành công", agencyList));
        } catch (Exception e){
            return ResponseEntity.ok(new BaseResponseData(500, "Hiển thị danh sách môi giới thất bại", null));
        }
    }

    @DeleteMapping("/deleteAgency")
    public ResponseEntity<BaseResponseData> findAllAgency(@RequestParam Long id) {
        try {
            agencyService.deleteAgency(id);
            return ResponseEntity.ok(new BaseResponseData(200, "Xóa môi giới thành công", null));
        } catch (Exception e){
            return ResponseEntity.ok(new BaseResponseData(500, "Xóa môi giới thất bại", null));
        }
    }
}
