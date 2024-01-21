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

import com.auth.auth.modules.Auth.Application.DTOs.mail.ResetPasswordMessageDTO;
import com.auth.auth.modules.Auth.Domain.service.cryptography.AnonymizationService;
import com.auth.auth.modules.Auth.Infra.persistence.entity.UserEntity;

@Component
public class AuthResetPasswordPublishEventListenerComponent {
    private AnonymizationService anonymizationService;

    @Autowired
    public AuthResetPasswordPublishEventListenerComponent(AnonymizationService anonymizationService) {
        this.anonymizationService = anonymizationService;
    }

    public void publishCustomerResetPasswordEvent(UserEntity user, String resetCode) {
        String emailUser = anonymizationService.decrypt(user.getEmail());
        ResetPasswordMessageDTO resetPasswordDTO = new ResetPasswordMessageDTO();
        resetPasswordDTO.setGenerateTokenCode(resetCode);
        resetPasswordDTO.setName(user.getUsername());
        resetPasswordDTO.setEmail(emailUser);
        resetPasswordDTO.setSubject("Solicitação de redefinição de senha");
        resetPasswordDTO.setMessage("Seu codigo de redefinição de senha e:" + resetCode);

        Message message = MessageBuilder
                .withBody(SerializationUtils.serialize(resetPasswordDTO))
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setHeader("subject", resetPasswordDTO.getSubject())
                .build();
        
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.send("auth-message", message);
    }
}
