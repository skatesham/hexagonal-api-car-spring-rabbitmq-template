-- DROP TABLE VEICULO;
CREATE TABLE VEICULO (
	id varchar(36) NOT NULL,
	placa varchar(255) NOT NULL,
	preco_anuncio numeric(19, 0) NOT NULL,
	preco_fipe numeric(19, 0) NOT NULL,
	ano int NOT NULL,
	created_at TIMESTAMP WITH TIME ZONE NOT NULL,

	id_marca varchar(36) NOT NULL,
	id_modelo varchar(36) NOT NULL,

	CONSTRAINT pk_veiculo PRIMARY KEY (id),
	CONSTRAINT uk_veiculo_placa UNIQUE (placa),
	CONSTRAINT fk_veiculo_x_marca FOREIGN KEY (id_marca) REFERENCES marca(id),
	CONSTRAINT fk_veiculo_x_modelo FOREIGN KEY (id_modelo) REFERENCES modelo(id)
);
