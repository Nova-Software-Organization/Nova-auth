/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Auth.Domain.service.resetPassword;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth.auth.modules.Auth.Application.DTOs.mail.AuthUserResetPassawordDTO;
import com.auth.auth.modules.Auth.Application.DTOs.response.ResponseMessageDTO;
import com.auth.auth.modules.Auth.Domain.event.AuthResetPasswordPublishEventListenerComponent;
import com.auth.auth.modules.Auth.Domain.provider.resetPassword.GenerateRandomCodeResetPasswordProvider;
import com.auth.auth.modules.Auth.Domain.service.cryptography.AnonymizationService;
import com.auth.auth.modules.Auth.Infra.persistence.entity.UserEntity;
import com.auth.auth.modules.Auth.Infra.persistence.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ResetPasswordAuthUserService {
    private UserRepository userRepository;
    private AnonymizationService anonymizationService;

    @Autowired
    public ResetPasswordAuthUserService(UserRepository userRepository, AnonymizationService anonymizationService) {
        this.userRepository = userRepository;
        this.anonymizationService = anonymizationService;
    }

    public ResponseEntity<ResponseMessageDTO> sendResetPasswordEmail(AuthUserResetPassawordDTO authUserResetPassawordDTO) {
        try {
            UserEntity userByEmail = userRepository.findByEmail(authUserResetPassawordDTO.getEmail());
            if (userByEmail != null) {
                return applyAndRespond(userByEmail);
            }

            // Se o usuário por e-mail não foi encontrado, tenta buscar pelo nome de usuário
            return Optional.ofNullable(userRepository.findByUsername(authUserResetPassawordDTO.getUsername()))
                    .map(this::applyAndRespond)
                    .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ResponseMessageDTO("Erro", this.getClass().getName(),
                                    "Nenhum usuário encontrado para o e-mail ou nome de usuário fornecido", null)));
        } catch (Exception e) {
            log.error("Erro ao enviar email de redefinição de senha", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessageDTO("Erro", this.getClass().getName(),
                            "Erro ao processar a solicitação de redefinição de senha: " + e.getMessage(), null));
        }
    }

    private ResponseEntity<ResponseMessageDTO> applyAndRespond(UserEntity user) {
        try {
            return send(user);
        } catch (Exception e) {
            log.error("Erro ao enviar email de redefinição de senha", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessageDTO("Erro", this.getClass().getName(),
                            "Erro ao processar a solicitação de redefinição de senha: " + e.getMessage(), null));
        }
    }

    private ResponseEntity<ResponseMessageDTO> send(UserEntity user) {
        GenerateRandomCodeResetPasswordProvider generateRandomCodeResetPasswordProvider = new GenerateRandomCodeResetPasswordProvider();
        String resetCode = generateRandomCodeResetPasswordProvider.generateRandomCode();
        user.setResetPasswordToken(resetCode);
        user.setResetPasswordTokenExpiration(LocalDateTime.now());
        userRepository.save(user);
        
        AuthResetPasswordPublishEventListenerComponent authResetPasswordPublish = new AuthResetPasswordPublishEventListenerComponent(anonymizationService);
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseMessageDTO("Sucesso",
                 this.getClass().getName(), "Email enviado com sucesso", null));
    }
}
