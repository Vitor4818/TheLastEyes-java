package com.thelasteyes.backend.Config.Security;

import com.thelasteyes.backend.Dto.LoginDto;
import com.thelasteyes.backend.Dto.TokenDto;
import com.thelasteyes.backend.Model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticação", description = "Endpoints para Login e geração de Tokens JWT.")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationService authenticationService;

    @Operation(summary = "Realiza o login e gera Token JWT",
            description = "Autentica o usuário com email e senha (BCrypt) e retorna um JSON Web Token (JWT) válido para acesso a endpoints protegidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida. Token JWT retornado."),
            @ApiResponse(responseCode = "400", description = "Credenciais inválidas ou dados malformados."),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas (senha ou email incorretos).")
    })
    @PostMapping
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginDto dto) {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(dto.login(), dto.password());

        Authentication authentication = authenticationManager.authenticate(authToken);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String login = userDetails.getUsername();
        User userEntity = authenticationService.findUserEntityByEmail(login);
        String token = tokenService.generateToken(userEntity);

        return ResponseEntity.ok(new TokenDto(token));
    }
}