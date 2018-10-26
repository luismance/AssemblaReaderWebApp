package com.webdrone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ROLE")
public class Role extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Column(name = "NAME", length = 255, nullable = false)
	private String name;

	@Column(name = "DESCRIPTION", columnDefinition = "TEXT", nullable = true)
	private String description;

	public Role() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
