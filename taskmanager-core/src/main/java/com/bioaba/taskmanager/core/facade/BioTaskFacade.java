package com.bioaba.taskmanager.core.facade;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.bioaba.taskmanager.core.events.BioTaskSavedEvent;
import com.bioaba.taskmanager.core.facade.base.AbstractCrudFacade;
import com.bioaba.taskmanager.core.service.BioTaskParameterService;
import com.bioaba.taskmanager.core.service.BioTaskService;
import com.bioaba.taskmanager.core.service.BioTaskStatusService;
import com.bioaba.taskmanager.persistence.entity.BioTask;
import com.bioaba.taskmanager.persistence.entity.BioTaskParameter;
import com.bioaba.taskmanager.persistence.entity.BioTaskStatus;

@Component
@Transactional
public class BioTaskFacade extends AbstractCrudFacade<BioTask> {

	private BioTaskService taskService;

	@Inject
	private BioTaskParameterService taskParameterService;
	@Inject
	private BioTaskStatusService taskStatusService;

	@Inject
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	public BioTaskFacade(BioTaskService taskService) {
		super(taskService);
		this.taskService = taskService;
	}

	@Override
	public BioTask save(BioTask entity) {
		for (BioTaskParameter param : entity.getParameters())
			taskParameterService.save(param);
		super.save(entity);

		eventPublisher.publishEvent(new BioTaskSavedEvent(this, entity
				.getTaskKey()));
		return entity;
	}

	public void updateTaskStatusByTaskKey(String taskKey, String taskStatusName) {
		BioTask biotask = taskService.findByTaskKey(taskKey);
		if (biotask == null) {
			System.out.println("Lançar erro 404");
			return;
		}

		BioTaskStatus taskStatus = taskStatusService.findByName(taskStatusName);
		if (taskStatus == null) {
			System.out.println("Lançar erro 404 também?");
			return;
		}

		biotask.setStatus(taskStatus);
		taskService.save(biotask);
	}

	public BioTask findByTaskKey(String taskKey) {
		BioTask task = taskService.findByTaskKey(taskKey);
		return task;
	}

	public void runTask(String taskKey) {
		BioTask task = taskService.findByTaskKey(taskKey);
		String resourcePath = "";
		if (task == null) {
			System.out.println("Lançar erro 404");
			return;
		}
		resourcePath = taskService.submitTask(task);
		task.setResourcePath(resourcePath);

		taskService.save(task);
	}
}
