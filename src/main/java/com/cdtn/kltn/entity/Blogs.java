package com.cdtn.kltn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "blogs")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Integer lever;

    private String url;

    private LocalDateTime dateCreate;
}
