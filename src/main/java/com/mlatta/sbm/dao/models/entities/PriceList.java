package com.mlatta.sbm.dao.models.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.mlatta.sbm.dao.models.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PriceList extends BaseEntity {
	
	private static final long serialVersionUID = -4407354006718227605L;
	
	@Column(name = "list_name")
	private String name;

	@OneToMany(
	        cascade = CascadeType.ALL,
	        orphanRemoval = true)
	@OrderBy("orderIdx ASC")
	private List<Section> sections = new ArrayList<>();

	public PriceList(String name) {
		this.name = name;
	}
	
	public void addSection(Section section) {
		int nextIdx = this.sections.isEmpty() ? 0 : this.sections.size();
		section.setOrderIdx(nextIdx);
        sections.add(section);
    }
	
	public void removeSection(Section section) {
		this.sections
			.stream()
			.filter(s -> s.getUniqueRef().equals(section.getUniqueRef()))
			.findFirst()
			.ifPresent(s -> {
				int idx = s.getOrderIdx();

				boolean removed = this.sections.remove(s);
				
				if(removed) {
					updateSectionOrderingValues(idx);
				}
			});
	}

	private void updateSectionOrderingValues(int idx) {
		this.sections.forEach(remainingItem -> {
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
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PriceList other = (PriceList) obj;
		return Objects.equals(super.id, other.id) && Objects.equals(super.uniqueRef, other.uniqueRef);
	}


}
