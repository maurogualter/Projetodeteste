package com.teste.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.teste.model.HistorioFuncionarioDepartamento;

@Component
public abstract interface HistorioFuncionarioDepartamentoRepository extends JpaRepository<HistorioFuncionarioDepartamento, Integer> {
	
	@Query("select f from HistorioFuncionarioDepartamento f where f.nameFuncionario = ?1 and f.dataFim = null")
	HistorioFuncionarioDepartamento findByNameFuncionarioDataFinal(String name);
}