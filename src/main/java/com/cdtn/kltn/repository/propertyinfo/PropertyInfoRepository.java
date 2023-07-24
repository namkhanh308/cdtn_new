package com.cdtn.kltn.repository.propertyinfo;

import com.cdtn.kltn.entity.PropertyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyInfoRepository extends JpaRepository<PropertyInfo, Long> {
    Optional<PropertyInfo> findByCodeProperty(String codeProperty);
}
