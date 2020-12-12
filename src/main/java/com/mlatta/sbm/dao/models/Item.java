package com.mlatta.sbm.dao.models;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sale_item")
public class Item extends BaseEntity {

	private static final long serialVersionUID = 6097750957253649716L;

	@Column(name = "name", length = 255)
	private String name;
	
	@Column(name = "price", scale = 2)
	private BigDecimal price;
	
	@ManyToMany(mappedBy = "packageItems")
	private Set<SalePackage> packages = new HashSet<>();
	
	public Item(String name, Double price) {
		super();
		this.name = name;
		this.price = BigDecimal.valueOf(price);
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
		Item other = (Item) obj;
		return Objects.equals(name, other.name) && Objects.equals(price, other.price);
	}
	
}
