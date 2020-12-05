package com.mlatta.sbm.dao.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mlatta.sbm.dao.models.Item;

public interface ItemRepository extends JpaRepository<Item, UUID> {

}
