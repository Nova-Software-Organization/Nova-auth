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
import org.springframework.stereotype.Service;

import com.auth.auth.modules.Customer.Application.DTOs.registration.CustomerAddressDTO;
import com.auth.auth.modules.Customer.Application.DTOs.registration.CustomerDTO;
import com.auth.auth.modules.Customer.Application.DTOs.response.ResponseMessageDTO;
import com.auth.auth.modules.Customer.Domain.service.CustomerUpdateService;

@Service
public class CustomerUpdateUseCase {
    private CustomerUpdateService customerServiceImp;

    @Autowired
    public CustomerUpdateUseCase(CustomerUpdateService customerServiceImp) {
        this.customerServiceImp = customerServiceImp;
    }

    public ResponseEntity<ResponseMessageDTO> execute(Long id, CustomerDTO customerDTO,
            CustomerAddressDTO customerAddressDTO) {
        try {
            return customerServiceImp.update(id, customerDTO, customerAddressDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessageDTO("Ocorreu um erro ao processar a requisição!", this.getClass().getName(), e.getMessage()));
        }
    }
}
