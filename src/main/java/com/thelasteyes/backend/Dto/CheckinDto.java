package com.thelasteyes.backend.Dto;


import jakarta.validation.constraints.NotBlank;

public record CheckinDto(
        @NotBlank(message = "O texto do humor é obrigatório")
        String text
) {}