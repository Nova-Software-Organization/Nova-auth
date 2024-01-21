/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Customer.Application.DTOs;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder("customerRequest")
public class CustomerRequest {
	private String name;
	private String email;
	private String cpf;
	private String phone;
	private String password;
	private String gender;
	private String lastName;
	private int age;
}
