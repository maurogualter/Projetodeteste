package com.teste.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.teste.model.RelatorioTeste;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class RelatorioService {
	
	
	
	public String geraRelatorioTest(String tipo) throws FileNotFoundException, JRException {
		List<RelatorioTeste> massa = gerarMassaTeste();
		
		//load file and complile it
		File file = ResourceUtils.getFile("classpath:reportTest3.jrxml");
		String path = "C:\\Develop\\relatório\\gerados";
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource (massa);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("createdBy", "Mauro Souza");
		parameters.put("datasource1", massa);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,new JREmptyDataSource());
		
		if(tipo.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint,path+"\\teste Relatorio 3.html");
		}else if(tipo.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint,path+"\\teste Relatorio 3.pdf");
		}
		
		return "Relatório Gerado no caminho: "+path;
	}
	
	private List<RelatorioTeste> gerarMassaTeste() {
		List<RelatorioTeste> massa = new ArrayList<RelatorioTeste>();
		for (int i = 1; i <= 10; i++) {
			RelatorioTeste a = new RelatorioTeste();
			a.setId(i);
			a.setNome("Nome "+i);
			a.setSalario(""+((i*1000)+10));
			a.setDataNasc((LocalDate.now().minusYears(i*20).minusMonths(i+10).minusDays(i+15)).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			massa.add(a);
		}
		return massa;
	}

}
