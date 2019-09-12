create table tipo
(
	idtipo serial not null
		constraint tipo_pkey
			primary key,
	tipo varchar(50) not null
);

alter table tipo owner to postgres;

create table vlan
(
	vlan integer not null
		constraint vlan_pkey
			primary key,
	nome varchar(40),
	ativo boolean default true
);

alter table vlan owner to postgres;

create table switch
(
	idswitch serial not null
		constraint switch_pkey
			primary key,
	nome varchar(100) not null,
	localizacao varchar(255),
	observacao varchar(8000),
	tipo_idtipo integer not null
		constraint fk_switch_tipo
			references tipo,
	vlan integer
		constraint fk_switch_vlan
			references vlan
);

alter table switch owner to postgres;

create table porta
(
	idporta serial not null
		constraint porta_pkey
			primary key,
	switch_idswitch integer not null
		constraint fk_porta_switch1
			references switch,
	porta varchar(10) not null
);

alter table porta owner to postgres;

create table equipamento
(
	idequipamento serial not null
		constraint equipamento_pkey
			primary key,
	porta_idporta integer not null
		constraint fk_equipamento_porta1
			references porta,
	nome varchar(50) not null,
	descricao varchar(500),
	observacao varchar(8000)
);

alter table equipamento owner to postgres;

create table porta_vlan
(
	idporta_vlan serial not null
		constraint porta_vlan_pkey
			primary key,
	porta_idporta integer not null
		constraint fk_porta_has_vlan_porta1
			references porta,
	vlan_vlan integer not null
		constraint fk_porta_has_vlan_vlan1
			references vlan
);

alter table porta_vlan owner to postgres;

create table reprovacao
(
	id serial not null
		constraint reprovacao_pk
			primary key,
	nome varchar,
	matricula varchar,
	cpf varchar,
	materia varchar,
	professor varchar
);

alter table reprovacao owner to postgres;

create table materia
(
	id serial not null,
	materia varchar
);

alter table materia owner to postgres;

