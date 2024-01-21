/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.DeliveryCompany.infra.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.auth.modules.DeliveryCompany.infra.persistence.entity.DeliveryCompanyEntity;

@Repository
public interface DeliveryCompanyRepository extends JpaRepository<DeliveryCompanyEntity, Long> { }
