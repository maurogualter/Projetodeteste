package com.teste.model;

import java.sql.Date;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.teste.util.UtilTeste;

@Entity
@Table(name="funcionario")
public class Funcionario {

	public Funcionario() {}
	
	public Funcionario(String name,Date birthday,String document,Cargo cargo, Set<Departamento> departamentos) {
		this.name = name;
		this.age = UtilTeste.calculaAge(birthday);
		this.birthday = birthday;
		this.document = document;
		this.cargo = cargo;
		//this.departamentos = departamentos;
	}
	
	public Funcionario(String name,Date birthday,String document,Cargo cargo) {
		this.name = name;
		this.age = UtilTeste.calculaAge(birthday);
		this.birthday = birthday;
		this.document = document;
		this.cargo = cargo;
	}
	
    @Id
    @Column(name="funcionario_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "funcionario_sequence")
    @SequenceGenerator(name = "funcionario_sequence", sequenceName = "funcionario_sequence", allocationSize = 1)
    private Integer id;
    
    @Column(name="funcionario_name")
    private String name;
    
    @Column(name="funcionario_age")
    private Integer age;
    
    @Column(name="funcionario_birthday")
    private Date birthday;
    
    @Column(name="funcionario_document")
    private String document;
    
	@ManyToOne
    @JoinColumn(name="cargo_id", nullable=false)
    private Cargo cargo;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "funcionario_departamento",
       joinColumns = {
               @JoinColumn(name = "funcionario_id", referencedColumnName = "funcionario_id",
                       nullable = false, updatable = false)},
       inverseJoinColumns = {
               @JoinColumn(name = "departamento_id", referencedColumnName = "departamento_id",
                       nullable = false, updatable = false)})
	private Departamento departamento; 

    public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

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
	
	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	@Override
    public boolean equals(Object o) {

      if (this == o)
        return true;
      if (!(o instanceof Funcionario))
        return false;
      Funcionario funcionario = (Funcionario) o;
      return Objects.equals(this.id, funcionario.id) && Objects.equals(this.name, funcionario.name)
    		  && Objects.equals(this.age, funcionario.age)
    		  && Objects.equals(this.birthday, funcionario.birthday)
    		  && Objects.equals(this.document, funcionario.document)
    		  && this.cargo.equals(funcionario.cargo);
    }

    @Override
    public int hashCode() {
      return Objects.hash(this.id, this.name, this.name,this.age,this.birthday,this.cargo.hashCode());
    }

    @Override
    public String toString() {
    	String retorno = "funcionario{" + "id=" + this.id + ", name='" + this.name +"', age=" + this.age + ", birthday='" + this.birthday 
		          + "', document='" + this.document + "', cargo='" + this.cargo.toString() +  "' }";
		          
		/*          
		          ", departamentos=[ ";
    	
    	if(departamentos != null) {
	    	for (Departamento departamento : departamentos) {
	    		retorno += departamento.toString() + ", ";
			}
    	}
    	
    	retorno += " ]}";
    	*/
    	return retorno;
    }
    
}