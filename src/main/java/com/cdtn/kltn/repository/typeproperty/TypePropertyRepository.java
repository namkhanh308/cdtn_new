package com.cdtn.kltn.repository.typeproperty;

import com.cdtn.kltn.entity.TypeProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypePropertyRepository extends JpaRepository<TypeProperty, Long> {
    List<TypeProperty> findAllByCodeCateTypePropertyCategory(Long codeParent);

    List<TypeProperty> findAllByCodeCateTypePropertyCategoryAndDisplayLevel(Long codeParent, Integer displayLevelCode);

}
