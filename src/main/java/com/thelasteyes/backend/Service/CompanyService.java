package com.thelasteyes.backend.Service;

import com.thelasteyes.backend.Dto.CompanyFilter;
import com.thelasteyes.backend.Dto.GetCompanyDto;
import com.thelasteyes.backend.Dto.PostCompanyDto;
import com.thelasteyes.backend.Dto.PutCompanyDto;
import com.thelasteyes.backend.Exceptions.DataConflictException;
import com.thelasteyes.backend.Exceptions.ResourceNotFoundException;
import com.thelasteyes.backend.Model.Company;
import com.thelasteyes.backend.Repository.CompanyRepository;
import com.thelasteyes.backend.Specification.CompanySpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

@Autowired
   private  CompanyRepository companyRepository;

    //Retorna todas as empresas
    @Cacheable(value = "companiesList", key = "#filter.hashCode() + #page.pageNumber")
    public Page<GetCompanyDto> getAllCompanies(Pageable page, CompanyFilter filter){
        Specification<Company> spec = CompanySpecification.withFilter(filter);
        return companyRepository.findAll(spec, page).map(GetCompanyDto::new);
    }

    //Retorna empresa por ID
    @Cacheable(value = "companyById", key = "#id")
    public GetCompanyDto getCompanyById(Long id){
        return companyRepository.findById(id).map(GetCompanyDto::new)
                .orElseThrow(()-> new ResourceNotFoundException("Empresa com o id "+id+" Não encontrada"));
    }

    //Cadastra uma nova empresa
    @Transactional
    @CacheEvict(value = "companiesList", allEntries = true)
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
    @Transactional
    @CacheEvict(value = "companiesList", allEntries = true)
    @CachePut(value = "companyById", key = "#id")
    public void putCompany(Long id, PutCompanyDto dto) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa com o id " + id + " não encontrada"));
        if (dto.email() != null && !dto.email().equals(company.getEmail())) {
            if (companyRepository.existsByEmailAndIdNot(dto.email(), id)) {
                throw new DataConflictException("Não foi possível atualizar dados da empresa. O email digitado já está em uso por outra empresa.");
            }
        }
        company.updateData(dto);
        companyRepository.save(company);
    }


    //Deleta dados da empresa
    @Transactional
    @CacheEvict(value = {"companiesList", "companyById"}, allEntries = true)
    public void deleteCompany(Long id){
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa com o id " + id + " não encontrada"));
        companyRepository.delete(company);
    }


}
