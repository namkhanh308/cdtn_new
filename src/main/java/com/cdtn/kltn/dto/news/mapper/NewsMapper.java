package com.cdtn.kltn.dto.news.mapper;

import com.cdtn.kltn.common.Enums;
import com.cdtn.kltn.dto.news.request.CreateNewsDTO;
import com.cdtn.kltn.dto.news.request.PushTopDTO;
import com.cdtn.kltn.entity.News;
import org.springframework.stereotype.Component;

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
                    .statusUpTop(Enums.StatusUpTop.HETHAN.getCode())
                    .build();
    }
    /*
    public CreateNewsDTO createNewsDetailRespones(News news){
        return CreateNewsDTO.builder()
                .codeProperty(news.getCodeProperty())
                .dateCreate(news.getDateCreate())
                .dateExpiration(news.getDateExpiration())
                .id(news.getId())
                .nameNews(news.getNameNews())
                .build();
    }
     */

    public News pushTopNews(PushTopDTO pushTopDTO, News news){
        news.setTimeUpTopStart(pushTopDTO.getTimeUpTopStart());
        news.setTimeUpTopEnd(pushTopDTO.getTimeUpTopEnd());
        news.setStatusUpTop(Enums.StatusUpTop.DANGHOATDONG.getCode());
        return news;
    }
}
