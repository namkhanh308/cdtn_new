package com.cdtn.kltn.repository.news;

import com.cdtn.kltn.dto.news.respone.CustomerNewsForCodeCate;
import com.cdtn.kltn.dto.news.respone.CustomerNewsResponse;
import com.cdtn.kltn.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    @Query(value = "select count(1) from news n join property p on n.code_property = p.code_property\n" +
            "where status_news = 1 and p.code_client = :codeClientSearch", nativeQuery = true)
    Integer findCountNewsActiveByCodeClient(@Param("codeClientSearch") String code);

    @Query(value = "select * from news where status_news = 1 and code_property in\n" +
            "                         (select code_property from property where code_client = :clientCode )", nativeQuery = true)
    List<News> findAllByClientCode(@Param("clientCode") String clientCode);

    List<News> findAllByStatusNews(Integer status);

    List<News> findAllByCodeProperty(String codeProperty);

    @Query(value = """
                select                            n.id as id,
                                                  n.name_news as nameNews,
                                                  n.address as address,
                                                  pi.area_use as areaUser,
                                                  pi.bed_count as bedCount,
                                                  pi.bath_count as bathCount,
                                                  pi.price_loan as priceLoan,
                                                  n.date_create as dateCreate,
                                                  n.date_expiration dateExpiration,
                                                  (case when n.status_up_top = 1 then 'YES' else 'NO' end) as isPushTop,
                                                  (select url from image where property_code = p.code_property limit 1) as url,
                                                  (select count(1) from image where property_code = p.code_property limit 1) as totalUrl
                                       from news n
                                                join property p on n.code_property = p.code_property
                                                join province p2 on p.province_code = p2.province_code
                                                join districs d on p.district_code = d.district_code
                                                join propertyinfo pi on p.code_property = pi.code_property
                                                where (?1 = '' or ?1 is null or (n.name_news like concat('%', ?1, '%')
                                                                        or n.address like concat('%', ?1, '%')
                                                                        or p2.province_name like concat('%', ?1, '%')
                                                                        or d.district_name like concat('%', ?1, '%')
                                                                        or p.name_property like concat('%', ?1, '%') ))
                                                    and (?2 = '' or ?2 is null or p.province_code = ?2)
                                                    and (?3 = '' or ?3 is null or p.district_code = ?3)
                                                    and (?4 = '' or ?4 is null or p.code_type_property = ?4)
                                                    and (?5 = '' or ?5 is null or p.code_cate_type_property_category = ?5)
                                                    and (case when ?6 is null or ?6 = '' then 1=1 else cast(pi.price_loan as SIGNED INTEGER)  >= cast(?6 as SIGNED INTEGER) end )
                                                    and (case when ?7 is null or ?7 = '' then 1=1 else cast(pi.price_loan as SIGNED INTEGER)  <= cast(?7 as SIGNED INTEGER) end )
                                                    and (case when ?8 is null or ?8 = '' then 1=1 else cast(pi.area_use as SIGNED INTEGER) >= cast(?8 as SIGNED INTEGER ) end )
                                                    and (case when ?9 is null or ?9 = '' then 1=1 else cast(pi.area_use as SIGNED INTEGER) <= cast(?9 as SIGNED INTEGER ) end )
                                                    and (case when ?10 is null or ?10 = '' then 1=1 else (cast(pi.bath_count as SIGNED integer) +
                                                                                                          cast(pi.bed_count as SIGNED integer) +
                                                                                                          cast(pi.kitchen_count as SIGNED integer) +
                                                                                                          cast(pi.living_count as SIGNED integer)) = ?10 end )
                                                    and (?11 = '' or ?11 is null or (n.date_create >= current_date + INTERVAL - ?11 DAY))
                                                    and n.status_news = 1
                                                    order by n.status_up_top asc, n.date_create desc, n.id desc                                                                                                                           
            """, nativeQuery = true)
    Page<CustomerNewsResponse> getPageCustomer(String nameSearch, String provinceCode, String districtCode, String codeTypeProperty,
                                               String codeCateTypePropertyCategory, String priceStart, String priceEnd,
                                               String areaMinRange, String areaMaxRange, String totalRoom, Integer rangeDaySearch,
                                               Pageable pageable);

    @Query(value = """
            select                 n.id as id,
                                   n.name_news as nameNews,
                                   n.address as address,
                                   pi.area_use as areaUser,
                                   pi.bed_count as bedCount,
                                   pi.bath_count as bathCount,
                                   pi.price_loan as priceLoan,
                                   (select url from image where property_code = p.code_property limit 1) as url
            from news n
                     join property p on n.code_property = p.code_property
                     join propertyinfo pi on p.code_property = pi.code_property
            where n.status_news = '1' and
                (case when (select count(1) from property where code_type_property = ?1) <= 1
                             then 1=1 else p.code_cate_type_property_category = ?2 end)
                and ( case when (select count(1) from property where province_code = ?3) <= 1
                    then 1=1 else p.province_code = ?3 end)
                and  n.id <> ?4   
            limit 5 """, nativeQuery = true)
    List<CustomerNewsResponse> getNewsSame(String codeTypeProperty, String codeCateTypeProperty, String provinceCode, Long id);

    @Query(value = """
            SELECT t.code_cate as codeCate, t.name_code_cate as nameCodeCate  , IF(sub.tong IS NULL, 0, sub.tong) as tong
            FROM (
                SELECT 'Nhà' as name_code_cate ,2  AS code_cate
                UNION ALL
                SELECT 'Căn hộ' as name_code_cate, 3
                UNION ALL
                SELECT 'Đất' as name_code_cate, 4
                UNION ALL
                SELECT 'Mặt bằng' as name_code_cate, 5
                ) AS t left join (select count(1) as tong, p.code_cate_type_property_category
                from news n join property p on n.code_property = p.code_property
                where n.status_news = 1) sub on sub.code_cate_type_property_category = t.code_cate
            group by t.code_cate
            """, nativeQuery = true)
    List<CustomerNewsForCodeCate> findNewsOrderCodeCategory();

    @Query("SELECT n FROM News n WHERE n.statusNews = 1 ORDER BY n.view DESC")
    List<News> outstandingProject();
}
