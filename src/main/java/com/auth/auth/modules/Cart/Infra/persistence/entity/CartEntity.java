/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 * Representa uma entidade de carrinho associado a um cliente.
 */
package com.auth.auth.modules.Cart.Infra.persistence.entity;

import java.io.Serializable;
import java.util.List;

import com.auth.auth.modules.CartItem.Infra.persistence.entity.CartItemEntity;
import com.auth.auth.modules.Customer.Infra.persistence.entity.CustomerEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.ws.rs.ext.ParamConverter.Lazy;
import lombok.EqualsAndHashCode;

@Lazy
@Entity
@Table(name = "carrinho")
@EqualsAndHashCode(of = "idCart")
public class CartEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrinho")
    private Long idCart;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private CustomerEntity customer;

    @OneToMany(mappedBy = "cartEntity", cascade = CascadeType.ALL)
    private List<CartItemEntity> cartItems;
}
