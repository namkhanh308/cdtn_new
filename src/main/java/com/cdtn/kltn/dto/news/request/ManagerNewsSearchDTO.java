package com.cdtn.kltn.dto.news.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ManagerNewsSearchDTO {
    private String nameNews;
    private LocalDateTime dateCreate;
    private LocalDateTime dateExpiration;
    private Integer statusNews;
    private String codeTypeProperty;
    private String codeCateTypePropertyCategory;
    private String codeClient;
    @NotNull
    private Integer page;
    @NotNull
    private Integer records;
    private String sort;
    private String order;
}
