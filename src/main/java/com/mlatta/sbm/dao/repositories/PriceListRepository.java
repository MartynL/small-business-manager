package com.mlatta.sbm.dao.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mlatta.sbm.dao.models.entities.PriceList;

public interface PriceListRepository extends JpaRepository<PriceList, Long> {

	PriceList findByUniqueRef(UUID uniqueRef);
	
}
