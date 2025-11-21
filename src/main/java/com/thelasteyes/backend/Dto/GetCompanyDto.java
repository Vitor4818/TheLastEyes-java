package com.thelasteyes.backend.Dto;

import com.thelasteyes.backend.Model.Company;

public record GetCompanyDto(Long id,
                            Integer cnpj,
                            String tradeName,
                            String corporateName,
                            Integer phone,
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
