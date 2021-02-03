package com.mlatta.sbm;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mlatta.sbm.dao.models.entities.Item;
import com.mlatta.sbm.dao.models.entities.PriceList;
import com.mlatta.sbm.dao.models.entities.SaleItem;
import com.mlatta.sbm.dao.models.entities.SalePackage;
import com.mlatta.sbm.dao.models.entities.Section;
import com.mlatta.sbm.dao.repositories.ItemRepository;
import com.mlatta.sbm.dao.repositories.PackageRepository;
import com.mlatta.sbm.dao.repositories.PriceListRepository;
import com.mlatta.sbm.dao.repositories.SectionRepository;

@Component
public class TestDataManager {
	
	public static final int NUM_OF_PACKAGES = 1;
	public static final int NUM_OF_SALE_ITEMS = 2;
	
	private static final int NUM_OF_SALE_ITEMS_PER_PACKAGE = 3;
	private static final int NUM_OF_SECTIONS = 1;
	
	@Autowired private PackageRepository packageRepository;
	@Autowired private ItemRepository itemRepository;
	@Autowired private SectionRepository sectionRepository;
	@Autowired private PriceListRepository priceListRepository;
	
	public PriceList createPriceList() {
		return createPriceListWithMultipleSections(NUM_OF_SECTIONS);
	}
	
	public PriceList createPriceListWithMultipleSections(int numOfSections) {
		
		Set<Section> sections = getMultipleSections(numOfSections);
		
		PriceList list = new PriceList("Test Price List");
		
		for (Section section : sections) {
			list.addSection(section);
		}
		
		return list;
	}

	
	public Set<Section> createSection() {
		return getMultipleSections(NUM_OF_SECTIONS);
	}
	
	public Set<Section> getMultipleSections(int numOfSections) {
		
		Set<Section> sections = new HashSet<>();
		
		for (int i = 0; i < numOfSections; i++) {
			Section section = createTestSection(i);
			sections.add(section);
		}
		
		return sections;
	}
	
	public Section createTestSection(int sectionNum) {
		Section testSection = new Section();
		
		testSection.setName("Test Section " + sectionNum);
		
		Set<Item> items = getSectionItems(testSection);
		
		for (Item item : items) {
			testSection.addItem(item);
		}
		
		return testSection;
	}

	public Set<Item> getSectionItems(Section section) {
		return getSectionItems(section, NUM_OF_SALE_ITEMS,  NUM_OF_PACKAGES);
	}
	
	public Set<Item> getSectionItems(Section section, int numItems, int numPackages) {
		
		Stream<Item> testItemStream = setUpTestItems(numItems, null).stream().map(Item.class::cast);
		Stream<Item> testPackageStream = setUpTestPackages(numPackages).stream().map(Item.class::cast);
		
		return Stream
				.concat(testItemStream, testPackageStream)
				.collect(Collectors.toSet());
	}
	

	public Set<SalePackage> setUpTestPackages(int numPackages) {	
		Set<SalePackage> packages = new LinkedHashSet<>();
		
		for (int i = 0; i < numPackages; i++) {
			SalePackage pack = new SalePackage("Test Package "+ (i + 1), (i + 1) * 5.0);
			Set<SaleItem> packItems = setUpTestItems(NUM_OF_SALE_ITEMS_PER_PACKAGE, pack);
			
			packItems.stream().forEachOrdered(item -> pack.addItem(item));
			
			SalePackage savedPackage = packageRepository.saveAndFlush(pack);
			
			packages.add(savedPackage);
		}
		
		return packages;
	}
	
	public Set<SaleItem> setUpTestItems(int numItems, SalePackage salePackage) {
		
		Set<SaleItem> items = new LinkedHashSet<>();
		
		for (int i = 0; i < numItems; i++) {
			String itemName = salePackage != null
					? "Test Item " + (i + 1) + " for package with name: " + salePackage.getName() 
					: "Test Item " + (i + 1);
			
			SaleItem item = new SaleItem(itemName, (i + 1) * 2.0);
			
			SaleItem savedItem = itemRepository.saveAndFlush(item);
			
			items.add(savedItem);
		}
		
		return items;
	}

	public void clearItemRepositories() {
		itemRepository.deleteAll();
		packageRepository.deleteAll();
		sectionRepository.deleteAll();
		priceListRepository.deleteAll();
	}
	
	
}
