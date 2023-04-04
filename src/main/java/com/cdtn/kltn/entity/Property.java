package com.cdtn.kltn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "property")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codeProperty;
    private String codeTypeProperty;
    private String nameProperty;
    private String provinceCode;
    private String districtCode;
    private String wardsCode;
    private LocalDateTime dateCreate;
    private String codeClient;
    private String typeLoanOrBuy;
    private Integer statusProperty;

}
