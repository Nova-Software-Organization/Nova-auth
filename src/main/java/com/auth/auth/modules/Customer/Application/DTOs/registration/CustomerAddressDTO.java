/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Customer.Application.DTOs.registration;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@JsonPropertyOrder("customerAddress")
public class CustomerAddressDTO {
    @NotBlank
    @Pattern(regexp = "^[^\\s].*[^\\s]$", message = "O nome da rua não pode começar ou terminar com espaços em branco.")
    private String road;

    @NotBlank
    @Pattern(regexp = "^[^\\s].*[^\\s]$", message = "O bairro não pode começar ou terminar com espaços em branco.")
    private String neighborhood;

    @NotBlank
    @Pattern(regexp = "^[^\\s].*[^\\s]$", message = "O número da casa não pode começar ou terminar com espaços em branco.")
    private String housenumber;

    @NotBlank
    @Pattern(regexp = "^[^\\s].*[^\\s]$", message = "O estado não pode começar ou terminar com espaços em branco.")
    private String state;

    @NotBlank
    @Pattern(regexp = "^[^\\s].*[^\\s]$", message = "O CEP não pode começar ou terminar com espaços em branco.")
    private String cep;
}
