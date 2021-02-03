package com.mlatta.sbm.dao.models.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.mlatta.sbm.dao.models.BaseEntity;
import com.mlatta.sbm.dao.models.linkentities.SectionItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Section extends BaseEntity {

	private static final long serialVersionUID = -7954410720530275102L;

	@Column(name = "section_name")
	private String name;
	
	@Column(name = "order_idx")
	private int orderIdx;
	
	@OneToMany(
			mappedBy = "section", 
			orphanRemoval = true, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@OrderBy("orderIdx ASC")
	private List<SectionItem> items = new ArrayList<>();
	
	public void addItem(Item item) {
		int nextIdx = this.items.isEmpty() ? 0 : this.items.size();
        SectionItem sectionItem = new SectionItem(this, item, nextIdx);
        
        this.items.add(sectionItem);
        item.getSections().add(sectionItem);
    }
	
	public void removeItem(Item item) {

		this.items
			.stream()
			.filter(sectionItem -> this.equals(sectionItem.getSection()) && item.equals(sectionItem.getItem()))
			.findFirst()
			.ifPresent(i -> {
				int idx = i.getOrderIdx();
				
				boolean removedRef1 = this.items.remove(i);
				boolean removedRef2 = item.getSections().remove(i);
				
				if(removedRef1 && removedRef2) {
					updateSectionItemOrderingValues(idx, items);
					updateSectionItemOrderingValues(idx, item.getSections());
					
					i.setItem(null);
					i.setSection(null);
				}
			});
	}

	private void updateSectionItemOrderingValues(int idx, List<SectionItem> updatedCollection) {
		updatedCollection.forEach(remainingItem -> {
			if(remainingItem.getOrderIdx() > idx) {
				remainingItem.setOrderIdx(remainingItem.getOrderIdx() - 1);
			}
		});
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(super.id, super.uniqueRef);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Section)) {
			return false;
		}
		Section other = (Section) obj;
		return Objects.equals(super.id, other.id) 
			&& Objects.equals(super.uniqueRef, other.uniqueRef);
	}
	

	
}
