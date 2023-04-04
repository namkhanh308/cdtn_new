package com.cdtn.kltn.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "typeproperty")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codeTypeProperty;
    private String nameProperty;
    private Long idParentTypeProperty;

}
