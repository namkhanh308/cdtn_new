package com.cdtn.kltn.repository.typeproperty;

import com.cdtn.kltn.entity.TypeProperty;
import com.cdtn.kltn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypePropertyRepository extends JpaRepository<TypeProperty, Long> {
    List<TypeProperty> findAllByCodeCateTypePropertyCategory(Long codeParent);

    List<TypeProperty> findAllByCodeCateTypePropertyCategoryAndDisplayLevel(Long codeParent, Integer displayLevelCode);

}
