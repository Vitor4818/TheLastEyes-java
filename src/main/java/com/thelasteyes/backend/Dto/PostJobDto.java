package com.thelasteyes.backend.Dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record PostJobDto(

        @NotNull(message = "O ID da empresa é obrigatório para vincular o emprego.")
        Long companyId, // Mapeia para a entidade Company

        @NotNull(message = "A data de admissão é obrigatória.")
        LocalDate hireDate,

        @NotBlank(message = "O cargo é obrigatório.")
        @Size(max = 50, message = "O cargo deve ter no máximo 50 caracteres.")
        String position,

        @NotBlank(message = "O tipo de contrato é obrigatório.")
        @Size(max = 30, message = "O tipo de contrato deve ter no máximo 30 caracteres.")
        String contractType,

        @NotNull(message = "A carga horária semanal é obrigatória.")
        @Min(value = 1, message = "A carga horária deve ser maior que zero.")
        @Max(value = 60, message = "A carga horária não pode exceder 60 horas.")
        int weeklyHours,

        @NotBlank(message = "O modelo de trabalho é obrigatório.")
        @Size(max = 30, message = "O modelo de trabalho deve ter no máximo 30 caracteres.")
        String workModel,

        @NotNull(message = "A pontuação de satisfação de trabalho é obrigatória.")
        @Min(value = 1, message = "A satisfação deve ser no mínimo 1.")
        @Max(value = 5, message = "A satisfação deve ser no máximo 5.")
        int jobSatisfactionScore
) {
}