package com.cdtn.kltn.dto.news.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PushTopDTO {
    @NotNull
    private Long id;
    private LocalDateTime timeUpTopStart;
    private LocalDateTime timeUpTopEnd;
}
