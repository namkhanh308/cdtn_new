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
    private String area;
    private String bedCount;
    private String livingCount;
    private String kitchenCount;
    private String law;
    private String priceBuy;
    private String priceLoan;
    private String introduces;
    private String location;
}
