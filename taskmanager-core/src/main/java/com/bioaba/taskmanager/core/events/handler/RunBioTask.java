package com.bioaba.taskmanager.core.events.handler;

import javax.inject.Inject;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.bioaba.taskmanager.core.events.BioTaskSavedEvent;
import com.bioaba.taskmanager.core.facade.BioTaskFacade;

@Component
public class RunBioTask implements ApplicationListener<BioTaskSavedEvent> {

	@Inject
	private BioTaskFacade facade; 
	
	@Override
	public void onApplicationEvent(BioTaskSavedEvent event) {
		facade.runTask(event.getTaskKey());
	}
	

}
