/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * Entidade responsavel por gerenciar toda a parte de transações da aplicação confirmação de pagamento de um pedido
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Transaction.infra.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.context.annotation.Lazy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Lazy
@Data
@Entity
@Table(name = "transacao")
@EqualsAndHashCode(of = "idTransaction")
public class TransactionEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;

      /**
     * Identificador único da transação.
     */
    @Id
    @Column(name = "id_transacao")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaction;

    /**
     * Número do cartão associado à transação.
     */
    @Column(name = "card_num")
    private String cardNumber;

    /**
     * CPF associado à transação.
     */
    @Column(name = "cpf")
    private String cpf;

    /**
     * Data da compra realizada na transação.
     */
    @Column(name = "dt_compra")
    private Date purchaseDate;

    /**
     * Status da transação.
     */
    @Column(name = "status")
    private String status;

    /**
     * Valor total da transação.
     */
    @Column(name = "valor_total")
    private BigDecimal value;

    /**
     * Número de parcelas da transação.
     */
    @Column(name = "parcela")
    private String installments;
}
