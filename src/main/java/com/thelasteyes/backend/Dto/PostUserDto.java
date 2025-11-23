package com.thelasteyes.backend.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record PostUserDto(

        @NotNull(message = "O ID do perfil (roleId) é obrigatório.")
        Long roleId,

        @NotBlank(message = "O nome do usuário é obrigatório.")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
        String name,

        @NotBlank(message = "O email é obrigatório.")
        @Size(max = 100, message = "O email deve ter no máximo 100 caracteres.")
        @Email(message = "O formato do email é inválido.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, max = 60, message = "A senha deve ter entre 6 e 60 caracteres.")
        String password,

        @NotBlank(message = "O CPF é obrigatório.")
        @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos.")
        String cpf,

        @NotBlank(message = "O telefone é obrigatório.")
        @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres.")
        String phone,

        @NotNull(message = "A data de nascimento é obrigatória.")
        @Past(message = "A data de nascimento deve ser uma data passada.")
        LocalDate birthDate,

        @NotNull(message = "O ID do emprego é obrigatório.")
        Long currentJobId



) {
}