package com.cdtn.kltn.dto.news.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FavouriteNewsDTO {
    private List<Long> listId;
    private Integer page;
    private Integer size;
}
