package com.mlatta.sbm.dao.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mlatta.sbm.dao.models.entities.SalePackage;

@Repository
public interface PackageRepository extends JpaRepository<SalePackage, Long> {
	
	Optional<SalePackage> findByUniqueRef(UUID uniqueRef);
}
