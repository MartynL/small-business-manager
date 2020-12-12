package com.mlatta.sbm.dao.models;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "package")
@NoArgsConstructor
@AllArgsConstructor
public class SalePackage extends BaseEntity {
	
	private static final long serialVersionUID = -8176631356390440181L;
	
	
	@Column(name = "name", length = 255)
	private String name;
	
	@Column(name = "price", scale = 2)
	private BigDecimal price;

	@ManyToMany(cascade = {
		CascadeType.PERSIST,
		CascadeType.MERGE
	})
	@JoinTable(
		name = "package_item", 
		joinColumns = @JoinColumn(name = "package_id"), 
		inverseJoinColumns = @JoinColumn(name = "item_id"))
	private Set<Item> packageItems = new HashSet<>();
	
	public SalePackage(String name, Double price, Set<Item> items) {
		super();
		this.name = name;
		this.price = BigDecimal.valueOf(price);
		items.stream().forEach(this::addItem);
	}
	
	public void addItem(Item item) {
		this.packageItems.add(item);
		item.getPackages().add(this);
	}
	
	public void removeItem(Item item) {
		if(this.packageItems.contains(item)) {
			this.packageItems.remove(item);
			item.getPackages().remove(this);
		} else {
			throw new EntityNotFoundException("Item is not found in this package");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(name, price);
		return result;
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
		return Objects.equals(name, other.name) && Objects.equals(price, other.price);
	}
	
	

}
