package com.cdtn.kltn.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "wards")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String wardsCode;
    private String codeName;
    private String divisionType;
    private String districtCode;
    private String provinceCode;

}
