package com.thelasteyes.backend.Controller;

import com.thelasteyes.backend.Dto.JobFilter;
import com.thelasteyes.backend.Dto.GetJobDto;
import com.thelasteyes.backend.Dto.PostJobDto;
import com.thelasteyes.backend.Dto.PutJobDto;
import com.thelasteyes.backend.Model.Job;
import com.thelasteyes.backend.Service.JobService;
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
@RequestMapping("/jobs")
@Tag(name = "Empregos (Jobs)", description = "Endpoints para gerenciamento do registro de vínculo empregatício e histórico.")
@SecurityRequirement(name = "bearerAuth")
public class JobController {

    @Autowired
    private JobService jobService;

    // Retorna todos os empregos
    @Operation(summary = "Lista todos os registros de empregos",
            description = "Retorna uma lista paginada de registros de trabalho, permitindo filtros por cargo, tipo de contrato, ID da empresa, etc.")
    @ApiResponse(responseCode = "200", description = "Lista paginada de empregos retornada com sucesso.")
    @GetMapping
    public ResponseEntity<Page<GetJobDto>> getAllJobs(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable page,
            JobFilter filter) {

        return ResponseEntity.ok(jobService.getAllJobs(page, filter));
    }

    // Retorna emprego por id
    @Operation(summary = "Busca registro de emprego por ID",
            description = "Retorna os detalhes de um vínculo empregatício específico, incluindo a empresa e o usuário associado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Registro de emprego não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetJobDto> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    // Cadastra novo emprego
    @Operation(summary = "Cadastra um novo registro de emprego",
            description = "Cria um novo vínculo empregatício, requerendo o ID da empresa e os detalhes do cargo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro de emprego criado com sucesso (URI no Header Location)."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou ID da empresa/usuário inexistente.")
    })
    @PostMapping
    public ResponseEntity<Void> createJob(@RequestBody @Valid PostJobDto dto) {
        Job savedJob = jobService.postJob(dto);
        return ResponseEntity.created(URI.create("/jobs/" + savedJob.getId())).build();
    }

    // Atualiza dados do emprego
    @Operation(summary = "Atualiza dados do registro de emprego",
            description = "Modifica campos de um vínculo empregatício existente. Atualizações parciais são suportadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registro atualizado com sucesso (No Content)."),
            @ApiResponse(responseCode = "404", description = "Registro de emprego não encontrado.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateJob(@PathVariable Long id, @Valid @RequestBody PutJobDto dto) {
        jobService.putJob(id, dto);
        return ResponseEntity.noContent().build();
    }

    // Deleta emprego
    @Operation(summary = "Deleta um registro de emprego",
            description = "Remove permanentemente um vínculo empregatício pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registro deletado com sucesso (No Content)."),
            @ApiResponse(responseCode = "404", description = "Registro de emprego não encontrado.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
}