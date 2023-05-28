-- DROP TABLE MARCA;
CREATE TABLE MARCA (
	id varchar(36) NOT NULL,
	name varchar(255) NOT NULL,
	fipe_id int NOT NULL,

	CONSTRAINT pk_marca PRIMARY KEY (id),
	CONSTRAINT uk_marca_fipe_id UNIQUE (fipe_id)

);
