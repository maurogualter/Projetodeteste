package com.teste.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.teste.model.Funcionario;

@Component
public class FuncionarioModelAssembler implements RepresentationModelAssembler<Funcionario, EntityModel<Funcionario>> {

  @Override
  public EntityModel<Funcionario> toModel(Funcionario funcionario) {

    return EntityModel.of(funcionario, //
        linkTo(methodOn(FuncionarioController.class).getById(funcionario.getId())).withSelfRel(),
        linkTo(methodOn(FuncionarioController.class).getAll()).withRel("funcionarios"));
  }
}