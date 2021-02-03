package com.mlatta.sbm.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mlatta.sbm.dao.models.entities.Section;

public interface SectionRepository extends JpaRepository<Section, Long> {}
