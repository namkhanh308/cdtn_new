package com.cdtn.kltn.dto.news.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CreateNewsDTO {
    private Long id;
    private String nameNews;
    private String codeProperty;
    private LocalDateTime dateCreate;
    private LocalDateTime dateExpiration;

}
