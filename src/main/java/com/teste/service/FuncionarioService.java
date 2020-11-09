package com.teste.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.exception.DepartamentoNotFoundException;
import com.teste.exception.FuncionarioNotFoundException;
import com.teste.model.Departamento;
import com.teste.model.Funcionario;
import com.teste.model.HistorioFuncionarioDepartamento;
import com.teste.repository.DepartamentoRepository;
import com.teste.repository.FuncionarioRepository;
import com.teste.repository.HistorioFuncionarioDepartamentoRepository;

@Service
public class FuncionarioService {

	@Autowired
	FuncionarioRepository funcionarioRepository;

	@Autowired
	DepartamentoRepository departamentoRepository;

	@Autowired
	HistorioFuncionarioDepartamentoRepository historioRepository;

	public Funcionario save(Funcionario funcionario) {
		gravaHistorioDepartamento(funcionario);
		return funcionarioRepository.save(funcionario);
	}

	public Funcionario findByName(String name) {
		return funcionarioRepository.findByName(name);
	}

	public List<Funcionario> findAll() {
		return funcionarioRepository.findAll();
	}

	public Optional<Funcionario> findById(Integer id) {
		return funcionarioRepository.findById(id);
	}

	public void deleteById(Integer id) {
		funcionarioRepository.deleteById(id);
		return;
	}

	public Funcionario update(Funcionario newFuncionario, Integer id) throws FuncionarioNotFoundException, Exception {
		Funcionario funcionario = funcionarioRepository.findById(id).get();

		if (funcionario == null) {
			throw new FuncionarioNotFoundException(id);
		}

		if (newFuncionario.getName() != null && newFuncionario.getName().isBlank()) {
			funcionario.setName(newFuncionario.getName());
		}
		if (newFuncionario.getAge() != null) {
			funcionario.setAge(newFuncionario.getAge());
		}
		if (newFuncionario.getBirthday() != null) {
			funcionario.setBirthday(newFuncionario.getBirthday());
		}
		if (newFuncionario.getDocument() != null && newFuncionario.getDocument().isBlank()) {
			funcionario.setDocument(newFuncionario.getDocument());
		}
		if (newFuncionario.getCargo() != null) {
			funcionario.setCargo(newFuncionario.getCargo());
		}

		return funcionarioRepository.save(funcionario);
	}

	public Funcionario updateDepartamento(Integer id, String nomeDerpartamento)
			throws FuncionarioNotFoundException, DepartamentoNotFoundException, Exception {
		Funcionario funcionario = funcionarioRepository.findById(id).get();

		if (funcionario == null) {
			throw new FuncionarioNotFoundException(id);
		}

		Departamento departamento = departamentoRepository.findByName(nomeDerpartamento);

		if (departamento == null) {
			throw new DepartamentoNotFoundException(nomeDerpartamento);
		}
		
		/******************************/
		Departamento depFuncionario = funcionario.getDepartamento();
		
		if(depFuncionario != null) {
			if(!depFuncionario.getName().equals(departamento.getName())) {
				Funcionario fun1 = depFuncionario.getFuncionarios().stream().filter(p -> p.getName().equals(funcionario.getName())).findFirst().get();
				depFuncionario.getFuncionarios().remove(fun1);
				departamentoRepository.save(depFuncionario);
			}
		}
		
		
		Set<Funcionario> funcsDep = null;
		try {
			funcsDep = departamento.getFuncionarios();
		} catch (Exception e) {
			funcsDep = new HashSet<Funcionario>();
		}
		funcsDep.add(funcionario);
		funcionario.setDepartamento(departamento);
		departamentoRepository.save(departamento);
		funcionario.setDepartamento(departamento);
		gravaHistorioDepartamento(funcionario);
		return funcionario;

	}

	private void gravaHistorioDepartamento(Funcionario funionario) {
		if (funionario.getDepartamento() != null) {
			HistorioFuncionarioDepartamento newHistorico = new HistorioFuncionarioDepartamento();
			newHistorico.setIdFuncionario(funionario.getId());
			newHistorico.setNameFuncionario(funionario.getName());
			newHistorico.setIdPepartamento(funionario.getDepartamento().getId());
			newHistorico.setNameDepartamento(funionario.getDepartamento().getName());
			newHistorico.setDataIncio(new Date(Calendar.getInstance().getTime().getTime()));
			
			HistorioFuncionarioDepartamento historico = historioRepository.findByNameFuncionarioDataFinal(funionario.getName());
			
			if(historico != null) {
				historico.setDataFim(new Date(Calendar.getInstance().getTime().getTime()));
				historioRepository.save(historico);
			}
			
			historioRepository.save(newHistorico);
		}
	}
}
