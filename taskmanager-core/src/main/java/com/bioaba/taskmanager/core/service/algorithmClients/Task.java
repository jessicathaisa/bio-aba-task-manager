package com.bioaba.taskmanager.core.service.algorithmClients;

import java.util.ArrayList;
import java.util.List;

import com.bioaba.taskmanager.persistence.entity.BioTask;

public class Task {

	private String algorithmName;

	private List<AlgorithmParameter> parameters;

	private String databaseURL;

	private String databaseName;
	
	private String callbackURL;

	public Task(BioTask task, String callbackURL) {
		this.algorithmName = task.getAlgorithm().getName();
		this.parameters = new ArrayList<AlgorithmParameter>();
		this.databaseURL = task.getDatabase().getUrl();
		this.databaseName = task.getDatabase().getName();
		this.callbackURL = callbackURL; 
	}

	public String getAlgorithmName() {
		return algorithmName;
	}

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}

	public List<AlgorithmParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<AlgorithmParameter> parameters) {
		this.parameters = parameters;
	}

	public String getDatabaseURL() {
		return databaseURL;
	}

	public void setDatabaseURL(String databaseURL) {
		this.databaseURL = databaseURL;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getCallbackURL() {
		return callbackURL;
	}
}
