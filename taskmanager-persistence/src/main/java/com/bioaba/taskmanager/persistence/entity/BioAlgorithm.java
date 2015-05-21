package com.bioaba.taskmanager.persistence.entity;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bioalgorithm")
public class BioAlgorithm {

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	@ElementCollection
	private List<String> allowedFormats;
	
	private String url;
	
	private String description ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getAllowedFormats() {
		return allowedFormats;
	}

	public void setAllowedFormats(List<String> allowedFormats) {
		this.allowedFormats = allowedFormats;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
