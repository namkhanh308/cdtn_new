package com.cdtn.kltn.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private Long userId;
    private String codeClient;
    private String lastName;
    private Integer typeLoan;
}
