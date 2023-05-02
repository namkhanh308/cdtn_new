package com.cdtn.kltn.repository.news;

import com.cdtn.kltn.common.Enums;
import com.cdtn.kltn.common.QueryJdbcUltils;
import com.cdtn.kltn.common.UltilsPage;
import com.cdtn.kltn.dto.news.request.ManagerNewsSearchDTO;
import com.cdtn.kltn.dto.news.respone.ManagerNewsSearchRespone;
import com.cdtn.kltn.exception.StoreException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class CustomNewsRepositoryImp implements CustomNewsRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Page<ManagerNewsSearchRespone> findAllNewsManager(ManagerNewsSearchDTO managerNewsSearchDTO) {
        try {
            List<ManagerNewsSearchRespone> managerNewsSearchResponeList =
                    jdbcTemplate.query(
                            createFindAllNewsManagerQuery(managerNewsSearchDTO),
                            setParamFindAllNewsManagerQuery(managerNewsSearchDTO),
                            (rs, rowNum) -> ManagerNewsSearchRespone.
                                    builder()
                                    .id(rs.getLong("id"))
                                    .address(rs.getString("address"))
                                    .statusNews(Enums.StatusNews.checkName(rs.getInt("statusNews")))
                                    .dateCreate(LocalDateTime.parse(rs.getString("dateCreate"),dateTimeFormatter))
                                    .dateExpiration(LocalDateTime.parse(rs.getString("dateExpiration"),dateTimeFormatter))
                                    .url(rs.getString("url"))
                                    .nameNews(rs.getString("nameNews"))
                                    .build()
                    );
            return UltilsPage.getPageJdbc(managerNewsSearchResponeList,
                                            managerNewsSearchDTO.getPage(),
                                            managerNewsSearchDTO.getRecords());

        } catch (Exception e) {
            throw new StoreException(e.getMessage());
        }
    }


    private String createFindAllNewsManagerQuery(ManagerNewsSearchDTO form) {

        String select =
                """
                select
                    n.id as id,
                    n.name_news as nameNews,
                    n.date_create as dateCreate,
                    n.date_expiration as dateExpiration,
                    n.status_news as statusNews,
                    (select url from image where image.property_code = n.code_property and level = 2 limit 1) as url,
                    n.address as address
                    from news n
                    join property p on n.code_property = p.code_property and n.status_news <> 4  \s \s""";

        List<String> whereList = new ArrayList<>();

        if (StringUtils.isNotBlank(form.getNameNews())) {
            whereList.add("n.name_news like :nameNews ");
        }

        if (StringUtils.isNotBlank(form.getCodeTypeProperty())) {
            whereList.add("p.code_type_property = :codeTypeProperty");
        }
        if (StringUtils.isNotBlank(form.getCodeCateTypePropertyCategory())) {
            whereList.add("p.code_cate_type_property_category = :codeCateTypePropertyCategory");
        }

        if (Objects.nonNull(form.getStatusNews())) {
            whereList.add("n.status_news = :statusNews ");
        }

        if (Objects.nonNull(form.getDateCreate()) && Objects.nonNull(form.getDateExpiration())) {
            whereList.add("n.date_create >= :dateCreate and n.date_create <= :dateExpiration ");
        } else if (Objects.nonNull(form.getDateCreate())){
            whereList.add("n.date_create >= :dateCreate");
        } else if (Objects.nonNull(form.getDateExpiration())) {
            whereList.add("n.date_create <= :dateExpiration ");
        }

        String where = "";
        if (!whereList.isEmpty()) {
            where = QueryJdbcUltils.createWhereQuery(whereList);
        }

        String order = " order by ";
        if (StringUtils.isNotBlank(form.getOrder())) {
            order += QueryJdbcUltils.checkOrderSearch(form.getOrder());
        } else {
            order += "p.id ";
        }

        if (StringUtils.isNotBlank(form.getSort())) {
            order += form.getSort();
        } else {
            order += "desc";
        }

        String query = select + where + order;

        return query;
    }

    private Map<String, Object> setParamFindAllNewsManagerQuery(ManagerNewsSearchDTO form) {

        Map<String, Object> map = new HashMap<>();

        if (StringUtils.isNotBlank(form.getNameNews())) {
            map.put("nameNews", form.getNameNews() + "%");
        }

        if (Objects.nonNull(form.getStatusNews())) {
            map.put("statusNews", form.getStatusNews());
        }

        if (StringUtils.isNotBlank(form.getCodeTypeProperty())) {
            map.put("codeTypeProperty", form.getCodeTypeProperty());
        }

        if (StringUtils.isNotBlank(form.getCodeCateTypePropertyCategory())) {
            map.put("codeCateTypePropertyCategory", form.getCodeCateTypePropertyCategory());
        }

        if (Objects.nonNull(form.getDateCreate())) {
            map.put("dateCreate", QueryJdbcUltils.convertLocalDateTimeISO(form.getDateCreate()));
        }

        if (Objects.nonNull(form.getDateExpiration())) {
            map.put("dateExpiration", QueryJdbcUltils.convertLocalDateTimeISO(form.getDateExpiration()));
        }
        return map;
    }
}
