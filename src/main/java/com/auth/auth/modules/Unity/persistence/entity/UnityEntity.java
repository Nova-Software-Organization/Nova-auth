/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 * Representa uma entidade de Unidade no sistema.
 */
package com.auth.auth.modules.Unity.persistence.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Representa uma entidade de Unidade no sistema.
 */
@Data
@Entity
@Table(name = "unidade")
@EqualsAndHashCode(of = "id_unidade")
public class UnityEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único da unidade.
     */
    @Id
    @Column(name = "id_unidade")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUnity;

    /**
     * Nome da unidade.
     */
    @Column(name = "nome", nullable = false)
    private String name;

    /**
     * Abreviação da unidade.
     */
    @Column(name = "abreviacao", nullable = false)
    private String abbreviation;
}
