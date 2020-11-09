package com.teste.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.teste.model.Departamento;

@Component
public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {
	@Query("select d from Departamento d where d.name = ?1")
	Departamento findByName(String name);
}