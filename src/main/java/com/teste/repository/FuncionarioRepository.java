package com.teste.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.teste.model.Funcionario;

@Component
public abstract interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
	@Query("select f from Funcionario f where f.name = ?1")
	Funcionario findByName(String name);
}