DROP TABLE IF EXISTS funcionario_departamento;
DROP TABLE IF EXISTS departamento;
DROP TABLE IF EXISTS funcionario;
DROP TABLE IF EXISTS cargo; 
DROP TABLE IF EXISTS historio_func_dep;

DROP SEQUENCE IF EXISTS cargo_sequence;
DROP SEQUENCE IF EXISTS funcionario_sequence;
DROP SEQUENCE IF EXISTS departamento_sequence;
DROP SEQUENCE IF EXISTS historio_func_dep_sequence;

CREATE SEQUENCE IF NOT EXISTS cargo_sequence
  START WITH 1
  INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS funcionario_sequence
  START WITH 1
  INCREMENT BY 1;
  
CREATE SEQUENCE IF NOT EXISTS departamento_sequence
  START WITH 1
  INCREMENT BY 1;  

CREATE SEQUENCE IF NOT EXISTS historio_func_dep_sequence
  START WITH 1
  INCREMENT BY 1;  

CREATE TABLE cargo (
  cargo_id INT AUTO_INCREMENT  PRIMARY KEY,
  cargo_name VARCHAR(50)  NOT NULL
);

CREATE TABLE funcionario (
  funcionario_id INT AUTO_INCREMENT  PRIMARY KEY,
  funcionario_name NVARCHAR(50) NOT NULL,
  funcionario_age INT,
  funcionario_birthday DATE,
  funcionario_document NVARCHAR(50),
  cargo_id INT,
  CONSTRAINT FK_funcionario_cargo FOREIGN KEY (cargo_id) REFERENCES cargo(cargo_id)
);

CREATE TABLE departamento (
  departamento_id INT AUTO_INCREMENT  PRIMARY KEY,
  departamento_name VARCHAR(50)  NOT NULL,
  chefe_departamento_funcionario_id INT,
  CONSTRAINT FK_departamento_chefe FOREIGN KEY (chefe_departamento_funcionario_id) REFERENCES funcionario(funcionario_id)
);

CREATE TABLE funcionario_departamento (
	departamento_id INT,
	funcionario_id INT,
	CONSTRAINT FK_funcionario_departamento_departamento FOREIGN KEY (departamento_id) REFERENCES departamento(departamento_id),
	CONSTRAINT FK_funcionario_departamento_funcionario FOREIGN KEY (funcionario_id) REFERENCES funcionario(funcionario_id),
	CONSTRAINT U_unico UNIQUE ( departamento_id, funcionario_id )
);


CREATE TABLE historio_func_dep (
  historio_func_dep_id INT AUTO_INCREMENT  PRIMARY KEY,
  funcionario_id INT NOT NULL,
  funcionario_name NVARCHAR(50) NOT NULL,
  departamento_id INT NOT NULL,
  departamento_name VARCHAR(50)  NOT NULL,
  historio_func_dep_data_incio  DATE,
  historio_func_dep_data_fim  DATE
);