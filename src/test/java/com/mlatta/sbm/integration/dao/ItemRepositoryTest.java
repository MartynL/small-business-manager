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
import org.springframework.test.annotation.DirtiesContext;

import com.mlatta.sbm.dao.models.entities.Item;
import com.mlatta.sbm.dao.models.entities.SaleItem;
import com.mlatta.sbm.dao.repositories.ItemRepository;

@DataJpaTest
class ItemRepositoryTest {
	
	@Autowired private ItemRepository itemRepository;

	@Test
	@DirtiesContext
	void shouldSaveItemSuccessfully_andReturnObjectWithHBGeneratedValues() {
		SaleItem item = new SaleItem("Test item", 12.00);
		SaleItem savedItem = itemRepository.save(item);
		
		assertThat(savedItem.getId(), notNullValue());
		assertThat(savedItem.getCreatedDateTime(), notNullValue());
		assertThat(savedItem.getVersion(), notNullValue());
		assertThat(savedItem.getUpdatedDateTime(), nullValue());
		assertThat(savedItem.getName(), is(item.getName()));
		assertThat(savedItem.getPrice(), is(item.getPrice()));
	}
	
	@Test
	@DirtiesContext
	void shouldUpdateItemSuccessfully_versionShouldBeUpdated() {
		
		Item item = (SaleItem) itemRepository.findById(10001L).get();
		
		OffsetDateTime firstUpdatedDateTime = item.getUpdatedDateTime();
		short firstVersion = item.getVersion();
		
		item.setName("Test update item");
		Item updateItem = itemRepository.saveAndFlush(item);
		
		assertThat(updateItem.getId(), equalTo(item.getId()));
		assertThat(updateItem.getName(), equalTo("Test update item"));
		assertThat(updateItem.getCreatedDateTime(), equalTo(item.getCreatedDateTime()));
		assertThat(updateItem.getUpdatedDateTime(), is(not(updateItem.getCreatedDateTime())));
		assertThat(updateItem.getUpdatedDateTime(), is(not(firstUpdatedDateTime)));
		assertThat(updateItem.getVersion(), is(not(firstVersion)));
	}

}
