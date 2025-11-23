package com.thelasteyes.backend.Controller;

import com.thelasteyes.backend.Dto.UserFilter;
import com.thelasteyes.backend.Dto.GetUserDto;
import com.thelasteyes.backend.Dto.PostUserDto;
import com.thelasteyes.backend.Dto.PutUserDto;
import com.thelasteyes.backend.Model.User;
import com.thelasteyes.backend.Service.UserService;
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
@RequestMapping("/users")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento completo (CRUD) da entidade User.")
@SecurityRequirement(name = "bearerAuth") // Aplica o requisito JWT a todos os métodos
public class UserController {

    @Autowired
    private UserService userService;

    // Retorna todos os usuarios
    @Operation(summary = "Lista todos os usuários",
            description = "Retorna uma lista paginada de usuários, permitindo filtragem por nome, email, CPF e Perfil (Role).")
    @ApiResponse(responseCode = "200", description = "Lista paginada de usuários retornada com sucesso.")
    @GetMapping
    public ResponseEntity<Page<GetUserDto>> getAllUsers(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable page,
            UserFilter filter) {

        return ResponseEntity.ok(userService.getAllUsers(page, filter));
    }

    // Retorna usuario pór ID
    @Operation(summary = "Busca usuário por ID",
            description = "Retorna os detalhes de um usuário específico, incluindo seu perfil (Role) e registro de trabalho atual (CurrentJob).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado (ResourceNotFoundException).")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetUserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Cadastra novo usuario
    @Operation(summary = "Cadastra um novo usuário",
            description = "Cria um novo registro de usuário, com validação de unicidade para CPF/Email. Pode incluir a vinculação de um Job existente (currentJobId).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso (URI no Header Location)."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (DTO Validation)."),
            @ApiResponse(responseCode = "409", description = "Conflito de dados (Email ou CPF já em uso).")
    })
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid PostUserDto dto) {
        User savedUser = userService.postUser(dto);
        return ResponseEntity.created(URI.create("/users/" + savedUser.getId())).build();
    }

    // Atualiza dados do usuario
    @Operation(summary = "Atualiza dados de um usuário existente",
            description = "Modifica campos de um usuário específico pelo ID, como nome, telefone ou perfil (Role). Também permite a vinculação/alteração do trabalho atual (currentJobId).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário atualizado com sucesso (No Content)."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "409", description = "Conflito de dados (Tentativa de usar Email/CPF já existente).")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @Valid @RequestBody PutUserDto dto) {
        userService.putUser(id, dto);
        return ResponseEntity.noContent().build();
    }

    // Deletar usuario
    @Operation(summary = "Deleta um usuário",
            description = "Remove permanentemente um usuário pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso (No Content)."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}