package com.mlatta.sbm.integration.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mlatta.sbm.dao.models.Item;
import com.mlatta.sbm.dao.repositories.ItemRepository;

@DataJpaTest
class ItemRepositoryTest {
	
	@Autowired private ItemRepository itemRepository;

	@Test
	void shouldSaveItemSuccessfully_andReturnObjectWithHBGeneratedValues() {
		Item item = new Item("Test item", 12.00);
		Item savedItem = itemRepository.save(item);
		
		assertThat(savedItem.getId(), notNullValue());
		assertThat(savedItem.getCreatedDateTime(), notNullValue());
		assertThat(savedItem.getVersion(), notNullValue());
		assertThat(savedItem.getUpdatedDateTime(), nullValue());
		assertThat(savedItem.getName(), is(item.getName()));
		assertThat(savedItem.getPrice(), is(item.getPrice()));
	}
	
	@Test
	void shouldUpdateItemSuccessfully_versionShouldBeUpdated() {
		Item item = new Item("Test item", 12.00);
		Item savedItem = itemRepository.saveAndFlush(item);
		
		OffsetDateTime firstUpdatedDateTime = savedItem.getUpdatedDateTime();
		short firstVersion = savedItem.getVersion();
		
		savedItem.setName("Test update item");
		Item updateItem = itemRepository.saveAndFlush(savedItem);
		
		assertThat(updateItem.getId(), equalTo(savedItem.getId()));
		assertThat(updateItem.getName(), equalTo("Test update item"));
		assertThat(updateItem.getCreatedDateTime(), equalTo(savedItem.getCreatedDateTime()));
		assertThat(updateItem.getUpdatedDateTime(), is(not(updateItem.getCreatedDateTime())));
		assertThat(updateItem.getUpdatedDateTime(), is(not(firstUpdatedDateTime)));
		assertThat(updateItem.getVersion(), is(not(firstVersion)));
	}

}
