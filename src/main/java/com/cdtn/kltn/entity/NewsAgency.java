package com.cdtn.kltn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "newsagency")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsAgency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeAgency;
    private String codeProperty;
    private String content;
    private LocalDateTime dateCreate;
    private LocalDateTime dateExpiration;
    private Integer statusNews;

}
