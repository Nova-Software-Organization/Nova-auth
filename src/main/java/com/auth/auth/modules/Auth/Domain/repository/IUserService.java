/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Auth.Domain.repository;

import com.auth.auth.modules.Auth.Infra.persistence.entity.UserEntity;
import com.auth.auth.modules.Customer.Infra.persistence.entity.CustomerEntity;

public interface IUserService {
    UserEntity createUser(UserEntity userEntity, CustomerEntity customerEntity);
}
