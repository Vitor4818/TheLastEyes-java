package com.thelasteyes.backend.Dto;

public record UserFilter(
        String name,
        String email,
        String cpf,
        Long roleId
) {
}