/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Auth.Application.DTOs.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "O campo 'password' não pode estar em branco.")
    @JsonProperty("password")
    private String password;

    @NotBlank(message = "O campo 'username' não pode estar em branco.")
    @JsonProperty("username")
    private String username;
}
