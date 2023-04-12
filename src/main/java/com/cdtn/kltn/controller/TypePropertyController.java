package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.base.BaseResponseData;
import com.cdtn.kltn.entity.TypeProperty;
import com.cdtn.kltn.service.TypePropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/typepropery")
@RequiredArgsConstructor
public class TypePropertyController {
    private final TypePropertyService typePropertyService;

    @RequestMapping("/findAllByCodeTypePropertyCategorySearch")
    public ResponseEntity<BaseResponseData> findAllByCodeTypePropertyCategorySearch(@RequestParam Long codeTypePropertyCategory){
        return ResponseEntity.ok(typePropertyService.findAllByCodeCateTypePropertyCategory(codeTypePropertyCategory));
    }
    @RequestMapping("/findAllByCodeTypePropertyCategory")
    public ResponseEntity<BaseResponseData> findAllByCodeTypePropertyCategory(@RequestParam Long codeTypePropertyCategory){
        return ResponseEntity.ok(typePropertyService.findAllByCodeTypePropertyCategory(codeTypePropertyCategory));
    }

}
