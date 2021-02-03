package com.mlatta.sbm.integration.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.AfterEach;
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
	
	@AfterEach
	void tearDown() {
		testDataManager.clearItemRepositories();
	}
	
	@Test
	void shouldSaveSectionWithMixOfItemsAndPackages() {
		
		assertThat(testSection.getId(), is(notNullValue()));
		assertThat(testSection.getItems().size(), is(TestDataManager.NUM_OF_SALE_ITEMS + TestDataManager.NUM_OF_PACKAGES));
		
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
		
		assertThat(packageCount, is(TestDataManager.NUM_OF_PACKAGES));
		assertThat(itemCount, is(TestDataManager.NUM_OF_SALE_ITEMS));
	}
	
	@Test
	void shouldUpdateSectionWithNewItem() {
		
		SaleItem newItem = new SaleItem("New Test Item", 15.00);
		
		testSection.addItem(newItem);
		
		Section updatedSection = sectionRepository.saveAndFlush(testSection);
		
		assertThat(updatedSection.getItems().size(), 
				is(TestDataManager.NUM_OF_SALE_ITEMS + TestDataManager.NUM_OF_PACKAGES + 1));
	}
	
	@Test
	void shouldUpdateSectionWithAnItemRemoved() {
		
		Item itemToRemove = testSection.getItems().stream().findFirst().get().getItem();
		
		testSection.removeItem(itemToRemove);
		
		Section updatedSection = sectionRepository.saveAndFlush(testSection);
		
		assertThat(updatedSection.getItems().size(), 
				is((TestDataManager.NUM_OF_SALE_ITEMS + TestDataManager.NUM_OF_PACKAGES) - 1));
		
		int testIdx = 0;
		
		for (SectionItem si : testSection.getItems()) {
			assertThat(si.getOrderIdx(), is(testIdx));
			testIdx++;
		}
	}
	
}
