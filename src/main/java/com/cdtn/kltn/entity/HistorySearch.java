package com.cdtn.kltn.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "historysearch")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorySearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codeClient;
    private String content;
}
