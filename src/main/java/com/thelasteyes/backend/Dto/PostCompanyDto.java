package com.thelasteyes.backend.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostCompanyDto(
        @NotNull(message = "O CNPJ é obrigatório.")
        String cnpj,

        @NotBlank(message = "O nome fantasia é obrigatório.")
        @Size(max = 100, message = "O nome fantasia deve ter no máximo 100 caracteres.")
        String tradeName,

        @NotBlank(message = "A razão social é obrigatória.")
        @Size(max = 100, message = "A razão social deve ter no máximo 100 caracteres.")
        String corporateName,

        @NotNull(message = "O telefone é obrigatório.")
        String phone,

        @NotBlank(message = "O email é obrigatório.")
        @Size(max = 100, message = "O email deve ter no máximo 100 caracteres.")
        @Email(message = "O formato do email é inválido.")
        String email
) {
}
