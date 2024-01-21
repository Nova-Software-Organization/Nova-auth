/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Auth.Application.useCases.ValidateToken;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.auth.modules.Auth.Application.DTOs.token.AuthAccessToken;
import com.auth.auth.modules.Auth.Infra.validation.utils.JwtUtills;

import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("v1/auth")
public class ValidateTokenController {
    private JwtUtills jwtUtills;

    @PostMapping(path = "/valida/token")
    @Tag(name = "Valida token", description = "Verifica se o token do usuário está expirado")
    @Operation(summary = "Rota responsavel por validar o token do usuário para ver se já está expirado ou não")
    public ResponseEntity<String> validateToken(@Valid @RequestBody AuthAccessToken token) {
        String valueToken = token.getAccess_token();

        if (StringUtils.hasText(valueToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido! O token não pode ser vazio ou nulo.");
        }

        try {
            jwtUtills.validateToken(valueToken);
            return ResponseEntity.status(HttpStatus.OK).body("Token válido");
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido! " + e.getMessage());
        }
    }
}
