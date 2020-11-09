package com.teste.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.teste.model.Cargo;

@Component
public interface CargoRepository extends JpaRepository<Cargo , Integer> {
	
	@Query("select c from Cargo c where c.name = ?1")
	Cargo findByName(String name);

}