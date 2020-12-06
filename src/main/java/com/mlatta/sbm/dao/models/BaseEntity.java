package com.mlatta.sbm.dao.models;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
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
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 6965767719265988245L;

	@NaturalId
	@Column(columnDefinition = "binary(16)", updatable = false, nullable = false, unique = true)
	private UUID uniqueRef; 

	@CreationTimestamp
	@Column(name = "created_on", updatable = false)
	private OffsetDateTime createdDateTime;

	@UpdateTimestamp
	@Column(name = "updated_on")
	private OffsetDateTime updatedDateTime;

	@Version
	@JsonIgnore
	private short version;
	
	@PrePersist
	private void beforePersist() {
		this.uniqueRef = UUID.randomUUID();
		this.createdDateTime = OffsetDateTime.now();
	}
	
	@PreUpdate
	private void beforeUpdate() {
		this.updatedDateTime = OffsetDateTime.now();
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdDateTime, uniqueRef, updatedDateTime, version);
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
		return Objects.equals(createdDateTime, other.createdDateTime) && Objects.equals(uniqueRef, other.uniqueRef)
				&& Objects.equals(updatedDateTime, other.updatedDateTime) && Objects.equals(version, other.version);
	}

	@Override
	public String toString() {
		return "BaseEntity [uniqueRef=" + uniqueRef + ", createdDateTime=" + createdDateTime + ", updatedDateTime="
				+ updatedDateTime + ", version=" + version + "]";
	}
	
}
