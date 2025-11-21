package com.thelasteyes.backend.Controller;

import com.thelasteyes.backend.Dto.GetCompanyDto;
import com.thelasteyes.backend.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
