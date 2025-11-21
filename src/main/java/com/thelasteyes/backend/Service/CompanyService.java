package com.thelasteyes.backend.Service;

import com.thelasteyes.backend.Dto.GetCompanyDto;
import com.thelasteyes.backend.Repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

@Autowired
   private  CompanyRepository companyRepository;

    //Retorna todas as empresas
    public Page<GetCompanyDto> getAllCompanies(Pageable page){
        return companyRepository.findAll(page).map(GetCompanyDto::new);
    }

    //Retorna empresa por ID

    //Cadastra uma nova empresa

    //Atualiza dados da empresa

    //Deleta dados da empresa

}
