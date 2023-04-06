package com.cdtn.kltn.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "districs")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Districs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String districtCode;
    private String districtName;
    private String codeName;
    private String divisionType;
    private String provinceCode;

}
