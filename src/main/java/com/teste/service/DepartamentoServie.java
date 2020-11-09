package com.teste.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.teste.exception.DepartamentoNotFoundException;
import com.teste.model.Departamento;
import com.teste.repository.DepartamentoRepository;

@Service
public class DepartamentoServie {
	
	@Autowired
	DepartamentoRepository repository;

	public List<Departamento> findAll() {
		return repository.findAll();
	}

	public Departamento save(Departamento departamento) {
		return repository.save(departamento);
	}

	public Optional<Departamento> findById(Integer id) {
		return repository.findById(id);
	}

	public void deleteById(Integer id) {
		repository.deleteById(id);
	}

	public Departamento replaceDepartamentos(Departamento newDepartamento, Integer id) throws DepartamentoNotFoundException, Exception  {
		Departamento dep = null;
		try {
			dep = repository.findById(id).get();
		} catch (Exception e) {
			throw new DepartamentoNotFoundException(id);
		}
		
		dep.setName(newDepartamento.getName());
		dep.setFuncionarios(newDepartamento.getFuncionarios());
		dep.setChefeDepartamento(newDepartamento.getChefeDepartamento());
        return repository.save(dep);
	}
}
