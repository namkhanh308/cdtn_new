package com.cdtn.kltn.dto.auth.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordDTO {
    @NotNull
    private Long id;

    private String oldPassword;

    private String newPassword;
}
