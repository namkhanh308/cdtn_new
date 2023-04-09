package com.cdtn.kltn.repository.image;

import com.cdtn.kltn.entity.Client;
import com.cdtn.kltn.entity.Image;
import com.cdtn.kltn.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByCodeClient(String clientCode);
    Optional<Image> findByCodeClientAndLevel(String clientCode, Integer level);
}
