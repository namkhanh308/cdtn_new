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
    private Client client;
    private String firstName;
    private String lastName;
}
