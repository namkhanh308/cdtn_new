package com.cdtn.kltn.repository.agency;

import com.cdtn.kltn.entity.Agency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {
    @Query("""
            select new Agency(a.id, a.url, a.nameAgency, a.phone, a.dateCreate, a.provinceCode, a.district1st, p.provinceName)
                        from Agency a join Province p on a.provinceCode = p.provinceCode
                        where ( ?1 = '' or ?1 is null or a.nameAgency like concat ('%', ?1, '%')
            			or a.phone like concat ('%', ?1, '%')) and (?2 = '' or ?2 is null or (p.provinceCode = ?2)) """)
    Page<Agency> findAllAgency(String nameSearch, String provinceCode, Pageable pageable);
}
