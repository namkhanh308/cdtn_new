package com.cdtn.kltn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "news")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameNews;
    private String codeProperty;
    private String address;
    private LocalDateTime dateCreate;
    private LocalDateTime dateExpiration;
    private Integer statusNews;
    private LocalDateTime timeUpTopStart;
    private LocalDateTime timeUpTopEnd;
    private Long statusUpTop;
    private Long view;

    @Transient
    private String url;

    @Transient
    private String money;

    public News(String nameNews, String codeProperty, String address, LocalDateTime dateCreate, LocalDateTime dateExpiration, Integer statusNews, LocalDateTime timeUpTopStart, LocalDateTime timeUpTopEnd, Long statusUpTop, Long view, String url, String money) {
        this.nameNews = nameNews;
        this.codeProperty = codeProperty;
        this.address = address;
        this.dateCreate = dateCreate;
        this.dateExpiration = dateExpiration;
        this.statusNews = statusNews;
        this.timeUpTopStart = timeUpTopStart;
        this.timeUpTopEnd = timeUpTopEnd;
        this.statusUpTop = statusUpTop;
        this.view = view;
        this.url = url;
        this.money = money;
    }
}
