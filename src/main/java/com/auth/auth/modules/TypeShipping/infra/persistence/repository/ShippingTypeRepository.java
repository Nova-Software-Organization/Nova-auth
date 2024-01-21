/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.TypeShipping.infra.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.auth.modules.TypeShipping.infra.persistence.entity.ShippingTypeEntity;

@Repository
public interface ShippingTypeRepository extends JpaRepository<ShippingTypeEntity, Long> {}
