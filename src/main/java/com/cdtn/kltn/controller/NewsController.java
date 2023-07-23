package com.cdtn.kltn.controller;

import com.cdtn.kltn.dto.base.response.BaseResponseData;
import com.cdtn.kltn.dto.news.request.*;
import com.cdtn.kltn.dto.news.respone.CustomerNewsDetailResponse;
import com.cdtn.kltn.dto.news.respone.CustomerNewsResponse;
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

    @PostMapping("/pushTopNews")
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
            Page<ManagerNewsSearchRespone> list = newsService.findAllNewsManager(managerNewsSearchDTO);
            return ResponseEntity.ok(new BaseResponseData(200, "Hiển thị danh sách tin thành công", list));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @PostMapping("/customer/findAllNews")
    public ResponseEntity<BaseResponseData> findAllNewsManager(@RequestBody @Valid CustomerNewsSearchDTO customerNewsSearchDTO
            , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = "Bad Request: " + bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponseData(400, errorMessage, null));
        }
        try {
            Page<?> list = newsService.findAllNewsCustomer(customerNewsSearchDTO);
            return ResponseEntity.ok(new BaseResponseData(200, "Hiển thị danh sách tin thành công", list));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @GetMapping("/customer/findNewsDetail")
    public ResponseEntity<BaseResponseData> findNewsDetailCustomer(@RequestParam Long id) {
        try {
            CustomerNewsDetailResponse newsDetailCustomer = newsService.findNewsDetailCustomer(id);
            return ResponseEntity.ok(new BaseResponseData(200, "Hiển thị thông tin chi tiết tin", newsDetailCustomer));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @GetMapping("/customer/findNewsSame")
    public ResponseEntity<BaseResponseData> findNewsSame(@RequestParam String codeTypeProperty,
                                                         @RequestParam String codeCateTypePropertyCategory,
                                                         @RequestParam String provinceCode,
                                                         @RequestParam Long idCurrent
    ) {
        try {
            List<CustomerNewsResponse> sameNews = newsService.findNewSame(codeTypeProperty, codeCateTypePropertyCategory, provinceCode, idCurrent);
            return ResponseEntity.ok(new BaseResponseData(200, "Hiển thị thông tin chi tiết tin", sameNews));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @GetMapping("/customer/findNewByCodeCate")
    public ResponseEntity<BaseResponseData> findNewByCodeCate() {
        try {
            List<?> news = newsService.findNewByCodeCate();
            return ResponseEntity.ok(new BaseResponseData(200, "Hiển thị danh sách tin theo codeCate", news));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @GetMapping("/customer/plusViewForNews")
    public ResponseEntity<BaseResponseData> plusViewForNews(@RequestParam Long id) {
        try {
            newsService.plusViewForNews(id);
            return ResponseEntity.ok(new BaseResponseData(200, "Success", null));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @GetMapping("/customer/outstandingProject")
    public ResponseEntity<BaseResponseData> outstandingProject() {
        try {
            List<?> news = newsService.outstandingProject();
            return ResponseEntity.ok(new BaseResponseData(200, "Danh sách dự án nổi bật hiển thị thành công", news));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @GetMapping("/statisticsByPrice")
    public ResponseEntity<BaseResponseData> statisticsByPrice(@RequestParam String provinceCode,
                                                              @RequestParam Long month,
                                                              @RequestParam Long year,
                                                              @RequestParam String codeCategoryTypeProperty) {
        try {
            List<?> news = newsService.statisticsByPrice(provinceCode,month,year,codeCategoryTypeProperty);
            return ResponseEntity.ok(new BaseResponseData(200, "Thống kê theo giá hiển thị thành công", news));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @GetMapping("/statisticsByDistrict")
    public ResponseEntity<BaseResponseData> StatisticsByDistrict(@RequestParam String provinceCode,
                                                              @RequestParam Long month,
                                                              @RequestParam Long year,
                                                              @RequestParam String codeCategoryTypeProperty) {
        try {
            List<?> news = newsService.statisticsByDistrict(provinceCode,month,year,codeCategoryTypeProperty);
            return ResponseEntity.ok(new BaseResponseData(200, "Thống kê theo quận/ huyện hiển thị thành công", news));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

    @GetMapping("/favouriteNews")
    public ResponseEntity<BaseResponseData> findByFavoriteNews(@RequestBody FavouriteNewsDTO favouriteNewsDTO) {
        try {
            Page<?> news = newsService.findByFavoriteNews(favouriteNewsDTO);
            return ResponseEntity.ok(new BaseResponseData(200, "Danh sách tin yêu thích", news));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponseData(500, e.getMessage(), null));
        }
    }

}
