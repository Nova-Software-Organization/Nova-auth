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

import com.auth.auth.modules.Auth.Application.DTOs.mail.AuthUserCreatedDTO;
import com.auth.auth.modules.Auth.Domain.service.cryptography.AnonymizationService;
import com.auth.auth.modules.Auth.Infra.persistence.entity.UserEntity;

@Component
public class AuthCreatedPublishEventListenerComponent {
    private AnonymizationService anonymizationService;

    @Autowired
    public AuthCreatedPublishEventListenerComponent(AnonymizationService anonymizationService) {
        this.anonymizationService = anonymizationService;
    }

    public void publishCustomerCreatedEvent(UserEntity user) {
        String emailUser = anonymizationService.decrypt(user.getEmail());
        AuthUserCreatedDTO authUserCreated = new AuthUserCreatedDTO();
        authUserCreated.setName(user.getUsername());
        authUserCreated.setEmail(emailUser);
        authUserCreated.setSubject("Conta criada com sucesso");
        authUserCreated.setMessage("Bem vindo a Nova software sua conta foi criada com sucesso!");

        Message message = MessageBuilder
                .withBody(SerializationUtils.serialize(authUserCreated))
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setHeader("subject", authUserCreated.getSubject())
                .build();
        
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.send("auth-message", message);
    }
}
