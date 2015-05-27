package com.bioaba.taskmanager.core.service.algorithmClients;


public class AlgorithmParameter {

	private String paramName;
	
	private String paramValue;

	public AlgorithmParameter(String paramName, String paramValue) {
		this.paramName = paramName;
		this.paramValue = paramValue;
	}
	
	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
}
