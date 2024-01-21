/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Auth.Domain.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.auth.auth.modules.Auth.Domain.service.cryptography.AnonymizationService;
import com.auth.auth.modules.Auth.Infra.persistence.repository.UserRepository;
import com.auth.auth.shared.event.Domain.IDomainEventListener;

@Component
public class AuthCreatedEventListener implements IDomainEventListener<AuthCreated> {
    private final UserRepository userRepository;
    private final AnonymizationService anonymizationService;

    @Autowired
    public AuthCreatedEventListener(UserRepository userRepository, AnonymizationService anonymizationService) {
        this.userRepository = userRepository;
        this.anonymizationService = anonymizationService;
    }

    @Override
    @EventListener
    public void onEvent(AuthCreated event) {
        Long createdUser = event.getCreatedUser();
    }
}
