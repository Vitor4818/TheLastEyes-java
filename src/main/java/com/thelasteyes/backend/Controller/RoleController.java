package com.thelasteyes.backend.Controller;

import com.thelasteyes.backend.Dto.GetRoleDto;
import com.thelasteyes.backend.Dto.RoleFilter;
import com.thelasteyes.backend.Service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@Tag(name = "Perfis (Roles)", description = "Endpoints para consulta da tabela de domínio de Perfis de Usuário.")
@SecurityRequirement(name = "bearerAuth") // Requisito JWT para acesso
public class RoleController {

    @Autowired
    private RoleService roleService;

    // Retorna todos os perfis
    @Operation(summary = "Lista todos os perfis de usuário",
            description = "Retorna uma lista paginada de perfis (Roles), permitindo a filtragem por nome.")
    @ApiResponse(responseCode = "200", description = "Lista paginada de perfis retornada com sucesso.")
    @GetMapping
    public ResponseEntity<Page<GetRoleDto>> getAllRoles(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable page,
            RoleFilter filter) {
        return ResponseEntity.ok(roleService.getAllRoles(page, filter));
    }

    // Retorna um perfil por id
    @Operation(summary = "Busca perfil por ID",
            description = "Retorna os detalhes de um perfil de usuário específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Perfil não encontrado (ResourceNotFoundException).")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetRoleDto> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }
}