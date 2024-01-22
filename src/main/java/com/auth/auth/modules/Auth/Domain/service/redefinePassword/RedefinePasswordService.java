/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Auth.Domain.service.redefinePassword;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth.auth.modules.Auth.Application.DTOs.response.ResponseMessageDTO;
import com.auth.auth.modules.Auth.Application.DTOs.token.TokenResetPasswordDTO;
import com.auth.auth.modules.Auth.Domain.event.AuthRedefinePasswordConcludedPublishEventListenerComponent;
import com.auth.auth.modules.Auth.Domain.provider.resetPassword.CodeExpiration;
import com.auth.auth.modules.Auth.Domain.service.cryptography.AnonymizationService;
import com.auth.auth.modules.Auth.Infra.persistence.entity.UserEntity;
import com.auth.auth.modules.Auth.Infra.persistence.repository.UserRepository;
import com.auth.auth.modules.Auth.Infra.validation.PasswordValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RedefinePasswordService {
    private UserRepository userRepository;
    private AnonymizationService anonymizationService;
    private CodeExpiration codeExpiration;
    private AuthRedefinePasswordConcludedPublishEventListenerComponent authRedefinePasswordConcludedPublishEventListenerComponent;

    @Autowired
    public RedefinePasswordService(UserRepository userRepository,
            AnonymizationService anonymizationService,
            CodeExpiration codeExpiration,
            AuthRedefinePasswordConcludedPublishEventListenerComponent authRedefinePasswordConcludedPublishEventListenerComponent
    ) {
        this.userRepository = userRepository;
        this.anonymizationService = anonymizationService;
        this.codeExpiration = codeExpiration;
        this.authRedefinePasswordConcludedPublishEventListenerComponent = authRedefinePasswordConcludedPublishEventListenerComponent;
    }

    public ResponseEntity<ResponseMessageDTO> redefinePassword(TokenResetPasswordDTO tokenResetPasswordDTO) {
        try {
            boolean isCodeValidad = codeExpiration.isCodeExpired(tokenResetPasswordDTO.getTokenPassword());
            if (!isCodeValidad) {
                log.info("Codigo inválido: {}");
                return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessageDTO("Token expirado!", this.getClass().getName(), "Token expirado, tempo minimo ultrapassado", null));
            }

            Optional<UserEntity> userOptional = userRepository
                    .findByResetPasswordToken(tokenResetPasswordDTO.getTokenPassword());
            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();
                String newPassword = tokenResetPasswordDTO.getNewPassword();

                String validPassword = new PasswordValidator().isValidPassword(newPassword);
                if (validPassword.equals(null)) {
                    String anonymizedNewPassword = anonymizationService.encrypt(newPassword);
                    user.setPassword(anonymizedNewPassword);
                    userRepository.save(user);

                    log.info("Redefinição senha feita com sucesso!: {}", user.getUsername());
                    authRedefinePasswordConcludedPublishEventListenerComponent.publishCustomerResetPasswordEvent(user);
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(new ResponseMessageDTO("Senha alterada com sucesso!", this.getClass().getName(), null,
                                    null));
                } else {
                    log.warn("Novo formato de senha inválido recebido para o usuário: {}", user.getUsername());
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(new ResponseMessageDTO("Erro de validação", this.getClass().getName(),
                                    "A nova senha não atende aos requisitos de segurança", null));
                }
            } else {
                log.warn("Nenhum usuário encontrado para o token de redefinição de senha fornecido : {}");
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ResponseMessageDTO("Erro", this.getClass().getName(),
                                "Token de redefinição de senha inválido ou expirado", null));
            }
        } catch (Exception e) {
            log.error("Ocorreu um erro ao redefinir a senha", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessageDTO("Erro", this.getClass().getName(), "Ocorreu um erro ao processar a requisição", null));
        }
    }
}
