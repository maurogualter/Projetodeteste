package com.teste.exception;

public class FuncionarioNotFoundException extends RuntimeException {

	  public FuncionarioNotFoundException(Integer id) {
	    super("Funcionario não enontrado " + id);
	  }
	  
	  public FuncionarioNotFoundException(String name) {
		    super("Funcionario não enontrado " + name);
		  }
	}