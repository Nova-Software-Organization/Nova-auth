/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Auth.Application.DTOs.mail;

import lombok.Data;

@Data
public class AuthRedefinePasswordConcludedDTO {
    private String name;
    private String email;
    private String message;
    private String subject;
}
