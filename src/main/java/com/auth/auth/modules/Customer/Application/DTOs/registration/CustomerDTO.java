/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Customer.Application.DTOs.registration;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@JsonPropertyOrder("customer")
public class CustomerDTO {
    @NotNull
    private Boolean isAdmin;

    @NotBlank
    @Pattern(regexp = "^[^\\s].*[^\\s]$", message = "O nome não pode começar ou terminar com espaços em branco.")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[^\\s].*[^\\s]$", message = "O sobrenome não pode começar ou terminar com espaços em branco.")
    private String lastName;

    @NotBlank
    @Pattern(regexp = "^[^\\s].*[^\\s]$", message = "O CPF não pode conter espaços em branco.")
    private String cpf;

    @NotBlank
    @Pattern(regexp = "^[^\\s].*[^\\s]$", message = "O telefone não pode conter espaços em branco.")
    private String phone;

    @Positive
    private int age;

    @NotBlank
    @Pattern(regexp = "^[^\\s].*[^\\s]$", message = "O gênero não pode começar ou terminar com espaços em branco.")
    private String gender;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^[^\\s].*[^\\s]$", message = "A senha não pode começar ou terminar com espaços em branco.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "A senha deve conter pelo menos 8 caracteres, incluindo letras e números.")
    private String password;

    @NotNull
    @Past
    private Date birthDate;

    @Pattern(regexp = "^[^\\s].*[^\\s]$", message = "A data de criação não pode começar ou terminar com espaços em branco.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$", message = "O formato da data deve ser yyyy-MM-ddTHH:mm:ss.")
    private Date dateCreate;
}
