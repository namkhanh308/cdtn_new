package com.cdtn.kltn.common;

import com.cdtn.kltn.dto.pagination.PagingResponeDTO;
import com.cdtn.kltn.dto.property.request.PropertySearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class UtilsPage {

        private UtilsPage(){}

        public static PagingResponeDTO paging(PropertySearchDTO request){
            int start = 0;
            int end = 0;
            int offset = 0;
            int page = 1;
            try {
                page = request.getPage();
            } catch (Exception e) {
                e.printStackTrace();
            }
            int records = 20;
            try {
                records = request.getRecords();
            } catch (Exception e) {
                e.printStackTrace();
            }
            start = (page - 1) * records + 1;
            end = page * records;
            offset = start - 1;
            return PagingResponeDTO.builder().start(start).page(page).offset(offset).end(end).records(records).build();
        }

    public static <T> Page<T> getPageJdbc(List<T> data, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        int total = data.size();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), total);
        List<T> content = data.subList(start, end);

        return new PageImpl<>(content, pageable, total);
    }
}
