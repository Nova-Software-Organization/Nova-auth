/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Customer.Application.useCase.CustomerUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.auth.modules.Customer.Application.DTOs.registration.CustomerAddressDTO;
import com.auth.auth.modules.Customer.Application.DTOs.registration.CustomerDTO;
import com.auth.auth.modules.Customer.Application.DTOs.response.ResponseMessageDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("v1/cliente")
public class CustomerUpdateController {
    private CustomerUpdateUseCase customerUpdateUseCase;

    @Autowired
    public CustomerUpdateController(CustomerUpdateUseCase customerUpdateUseCase) {
        this.customerUpdateUseCase = customerUpdateUseCase;
    }

    @PutMapping(path = "/atualizar/{id}")
    @Operation(summary = "Atualizar Cliente", description = "Atualiza os dados de um cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<ResponseMessageDTO> handle(
            @PathVariable Long id,
            @RequestBody CustomerDTO customerDTO,
            CustomerAddressDTO customerAddressDTO) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                        .body(new ResponseMessageDTO("ID do cliente inválido", this.getClass().getName(), null));
            }

            if (customerDTO == null) {
                return ResponseEntity.badRequest()
                    .body(new ResponseMessageDTO("Erro os dados não podem estar vazios!", this.getClass().getName(), null));
            }

            return customerUpdateUseCase.execute(id, customerDTO, customerAddressDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessageDTO("Ocorreu um erro ao processar a requisição!", this.getClass().getName(), e.getMessage()));
        }
    }
}