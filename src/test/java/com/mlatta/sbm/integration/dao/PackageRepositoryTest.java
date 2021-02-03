package com.mlatta.sbm.integration.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.mlatta.sbm.TestDataManager;
import com.mlatta.sbm.dao.models.entities.SaleItem;
import com.mlatta.sbm.dao.models.entities.SalePackage;
import com.mlatta.sbm.dao.repositories.PackageRepository;

@DataJpaTest
@Import(TestDataManager.class)
class PackageRepositoryTest {

	private static final int NUM_ITEMS = 3;
	
	@Autowired private PackageRepository packageRepository;
	@Autowired private TestDataManager testDataManager;
	
	private SalePackage testPackage;
	
	@BeforeEach
	public void setUp() {
		testPackage = new SalePackage("test package", 35.00);
		Set<SaleItem> testItems = testDataManager.setUpTestItems(NUM_ITEMS, testPackage);
		
		testItems.stream().forEachOrdered(item -> testPackage.addItem(item));
		testPackage = packageRepository.saveAndFlush(testPackage);
	}
	
	@Test
	void packageShouldbeCreatedWithMultipleItems() {
		assertThat(testPackage.getId(), is(notNullValue()));
		assertThat(testPackage.getPackageItems().size(),is(3));
	}
	
	@Test
	void addingAnItemToThePackageShouldIncreaseTheNumberOfItemsInPackageByOne() {

		SaleItem newItem = new SaleItem("New Addition", 15.00);
		
		testPackage.addItem(newItem);
		
		SalePackage additionalItemPackage = packageRepository.save(testPackage);
		
		assertThat(additionalItemPackage.getPackageItems().size(), is(4));
	}
	
	@Test
	void removingAnItemFromThePackageShouldDecreaseTheNumberOfItemsInPackageByOne() {

		SaleItem item = testPackage.getPackageItems().stream().skip(1).findFirst().orElseThrow();
		
		testPackage.removeItem(item);
		
		SalePackage modifiedPackage = packageRepository.save(testPackage);
		
		assertThat(modifiedPackage.getPackageItems().size(), is(2));
	}

}
