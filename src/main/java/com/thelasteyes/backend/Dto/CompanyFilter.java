package com.thelasteyes.backend.Dto;

public record CompanyFilter(
        String tradeName,
        String corporateName,
        String cnpj,
        String phone,
        String email
) {
}