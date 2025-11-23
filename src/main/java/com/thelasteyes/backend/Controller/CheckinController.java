package com.thelasteyes.backend.Controller;

import com.thelasteyes.backend.Dto.CheckinDto;
import com.thelasteyes.backend.Dto.CheckinResultDto;
import com.thelasteyes.backend.Model.User;
import com.thelasteyes.backend.Service.CheckinProducerService;
import com.thelasteyes.backend.Config.Security.AuthenticationService;
import com.thelasteyes.backend.Service.CheckinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkin")
@Tag(name = "Check-ins de Humor", description = "Endpoints para registro, processamento assíncrono (RabbitMQ/IA Generativa) e consulta dos resultados de humor.")
@SecurityRequirement(name = "bearerAuth")
public class CheckinController {

    @Autowired
    private CheckinProducerService producerService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CheckinService checkinService;

    @Operation(summary = "Registra o Check-in e inicia o processamento assíncrono",
            description = "Salva o texto de humor no DB, publica o ID na fila RabbitMQ e retorna 202 ACCEPTED. O resultado do processamento é persistido posteriormente.")
    @ApiResponse(responseCode = "202", description = "Pedido aceito e processamento iniciado.")
    @ApiResponse(responseCode = "400", description = "Dados inválidos.")
    @PostMapping
    public ResponseEntity<?> performCheckin(
            @RequestBody @Valid CheckinDto dto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        String userEmail = userDetails.getUsername();
        Long userId = dto.userId();
        Long checkinId = producerService.createAndSendToQueue(userId, dto.text());
        return ResponseEntity.accepted().body(
                "Pedido de classificação aceito. O resultado será processado e estará disponível em breve. " +
                        "ID do Checkin: " + checkinId
        );
    }

    @Operation(summary = "Retorna todos os check-ins do usuário autenticado",
            description = "Lista todos os registros de check-in (incluindo status e resultados de IA) de forma paginada.")
    @ApiResponse(responseCode = "200", description = "Lista de check-ins retornada com sucesso.")
    @GetMapping("/results")
    public ResponseEntity<Page<CheckinResultDto>> getResultsByUserId(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User userEntity = authenticationService.findUserEntityByEmail(userEmail);
        Long userId = userEntity.getId();
        Page<CheckinResultDto> results = checkinService.getResultsByUserId(userId, pageable);
        return ResponseEntity.ok(results);
    }

    @Operation(summary = "Retorna o último check-in e sugestão de IA do usuário",
            description = "Busca o registro mais recente (por ID) e retorna a sugestão de saúde mental gerada pelo Gemini, sem paginação.")
    @ApiResponse(responseCode = "200", description = "Último resultado encontrado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Nenhum check-in encontrado para o usuário.")
    @GetMapping("/latest-result")
    public ResponseEntity<CheckinResultDto> getLastResultByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User userEntity = authenticationService.findUserEntityByEmail(userEmail);
        Long userId = userEntity.getId();
        CheckinResultDto latestResult = checkinService.getLastResultByUserId(userId);
        return ResponseEntity.ok(latestResult);
    }
}