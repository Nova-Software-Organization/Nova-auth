/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 * Representa uma entidade de items dentro carrinho associados a um cliente.
 */
package com.auth.auth.modules.CartItem.Infra.persistence.entity;

import java.io.Serializable;

import com.auth.auth.modules.Cart.Infra.persistence.entity.CartEntity;
import com.auth.auth.modules.Product.Infra.persistence.entity.ProductEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.ws.rs.ext.ParamConverter.Lazy;
import lombok.EqualsAndHashCode;

@Lazy
@Entity
@Table(name = "item_carrinho")
@EqualsAndHashCode(of = "idCartItem")
public class CartItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrinho_item")
    private Long idCartItem;

    /**
     * entidade carrinho que se relaciona com carrinho item
     */
    @ManyToOne
    @JoinColumn(name = "id_carrinho")
    private CartEntity cartEntity;

    /*
     * produtos que estão dentro do carrinho
     */
    @ManyToOne
    @JoinColumn(name = "id_produto")
    private ProductEntity product;

    /**
     * quantidade de items que estão dentro do carrinho
     */
    @Column(name = "quantidade")
    private int quantity;
}
