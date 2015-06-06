package com.bioaba.taskmanager.core.events;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class BioTaskSavedEvent extends ApplicationEvent {

	private String taskKey;

	public BioTaskSavedEvent(Object source, String taskKey) {
		super(source);

		this.taskKey = taskKey;
	}

	public String getTaskKey() {
		return this.taskKey;
	}

}
