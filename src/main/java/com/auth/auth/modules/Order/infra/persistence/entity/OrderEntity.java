/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 * Representa uma entidade de Pedido no sistema.
 */
package com.auth.auth.modules.Order.infra.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Lazy;

import com.auth.auth.modules.Customer.Infra.persistence.entity.CustomerEntity;
import com.auth.auth.modules.OrderAddress.Infra.persistence.entity.OrderAddressEntity;
import com.auth.auth.modules.Product.Infra.persistence.entity.ProductEntity;
import com.auth.auth.modules.Transaction.infra.entity.TransactionEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Lazy
@Data
@Entity
@Table(name = "pedido")
@EqualsAndHashCode(of = "numberOrder")
public class OrderEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * Número único do pedido.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long numberOrder;
    
    /**
     * Cliente associado ao pedido.
     */
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    private CustomerEntity client;
    
    /**
     * Lista de produtos associados ao pedido.
     */
    @ManyToMany
    @JoinTable(
    name = "pedido_produto",
    joinColumns = @JoinColumn(name = "id_num_ped"),
    inverseJoinColumns = @JoinColumn(name = "id_produto")
    )
    @JsonBackReference
    private List<ProductEntity> products;

    /**
     * E-mail do cliente.
     */
    @Column(name = "email")
    private String customerEmail;

    /**
     * Status do pedido.
     */
    @Column(name = "status")
    private String status;

    /**
     * Data de pagamento do pedido.
     */
    @Column(name = "dt_pagamento")
    private Date datePayment;

    /**
     * Nome do cliente.
     */
    @Column(name = "nm_cliente")
    private String customerName;
    
    /**
     * Valor total do pedido.
     */
    @Column(name = "valorTotal")
    private Float totalValue;
    
    /**
     * Método de pagamento utilizado no pedido.
     */
    @Column(name = "metodo_pagamento")
    private String paymentMethod;

    /**
     * Entidade de transação associada ao pedido.
     */
    @ManyToOne
    @JoinColumn(name = "id_transacao", referencedColumnName = "id_transacao")
    private TransactionEntity transactionEntity;

    /**
     * Entidade de endereço de pedido associada ao pedido.
     */
    @OneToOne(mappedBy = "orderEntity")
    private OrderAddressEntity orderAddressEntity;
}
