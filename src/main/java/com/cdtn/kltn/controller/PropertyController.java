package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.pagination.PagingResponeDTO;
import com.cdtn.kltn.dto.property.request.PropertySearchDTO;
import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.dto.property.request.CreatePropertyDTO;
import com.cdtn.kltn.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/property")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping("/loan/saveProperty")
    public ResponseEntity<BaseResponseData> saveProperty(@RequestBody @Valid CreatePropertyDTO propertyDTO) {
        try{
            propertyService.createProperty(propertyDTO);
            return ResponseEntity.ok(new BaseResponseData(200, "Success", null));
        }catch (Exception e){
            return ResponseEntity.ok(new BaseResponseData(500,e.getMessage(),null));
        }
    }

    @DeleteMapping("/loan/deleteProperty")
    public ResponseEntity<BaseResponseData> deleteProperty(@RequestParam String codeProperty) {
        try {
            propertyService.deleteProperty(codeProperty);
            return ResponseEntity.ok(new BaseResponseData(200, "Xóa tài sản thành công", null));
        }catch (Exception e){
            return ResponseEntity.ok(new BaseResponseData(500,e.getMessage(),null));
        }
    }

    @GetMapping("/loan/findAllProperty")
    public ResponseEntity<BaseResponseData> findAllPropertyManager(@ModelAttribute PropertySearchDTO brp, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = "Bad Request: " + bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponseData(400, errorMessage, null));
        }
        try {
            PagingResponeDTO pagingResponeDTO = propertyService.findAllPropertyManager(brp);
            return ResponseEntity.ok(new BaseResponseData(200, "Danh sách tài sản hiển thị thành công", pagingResponeDTO));
        }catch (Exception e){
            return ResponseEntity.ok(new BaseResponseData(500,e.getMessage(),null));
        }
    }

    @GetMapping("/loan/findPropertyDetail")
    public ResponseEntity<BaseResponseData> findPropertyDetail(@RequestParam String codeProperty) {
        try{
            CreatePropertyDTO createPropertyDTO = propertyService.findPropertyDetail(codeProperty);
            return ResponseEntity.ok(new BaseResponseData(200, "Chi tiết tài sản hiển thị thành công", createPropertyDTO));
        }catch (Exception e){
            return ResponseEntity.ok(new BaseResponseData(500,e.getMessage(),null));
        }
    }
}
