/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Auth.Infra.validation;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {
    public String isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return "A senha não pode estar em branco.";
        }

        if (password.length() < 8) {
            return "A senha deve ter pelo menos 8 caracteres.";
        }

        if (!password.matches(".*[A-Z].*")) {
            return "A senha deve conter pelo menos uma letra maiúscula.";
        }

        if (!password.matches(".*\\d.*")) {
            return "A senha deve conter pelo menos um número.";
        }

        return null;
    }

    public boolean isValidEmailBoolean(String email) {
		return email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
	}
}
