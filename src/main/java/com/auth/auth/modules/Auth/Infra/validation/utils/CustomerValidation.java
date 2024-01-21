/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Auth.Infra.validation.utils;

import org.springframework.stereotype.Component;

import com.auth.auth.modules.Customer.Application.DTOs.registration.CustomerAddressDTO;
import com.auth.auth.modules.Customer.Application.DTOs.registration.CustomerDTO;

@Component
public class CustomerValidation {
    public String isValidCustomerAndCustomerAndCustomerAddress(CustomerDTO customerDTO,
			CustomerAddressDTO customerAddressDTO) {

		if (customerDTO.getName() == null || customerDTO.getName().isEmpty()) {
			return "Nome do cliente não pode estar vazio.";
		}

		return null;
	}
}
