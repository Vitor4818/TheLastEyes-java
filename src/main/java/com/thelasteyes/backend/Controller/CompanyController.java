package com.thelasteyes.backend.Controller;

import com.thelasteyes.backend.Dto.CompanyFilter;
import com.thelasteyes.backend.Dto.GetCompanyDto;
import com.thelasteyes.backend.Dto.PostCompanyDto;
import com.thelasteyes.backend.Dto.PutCompanyDto;
import com.thelasteyes.backend.Model.Company;
import com.thelasteyes.backend.Service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Empresas", description = "Endpoints para gerenciamento completo (CRUD) da entidade Company.")
@SecurityRequirement(name = "bearerAuth")
public class CompanyController {

    @Autowired
        private CompanyService companyService;


    //Retorna todas as empresas
    @Operation(summary = "Lista todas as empresas", description = "Retorna uma lista paginada de empresas, permitindo filtragem por CNPJ, nome, email, etc.")
    @ApiResponse(responseCode = "200", description = "Lista paginada de empresas retornada com sucesso.")
    @GetMapping
    public ResponseEntity<Page<GetCompanyDto>> getAllCompanies(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable page,
            CompanyFilter filter ){
    return ResponseEntity.ok(companyService.getAllCompanies(page, filter));

    }

    //Retorna empresa por id
    @Operation(summary = "Busca empresa por ID", description = "Retorna os detalhes de uma empresa específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa encontrada."),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada (ResourceNotFoundException).")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetCompanyDto> getCompanyById(@PathVariable Long id){
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    //Cadastra empresa
    @Operation(summary = "Cadastra uma nova empresa", description = "Cria um novo registro de empresa (Company).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empresa criada com sucesso (URI no Header Location)."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (DTO Validation ou Email/CNPJ em uso).")
    })
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid PostCompanyDto dto){
        Company savedCompany = companyService.postCompany(dto);
        return ResponseEntity.created(URI.create("/company" + savedCompany.getId())).build();
    }

    //Atualiza dados da empresa
    @Operation(summary = "Atualiza dados de uma empresa existente", description = "Modifica os dados de uma empresa específica pelo ID. Atualizações parciais são suportadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Empresa atualizada com sucesso (No Content)."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou Email/CNPJ já em uso."),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCompany(@PathVariable Long id, @Valid @RequestBody PutCompanyDto dto){
        companyService.putCompany(id, dto);
        return ResponseEntity.noContent().build();
    }

    // Deleta empresa
    @Operation(summary = "Deleta uma empresa", description = "Remove permanentemente uma empresa pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Empresa deletada com sucesso (No Content)."),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id){
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }

}
