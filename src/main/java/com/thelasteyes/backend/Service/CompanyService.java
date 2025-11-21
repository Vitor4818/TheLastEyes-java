package com.thelasteyes.backend.Service;

import com.thelasteyes.backend.Dto.GetCompanyDto;
import com.thelasteyes.backend.Dto.PostCompanyDto;
import com.thelasteyes.backend.Exceptions.DataConflictException;
import com.thelasteyes.backend.Exceptions.ResourceNotFoundException;
import com.thelasteyes.backend.Model.Company;
import com.thelasteyes.backend.Repository.CompanyRepository;
import jakarta.transaction.Transactional;
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
    public GetCompanyDto getCompanyById(Long id){
        return companyRepository.findById(id).map(GetCompanyDto::new)
                .orElseThrow(()-> new ResourceNotFoundException("Empresa com o id "+id+" Não encontrada"));
    }

    //Cadastra uma nova empresa
    @Transactional
    public Company postCompany (PostCompanyDto dto){

        //verifica se o email ja esta em uso
        if(companyRepository.existsByEmail(dto.email())){
            throw new DataConflictException("Esse email ja esta em uso");
        }
        //Verifica se o cnpj ja esta em uso
        if(companyRepository.existsByCnpj(dto.cnpj())){
            throw new DataConflictException("Esse CNPJ ja esta em uso");
        }
        Company company = new Company();
        company.setCnpj(dto.cnpj());
        company.setTradeName(dto.tradeName());
        company.setCorporateName(dto.corporateName());
        company.setPhone(dto.phone());
        company.setEmail(dto.email());
        return companyRepository.save(company);
    }


    //Atualiza dados da empresa

    //Deleta dados da empresa

}
