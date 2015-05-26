package com.bioaba.taskmanager.persistence.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bioaba.taskmanager.persistence.entity.BioTask;

@Repository
public interface IBioTaskRepository  extends JpaRepository<BioTask, Long>{

	BioTask findByTaskKey(String taskKey);
}
