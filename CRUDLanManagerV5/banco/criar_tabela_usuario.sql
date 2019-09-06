create table usuario(
	nome_usuario varchar(50) not null,
	senha_usuario varchar(50) not null,
	primary key(nome_usuario)	
);


insert into usuario (nome_usuario,senha_usuario) values('login','senha');