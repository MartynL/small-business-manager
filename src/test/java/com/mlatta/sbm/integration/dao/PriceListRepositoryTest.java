package com.mlatta.sbm.integration.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.mlatta.sbm.TestDataManager;
import com.mlatta.sbm.dao.models.entities.PriceList;
import com.mlatta.sbm.dao.models.entities.Section;
import com.mlatta.sbm.dao.repositories.PriceListRepository;

@DataJpaTest
@Import(TestDataManager.class)
class PriceListRepositoryTest {
	
	private static final int NUM_OF_SECTIONS = 3;
	
	@Autowired private TestDataManager testDataManager;
	@Autowired private PriceListRepository priceListRepository;
	
	@Test
	@Transactional
	void shouldSavePriceListWithMultipleEmptySections() {
		
		PriceList testList = testDataManager.createPriceListWithMultipleSections(NUM_OF_SECTIONS);
		PriceList list = priceListRepository.save(testList );
		
		assertThat(list.getName(), is(testList.getName()));
		assertThat(list.getUniqueRef(), is(testList.getUniqueRef()));
	}
	
	@Test
	void shouldAddAnAdditionalSectionToListOfSectionsWithCorrectOrderIdx() {
		
		Section newSection = testDataManager.createTestSection(1);
		PriceList list = priceListRepository.findAll().get(0);
		
		int initialNumOfSections = list.getSections().size();
		
		list.addSection(newSection);
		
		assertThat(list.getSections().contains(newSection), is(true));
		assertThat(list.getSections().size(), is(initialNumOfSections + 1));
		
		boolean sectionFoundInSectionsSetWithCorrectIdx = false;
		
		for (Section section : list.getSections()) {
			if(section.getUniqueRef().equals(newSection.getUniqueRef()) 
					&& section.getListOrderIdx() == (list.getSections().size() - 1)) {
				sectionFoundInSectionsSetWithCorrectIdx = true;
			}
		}
		
		assertThat(sectionFoundInSectionsSetWithCorrectIdx, is(true));
	}

	@Test
	void shouldRemoveSectionAndReOrderRemainingSections() {
		
		PriceList list = priceListRepository.findAll().get(0);
		Section sectionToDelete = list.getSections().stream().findFirst().get();
		int initialNumOfSections = list.getSections().size();
		
		list.removeSection(sectionToDelete);
		
		assertThat(list.getSections().contains(sectionToDelete), is(false));
		assertThat(list.getSections().size(), is(initialNumOfSections - 1));
		
		int testIdx = 0;
		
		for (Section s : list.getSections()) {
			assertThat(s.getListOrderIdx(), is(testIdx));
			testIdx++;
		}
	}
}
