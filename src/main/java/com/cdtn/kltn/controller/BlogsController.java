package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.entity.Blogs;
import com.cdtn.kltn.service.BlogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class BlogsController {
    private final BlogsService blogsService;

    @PostMapping("/addBlogs")
    public ResponseEntity<BaseResponseData> addBanner(@RequestBody Blogs blogs) {
        try {
            blogsService.addBlogs(blogs);
            return ResponseEntity.ok(new BaseResponseData(200, "Success", "Tạo blogs thành công"));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @DeleteMapping("/deleteBlogs")
    public ResponseEntity<BaseResponseData> deleteBanner(@RequestParam Long id) {
        try {
            blogsService.deleteBlogs(id);
            return ResponseEntity.ok(new BaseResponseData(200, "Success", "Xóa blogs thành công"));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @GetMapping("/getAllBlogs")
    public ResponseEntity<BaseResponseData> getAllBlogs(@RequestParam String searchName, int page, int size) {
        try {
            Page<?> blogs = blogsService.getPage(searchName, page, size);
            return ResponseEntity.ok(new BaseResponseData(200, "Success", blogs));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @GetMapping("/getDetails")
    public ResponseEntity<BaseResponseData> getDetailBlogs(@RequestParam Long id) {
        try {
            Blogs blog = blogsService.detailBlogs(id);
            return ResponseEntity.ok(new BaseResponseData(200, "Success", blog));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @GetMapping("/getList")
    public ResponseEntity<BaseResponseData> getList() {
        try {
            List<Blogs> blogsList = blogsService.getList();
            return ResponseEntity.ok(new BaseResponseData(200, "Success", blogsList));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

}
