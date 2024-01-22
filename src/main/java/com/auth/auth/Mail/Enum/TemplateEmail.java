/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.Mail.Enum;

public enum TemplateEmail {
    RESET_PASSWORD("reset-password"),
    USER_CREATED("user-created"),
    REDEFINE_PASSWORD("redefine_password");

    private final String type;

    TemplateEmail(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
