package com.cdtn.kltn.dto.client.respone;

import com.cdtn.kltn.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientInfoDTO {
    private String codeClient;
    private Long userId;
    private String fullName;
    private String provinceCode;
    private String districtCode;
    private String wardsCode;
    private String introduces;
    private String phone;
    private Integer typeLoan;
    private String money;
    private String passport;
    private String firstName;
    private String lastName;
}
