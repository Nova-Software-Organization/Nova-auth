/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Auth.Domain.repository;

import org.springframework.http.ResponseEntity;

import com.auth.auth.modules.Auth.Application.DTOs.response.ResponseMessageDTO;
import com.auth.auth.modules.Customer.Application.DTOs.registration.CustomerAddressDTO;
import com.auth.auth.modules.Customer.Application.DTOs.registration.CustomerDTO;

public interface IAutheticationRegister {
    ResponseEntity<ResponseMessageDTO> register(CustomerDTO customerDTO,
            CustomerAddressDTO CustomerAddressDTO);
}
