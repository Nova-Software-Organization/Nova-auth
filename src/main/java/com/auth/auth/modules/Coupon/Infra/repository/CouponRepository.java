/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Coupon.Infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.auth.modules.Coupon.Infra.entity.CouponEntity;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, Long> {
    boolean existsByCouponCode(String couponCode);
    Optional<CouponEntity> findByCouponCode(String couponCode);

}
