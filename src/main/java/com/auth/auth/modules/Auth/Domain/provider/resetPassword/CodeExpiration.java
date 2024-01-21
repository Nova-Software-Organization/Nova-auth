/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Auth.Domain.provider.resetPassword;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.auth.modules.Auth.Infra.persistence.entity.UserEntity;
import com.auth.auth.modules.Auth.Infra.persistence.repository.UserRepository;

@Service
public class CodeExpiration {
    private UserRepository userRepository;

    @Autowired
    public CodeExpiration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isCodeExpired(String code) {
        return userRepository.findByResetPasswordToken(code)
                .map(UserEntity::getResetPasswordTokenExpiration)
                .map(expiration -> expiration.isBefore(LocalDateTime.now().minusMinutes(10)))
                .orElse(true);
    }
}
