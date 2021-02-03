package com.mlatta.sbm.dao.models.linkentities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.mlatta.sbm.dao.models.embeddable.SectionItemId;
import com.mlatta.sbm.dao.models.entities.Item;
import com.mlatta.sbm.dao.models.entities.Section;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "section_item")
public class SectionItem implements Serializable {

	private static final long serialVersionUID = 6109597940615517467L;

	@EmbeddedId
	private SectionItemId id = new SectionItemId();
	
	@ManyToOne
	@MapsId("sectionId")
	private Section section;
	
	@ManyToOne
	@MapsId("itemId")
	private Item item;
	
	@Column(name = "order_idx")
	private int orderIdx;

	public SectionItem(Section section, Item item, int orderIdx) {
		this.id = new SectionItemId(section.getId(), item.getId());
		this.section = section;
		this.item = item;
		this.orderIdx = orderIdx;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, item, orderIdx, section);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SectionItem)) {
			return false;
		}
		SectionItem other = (SectionItem) obj;
		return Objects.equals(id, other.id) && Objects.equals(item, other.item) && orderIdx == other.orderIdx
				&& Objects.equals(section, other.section);
	}
	
}
