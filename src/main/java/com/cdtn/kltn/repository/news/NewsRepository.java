package com.cdtn.kltn.repository.news;

import com.cdtn.kltn.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository  extends JpaRepository<News, Long> {
    @Query(value = "select count(1) from news n join property p on n.code_property = p.code_property\n" +
            "where status_news = 1 and p.code_client = :codeClientSearch",nativeQuery = true)
    Integer findCountNewsActiveByCodeClient(@Param("codeClientSearch") String code);

    @Query(value = "select * from news where status_news = 1 and code_property in\n" +
            "                         (select code_property from property where code_client = :clientCode )",nativeQuery = true)
    List<News> findAllByClientCode(@Param("clientCode") String clientCode);

    List<News> findAllByStatusNews(Integer status);
}
