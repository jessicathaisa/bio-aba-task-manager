package com.bioaba.taskmanager.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="bioalgorithm")
public class BioAlgorithm {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	private String name;
	
	private String provider;
	
	@ElementCollection(fetch=FetchType.EAGER)
	private List<String> allowedFormats;
	
	@ElementCollection(fetch=FetchType.EAGER)
	private List<BioAlgoParameter> parameters;
	
	@Column(nullable=false)
	private String url;
	
	@Column(nullable=false)
	private String description ;
	
	@OneToOne(fetch = FetchType.EAGER)
	private BioAlgorithmClassification classification;

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

	public List<BioAlgoParameter> getParameters() {
		return parameters != null ? parameters : new ArrayList<BioAlgoParameter>();
	}

	public void setParameters(List<BioAlgoParameter> parameters) {
		this.parameters = parameters;
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

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public BioAlgorithmClassification getClassification() {
		return classification;
	}

	public void setClassification(BioAlgorithmClassification classification) {
		this.classification = classification;
	}
}
