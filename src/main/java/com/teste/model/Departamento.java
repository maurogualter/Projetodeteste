package com.teste.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="departamento")
public class Departamento {

	public Departamento() {}
	
	public Departamento( String name, Funcionario chefeDepartamento) {
		this.name = name;
		this.chefeDepartamento = chefeDepartamento;
	}
	
    @Id
    @Column(name="departamento_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departamento_sequence")
    @SequenceGenerator(name = "departamento_sequence", sequenceName = "departamento_sequence", allocationSize = 1)
    private Integer id;
    
    @Column(name="departamento_name")
    private String name;
    
	@ManyToOne
    @JoinColumn(name="chefe_departamento_funcionario_id", nullable=false)
    private Funcionario chefeDepartamento;
	
	
	//@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "funcionario_departamento",
            joinColumns = {
                    @JoinColumn(name = "departamento_id", referencedColumnName = "departamento_id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "funcionario_id", referencedColumnName = "funcionario_id",
                            nullable = false, updatable = false)})
	
    private Set<Funcionario> funcionarios;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
    public Funcionario getChefeDepartamento() {
		return chefeDepartamento;
	}

	public void setChefeDepartamento(Funcionario chefeDepartamento) {
		this.chefeDepartamento = chefeDepartamento;
	}

	public Set<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(Set<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	@Override
    public boolean equals(Object o) {

      if (this == o)
        return true;
      if (!(o instanceof Departamento))
        return false;
      Departamento departamento = (Departamento) o;
      return Objects.equals(this.id, departamento.id) && Objects.equals(this.name, departamento.name)
    		  && this.chefeDepartamento.equals(departamento.chefeDepartamento);
    }

    @Override
    public int hashCode() {
      return Objects.hash(this.id, this.name, this.name,this.chefeDepartamento.hashCode());
    }

    @Override
    public String toString() {
    	String retorno = "Departamento{" + "id=" + this.id + ", name='" + this.name + "', chefeDepartamento=" + this.chefeDepartamento.toString() +  " }";
    	/*
      		  ", funcionarios=[ ";
    	if(funcionarios != null) {
	    	for (Funcionario funcionario : funcionarios) {
	    		retorno += funcionario.toString() + ", ";
			}
    	}
    	retorno += " ]}";
    	*/
      return retorno;
    		  
    		  
    		  
    		  
    		  
    }
    
    
}