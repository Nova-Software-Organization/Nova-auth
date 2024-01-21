/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 * Representa uma entidade de endereço do cliente dentro da empresa.
 */
package com.auth.auth.modules.CustomerAddress.Infra.persistence.entity;

import java.io.Serializable;

import com.auth.auth.modules.Customer.Infra.persistence.entity.CustomerEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "Endereco")
@EqualsAndHashCode(of = "idAddress")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerAddressEntity implements Serializable {

    /**
     * Identificador único do endereço.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    private Long idAddress;

    /**
     * Cliente associado ao endereço.
     */
    @JsonBackReference
    @OneToOne(mappedBy = "address")
    private CustomerEntity customerEntity;

    /**
     * Rua do endereço.
     */
    @NotBlank(message = "A rua não pode estar em branco")
    @Column(name = "rua")
    private String road;

    /**
     * Bairro do endereço.
     */
    @NotBlank(message = "O bairro não pode estar em branco")
    @Column(name = "bairro")
    private String neighborhood;

    /**
     * Número da casa do endereço.
     */
    @NotBlank(message = "O número da casa não pode estar em branco")
    @Column(name = "numCasa")
    private String housenumber;

    /**
     * Estado do endereço.
     */
    @Column(name = "estado")
    private String state;

    /**
     * CEP do endereço.
     */
    @NotBlank(message = "O cep não pode estar em branco")
    @Column(name = "cep")
    private String cep;

    /**
     * Construtor padrão.
     */
    public CustomerAddressEntity() { }

    /**
     * Obtém o cliente associado ao endereço.
     *
     * @return Cliente associado ao endereço.
     */
    @JsonProperty("customer")
    public CustomerEntity getcustomerEntity() {
        return this.customerEntity;
    }

    /**
     * Verifica se dois endereços são iguais.
     *
     * @param other Outro endereço a ser comparado.
     * @return True se os endereços são iguais, False caso contrário.
     */
    public boolean isSameAddress(CustomerAddressEntity other) {
        return this.getRoad().equals(other.getRoad()) &&
               this.getNeighborhood().equals(other.getNeighborhood()) &&
               this.getHousenumber().equals(other.getHousenumber()) &&
               this.getState().equals(other.getState()) &&
               this.getCep().equals(other.getCep());
    }
}
