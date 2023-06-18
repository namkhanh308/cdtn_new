package com.cdtn.kltn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "province")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String provinceCode;
    private String provinceName;
    private String codeName;
    private String divisionType;
    private String phoneCode;

    @Transient
    private List<Districs> districsList;

}
