package com.bioaba.taskmanager.core.facade;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bioaba.taskmanager.core.events.BioTaskSavedEvent;
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

	@Inject
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	public BioTaskFacade(BioTaskService taskService) {
		super(taskService);
		this.taskService = taskService;
		this.s3Service = new StorageAmazonS3();
	}

	public BioTask saveTask(BioTask entity, MultipartFile queryFile) {
		for (BioTaskParameter param : entity.getParameters())
			taskParameterService.save(param);
		super.save(entity);

		s3Service.save(entity.getTaskKey(), queryFile, FileStoreType.QUERY);

		eventPublisher.publishEvent(new BioTaskSavedEvent(this, entity
				.getTaskKey()));
		return entity;
	}

	public BioTask saveTask(BioTask entity, String queryText) {
		for (BioTaskParameter param : entity.getParameters())
			taskParameterService.save(param);
		super.save(entity);

		s3Service.save(entity.getTaskKey(), queryText, FileStoreType.QUERY);

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

	private String readFile(File file, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(file.toPath());
		return new String(encoded, encoding);
	}

	public String findResultByTaskKey(String taskKey) {
		File resultFile = s3Service
				.findByTaskKey(taskKey, FileStoreType.RESULT);
		String content = "";
		try{
			content = readFile(resultFile, Charset.defaultCharset());
		}
		catch (IOException ioe){
			System.out.println("IO Error while extracting text from ResultFile.");
		}
		return content;
	}

	public void runTask(String taskKey) {
		BioTask task = taskService.findByTaskKey(taskKey);
		File queryFile = s3Service.findByTaskKey(taskKey, FileStoreType.QUERY);
		String pathInTheAlgorithm = "";
		if (task == null) {
			System.out.println("Lançar erro 404");
			return;
		}
		pathInTheAlgorithm = taskService.submitTask(task, queryFile);
		task.setPathInTheAlgorithm(pathInTheAlgorithm);

		taskService.save(task);
	}
}
