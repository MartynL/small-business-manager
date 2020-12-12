package com.mlatta.sbm;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.mlatta.sbm.dao.models.Item;

public class TestDataUtil {

	public static Set<Item> getTestItems() {
		Item item1 = new Item("Test Item One", 12.00);
		Item item2 = new Item("Test Item Two", 12.00);
		Item item3 = new Item("Test Item Three", 12.00);
		
		Set<Item> items = new HashSet<>();
		Collections.addAll(items, item1, item2, item3);
		
		return items;
	}
}
