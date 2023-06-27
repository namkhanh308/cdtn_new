package com.cdtn.kltn.repository.property;

import com.cdtn.kltn.dto.property.respone.PropertyDataSearchRespone;
import com.cdtn.kltn.dto.property.respone.PropertyDetailDataRespone;
import com.cdtn.kltn.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query(value = "select id from property order by id desc limit 1", nativeQuery = true)
    Long getIndex();

    Optional<Property> findByCodeProperty(String codeProperty);

    @Query(value = "SELECT *,'' as totalRecord FROM (\n" +
            "                                    SELECT DISTINCT\n" +
            "                                        p.code_property AS codeProperty,\n" +
            "                                        p.name_property AS nameProperty,\n" +
            "                                        CONCAT(p2.province_name, '/', d.district_name, '/', w.code_name) AS addressView,\n" +
            "                                        t.name_type_property AS nameTypeProperty,\n" +
            "                                        (\n" +
            "                                            SELECT url\n" +
            "                                            FROM image i\n" +
            "                                            WHERE property_code = i.property_code\n" +
            "                                            ORDER BY id DESC\n" +
            "                                            LIMIT 1\n" +
            "                                        ) AS url,\n" +
            "                                        case when p.date_change is null then p.date_create\n" +
            "                                             else p.date_change\n" +
            "                                            end as lastDateUpdate,\n" +
            "                                       case when p.status_property = 1 then 'Tạo mới' " +
            "                                       when p.status_property = 2 then 'Đã chỉnh sửa' " +
            "                                           when p.status_property = 3 then 'Đang cho thuê' " +
            "                                           when p.status_property = 4 then 'Đã hủy' " +
            "                                       end as status " +
            "                                    FROM property p\n" +
            "                                             INNER JOIN propertyinfo pi ON p.code_property = pi.code_property\n" +
            "                                             JOIN districs d ON p.district_code = d.district_code\n" +
            "                                             JOIN province p2 ON p2.province_code = p.province_code\n" +
            "                                             JOIN wards w ON p.wards_code = w.wards_code\n" +
            "                                             JOIN typeproperty t ON t.code_type_property = p.code_type_property\n" +
            "                                    WHERE\n" +
            "                                        ((:codePropertySearch = '') OR (p.code_property = :codePropertySearch))\n" +
            "                                      AND ((:namePropertySearch = '') OR (p.name_property = TRIM(:namePropertySearch)))\n" +
            "                                      AND ((:codeTypePropertySearch = '') OR\n" +
            "                                           (p.code_type_property = :codeTypePropertySearch))\n" +
            "                                      and ((:codeClient = '') or :codeClient = p.code_client)\n" +
            "                                    LIMIT :offset, :page) t\n" +
            "UNION\n" +
            "(select '' as codeProperty,\n" +
            "        '' as nameProperty,\n" +
            "        '' as addressView,\n" +
            "        '' as nameTypeProperty,\n" +
            "        '' as url,\n" +
            "        '' as lastDateUpdate,\n" +
            "        '' as status,\n" +
            "        count(*) as totalRecord\n" +
            " FROM property p\n" +
            "          INNER JOIN propertyinfo pi ON p.code_property = pi.code_property\n" +
            "          JOIN districs d ON p.district_code = d.district_code\n" +
            "          JOIN province p2 ON p2.province_code = p.province_code\n" +
            "          JOIN wards w ON p.wards_code = w.wards_code\n" +
            "          JOIN typeproperty t ON t.code_type_property = p.code_type_property\n" +
            "             WHERE\n" +
            "                 ((:codePropertySearch = '') OR (p.code_property = :codePropertySearch))\n" +
            "               AND ((:namePropertySearch = '') OR (p.name_property = TRIM(:namePropertySearch)))\n" +
            "               AND ((:codeTypePropertySearch = '') OR\n" +
            "                    (p.code_type_property = :codeTypePropertySearch))\n" +
            "               and ((:codeClient = '') or :codeClient = p.code_client))", nativeQuery = true)
    List<PropertyDataSearchRespone> findAllPropertyManager(@Param("codePropertySearch") String codePropertySearch,
                                                           @Param("namePropertySearch") String namePropertySearch,
                                                           @Param("codeTypePropertySearch") String codeTypePropertySearch,
                                                           @Param("codeClient") String codeClient,
                                                           @Param("offset") Integer offset,
                                                           @Param("page") Integer page);

    @Query(value = "select p.code_property as codeProperty,\n" +
            "       p.code_type_property as codeTypeProperty,\n" +
            "       p.code_cate_type_property_category as codeCateTypePropertyCategory,\n" +
            "       p.name_property as nameProperty,\n" +
            "       p.province_code as provinceCode,\n" +
            "       p.district_code as districtCode,\n" +
            "       p.wards_code as wardsCode,\n" +
            "       p.code_client as codeClient,\n" +
            "       pi.area_use as areaUse,\n" +
            "       pi.usable_area as usableArea,\n" +
            "       pi.land_area as landArea,\n" +
            "       pi.bed_count as bedCount,\n" +
            "       pi.living_count as livingCount,\n" +
            "       pi.kitchen_count as kitchenCount,\n" +
            "       pi.law as law,\n" +
            "       pi.price_buy as priceBuy,\n" +
            "       pi.price_loan as priceLoan,\n" +
            "       pi.introduces as introduces,\n" +
            "       pi.location as location\n" +
            "    from property p inner join propertyinfo pi on p.code_property = pi.code_property\n" +
            "    where p.code_property = :codeProperty", nativeQuery = true)
    Optional<PropertyDetailDataRespone> findPropertyByCodeProperty(@Param("codeProperty") String codeProperty);

    @Query(value = "delete from propertyinfo where code_property = :codeProperty ;\n" +
            "delete from image where property_code = :codeProperty ; \n" +
            "delete from property where code_property = :codeProperty ;", nativeQuery = true)
    void deleteProperty(@Param("codeProperty") String codeProperty);
}
