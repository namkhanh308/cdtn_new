package com.cdtn.kltn.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "propertyinfo")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codeProperty;
    private String areaUse;
    private String usableArea;
    private String landArea;
    private Integer bedCount;
    private Integer livingCount;
    private Integer kitchenCount;
    private Integer bathCount;
    private Integer law;
    private String priceBuy;
    private String priceLoan;
    private String introduces;
    private String location;
}
