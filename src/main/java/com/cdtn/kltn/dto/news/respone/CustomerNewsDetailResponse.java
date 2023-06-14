package com.cdtn.kltn.dto.news.respone;

import com.cdtn.kltn.dto.property.request.CreatePropertyDTO;
import com.cdtn.kltn.entity.News;
import com.cdtn.kltn.entity.User;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerNewsDetailResponse {
    News news;
    CreatePropertyDTO createPropertyDTO;
    User user;
}
