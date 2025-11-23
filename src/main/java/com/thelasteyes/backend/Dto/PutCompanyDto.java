package com.thelasteyes.backend.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record PutCompanyDto(

        @Size(max = 100, message = "O nome fantasia deve ter no máximo 100 caracteres.")
        String tradeName,

        @Size(max = 100, message = "A razão social deve ter no máximo 100 caracteres.")
        String corporateName,

        String phone,

        @Size(max = 100, message = "O email deve ter no máximo 100 caracteres.")
        @Email(message = "O formato do email é inválido.")
        String email
) {
}
