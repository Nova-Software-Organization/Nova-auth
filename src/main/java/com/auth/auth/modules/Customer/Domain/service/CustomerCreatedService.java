/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Customer.Domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.auth.modules.Customer.Infra.persistence.entity.CustomerEntity;
import com.auth.auth.modules.Customer.Infra.persistence.repository.CustomerRepository;
import com.auth.auth.modules.CustomerAddress.Infra.persistence.entity.CustomerAddressEntity;

@Service
public class CustomerCreatedService {
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerCreatedService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

	public CustomerEntity createClient(CustomerEntity customerEntity, CustomerAddressEntity addressEntity) {
		customerEntity.setAddress(addressEntity);
		CustomerEntity savedClient = customerRepository.save(customerEntity);
		return savedClient;
	}
}
