package com.bioaba.taskmanager.persistence.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "biotask")
public class BioTask {

	@Id @GeneratedValue
	private Integer id;

	@Column(unique = true)
	private String taskKey;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_database")
	private BioDatabase database;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_algoritm")
	private BioAlgorithm algorithm;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_status")
	private BioTaskStatus status;
	
	private String resourcePath;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public BioAlgorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(BioAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	public BioDatabase getDatabase() {
		return database;
	}

	public void setDatabase(BioDatabase database) {
		this.database = database;
	}


	public BioTaskStatus getStatus() {
		return status;
	}

	public void setStatus(BioTaskStatus status) {
		this.status = status;
	}
	
	@PrePersist
	public void preInsert(){
		taskKey = UUID.randomUUID().toString();
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

}
