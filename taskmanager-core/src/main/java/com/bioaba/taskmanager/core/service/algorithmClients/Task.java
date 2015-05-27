package com.bioaba.taskmanager.core.service.algorithmClients;

import java.util.ArrayList;
import java.util.List;

import com.bioaba.taskmanager.persistence.entity.BioTask;
import com.bioaba.taskmanager.persistence.entity.BioTaskParameter;

public class Task {

	private String algorithmName;
	
	private String query;

	private List<AlgorithmParameter> parameters;

	private String databaseURL;

	private String databaseName;
	
	private String callbackURL;

	public Task(BioTask task, String callbackURL) {
		this.algorithmName = task.getAlgorithm().getName();
		this.query = task.getQuery();
		this.parameters= new ArrayList<AlgorithmParameter>();
		this.databaseURL = task.getDatabase().getUrl();
		this.databaseName = task.getDatabase().getName();
		this.callbackURL = callbackURL; 
		
		for(BioTaskParameter taskParam : task.getParameters()){
			this.parameters.add(new AlgorithmParameter(taskParam.getAlgorithmParameter().getName(), taskParam.getParameterValue()));
		}
	}
	
	public String getAlgorithmName() {
		return algorithmName;
	}

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
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
