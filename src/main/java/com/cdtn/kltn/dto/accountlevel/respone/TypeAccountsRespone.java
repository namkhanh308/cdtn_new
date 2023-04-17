package com.cdtn.kltn.dto.accountlevel.respone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeAccountsRespone {
    private  Integer code;
    private  String name;
    private  Integer countNewsUpload;
    private  Integer denominations;
}
