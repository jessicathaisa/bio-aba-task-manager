package com.bioaba.taskmanager.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="biotaskparameter")
public class BioTaskParameter {

	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER)
	private BioAlgoParameter algorithmParameter;
	
	@Column(nullable=true)
	private String parameterValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BioAlgoParameter getAlgorithmParameter() {
		return algorithmParameter;
	}

	public void setAlgorithmParameter(BioAlgoParameter algorithmParameter) {
		this.algorithmParameter = algorithmParameter;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String valueAlgorithm) {
		this.parameterValue = valueAlgorithm;
	}
	
	
}
