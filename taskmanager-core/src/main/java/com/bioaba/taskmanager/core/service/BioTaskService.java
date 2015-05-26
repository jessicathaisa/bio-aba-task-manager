package com.bioaba.taskmanager.core.service;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bioaba.taskmanager.core.service.algorithmClients.Task;
import com.bioaba.taskmanager.core.service.base.AbstractCrudService;
import com.bioaba.taskmanager.persistence.entity.BioTask;
import com.bioaba.taskmanager.persistence.repository.IBioTaskRepository;

@Service
public class BioTaskService extends AbstractCrudService<BioTask> {

	private IBioTaskRepository repository;
	@Value("${application.callbackUrl}")
	private String callbackURL;
	
	@Inject
	public BioTaskService(IBioTaskRepository repository) {
		super(repository);
		this.repository = repository;
	}
	
	public BioTask findByTaskKey(String taskKey){
		return repository.findByTaskKey(taskKey);
	}
	
	public String submitTask(BioTask biotask){
		Task task = new Task(biotask, callbackURL);
		RestTemplate restTemplate = new RestTemplate();
		String taskPath = "";
		try{
			String URL = biotask.getAlgorithm().getUrl() + "/tasks";
			ResponseEntity<Void> response = restTemplate.postForEntity(URL, task, Void.class);
			taskPath = response.getHeaders().get("Location").get(0);
		}
		catch(ArrayIndexOutOfBoundsException ex){
			System.out.println("Wrong returned Location");
		}
		catch(Exception ex){
			System.out.println("Invalid URL");
		}
		
		return taskPath;
	}

}
