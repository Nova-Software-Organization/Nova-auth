/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Auth.Application.DTOs.request;

import com.auth.auth.modules.Customer.Application.DTOs.registration.CustomerAddressDTO;
import com.auth.auth.modules.Customer.Application.DTOs.registration.CustomerDTO;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder("Registration")
public class RegistrationRequest {
	private CustomerDTO customerDTO;
	private CustomerAddressDTO customerAddressDTO;

	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	public void setcustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	public CustomerAddressDTO getCustomerAddressDTO() {
		return customerAddressDTO;
	}

	public void setCustomerAddressDTO(CustomerAddressDTO customerAddressDTO) {
		this.customerAddressDTO = customerAddressDTO;
	}
}

