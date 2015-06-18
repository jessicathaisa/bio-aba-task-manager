package com.bioaba.taskmanager.core.facade;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bioaba.taskmanager.core.facade.base.AbstractCrudFacade;
import com.bioaba.taskmanager.core.service.BioTaskParameterService;
import com.bioaba.taskmanager.core.service.BioTaskService;
import com.bioaba.taskmanager.core.service.BioTaskStatusService;
import com.bioaba.taskmanager.core.service.amazons3Client.FileStoreType;
import com.bioaba.taskmanager.core.service.amazons3Client.StorageAmazonS3;
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

	private StorageAmazonS3 s3Service;

	@Autowired
	public BioTaskFacade(BioTaskService taskService) {
		super(taskService);
		this.taskService = taskService;
		this.s3Service = new StorageAmazonS3();
	}

	public BioTask saveTask(BioTask entity, byte[] file) {
		for (BioTaskParameter param : entity.getParameters())
			taskParameterService.save(param);
		super.save(entity);

		s3Service.save(entity.getTaskKey(), file, FileStoreType.QUERY);

		return entity;
	}


	public void updateTaskStatusByTaskKey(String taskKey, String taskStatusName, byte[] result) {
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

		s3Service.save(taskKey, result, FileStoreType.RESULT);

		biotask.setStatus(taskStatus);
		taskService.save(biotask);
	}

	public BioTask findByTaskKey(String taskKey) {
		BioTask task = taskService.findByTaskKey(taskKey);
		return task;
	}

	public String findResultByTaskKey(String taskKey) {
		byte[] resultArray = s3Service.findByTaskKey(taskKey,
				FileStoreType.RESULT);
		String content = "";
		try {
			content = new String(resultArray);
		} catch (Exception ex) {
			System.out.println("Error while extracting text from ResultFile.");
			System.out.println(ex.toString());
		}
		return content;
	}

	public void runTask(String taskKey) {
		BioTask task = taskService.findByTaskKey(taskKey);
		byte[] resultArray = s3Service.findByTaskKey(taskKey,
				FileStoreType.QUERY);
		String pathInTheAlgorithm = "";
		if (task == null) {
			System.out.println("Lançar erro 404");
			return;
		}
		pathInTheAlgorithm = taskService.submitTask(task, resultArray);
		task.setPathInTheAlgorithm(pathInTheAlgorithm);

		taskService.save(task);
	}
}
