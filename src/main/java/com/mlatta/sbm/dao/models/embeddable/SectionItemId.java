package com.mlatta.sbm.dao.models.embeddable;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Embeddable
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SectionItemId implements Serializable {

	private static final long serialVersionUID = -2121610420547163111L;

	private Long sectionId;
	private Long itemId;
	
	@Override
	public int hashCode() {
		return Objects.hash(itemId, sectionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof SectionItemId)) return false;
		SectionItemId other = (SectionItemId) obj;
		return Objects.equals(itemId, other.itemId) && Objects.equals(sectionId, other.sectionId);
	}

}
