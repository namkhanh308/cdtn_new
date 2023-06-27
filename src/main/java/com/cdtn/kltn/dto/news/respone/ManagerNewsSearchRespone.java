package com.cdtn.kltn.dto.news.respone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerNewsSearchRespone {
    private Long id;
    private String nameNews;
    private LocalDateTime dateCreate;
    private LocalDateTime dateExpiration;
    private String statusNews;
    private String url;
    private String address;
    private LocalDateTime timeUpTopStart;
    private LocalDateTime timeUpTopEnd;
    private Long statusUpTop;
}
