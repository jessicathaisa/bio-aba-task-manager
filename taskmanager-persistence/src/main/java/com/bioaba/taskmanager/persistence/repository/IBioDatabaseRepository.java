package com.bioaba.taskmanager.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bioaba.taskmanager.persistence.entity.BioDatabase;

@Repository
public interface IBioDatabaseRepository extends JpaRepository<BioDatabase, Long>{

	public List<BioDatabase> findAll();
	
}
