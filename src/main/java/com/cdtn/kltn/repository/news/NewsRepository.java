package com.cdtn.kltn.repository.news;

import com.cdtn.kltn.dto.news.respone.*;
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
                                                  im.url as url,
                                                  imc.count as totalUrl
                                       from news n
                                                join property p on n.code_property = p.code_property
                                                join province p2 on p.province_code = p2.province_code
                                                join districs d on p.district_code = d.district_code
                                                join propertyinfo pi on p.code_property = pi.code_property
                                                join imagelimit1 im on im.property_code = p.code_property
                                                join imageCountForPropertyCode imc on imc.property_code = p.code_property
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
                where n.status_news = 1
                group by p.code_cate_type_property_category
                ) sub on sub.code_cate_type_property_category = t.code_cate
            group by t.code_cate
            """, nativeQuery = true)
    List<CustomerNewsForCodeCate> findNewsOrderCodeCategory();

    @Query(""" 
            SELECT new News(n.nameNews,
                            n.codeProperty,
                            n.address,
                            n.dateCreate,
                            n.dateExpiration,
                            n.statusNews,
                            n.timeUpTopStart,
                            n.timeUpTopEnd,
                            n.statusUpTop,
                            n.view,
                            i.url,
                            pi.priceLoan)
            FROM News n
            JOIN PropertyInfo pi ON n.codeProperty = pi.codeProperty
            LEFT JOIN Image i ON i.propertyCode = n.codeProperty
            WHERE n.statusNews = 1
            GROUP BY n.codeProperty
            ORDER BY n.view DESC                         
            """)
    List<News> outstandingProject();


    @Query(value = """
                select t.rangeName, IFNULL(sub.count,'0') as rangeCount
                    from (
                             SELECT '0 - 1000000' as rangeName, 1 AS rangeCode
                             UNION ALL
                             SELECT '1000000 - 5000000' as rangeName, 2
                             UNION ALL
                             SELECT '5000000 - 10000000' as rangeName, 3
                             UNION ALL
                             SELECT '10000000 - 20000000' as rangeName, 4
                             UNION ALL
                             SELECT '20000000 - 100000000' as rangeName, 5
                             UNION ALL
                             SELECT '> 1000000000' as rangeName, 6
                         ) AS t
                left join (
                select  c.rangeCode, count(c.rangeCode) as count
                from (
                select
                       (case
                            when cast(price_loan as SIGNED INTEGER) > 0 and
                                 cast(price_loan as SIGNED INTEGER) < 1000000 then 1
                            when cast(price_loan as SIGNED INTEGER) >= 1000000 and
                                 cast(price_loan as SIGNED INTEGER) < 5000000 then 2
                            when cast(price_loan as SIGNED INTEGER) >= 5000000 and
                                 cast(price_loan as SIGNED INTEGER) < 10000000 then 3
                            when cast(price_loan as SIGNED INTEGER) >= 10000000 and
                                 cast(price_loan as SIGNED INTEGER) < 20000000 then 4
                            when cast(price_loan as SIGNED INTEGER) >= 20000000 and
                                 cast(price_loan as SIGNED INTEGER) < 100000000 then 5
                            when cast(price_loan as SIGNED INTEGER) >= 1000000000 then 6 end) as rangeCode
                from propertyinfo pi
                         join property p on pi.code_property = p.code_property
                         join news n on p.code_property = n.code_property
                where p.province_code = ?1 and MONTH(n.date_create) = ?2 and YEAR(n.date_create) = ?3
                    and p.code_cate_type_property_category = ?4
                    ) as c
                group by c.rangeCode) as sub on t.rangeCode = sub.rangeCode;
            """, nativeQuery = true)
    List<StatisticsByPrice> statisticsByPrice(String provinceCode, Long month, Long year, String codeCategory);


    @Query(value = """
                select di.district_name as districtName, IFNULL(ne.count,0) as count from
                (select district_code, district_name
                 from districs where province_code = ?1) as di left join (
                    select p.district_code, count(n.id) as count
                    from news n
                             join property p on n.code_property = p.code_property
                    where p.province_code = ?1
                      and MONTH(n.date_create) = ?2
                      and YEAR(n.date_create) = ?3
                      and p.code_cate_type_property_category = ?4
                      group by p.district_code
                      )
                       as ne on di.district_code = ne.district_code
            """, nativeQuery = true)
    List<StatisticsByDistrict> statisticsByDistrict(String provinceCode, Long month, Long year, String codeCategory);


    @Query(value = """
                select name_news as nameNews,
                   n.address as address,
                   ifnull(p.area_use, 0) as areaUse,
                   ifnull(p.bath_count, 0) as bathCount,
                   ifnull(p.living_count, 0) as livingCount,
                   ifnull(p.kitchen_count, 0) as kitchenCount,
                   ifnull(p.bed_count, 0) as bedCount,
                   il.url as url,
                   p.price_loan as priceLoan
                from news n join propertyinfo p on n.code_property = p.code_property
                join imagelimit1 il on il.property_code = n.code_property
            where n.id in ?1
            """, nativeQuery = true)
    Page<FavouriteNewsResponse> favouriteNews(List<Long> listId, Pageable pageable);
}
