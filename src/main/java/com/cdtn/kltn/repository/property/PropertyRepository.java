package com.cdtn.kltn.repository.property;

import com.cdtn.kltn.entity.Image;
import com.cdtn.kltn.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query(value = "select id from property order by id desc limit 1",nativeQuery = true)
    Long getIndex();

    Optional<Property> findByCodeProperty(String codeProperty);
}
