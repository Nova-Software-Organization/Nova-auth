package com.auth.auth.modules.Brand.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.auth.modules.Brand.persistence.entity.BrandEntity;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

}