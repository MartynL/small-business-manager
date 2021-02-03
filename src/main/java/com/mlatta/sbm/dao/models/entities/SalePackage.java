package com.mlatta.sbm.dao.models.entities;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@DiscriminatorValue("package")
public class SalePackage extends Item {
	
	private static final long serialVersionUID = -8176631356390440181L;
	
	@ManyToMany(cascade = {
		CascadeType.PERSIST,
		CascadeType.MERGE
	})
	@JoinTable(
		name = "package_item", 
		joinColumns = @JoinColumn(name = "package_id"), 
		inverseJoinColumns = @JoinColumn(name = "item_id"))
	private Set<SaleItem> packageItems = new HashSet<>();
	
	public SalePackage(String name, Double price, Set<SaleItem> items) {
		super();
		super.setName(name);
		super.setPrice(BigDecimal.valueOf(price));
		items.stream().forEach(this::addItem);
	}
	
	public SalePackage(String name, Double price) {
		super();
		super.setName(name);
		super.setPrice(BigDecimal.valueOf(price));
	}
	
	public void addItem(SaleItem item) {
		this.packageItems.add(item);
		item.getPackages().add(this);
	}
	
	public void removeItem(SaleItem item) {
		if(this.packageItems.contains(item)) {
			this.packageItems.remove(item);
			item.getPackages().remove(this);
		} else {
			throw new EntityNotFoundException("Item is not found in this package");
		}
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
		SalePackage other = (SalePackage) obj;
		return Objects.equals(super.id, other.id) 
				&& Objects.equals(super.uniqueRef, other.uniqueRef);
	}

}
