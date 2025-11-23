package com.thelasteyes.backend.Dto;

import com.thelasteyes.backend.Model.Company;

public record GetCompanyDto(Long id,
                            String cnpj,
                            String tradeName,
                            String corporateName,
                            String phone,
                            String email) {

    public GetCompanyDto(Company company) {
        this(
                company.getId(),
                company.getCnpj(),
                company.getTradeName(),
                company.getCorporateName(),
                company.getPhone(),
                company.getEmail()
        );
        }
}
