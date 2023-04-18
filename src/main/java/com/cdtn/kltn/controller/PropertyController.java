package com.cdtn.kltn.controller;

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
        return ResponseEntity.ok(propertyService.createProperty(propertyDTO));
    }

    @DeleteMapping("/loan/deleteProperty")
    public ResponseEntity<BaseResponseData> deleteProperty(@RequestParam String codeProperty) {
        return ResponseEntity.ok(propertyService.deleteProperty(codeProperty));
    }

    @GetMapping("/loan/findAllProperty")
    public ResponseEntity<BaseResponseData> findAllPropertyManager(@ModelAttribute PropertySearchDTO brp, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = "Bad Request: " + bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponseData(400, errorMessage, null));
        }
        return ResponseEntity.ok(propertyService.findAllPropertyManager(brp));
    }

    @GetMapping("/loan/findPropertyDetail")
    public ResponseEntity<BaseResponseData> findPropertyDetail(@RequestParam String codeProperty) {
        return ResponseEntity.ok(propertyService.findPropertyDetail(codeProperty));
    }
}
