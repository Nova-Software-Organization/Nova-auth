/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Auth.Domain.authentication;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth.auth.modules.Auth.Application.DTOs.response.ResponseMessageDTO;
import com.auth.auth.modules.Auth.Domain.Enum.CustomGrantedAuthority;
import com.auth.auth.modules.Auth.Domain.component.UserRolesDeterminesComponent;
import com.auth.auth.modules.Auth.Domain.event.AuthCreatedPublishEventListenerComponent;
import com.auth.auth.modules.Auth.Domain.repository.IAutheticationRegister;
import com.auth.auth.modules.Auth.Domain.service.cryptography.AnonymizationService;
import com.auth.auth.modules.Auth.Domain.service.user.UserService;
import com.auth.auth.modules.Auth.Domain.token.GeneratedTokenAuthorizationService;
import com.auth.auth.modules.Auth.Infra.persistence.entity.UserEntity;
import com.auth.auth.modules.Auth.Infra.persistence.repository.UserRepository;
import com.auth.auth.modules.Auth.Infra.validation.DocumentValidator;
import com.auth.auth.modules.Auth.Infra.validation.EmailValidator;
import com.auth.auth.modules.Auth.Infra.validation.PasswordValidator;
import com.auth.auth.modules.Customer.Application.DTOs.registration.CustomerAddressDTO;
import com.auth.auth.modules.Customer.Application.DTOs.registration.CustomerDTO;
import com.auth.auth.modules.Customer.Domain.service.CustomerUpdateService;
import com.auth.auth.modules.Customer.Infra.persistence.entity.CustomerEntity;
import com.auth.auth.modules.Customer.Infra.persistence.repository.CustomerRepository;
import com.auth.auth.modules.CustomerAddress.Infra.persistence.entity.CustomerAddressEntity;
import com.auth.auth.modules.CustomerAddress.Infra.persistence.repository.CustomerAddressRepository;
import com.auth.auth.shared.helpers.ModelMappersConvertion;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AutheticationRegisterService implements IAutheticationRegister {
    private PasswordEncoder passwordEncoder;
    private CustomerUpdateService clientServiceImp;
    private UserService userService;
    private GeneratedTokenAuthorizationService generatedTokenAuthorizationService;
    private AnonymizationService anonymizationService;
    private CustomerRepository customerRepository;
    private UserRepository userRepository;
    private CustomerAddressRepository customerAddressRepository;
    private AuthCreatedPublishEventListenerComponent authCreatedPublishEventListenerComponent;
    private UserRolesDeterminesComponent userRolesDeterminesComponent;

    @Autowired

    public AutheticationRegisterService(
           PasswordEncoder passwordEncoder,
           CustomerUpdateService clientServiceImp,
           UserService userService,
           ApplicationEventPublisher eventPublisher,
           GeneratedTokenAuthorizationService generatedTokenAuthorizationService,
           AnonymizationService anonymizationService,
           CustomerRepository customerRepository,
           UserRepository userRepository,
           CustomerAddressRepository customerAddressRepository,
           AuthCreatedPublishEventListenerComponent authCreatedPublishEventListenerComponent,
           UserRolesDeterminesComponent userRolesDeterminesComponent
    ) {
        this.passwordEncoder = passwordEncoder;
        this.clientServiceImp = clientServiceImp;
        this.userService = userService;
        this.generatedTokenAuthorizationService = generatedTokenAuthorizationService;
        this.anonymizationService = anonymizationService;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.customerAddressRepository = customerAddressRepository;
        this.authCreatedPublishEventListenerComponent = authCreatedPublishEventListenerComponent;
        this.userRolesDeterminesComponent = userRolesDeterminesComponent;
    }
    
    @Transactional
    public ResponseEntity<ResponseMessageDTO> register(
            CustomerDTO customerDTO,
            CustomerAddressDTO customerAddressDTO
    ) {
        try {
            List<String> emailValidation = new EmailValidator().isValidEmail(customerDTO.getEmail());
            String passwordValidation = new PasswordValidator().isValidPassword(customerDTO.getPassword());

            if (emailValidation != null || passwordValidation != null) {
                String errorMessage = "Erro de validação: ";
                if (emailValidation != null) {
                    errorMessage += String.join(", ", emailValidation);
                }

                if (passwordValidation != null) {
                    if (passwordValidation != null) {
                        errorMessage += ", ";
                    }
                    errorMessage += String.join(", ", passwordValidation);
                }

                log.warn("erro de validação: {}" + errorMessage);
                return ResponseEntity.badRequest().body(new ResponseMessageDTO(
                        errorMessage, this.getClass().getSimpleName(), null, null));
            }

            Optional<CustomerEntity> existingCustomer = customerRepository.findByEmail(customerDTO.getEmail());
            if (existingCustomer.isPresent()) {
                log.warn("usuário já existe {}");
                return ResponseEntity.badRequest()
                        .body(new ResponseMessageDTO("E-mail já está em uso.", this.getClass().getSimpleName(), null,
                                null));
            }

            boolean documentValidator = new DocumentValidator().isValidCPF(customerDTO.getCpf());
            if (!documentValidator) {
                log.warn("erro de válidação no cpf do usuário {}");
                return ResponseEntity.badRequest()
                        .body(new ResponseMessageDTO("CPF inválido.", this.getClass().getSimpleName(), null, null));
            }

            String emailAnonymization = anonymizationService.encrypt(customerDTO.getEmail());
            String cpfAnonymization = anonymizationService.encrypt(customerDTO.getCpf());
            String hashedPassword = passwordEncoder.encode(customerDTO.getPassword());
            CustomerEntity newCustomerEntity = createNewCustomerEntity(customerDTO, cpfAnonymization);
            UserEntity newUserEntity = createUserEntity(customerDTO, hashedPassword, newCustomerEntity, emailAnonymization);
            userRepository.save(newUserEntity);

            CustomerAddressEntity newAddressEntityCustomer = createNewAddressEntityCustomer(customerAddressDTO);
            newCustomerEntity.setAddress(newAddressEntityCustomer);
            newCustomerEntity.setUser(newUserEntity);

            
            CustomerEntity savedClient = customerRepository.save(newCustomerEntity);
            Set<CustomGrantedAuthority> userRoles = userRolesDeterminesComponent.determineUserRoles(customerDTO);
            String jwtToken = generatedTokenAuthorizationService.generateToken(newUserEntity.getUsername(), userRoles);
            newCustomerEntity.setUser(newUserEntity);
            authCreatedPublishEventListenerComponent.publishCustomerCreatedEvent(newUserEntity);
            
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                   .path("/{id}")
                   .buildAndExpand(savedClient.getId())
                   .toUri();
            
            log.info("usuario criado com sucesso {}");
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header(HttpHeaders.LOCATION, location.toString())
                    .body(new ResponseMessageDTO("Usuário registrado com sucesso!",
                            this.getClass().getSimpleName(), null, jwtToken));
        } catch (Exception e) {
            log.error("Ocorreu um erro ao processar a requisição!", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessageDTO(null, this.getClass().getSimpleName(),
                            "Erro ao registrar o cliente: " + e.getMessage(), null));
        }
    }

    private CustomerEntity createNewCustomerEntity(
        CustomerDTO customerDTO,
        String cpfAnonymization
    ) {
        ModelMappersConvertion<CustomerDTO, CustomerEntity> customerDto = new ModelMappersConvertion<>(
                    new ModelMapper());
        CustomerEntity newCustomerModelMapperEntity = customerDto.toDTOFromEntity(customerDTO, CustomerEntity.class);
        newCustomerModelMapperEntity.setDateCreate(new Date());
        newCustomerModelMapperEntity.setCpf(cpfAnonymization);
        return newCustomerModelMapperEntity;
    }

    private CustomerAddressEntity createNewAddressEntityCustomer(CustomerAddressDTO customerAddressDTO) {
        ModelMappersConvertion<CustomerAddressDTO, CustomerAddressEntity> customerAddressDto = new ModelMappersConvertion<>(
                    new ModelMapper());
        CustomerAddressEntity newAddressEntityCustomer = customerAddressDto.toDTOFromEntity(customerAddressDTO, CustomerAddressEntity.class);
        newAddressEntityCustomer.setCep(anonymizationService.anonymizeCep(customerAddressDTO.getCep()));
        return newAddressEntityCustomer;
    }

    private UserEntity createUserEntity(CustomerDTO customerDTO, String hashedPassword,
            CustomerEntity newCustomerEntity, String emailAnonymization) {
        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setUsername(customerDTO.getName());
        newUserEntity.setPassword(hashedPassword);
        newUserEntity.setEmail(emailAnonymization);
        newUserEntity.setCustomer(newCustomerEntity);
        newUserEntity.setRoles(userRolesDeterminesComponent.determineUserRoles(customerDTO));
        return newUserEntity;
    }
}
