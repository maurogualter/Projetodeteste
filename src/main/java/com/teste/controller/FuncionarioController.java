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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.dto.FuncionarioDTO;
import com.teste.exception.DepartamentoNotFoundException;
import com.teste.exception.FuncionarioNotFoundException;
import com.teste.model.Funcionario;
import com.teste.repository.DepartamentoRepository;
import com.teste.service.FuncionarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/funcionarios")
@Api(value = "Funcionario", description = "CRUD de funcionarios")
public class FuncionarioController {

	@Autowired
	private final FuncionarioService service;

	@Autowired
	private final DepartamentoRepository repositoryDepartamento;

	public FuncionarioController(FuncionarioService service, DepartamentoRepository repositoryDepartamento) {
		this.service = service;
		this.repositoryDepartamento = repositoryDepartamento;
	}

	/**
	 * Busca todos os funcionarios
	 * @return
	 */
	@ApiOperation(value = "Busca todos os funcionarios")
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Funcionario encontrado com sucesso"),
		    @ApiResponse(code = 400, message = "Funcionario não enontrado"),
		    
	})
	@GetMapping("/all")
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

	/**
	 * Salva um novo funcionario
	 * @param newFuncionario - Objeto do novo funcionario
	 * @return
	 */
	@ApiOperation(value = "Salva um novo funcionario")
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Funcionario salvo com sucesso"),
		    @ApiResponse(code = 500, message = "Erro ao Salvar"),
	})
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Funcionario newFuncionario) {
		try {
			return new ResponseEntity<>(convertFuncionarioToDto(service.save(newFuncionario)), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao Salvar", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Busca o funcionario por id
	 * @param id - id do funionario
	 * @return
	 */
	@ApiOperation(value = "Busca o funcionario por id")
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Funcionario encontrado com sucesso"),
		    @ApiResponse(code = 400, message = "Funcionario não enontrado"),
	})
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		Funcionario funcionario = null;
		try {
			funcionario = service.findById(id).get();
		} catch (Exception e) {
			return new ResponseEntity<>("Funcionario não encontrado", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(convertFuncionarioToDto(funcionario), HttpStatus.OK);
	}

	/**
	 * Altera o funionario por id
	 * @param newFuncionario - objeto do funcionario a ser alterado
	 * @param id - Id do funcionario a buscar
	 * @return
	 */
	@ApiOperation(value = "Altera o funionario por id")
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Funcionario alterado com sucesso"),
		    @ApiResponse(code = 400, message = "Funcionario não enontrado"),
		    @ApiResponse(code = 500, message = "Erro ao Salvar"),
	})
	@PutMapping("/{id}")
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

	/**
	 *  Deleta funionario
	 * @param id - id do funionario
	 */
	@ApiOperation(value = "Deleta funionario")
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		service.deleteById(id);
	}

	/**
	 * Altera o departamento de um funcionario
	 * @param id - id do funcionario
	 * @param nomeDerpartamento - nome do novo departamaneto do funionario
	 * @return
	 */
	@ApiOperation(value = "Altera o departamento de um funcionario")
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Funcionario alterado com sucesso"),
		    @ApiResponse(code = 400, message = "Funcionario não enontrado"),
		    @ApiResponse(code = 500, message = "Erro ao Salvar"),
	})
	@PutMapping("/{id}/departamento/nome/{nomeDerpartamento}")
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

	/**
	 * converte a entety do funionario em DTO
	 * @param func - objeto do funcionario
	 * @return
	 */
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