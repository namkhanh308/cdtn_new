package com.cdtn.kltn.repository.client;

import com.cdtn.kltn.dto.client.respone.HomeClientResponse;

public interface CustomClientRepository {
    HomeClientResponse homeClient(String codeClient);
}
