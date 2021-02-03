package com.mlatta.sbm.dao.models.entities;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("item")
public class SaleItem extends Item {

	private static final long serialVersionUID = -1286945852219106096L;
	
	@ManyToMany(mappedBy = "packageItems")
	private Set<SalePackage> packages = new HashSet<>();
	
	public SaleItem(String name, Double price) {
		super.setName(name);
		super.setPrice(BigDecimal.valueOf(price));
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
		SaleItem other = (SaleItem) obj;
		return Objects.equals(super.id, other.id)
			&& Objects.equals(super.uniqueRef, other.uniqueRef);
	}
	
}
