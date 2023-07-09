package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.entity.Agency;
import com.cdtn.kltn.entity.Banner;
import com.cdtn.kltn.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/banner")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    @PostMapping("/addBanner")
    public ResponseEntity<BaseResponseData> addBanner(@RequestBody List<Banner> list) {
        try{
            bannerService.addBanner(list);
            return ResponseEntity.ok(new BaseResponseData(200, "Success", "Tạo banner thành công"));
        }catch (Exception e){
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @PostMapping("/updateBanner")
    public ResponseEntity<BaseResponseData> updateBanner(@RequestBody List<Banner> list) {
        try{
            bannerService.updateBanner(list);
            return ResponseEntity.ok(new BaseResponseData(200, "Success", "Sửa banner thành công"));
        }catch (Exception e){
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @DeleteMapping("/deleteBanner")
    public ResponseEntity<BaseResponseData> deleteBanner(@RequestParam Long id) {
        try{
            bannerService.deleteBanner(id);
            return ResponseEntity.ok(new BaseResponseData(200, "Success", "Xóa banner thành công"));
        }catch (Exception e){
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }
}
