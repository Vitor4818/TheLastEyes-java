package com.thelasteyes.backend.Controller;

import com.thelasteyes.backend.Dto.GetCompanyDto;
import com.thelasteyes.backend.Dto.PostCompanyDto;
import com.thelasteyes.backend.Model.Company;
import com.thelasteyes.backend.Service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
        private CompanyService companyService;


    //Retorna todas as empresas
    @GetMapping
    public ResponseEntity<Page<GetCompanyDto>> getAllCompanies(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable page){
    return ResponseEntity.ok(companyService.getAllCompanies(page));

    }

    //Retorna empresa por id
    @GetMapping("/{id}")
    public ResponseEntity<GetCompanyDto> getCompanyById(@PathVariable Long id){
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    //Cadastra empresa
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid PostCompanyDto dto){
        Company savedCompany = companyService.postCompany(dto);
        return ResponseEntity.created(URI.create("/company" + savedCompany.getId())).build();
    }

}
