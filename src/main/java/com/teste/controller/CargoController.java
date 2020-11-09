package com.teste.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.teste.exception.FuncionarioNotFoundException;
import com.teste.model.Cargo;
import com.teste.repository.CargoRepository;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
public class CargoController {

  @Autowired
  private final CargoRepository repository;

  @Autowired
  private final CargoModelAssembler assembler;

  public CargoController(CargoRepository repository, CargoModelAssembler assembler) {

    this.repository = repository;
    this.assembler = assembler;
  }

	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Cargo salvo com sucesso"),
		    @ApiResponse(code = 400, message = "Cargo não enontrado"),
		    @ApiResponse(code = 500, message = "Erro ao Salvar"),
	})
  @GetMapping("/cargos")
  public CollectionModel<EntityModel<Cargo>> getAll() {

    List<EntityModel<Cargo>> cargo = repository.findAll().stream() //
        .map(assembler::toModel) //
        .collect(Collectors.toList());

    return CollectionModel.of(cargo, linkTo(methodOn(CargoController.class).getAll()).withSelfRel());
  }

	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Cargo salvo com sucesso"),
		    @ApiResponse(code = 500, message = "Erro ao Salvar"),
	})
  @PostMapping("/cargos")
  public ResponseEntity<?> create(@RequestBody Cargo newCargo) {
    try {
		return new ResponseEntity<>( repository.save(newCargo), HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<>("Erro ao Salvar", HttpStatus.INTERNAL_SERVER_ERROR);
	}
  }

	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Cargo encontrado com sucesso"),
		    @ApiResponse(code = 400, message = "Cargo não enontrado"),
		    @ApiResponse(code = 500, message = "Erro ao busar")
	})
  @GetMapping("/cargos/{id}")
  public ResponseEntity<?> get(@PathVariable Integer id) {

	  try {
		Cargo cargo = repository.findById(id).get();
			
			if(cargo == null) {
				return new ResponseEntity<>("Cargo não enontrado", HttpStatus.BAD_REQUEST);
			}

		  return new ResponseEntity<>( assembler.toModel(cargo), HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<>("Erro ao busar", HttpStatus.INTERNAL_SERVER_ERROR);
	}
  }
  
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Cargo encontrado com sucesso"),
		    @ApiResponse(code = 400, message = "Cargo não enontrado"),
		    @ApiResponse(code = 500, message = "Erro ao busar")
	})
  @GetMapping("/cargos/name/{name}")
  public ResponseEntity<?> getByName(@PathVariable String name) {

	try {
		Cargo cargo = repository.findByName(name);
		
		if(cargo == null) {
			return new ResponseEntity<>("Cargo não enontrado", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>( assembler.toModel(cargo), HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<>("Erro ao busar", HttpStatus.INTERNAL_SERVER_ERROR);
	}
  }
  
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Cargo salvo com sucesso"),
		    @ApiResponse(code = 500, message = "Erro ao Salvar")
	})
  @PutMapping("/cargos/{id}")
  public ResponseEntity<?> update(@RequestBody Cargo newCargo, @PathVariable Integer id) {

    try {
		return repository.findById(id)
		  .map(cargo -> {
			  cargo.setName(newCargo.getName());
			  return new ResponseEntity<>(repository.save(newCargo), HttpStatus.OK);
		  })
		  .orElseGet(() -> {
			  newCargo.setId(id);
		    return new ResponseEntity<>(repository.save(newCargo), HttpStatus.OK);
		  });
	} catch (Exception e) {
		return new ResponseEntity<>("Erro ao salvar", HttpStatus.INTERNAL_SERVER_ERROR);
	}
  }

	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Cargo deletado com sucesso"),
		    @ApiResponse(code = 500, message = "Erro ao deletar")
	})
  @DeleteMapping("/cagos/{id}")
  public ResponseEntity<?> delete(@PathVariable Integer id) {
    try {
		repository.deleteById(id);
		return new ResponseEntity<>( "Cargo deletado com sucesso", HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<>("Erro ao busar", HttpStatus.INTERNAL_SERVER_ERROR);
	}
  }
}