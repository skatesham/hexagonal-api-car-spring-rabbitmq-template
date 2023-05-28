-- DROP TABLE MODELO;
CREATE TABLE MODELO (

	id varchar(36) NOT NULL,
	name varchar(255) NOT NULL,
	fipe_id int NOT NULL,
	id_marca varchar(36) NOT NULL,

	CONSTRAINT pk_modelo PRIMARY KEY (id),
	CONSTRAINT uk_modelo_fipe_id UNIQUE (fipe_id),
	CONSTRAINT fk_modelo_x_marca FOREIGN KEY (id_marca) REFERENCES marca(id)

);

