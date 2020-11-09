package com.teste.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="historio_func_dep")
public class HistorioFuncionarioDepartamento {
	  
	@Id
    @Column(name="historio_func_dep_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "historio_func_dep_sequence")
    @SequenceGenerator(name = "historio_func_dep_sequence", sequenceName = "historio_func_dep_sequence", allocationSize = 1)
    private Integer id;	
	
	@Column(name="funcionario_id")
	private Integer idFuncionario;
	@Column(name="funcionario_name")
	private String  nameFuncionario;
	@Column(name="departamento_id")
	private Integer idPepartamento;
	@Column(name="departamento_name")
	private String  nameDepartamento;
	@Column(name="historio_func_dep_data_incio")
	private Date    dataIncio;
	@Column(name="historio_func_dep_data_fim")
	private Date    dataFim;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdFuncionario() {
		return idFuncionario;
	}
	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
	public String getNameFuncionario() {
		return nameFuncionario;
	}
	public void setNameFuncionario(String nameFuncionario) {
		this.nameFuncionario = nameFuncionario;
	}
	public Integer getIdPepartamento() {
		return idPepartamento;
	}
	public void setIdPepartamento(Integer idPepartamento) {
		this.idPepartamento = idPepartamento;
	}
	public String getNameDepartamento() {
		return nameDepartamento;
	}
	public void setNameDepartamento(String nameDepartamento) {
		this.nameDepartamento = nameDepartamento;
	}
	public Date getDataIncio() {
		return dataIncio;
	}
	public void setDataIncio(Date dataIncio) {
		this.dataIncio = dataIncio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	
}
