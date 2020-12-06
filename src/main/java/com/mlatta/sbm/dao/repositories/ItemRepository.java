package com.mlatta.sbm.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mlatta.sbm.dao.models.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
