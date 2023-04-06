package com.cdtn.kltn.repository.districs;

import com.cdtn.kltn.entity.Districs;
import com.cdtn.kltn.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistricsRepository extends JpaRepository<Districs, Long> {
    List<Districs> findAllByProvinceCode(String provinceCode);
}
