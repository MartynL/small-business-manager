package com.mlatta.sbm.dao.models.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.mlatta.sbm.dao.models.BaseEntity;
import com.mlatta.sbm.dao.models.linkentities.SectionItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorColumn(name = "Item_Type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Item extends BaseEntity {

	private static final long serialVersionUID = 6097750957253649716L;

	@Column(name = "name", length = 255)
	private String name;
	
	@Column(name = "price", scale = 2)
	private BigDecimal price;
	
	@OneToMany(
	        mappedBy = "item",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true)
	@OrderBy("orderIdx ASC")
	private List<SectionItem> sections = new ArrayList<>();
	
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
		Item other = (Item) obj;
		return Objects.equals(super.id, other.id) 
				&& Objects.equals(super.uniqueRef, other.uniqueRef);
	}
	
}
