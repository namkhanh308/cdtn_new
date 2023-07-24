package com.cdtn.kltn.repository.image;

import com.cdtn.kltn.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByCodeClient(String clientCode);

    @Query(value = "select id from image order by id desc limit 1", nativeQuery = true)
    Long getIndex();

    Optional<Image> findByCodeClientAndLevel(String clientCode, Integer level);

    List<Image> findAllByPropertyCode(String propertyCode);

}
