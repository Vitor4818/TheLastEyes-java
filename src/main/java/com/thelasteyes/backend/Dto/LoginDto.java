package com.thelasteyes.backend.Dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank
        String login,

        @NotBlank
        String password
) {
}
