package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.dto.news.request.CreateNewsDTO;
import com.cdtn.kltn.dto.news.request.ManagerNewsSearchDTO;
import com.cdtn.kltn.dto.news.request.PushTopDTO;
import com.cdtn.kltn.dto.news.respone.ManagerNewsSearchRespone;
import com.cdtn.kltn.entity.News;
import com.cdtn.kltn.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @PostMapping("/saveNews")
    public ResponseEntity<BaseResponseData> saveNews(@RequestBody CreateNewsDTO createNewsDTO) {
        try {
            newsService.saveNews(createNewsDTO);
            return ResponseEntity.ok(new BaseResponseData(200, "Cập nhật tin thành công", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @GetMapping("/deleteNews")
    public ResponseEntity<BaseResponseData> deleteNews(@RequestParam Long id) {
        try {
            newsService.deleteNews(id);
            return ResponseEntity.ok(new BaseResponseData(200, "Xóa tin thành công", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @GetMapping("/findNewsDetail")
    public ResponseEntity<BaseResponseData> findNewsDetail(@RequestParam Long id) {
        try {
            News news = newsService.findNewsDetail(id);
            return ResponseEntity.ok(new BaseResponseData(200, "Hiển thị thông tin chi tiết tin", news));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @GetMapping("/pushTopNews")
    public ResponseEntity<BaseResponseData> pushTopNews(@RequestBody @Valid PushTopDTO pushTopDTO) {
        try {
            newsService.pushTopNews(pushTopDTO);
            return ResponseEntity.ok(new BaseResponseData(200, "Đẩy top thành công", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @PostMapping("/manager/findAllNews")
    public ResponseEntity<BaseResponseData> findAllNewsManager(@RequestBody @Valid ManagerNewsSearchDTO managerNewsSearchDTO
            , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = "Bad Request: " + bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponseData(400, errorMessage, null));
        }
        try {
            Page<ManagerNewsSearchRespone> list =  newsService.findAllNewsManager(managerNewsSearchDTO);
            return ResponseEntity.ok(new BaseResponseData(200, "Hiển thị danh sách tin thành công", list));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }


}
