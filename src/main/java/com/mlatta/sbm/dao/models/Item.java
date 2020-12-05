package com.mlatta.sbm.dao.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 6097750957253649716L;

	@Id
	@GeneratedValue
	@Column(columnDefinition = "binary(16)", updatable = false)
	private UUID id;
	
	@Column(name = "name", length = 255)
	private String name;
	
	@Column(name = "price", scale = 2)
	private BigDecimal price;

	@Override
	public int hashCode() {
		return Objects.hash(id);
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
		return Objects.equals(id, other.id);
	}

}
