package com.teste.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.teste.dto.DepartamentoDTO;
import com.teste.exception.DepartamentoNotFoundException;
import com.teste.model.Departamento;
import com.teste.model.Funcionario;
import com.teste.service.DepartamentoServie;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class DepartamentoController {

	@Autowired
	private final DepartamentoServie service;

	public DepartamentoController(DepartamentoServie service) {

		this.service = service;
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Departamento encontrado com sucesso"),
			@ApiResponse(code = 500, message = "Erro ao buscar departamento"), })
	@GetMapping("/departamentos")
	public ResponseEntity<?> getAll() {
		List<DepartamentoDTO> depDTO = new ArrayList<DepartamentoDTO>();

		try {
			List<Departamento> departamentos = service.findAll();
			if (departamentos != null) {
				for (Departamento departamento : departamentos) {
					depDTO.add(convertDepartamentoToDto(departamento));
				}
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao buscar departamento", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(depDTO, HttpStatus.OK);
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Departamento salvo com sucesso"),
			@ApiResponse(code = 500, message = "Erro ao Salvar"), })
	@PostMapping("/departamentos")
	public ResponseEntity<?> create(@RequestBody Departamento newDepartamento) {
		try {
			return new ResponseEntity<>(convertDepartamentoToDto(service.save(newDepartamento)), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao Salvar", HttpStatus.BAD_REQUEST);
		}
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Departamento encontrado com sucesso"),
			@ApiResponse(code = 400, message = "Departamento não enontrado") })
	@GetMapping("/departamentos/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		Departamento departamento = null;
		try {
			departamento = service.findById(id).get();
		} catch (Exception e) {
			return new ResponseEntity<>("Departamento não encontrado", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(convertDepartamentoToDto(departamento), HttpStatus.OK);
	}

	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Departamento salvo com sucesso"),
		    @ApiResponse(code = 400, message = "Departamento não enontrado"),
		    @ApiResponse(code = 500, message = "Erro ao Salvar"),
	})
	@PutMapping("/departamentos/{id}")
	public ResponseEntity<?> update(@RequestBody Departamento newDepartamento, @PathVariable Integer id) {
		Departamento departamento = null;
		try {
			departamento = service.replaceDepartamentos(newDepartamento, id);
		} catch (DepartamentoNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao Salvar", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(convertDepartamentoToDto(departamento), HttpStatus.OK);
	}

	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Departamento Deletado com sucesso"),
		    @ApiResponse(code = 400, message = "Erro ao deletar"),
	})
	@DeleteMapping("/departamentos/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		try {
			service.deleteById(id);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao deletar", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Departamento Deletado com sucesso", HttpStatus.OK);
	}

	private DepartamentoDTO convertDepartamentoToDto(Departamento depa) {
		DepartamentoDTO depaDto = new DepartamentoDTO();
		depaDto.setId(depa.getId());
		depaDto.setName(depa.getName());

		if (depa.getChefeDepartamento() != null) {
			depaDto.setNomeChefeDepartamento(depa.getChefeDepartamento().getName());
		}
		depaDto.setFuncionarios(new ArrayList<String>());
		if (depa.getFuncionarios() != null) {
			for (Funcionario funcionario : depa.getFuncionarios()) {
				depaDto.getFuncionarios().add(funcionario.getName());
			}
		}
		return depaDto;
	}
}