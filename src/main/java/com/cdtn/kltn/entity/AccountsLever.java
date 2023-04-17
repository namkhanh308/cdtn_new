package com.cdtn.kltn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "accounts_lever")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountsLever {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codeClient;
    private Integer accountTypeLever;
    private Integer countNewsUpload;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer status;
}
