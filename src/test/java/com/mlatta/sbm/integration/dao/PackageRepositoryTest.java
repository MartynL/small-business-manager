package com.mlatta.sbm.integration.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mlatta.sbm.TestDataUtil;
import com.mlatta.sbm.dao.models.Item;
import com.mlatta.sbm.dao.models.SalePackage;
import com.mlatta.sbm.dao.repositories.PackageRepository;

@DataJpaTest
class PackageRepositoryTest {

	@Autowired private PackageRepository packageRepository;
	
	private SalePackage testPackage;
	
	@BeforeEach
	public void setUp() {

		Set<Item> testItems = TestDataUtil.getTestItems();
		testPackage = new SalePackage("test package", 35.00, testItems);
	
	}
	
	@Test
	void packageShouldbeCreatedWithMultipleItems() {

		SalePackage savedPackage = packageRepository.save(testPackage);
		
		assertThat(savedPackage.getId(), is(notNullValue()));
		assertThat(savedPackage.getPackageItems().size(),is(3));
	}
	
	@Test
	void addingAnItemToThePackageShouldIncreaseTheNumberOfItemsInPackageByOne() {

		Item newItem = new Item("New Addition", 15.00);
		SalePackage salePack = packageRepository.save(testPackage);
		
		salePack.addItem(newItem);
		
		SalePackage additionalItemPackage = packageRepository.save(salePack);
		
		assertThat(additionalItemPackage.getPackageItems().size(), is(4));
	}
	
	@Test
	void removingAnItemFromThePackageShouldDecreaseTheNumberOfItemsInPackageByOne() {

		Item item = testPackage.getPackageItems().stream().skip(1).findFirst().orElseThrow();
		SalePackage salePack = packageRepository.save(testPackage);
		
		salePack.removeItem(item);
		
		SalePackage modifiedPackage = packageRepository.save(salePack);
		
		assertThat(modifiedPackage.getPackageItems().size(), is(2));
	}

}
