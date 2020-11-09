package com.teste.exception;

public class DepartamentoNotFoundException extends RuntimeException {

	  public DepartamentoNotFoundException(Integer id) {
	    super("Could not find departamento " + id);
	  }
	  
	  public DepartamentoNotFoundException(String name) {
		    super("Could not find departamento " + name);
		  }
	}