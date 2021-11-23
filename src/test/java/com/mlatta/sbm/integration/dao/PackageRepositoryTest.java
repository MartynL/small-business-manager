package com.mlatta.sbm.integration.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Set;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import com.mlatta.sbm.TestDataManager;
import com.mlatta.sbm.dao.models.entities.SaleItem;
import com.mlatta.sbm.dao.models.entities.SalePackage;
import com.mlatta.sbm.dao.repositories.PackageRepository;

@DataJpaTest
@Sql(scripts = "/data.sql")
@Import(TestDataManager.class)
class PackageRepositoryTest {

	private static final int NUM_ITEMS = 3;
	
	@Autowired private PackageRepository packageRepository;
	@Autowired private TestDataManager testDataManager;
	
	@Test
	void getPackageFromUniqueRef() {
		
		// Having to do this for now because H2 removes the dashes when storing as binary data type.
		// Shouldn't be an issue when run against a postgres db test container
		byte[] binaryString = DatatypeConverter.parseHexBinary("4e19316023874ef39a20949162d4c36c");
		
		packageRepository
			.findByUniqueRef(UUID.nameUUIDFromBytes(binaryString))
			.ifPresentOrElse(tp -> {
				assertThat(tp.getName(), is("Test Package 1"));
			}, Assertions.fail("testPackage not present."));
	}
	
	
	@Test
	void packageShouldbeCreatedWithMultipleItems() {
		
		final SalePackage testPackage = new SalePackage("test package", 35.00);
		Set<SaleItem> testItems = testDataManager.setUpTestItems(NUM_ITEMS, testPackage);
		
		testItems.stream().forEachOrdered(item -> testPackage.addItem(item));
		SalePackage savedTestPackage = packageRepository.saveAndFlush(testPackage);
		
		assertThat(savedTestPackage.getId(), is(notNullValue()));
		assertThat(savedTestPackage.getPackageItems().size(),is(3));
	}
	
	@Test
	void addingAnItemToThePackageShouldIncreaseTheNumberOfItemsInPackageByOne() {

		packageRepository
				.findById(10006L)
				.ifPresentOrElse(tp -> {
					SaleItem newItem = testDataManager.setUpTestItems(1, tp).stream().findFirst().get();
					tp.addItem(newItem);
					SalePackage additionalItemPackage = packageRepository.save(tp);
				
					assertThat(additionalItemPackage.getPackageItems().size(), is(4));
				}, Assertions.fail("testPackage not present."));
		
	}
	
	@Test
	@DirtiesContext
	void removingAnItemFromThePackageShouldDecreaseTheNumberOfItemsInPackageByOne() {

		packageRepository
			.findById(10006L)
			.ifPresentOrElse(tp -> {
				SaleItem item = tp.getPackageItems().stream().skip(1).findFirst().orElseThrow();
				tp.removeItem(item);
				
				SalePackage modifiedPackage = packageRepository.save(tp);
				
				assertThat(modifiedPackage.getPackageItems().size(), is(2));
			}, Assertions.fail("testPackage not present."));
	}

}
