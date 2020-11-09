package com.teste.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cargo")
public class Cargo {

	public Cargo() {}
	
	public Cargo(String name) {
		this.name = name;
	}
	
    @Id
    @Column(name="cargo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name="cargo_name")
    private String name;

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
    
    @Override
    public boolean equals(Object o) {

      if (this == o)
        return true;
      if (!(o instanceof Cargo))
        return false;
      Cargo cargo = (Cargo) o;
      return Objects.equals(this.id, cargo.id) && Objects.equals(this.name, cargo.name);
    }

    @Override
    public int hashCode() {
      return Objects.hash(this.id, this.name, this.name);
    }

    @Override
    public String toString() {
      return "cargo{" + "id=" + this.id + ", name='" + this.name + "}";
    }

}