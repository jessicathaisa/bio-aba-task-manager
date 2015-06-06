package com.bioaba.taskmanager.core.service.amazons3Client;

public enum FileStoreType {
	QUERY("query"), RESULT("result");
	
	private final String fileDescription;
	
	private FileStoreType(String value) {
        fileDescription = value;
    }

    public String getTypeDescription() {
        return fileDescription;
    }
}
