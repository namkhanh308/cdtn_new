package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.dto.news.request.CreateNewsDTO;
import com.cdtn.kltn.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @PostMapping("/saveNews")
    public ResponseEntity<BaseResponseData> saveNews(@RequestBody CreateNewsDTO createNewsDTO){
        return ResponseEntity.ok(newsService.saveNews(createNewsDTO));
    }


    @GetMapping("/deleteNews")
    public ResponseEntity<BaseResponseData> deleteNews(@RequestParam Long id){
        return ResponseEntity.ok(newsService.deleteNews(id));
    }

    @GetMapping("/loan/findNewsDetail")
    public ResponseEntity<BaseResponseData> findNewsDetail(@RequestParam Long id) {
        return ResponseEntity.ok(newsService.findNewsDetail(id));
    }


}
