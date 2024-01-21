/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Customer.Domain.repository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.auth.auth.modules.Customer.Application.DTOs.registration.CustomerAddressDTO;
import com.auth.auth.modules.Customer.Application.DTOs.registration.CustomerDTO;
import com.auth.auth.modules.Customer.Application.DTOs.response.ResponseMessageDTO;

@Repository
public interface IClientService {
    public ResponseEntity<ResponseMessageDTO> update(Long clientId, CustomerDTO updatedClient, CustomerAddressDTO updatedAddress);
}
