/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Customer.Domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth.auth.modules.Customer.Application.DTOs.registration.CustomerAddressDTO;
import com.auth.auth.modules.Customer.Application.DTOs.registration.CustomerDTO;
import com.auth.auth.modules.Customer.Application.DTOs.response.ResponseMessageDTO;
import com.auth.auth.modules.Customer.Domain.repository.IClientService;
import com.auth.auth.modules.Customer.Infra.persistence.entity.CustomerEntity;
import com.auth.auth.modules.Customer.Infra.persistence.repository.CustomerRepository;
import com.auth.auth.modules.CustomerAddress.Infra.persistence.entity.CustomerAddressEntity;
import com.auth.auth.modules.CustomerAddress.Infra.persistence.repository.CustomerAddressRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerUpdateService implements IClientService {
	private CustomerRepository customerRepository;
	private CustomerAddressRepository addressRepository;

	@Autowired
	public CustomerUpdateService(CustomerRepository customerRepository, CustomerAddressRepository addressRepository) {
		this.customerRepository = customerRepository;
		this.addressRepository = addressRepository;
	}

	@Transactional
	public ResponseEntity<ResponseMessageDTO> update(
		Long clientId,
		CustomerDTO updatedClient,
		CustomerAddressDTO updatedAddress
	) {
		Optional<CustomerEntity> existingClient = customerRepository.findById(clientId);
		Optional<CustomerAddressEntity> existingAddress = addressRepository.findById(clientId);

		if (existingClient.isPresent()) {
			CustomerEntity clientToUpdate = existingClient.get();
			clientToUpdate.setPhone(updatedClient.getPhone());
		}

		if (existingAddress.isPresent()) {
			CustomerAddressEntity addressToUpdate = existingAddress.get();
			addressToUpdate.setCep(updatedAddress.getCep() != addressToUpdate.getCep() ? updatedAddress.getCep()
					: addressToUpdate.getCep());
			addressToUpdate.setRoad(updatedAddress.getRoad() != addressToUpdate.getRoad() ? updatedAddress.getRoad()
					: addressToUpdate.getRoad());
			addressToUpdate.setNeighborhood(updatedAddress.getNeighborhood() != addressToUpdate.getNeighborhood()
					? updatedAddress.getNeighborhood()
					: addressToUpdate.getNeighborhood());
			addressToUpdate.setHousenumber(updatedAddress.getHousenumber() != addressToUpdate.getHousenumber()
					? updatedAddress.getHousenumber()
					: addressToUpdate.getHousenumber());
		}

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseMessageDTO("Dados atualizados com sucesso", this.getClass().getName(), null));
	}
}
