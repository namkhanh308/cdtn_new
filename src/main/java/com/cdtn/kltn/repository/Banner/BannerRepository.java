package com.cdtn.kltn.repository.Banner;

import com.cdtn.kltn.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<Banner,Long> {
}
