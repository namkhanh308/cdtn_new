package com.cdtn.kltn.common;

import com.cdtn.kltn.dto.pagination.PagingResponeDTO;
import com.cdtn.kltn.dto.property.request.PropertySearchDTO;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.JpaSort;

import java.util.List;

public class UtilsPage {

    private UtilsPage() {
    }

    public static PagingResponeDTO paging(PropertySearchDTO request) {
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


    public static Pageable getPage(String sortDir, String sortField, int page, int size) {
        Pageable pageable;
        if (Constant.ASC.equalsIgnoreCase(sortDir)) {
            pageable = PageRequest.of(page, size, Sort.Direction.ASC, sortField);
        } else {
            pageable = PageRequest.of(page, size, Sort.Direction.DESC, sortField);
        }
        return pageable;
    }

    public static Pageable getPageWithSort(int page, int size, Sort sort) {
        return PageRequest.of(page, size, sort);
    }

    public static Pageable getPageWithJpaSortUnsafe(String sortDir, String sortField, int page, int size) {
        Sort sort;
        if (Constant.ASC.equalsIgnoreCase(sortDir)) {
            sort = JpaSort.unsafe(Sort.Direction.ASC, "(" + sortField + ")");
        } else {
            sort = JpaSort.unsafe(Sort.Direction.DESC, "(" + sortField + ")");
        }
        return PageRequest.of(page - 1, size, sort);
    }

    public static Pageable getPageForSortStringAsNumber(String sortDir, String sortField, int page, int size) {
        Sort sort;
        if (Constant.ASC.equalsIgnoreCase(sortDir)) {
            sort = JpaSort.unsafe(Sort.Direction.ASC, "length(" + sortField + ") asc, " + sortField);
        } else {
            sort = JpaSort.unsafe(Sort.Direction.DESC, "length(" + sortField + ") desc, " + sortField);
        }
        return PageRequest.of(page, size, sort);
    }


}
