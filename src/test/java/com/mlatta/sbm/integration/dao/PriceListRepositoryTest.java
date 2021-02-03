package com.mlatta.sbm.integration.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
	
	private PriceList testList;
	
	@BeforeEach
	public void setUp() {
		testList = testDataManager.createPriceListWithMultipleSections(NUM_OF_SECTIONS);
	}
	
	@AfterEach
	public void tearDown() {
		testDataManager.clearItemRepositories();
	}
	
	@Test
	void shouldSavePriceListWithMultipleSections() {
		
		PriceList list = priceListRepository.saveAndFlush(testList);
		
		assertThat(list.getName(), is(testList.getName()));
		assertThat(list.getUniqueRef(), is(testList.getUniqueRef()));
	}
	
	@Test
	void shouldAddAnAdditionalSectionToListOfSectionsWithCorrectOrderIdx() {
		
		priceListRepository.saveAndFlush(testList);
		
		Section newSection = testDataManager.createTestSection(1);
		PriceList list = priceListRepository.findByUniqueRef(testList.getUniqueRef());
		
		list.addSection(newSection);
		
		assertThat(list.getSections().contains(newSection), is(true));
		assertThat(list.getSections().size(), is(NUM_OF_SECTIONS + 1));
		
		boolean sectionFoundInSectionsSetWithCorrectIdx = false;
		
		for (Section section : list.getSections()) {
			if(section.getUniqueRef().equals(newSection.getUniqueRef()) 
					&& section.getOrderIdx() == (list.getSections().size() - 1)) {
				sectionFoundInSectionsSetWithCorrectIdx = true;
			}
		}
		
		assertThat(sectionFoundInSectionsSetWithCorrectIdx, is(true));
	}

	@Test
	void shouldRemoveSectionAndReOrderRemainingSections() {
		
		priceListRepository.saveAndFlush(testList);
		
		Section sectionToDelete = testList.getSections().stream().findFirst().get();
		
		PriceList list = priceListRepository.findByUniqueRef(testList.getUniqueRef());
		list.removeSection(sectionToDelete);
		
		assertThat(list.getSections().contains(sectionToDelete), is(false));
		assertThat(list.getSections().size(), is(NUM_OF_SECTIONS - 1));
		
		int testIdx = 0;
		
		for (Section s : list.getSections()) {
			assertThat(s.getOrderIdx(), is(testIdx));
			testIdx++;
		}
	}
}
