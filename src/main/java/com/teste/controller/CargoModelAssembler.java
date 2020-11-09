package com.teste.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.teste.model.Cargo;
import com.teste.model.Funcionario;

@Component
public class CargoModelAssembler implements RepresentationModelAssembler<Cargo, EntityModel<Cargo>> {

  @Override
  public EntityModel<Cargo> toModel(Cargo cargo) {

    return EntityModel.of(cargo, //
        linkTo(methodOn(CargoController.class).get(cargo.getId())).withSelfRel(),
        linkTo(methodOn(CargoController.class).getAll()).withRel("cargos"));
  }
}