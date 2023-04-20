package com.cdtn.kltn.dto.news.mapper;

import com.cdtn.kltn.common.Enums;
import com.cdtn.kltn.dto.news.request.CreateNewsDTO;
import com.cdtn.kltn.entity.News;
import com.cdtn.kltn.entity.Property;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NewsMapper {

    public News createNews(CreateNewsDTO createNewsDTO, String address){
        return News.builder()
                    .nameNews(createNewsDTO.getNameNews())
                    .dateCreate(createNewsDTO.getDateCreate())
                    .dateExpiration(createNewsDTO.getDateExpiration())
                    .codeProperty(createNewsDTO.getCodeProperty())
                    .statusNews(Enums.StatusNews.DANGHOATDONG.getCode())
                    .address(address)
                    .build();
    }

    public CreateNewsDTO createNewsDetailRespones(News news){
        return CreateNewsDTO.builder()
                .codeProperty(news.getCodeProperty())
                .dateCreate(news.getDateCreate())
                .dateExpiration(news.getDateExpiration())
                .id(news.getId())
                .nameNews(news.getNameNews())
                .build();
    }
}
