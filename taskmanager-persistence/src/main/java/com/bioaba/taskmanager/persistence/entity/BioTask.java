package com.bioaba.taskmanager.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "task")
public class BioTask {

	@Id @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_database")
	private BioDatabase database;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_algoritm")
	private BioAlgorithm algorithm;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_status")
	private BioTaskStatus status;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BioDatabase getDatabase() {
		return database;
	}
	public void setDatabase(BioDatabase database) {
		this.database = database;
	}
	public BioAlgorithm getAlgorith() {
		return algorithm;
	}
	public void setAlgorith(BioAlgorithm algorithm) {
		this.algorithm = algorithm;
	}
	public BioTaskStatus getStatus() {
		return status;
	}
	public void setStatus(BioTaskStatus status) {
		this.status = status;
	}
	
}
