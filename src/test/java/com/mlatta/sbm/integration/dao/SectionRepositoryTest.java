package com.mlatta.sbm.integration.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.mlatta.sbm.TestDataManager;
import com.mlatta.sbm.dao.models.entities.Item;
import com.mlatta.sbm.dao.models.entities.SaleItem;
import com.mlatta.sbm.dao.models.entities.SalePackage;
import com.mlatta.sbm.dao.models.entities.Section;
import com.mlatta.sbm.dao.models.linkentities.SectionItem;
import com.mlatta.sbm.dao.repositories.SectionRepository;

@DataJpaTest
@Import(TestDataManager.class)
class SectionRepositoryTest {

	@Autowired private TestDataManager testDataManager;
	@Autowired private SectionRepository sectionRepository;
	
	private Section testSection;
	
	@BeforeEach
	void setUp() {
		Section section = testDataManager.createTestSection(1);
		testSection = sectionRepository.saveAndFlush(section);
	}
	
	@Test
	@Transactional
	void shouldSaveSectionWithMixOfItemsAndPackages() {
		
		assertThat(testSection.getId(), is(notNullValue()));
		
		Set<Item> sectionItems = testDataManager.getSectionItems(testSection, TestDataManager.NUM_OF_SALE_ITEMS, TestDataManager.NUM_OF_PACKAGES);
		
		for (Item item : sectionItems) {
			testSection.addItem(item);
		}
		
		sectionRepository.save(testSection);
		
		int packageCount = 0;
		int itemCount = 0;

		for (SectionItem sItem : testSection.getItems()) {
			Item item = (Item) Hibernate.unproxy(sItem.getItem());
			
			if(item instanceof SaleItem) {
				itemCount++;
			}
			
			if(item instanceof SalePackage) {
				packageCount++;
			}
		}

		assertThat(testSection.getItems().size(), is(TestDataManager.NUM_OF_SALE_ITEMS + TestDataManager.NUM_OF_PACKAGES));
		assertThat(packageCount, is(TestDataManager.NUM_OF_PACKAGES));
		assertThat(itemCount, is(TestDataManager.NUM_OF_SALE_ITEMS));
	}
	
	@Test
	void shouldUpdateSectionWithNewItem() {
		
		SaleItem newItem = new SaleItem("New Test Item", 15.00);
		
		testSection.addItem(newItem);
		
		Section updatedSection = sectionRepository.saveAndFlush(testSection);
		
		assertThat(updatedSection.getItems().size(), is(1));
	}
	
	@Test
	@Transactional
	void shouldUpdateSectionWithAnItemRemoved() {
		Set<Item> sectionItems = testDataManager.getSectionItems(testSection, TestDataManager.NUM_OF_SALE_ITEMS, TestDataManager.NUM_OF_PACKAGES);
		
		for (Item item : sectionItems) {
			testSection.addItem(item);
		}
		
		sectionRepository.save(testSection);
		
		Item itemToRemove = testSection.getItems().get(0).getItem();
		
		testSection.removeItem(itemToRemove);
		
		Section updatedSection = sectionRepository.save(testSection);
		
		assertThat(updatedSection.getItems().size(), 
				is((TestDataManager.NUM_OF_SALE_ITEMS + TestDataManager.NUM_OF_PACKAGES) - 1));
		
		int testIdx = 0;
		
		for (SectionItem si : testSection.getItems()) {
			assertThat(si.getOrderIdx(), is(testIdx));
			testIdx++;
		}
	}
	
}
