package com.teste.application;

import java.sql.Date;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.teste.model.Cargo;
import com.teste.model.Departamento;
import com.teste.model.Funcionario;
import com.teste.repository.CargoRepository;
import com.teste.repository.DepartamentoRepository;
import com.teste.service.FuncionarioService;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@ComponentScan({ "com.teste.controller" })
@EntityScan("com.teste.model")
@EnableJpaRepositories("com.teste.repository")
@ComponentScan({ "com.teste.service" })
@EnableSwagger2
public class Configuracao {

	private static final Logger log = LoggerFactory.getLogger(Configuracao.class);

	@Autowired
	CargoRepository cargoRepository;

	@Autowired
	FuncionarioService funcionarioService;

	@Autowired
	DepartamentoRepository departamentoRepository;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	CommandLineRunner initDatabaseCargo(CargoRepository repository) {

		return args -> {
			log.info("Preloading " + repository.save(new Cargo("Vendedor")));
			log.info("Preloading " + repository.save(new Cargo("Contador")));
			log.info("Preloading " + repository.save(new Cargo("Escriturario")));
		};
	}

	@Bean
	CommandLineRunner initDatabaseFuncionario(FuncionarioService service) throws ParseException {

		return args -> {
			log.info("Preloading " + service.save(new Funcionario("Bilbo Baggins", Date.valueOf("1987-07-11"),
					"Documento 1", cargoRepository.findByName("Vendedor"))));
			log.info("Preloading " + service.save(new Funcionario("Sophia Hannah", Date.valueOf("1983-05-20"),
					"Documento 2", cargoRepository.findByName("Escriturario"))));
			log.info("Preloading " + service.save(new Funcionario("Maureen Charity", Date.valueOf("1983-05-20"),
					"Documento 2", cargoRepository.findByName("Contador"))));
			log.info("Preloading " + service.save(new Funcionario("Una Maud", Date.valueOf("1983-05-20"), "Documento 2",
					cargoRepository.findByName("Escriturario"))));
			log.info("Preloading " + service.save(new Funcionario("Mauro Gualter", Date.valueOf("1980-09-20"),
					"Documento 2225", cargoRepository.findByName("Vendedor"))));
		};
	}

	@Bean
	CommandLineRunner initDatabaseDepartamento(DepartamentoRepository repository) {

		return args -> {
			log.info("Preloading "
					+ repository.save(new Departamento("Vendas", funcionarioService.findByName("Mauro Gualter"))));
			log.info("Preloading "
					+ repository.save(new Departamento("Escritorio", funcionarioService.findByName("Una Maud"))));

			Departamento depA = repository.findByName("Vendas");
			Set<Funcionario> funs = new HashSet<Funcionario>();
			funs.add(funcionarioService.findByName("Bilbo Baggins"));
			funs.add(funcionarioService.findByName("Mauro Gualter"));
			depA.setFuncionarios(funs);
			log.info("Preloading " + repository.save(depA));

			Funcionario func2 = funcionarioService.findByName("Sophia Hannah");
			Departamento depB = repository.findByName("Escritorio");
			try {
				depB.getFuncionarios().add(func2);
			} catch (Exception e) {
				depB.setFuncionarios(new HashSet<Funcionario>());
				depB.getFuncionarios().add(func2);
			}
			log.info("Preloading " + repository.save(depB));
			func2.setDepartamento(depB);
			log.info("Preloading " + funcionarioService.save(func2));

		};
	}
    
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
           .apis(RequestHandlerSelectors.basePackage("com.teste.controller"))
            .paths(PathSelectors.any())
            .build()
            .useDefaultResponseMessages(false);
    }
	

}