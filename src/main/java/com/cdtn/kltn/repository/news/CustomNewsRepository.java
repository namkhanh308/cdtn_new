package com.cdtn.kltn.repository.news;


import com.cdtn.kltn.dto.news.request.ManagerNewsSearchDTO;
import com.cdtn.kltn.dto.news.respone.ManagerNewsSearchRespone;
import org.springframework.data.domain.Page;

public interface CustomNewsRepository {
    Page<ManagerNewsSearchRespone> findAllNewsManager(ManagerNewsSearchDTO managerNewsSearchDTO);
}
