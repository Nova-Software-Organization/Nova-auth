/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Midia.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.auth.modules.Midia.persistence.entity.MidiaEntity;

@Repository
public interface MidiaRepository extends JpaRepository<MidiaEntity, Long> {
   List<MidiaEntity> findByCategory(String category);
   List<MidiaEntity> findAllByCategoryIn(List<String> categories);
}
