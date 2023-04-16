package com.cdtn.kltn.common;

import com.cdtn.kltn.dto.pagination.PagingResponeDTO;
import com.cdtn.kltn.dto.property.request.PropertySearchDTO;

public class UltilsPage {
        public static PagingResponeDTO Paging(PropertySearchDTO request){
            int start = 0;
            int end = 0;
            int offset = 0;
            int page = 1;
            try {
                page = request.getPage();
            } catch (Exception e) {
            }
            int records = 20;
            try {
                records = request.getRecords();
            } catch (Exception e) {
            }
            start = (page - 1) * records + 1;
            end = page * records;
            offset = start - 1;
            return PagingResponeDTO.builder().start(start).page(page).offset(offset).end(end).record(records).build();
        }

}
