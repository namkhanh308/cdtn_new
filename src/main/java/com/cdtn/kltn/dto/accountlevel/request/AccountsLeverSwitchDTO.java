package com.cdtn.kltn.dto.accountlevel.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountsLeverSwitchDTO {
    private String codeClient;
    private Integer accountTypeLever;
    private Integer countMonth;
}
