package com.mlatta.sbm.dao.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 6965767719265988245L;

	@NaturalId
	@Column(nullable = false, unique = true)
	private String uniqueRef; 

	@CreationTimestamp
	@Column(name = "created_on", updatable = false)
	private Timestamp createdDate;

	@UpdateTimestamp
	@Column(name = "updated_on")
	private Timestamp updatedDate;

	@Version
	@JsonIgnore
	private Long version;

	@Override
	public int hashCode() {
		return Objects.hash(uniqueRef);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		return Objects.equals(uniqueRef, other.uniqueRef);
	}

}
