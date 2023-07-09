package com.cdtn.kltn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "agency")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String nameAgency;

    private String phone;

    private LocalDateTime dateCreate;

    private String provinceCode;

    private String district1st;

    @Transient
    private String provinceName;

    @Transient
    private String districtName1st;

    public Agency(Long id, String url, String nameAgency, String phone, LocalDateTime dateCreate, String provinceCode, String district1st, String provinceName) {
        this.id = id;
        this.url = url;
        this.nameAgency = nameAgency;
        this.phone = phone;
        this.dateCreate = dateCreate;
        this.provinceCode = provinceCode;
        this.district1st = district1st;
        this.provinceName = provinceName;
    }
}
