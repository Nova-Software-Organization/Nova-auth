/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Auth.Domain.event;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth.auth.modules.Auth.Application.DTOs.mail.AuthRedefinePasswordConcludedDTO;
import com.auth.auth.modules.Auth.Domain.service.cryptography.AnonymizationService;
import com.auth.auth.modules.Auth.Infra.persistence.entity.UserEntity;

@Component
public class AuthRedefinePasswordConcludedPublishEventListenerComponent {
    private AnonymizationService anonymizationService;

    @Autowired
    public AuthRedefinePasswordConcludedPublishEventListenerComponent(AnonymizationService anonymizationService) {
        this.anonymizationService = anonymizationService;
    }

    public void publishCustomerResetPasswordEvent(UserEntity user) {
        String emailUser = anonymizationService.decrypt(user.getEmail());
        AuthRedefinePasswordConcludedDTO redefinePasswordPasswordDTO = new AuthRedefinePasswordConcludedDTO();
        redefinePasswordPasswordDTO.setName(user.getUsername());
        redefinePasswordPasswordDTO.setEmail(emailUser);
        redefinePasswordPasswordDTO.setSubject("Senha redefinida com sucesso!");
        redefinePasswordPasswordDTO.setMessage("Parabéns sua senha foi alterada");

        Message message = MessageBuilder
                .withBody(SerializationUtils.serialize(redefinePasswordPasswordDTO))
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setHeader("subject", redefinePasswordPasswordDTO.getSubject())
                .build();
        
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.send("auth-message", message);
    }
}
