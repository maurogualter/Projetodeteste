package com.teste.dto;

import java.util.List;

public class DepartamentoDTO {
    private Integer id;
    private String name;
    private String nomeChefeDepartamento;
    private List<String> funcionarios;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getFuncionarios() {
		return funcionarios;
	}
	public void setFuncionarios(List<String> funcionarios) {
		this.funcionarios = funcionarios;
	}
	public String getNomeChefeDepartamento() {
		return nomeChefeDepartamento;
	}
	public void setNomeChefeDepartamento(String nomeChefeDepartamento) {
		this.nomeChefeDepartamento = nomeChefeDepartamento;
	}
	
    
    
}
