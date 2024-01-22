/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Auth.Application.useCases.RegisterCustomer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth.auth.modules.Auth.Application.DTOs.response.ResponseMessageDTO;
import com.auth.auth.modules.Auth.Domain.authentication.AutheticationRegisterService;
import com.auth.auth.modules.Customer.Application.DTOs.registration.CustomerAddressDTO;
import com.auth.auth.modules.Customer.Application.DTOs.registration.CustomerDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RegisterCustomerUseCase {
    private AutheticationRegisterService autheticationRegister;

    @Autowired
    public RegisterCustomerUseCase(AutheticationRegisterService autheticationRegister) {
        this.autheticationRegister = autheticationRegister;
    }

    public ResponseEntity<ResponseMessageDTO> execute(CustomerDTO customerDTO, CustomerAddressDTO customerAddressDTO) {
        try {
            return Optional.ofNullable(customerDTO)
                    .map(dto -> Optional.ofNullable(customerAddressDTO)
                            .map(addressDTO -> autheticationRegister.register(dto, addressDTO))
                            .filter(response -> response.getStatusCode() == HttpStatus.CREATED)
                            .orElseGet(() -> ResponseEntity.badRequest().body(new ResponseMessageDTO(
                                    "Erro: dados de cliente ou endereço não fornecidos",
                                    this.getClass().getSimpleName(), null, null))))
                    .orElseThrow(
                            () -> new IllegalArgumentException("Erro: dados de cliente ou endereço não fornecidos"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessageDTO("Ocorreu um erro ao tentar processar a requisição!", this.getClass().getSimpleName(),
                            e.getMessage(), null));
        }
    }
}
