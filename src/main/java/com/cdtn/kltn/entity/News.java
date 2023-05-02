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

}
