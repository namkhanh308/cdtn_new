package com.cdtn.kltn.repository.wards;

import com.cdtn.kltn.entity.Wards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardsRepository extends JpaRepository<Wards, Long> {
    List<Wards> findAllByDistrictCode(String districsCode);
}
