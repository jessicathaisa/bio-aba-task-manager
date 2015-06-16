package com.bioaba.taskmanager.core.service;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.bioaba.taskmanager.core.service.algorithmClients.AlgorithmParameter;
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

	public BioTask findByTaskKey(String taskKey) {
		return repository.findByTaskKey(taskKey);
	}

	public String submitTask(BioTask biotask, byte[] resultArray) {
		Task task = new Task(biotask, resultArray, callbackURL);
		RestTemplate restTemplate = new RestTemplate();
		String taskPath = "";
		try {
			String URL = biotask.getAlgorithm().getUrl() + "/tasks";
			Map<String, Object> parts = new HashMap<String, Object>();
			parts.put("algorithmName", task.getAlgorithmName());
			Map<String, String> params = new HashMap();
			for(AlgorithmParameter alg : task.getParameters())
				params.put(alg.getParamName(), alg.getParamValue());
			parts.put("parameters", params);
			parts.put("taskKey", biotask.getTaskKey());
			parts.put("databaseName", task.getDatabaseName());
			parts.put("databaseURL", task.getDatabaseURL());
			parts.put("callbackURL", task.getCallbackURL());
			parts.put("query", Base64.encodeBase64(task.getQuery()));

			ResponseEntity<Void> response = restTemplate.postForEntity(URL, parts, Void.class);

			taskPath = response.getHeaders().get("Location").get(0);
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.out.println("Error while receiving response of algorithm: Wrong Location");
		} catch (Exception ex) {
			System.out.println("Error while receiving response of algorithm: Invalid URL");
		}

		return taskPath;
	}	

}
