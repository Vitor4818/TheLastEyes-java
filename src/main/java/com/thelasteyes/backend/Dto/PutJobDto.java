package com.thelasteyes.backend.Dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record PutJobDto(

        Long companyId,

        LocalDate hireDate,

        @Size(max = 50, message = "O cargo deve ter no máximo 50 caracteres.")
        String position,

        @Size(max = 30, message = "O tipo de contrato deve ter no máximo 30 caracteres.")
        String contractType,

        @Min(value = 1, message = "A carga horária deve ser maior que zero.")
        @Max(value = 60, message = "A carga horária não pode exceder 60 horas.")
        Integer weeklyHours, // Usamos Integer para permitir null

        @Size(max = 30, message = "O modelo de trabalho deve ter no máximo 30 caracteres.")
        String workModel,

        @Min(value = 1, message = "A satisfação deve ser no mínimo 1.")
        @Max(value = 5, message = "A satisfação deve ser no máximo 5.")
        Integer jobSatisfactionScore // Usamos Integer para permitir null
) {
}