package com.thelasteyes.backend.Dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CheckinDto(
        @NotNull
        Long userId,
        @NotBlank(message = "O texto do humor é obrigatório")
        String text
) {}