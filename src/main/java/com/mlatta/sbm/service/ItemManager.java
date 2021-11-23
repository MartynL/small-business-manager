package com.mlatta.sbm.service;

import org.springframework.stereotype.Service;

import com.mlatta.sbm.dao.repositories.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemManager {
	
	private final ItemRepository itemRepository;

}
