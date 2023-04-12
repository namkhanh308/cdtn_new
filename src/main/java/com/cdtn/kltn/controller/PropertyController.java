package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.base.request.BaseRequestPagination;
import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.dto.property.request.CreatePropertyDTO;
import com.cdtn.kltn.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/property")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping("/loan/saveProperty")
    public ResponseEntity<BaseResponseData> saveProperty(@RequestBody @Valid CreatePropertyDTO propertyDTO) {
        return ResponseEntity.ok(propertyService.createProperty(propertyDTO));
    }

//    @PostMapping("/loan/findAllProperty")
//    public ResponseEntity<BaseResponseData> findAllPropertyLoan(@RequestBody @Valid BaseRequestPagination brp) {
//        return ResponseEntity.ok(propertyService.createProperty(propertyDTO));
//    }
}
