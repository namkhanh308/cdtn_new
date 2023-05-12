package com.cdtn.kltn.dto.client.respone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeClientResponse {
    private String fullName;
    private String email;
    private String lever;
    private String money;
    private String postingProperty;
    private String postingNews;
    private String expiredNews;
    private String completeNews;
    private String allNews;


}
