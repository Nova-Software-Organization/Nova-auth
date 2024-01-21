/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Auth.Domain.exception;

public class InvalidGenerateTokenException extends Exception {
    public InvalidGenerateTokenException(String menssage) {
        super(menssage);
    }
}
