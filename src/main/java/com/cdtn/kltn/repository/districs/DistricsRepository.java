package com.cdtn.kltn.repository.districs;

import com.cdtn.kltn.entity.Districs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistricsRepository extends JpaRepository<Districs, Long> {
    List<Districs> findAllByProvinceCode(String provinceCode);

    Optional<Districs> findByDistrictCode(String districtCode);
}
