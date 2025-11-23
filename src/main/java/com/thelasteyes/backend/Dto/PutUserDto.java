package com.thelasteyes.backend.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record PutUserDto(


        Long roleId,
        Long currentJobId,

        @Size(min = 6, max = 60, message = "A senha deve ter entre 6 e 60 caracteres.")
        String password,

        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
        String name,

        @Size(max = 100, message = "O email deve ter no máximo 100 caracteres.")
        @Email(message = "O formato do email é inválido se não for nulo.")
        String email,

        String cpf,

        @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres.")
        String phone,

        @Past(message = "A data de nascimento deve ser uma data passada.")
        LocalDate birthDate


) {
}