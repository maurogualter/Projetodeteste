package com.teste.controller;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.service.RelatorioService;

import io.swagger.annotations.Api;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/relatorio")
@Api(value = "Relatório", description = "Gerar os Relatóriso da aplicação")
public class relatorioController {
	
	@Autowired
	private RelatorioService relatorioService;
	
	@GetMapping("gera/{tipo}")
	public String geraRelatorio(@PathVariable String tipo) throws FileNotFoundException, JRException {
		return relatorioService.geraRelatorioTest(tipo);
	}

}
