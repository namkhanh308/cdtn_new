package com.cdtn.kltn.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagingResponeDTO {
    private Integer start;
    private Integer end;
    private Integer offset;
    private Integer record;
    private Integer page;
    private Integer totalRecord;
    private Object data;
}
