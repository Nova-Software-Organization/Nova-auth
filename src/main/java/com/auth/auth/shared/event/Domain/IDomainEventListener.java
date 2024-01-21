/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.shared.event.Domain;

import org.springframework.context.ApplicationEvent;

public interface IDomainEventListener<T extends ApplicationEvent> {
    void onEvent(T event);
}
