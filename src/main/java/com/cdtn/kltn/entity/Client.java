package com.cdtn.kltn.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "client")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codeClient;
    private Long userId;
    private String fullName;
    private String provinceCode;
    private String districtCode;
    private String wardsCode;
    private String introduces;
    private String phone;
    private Integer typeLoan;
    private String money;
    private String passport;



}
