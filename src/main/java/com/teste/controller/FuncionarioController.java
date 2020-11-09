package com.teste.controller;

import java.text.SimpleDateFormat;
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

import com.teste.dto.FuncionarioDTO;
import com.teste.exception.DepartamentoNotFoundException;
import com.teste.exception.FuncionarioNotFoundException;
import com.teste.model.Funcionario;
import com.teste.repository.DepartamentoRepository;
import com.teste.service.FuncionarioService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class FuncionarioController {

	@Autowired
	private final FuncionarioService service;

	@Autowired
	private final DepartamentoRepository repositoryDepartamento;

	public FuncionarioController(FuncionarioService service, DepartamentoRepository repositoryDepartamento) {
		this.service = service;
		this.repositoryDepartamento = repositoryDepartamento;
	}

	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Funcionario encontrado com sucesso"),
		    @ApiResponse(code = 400, message = "Funcionario não enontrado"),
	})
	@GetMapping("/funcionarios/all")
	public ResponseEntity<?> getAll() {

		try {
			List<Funcionario> funcionarios = service.findAll();
			List<FuncionarioDTO> funDTO = new ArrayList<FuncionarioDTO>();
			if (funcionarios != null) {
				for (Funcionario funcionario : funcionarios) {
					funDTO.add(convertFuncionarioToDto(funcionario));
				}
			}
			return new ResponseEntity<>(funDTO, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao bucar Funcionário", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Funcionario salvo com sucesso"),
		    @ApiResponse(code = 500, message = "Erro ao Salvar"),
	})
	@PostMapping("/funcionarios")
	public ResponseEntity<?> create(@RequestBody Funcionario newFuncionario) {
		try {
			return new ResponseEntity<>(convertFuncionarioToDto(service.save(newFuncionario)), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao Salvar", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Funcionario encontrado com sucesso"),
		    @ApiResponse(code = 400, message = "Funcionario não enontrado"),
	})
	@GetMapping("/funcionarios/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		Funcionario funcionario = null;
		try {
			funcionario = service.findById(id).get();
		} catch (Exception e) {
			return new ResponseEntity<>("Funcionario não encontrado", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(convertFuncionarioToDto(funcionario), HttpStatus.OK);
	}

	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Funcionario alterado com sucesso"),
		    @ApiResponse(code = 400, message = "Funcionario não enontrado"),
		    @ApiResponse(code = 500, message = "Erro ao Salvar"),
	})
	@PutMapping("/funcionarios/{id}")
	public ResponseEntity<?> update(@RequestBody Funcionario newFuncionario, @PathVariable Integer id) {

		Funcionario funcionario = null;
		try {
			funcionario = service.update(newFuncionario, id);
		} catch (FuncionarioNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao Salvar", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertFuncionarioToDto(funcionario), HttpStatus.OK);
	}

	@DeleteMapping("/funcionarios/{id}")
	public void delete(@PathVariable Integer id) {
		service.deleteById(id);
	}

	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Funcionario alterado com sucesso"),
		    @ApiResponse(code = 400, message = "Funcionario não enontrado"),
		    @ApiResponse(code = 500, message = "Erro ao Salvar"),
	})
	@PutMapping("/funcionarios/{id}/departamento/nome/{nomeDerpartamento}")
	public ResponseEntity<?> updateDepartamento(@PathVariable Integer id, @PathVariable String nomeDerpartamento) {
		
		Funcionario funcionario = null;
		try {
			funcionario = service.updateDepartamento(id, nomeDerpartamento);
		} catch (FuncionarioNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (DepartamentoNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity<>("Erro ao Salvar", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertFuncionarioToDto(funcionario), HttpStatus.OK);
		
	}

	private FuncionarioDTO convertFuncionarioToDto(Funcionario func) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		FuncionarioDTO funcDto = new FuncionarioDTO();
		funcDto.setId(func.getId());
		funcDto.setName(func.getName());
		funcDto.setAge(func.getAge());
		funcDto.setBirthday(dateFormat.format(func.getBirthday()));
		if (func.getCargo() != null) {
			funcDto.setCargo(func.getCargo().getName());
		}
		funcDto.setDocument(func.getDocument());

		if (func.getDepartamento() != null) {
			funcDto.setDepartamento(func.getDepartamento().getName());
		}
		return funcDto;
	}
}