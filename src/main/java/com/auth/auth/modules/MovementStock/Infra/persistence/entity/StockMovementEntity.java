/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 * Representa uma entidade de movimentação do estoque dentro da empresa.
 */
package com.auth.auth.modules.MovementStock.Infra.persistence.entity;

import java.util.Date;

import com.auth.auth.modules.Stock.Infra.persistence.entity.StockEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "estoque_movimento")
@EqualsAndHashCode(of = "id")
public class StockMovementEntity {

    /**
     * Identificador único da movimentação de estoque.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Entidade de estoque associada à movimentação.
     */
    @ManyToOne
    @JoinColumn(name = "idestoque", nullable = false)
    private StockEntity stock;

    /**
     * Quantidade de saída na movimentação.
     */
    @Column(name = "quantidade_saida")
    private int outputQuantity;

    /**
     * Data da movimentação de estoque.
     */
    @Column(name = "data_movimentacao", nullable = false)
    private Date movementDate;
}
