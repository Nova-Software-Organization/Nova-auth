/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Auth.Application.DTOs.token;

import lombok.Data;

@Data
public class TokenResetPasswordDTO {
    private String tokenPassword;
    private String newPassword;
}