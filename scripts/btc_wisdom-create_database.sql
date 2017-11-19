drop database if exists btc_wisdom;
create database btc_wisdom collate "utf8_unicode_ci";
use btc_wisdom;

create table usuario(
		id int(11) auto_increment not null,
		nombre varchar(50) not null,
		correo varchar(50) not null,
		contrasena varchar(255) not null,
		saldo double(10,2) not null default 0.0,
		primary key (id)
);

create table seguidor(
		fk_seguidor int(11) not null,
		fk_seguido int(11) not null,
		primary key (fk_seguidor, fk_seguido),
		constraint foreign key (fk_seguidor) references usuario (id),
		constraint foreign key (fk_seguido) references usuario (id)
);

create table moneda(
		codigo varchar(3) not null,
		nombre varchar(50) not null,
		simbolo varchar(4) not null,
		valor double(9,2) not null,
		primary key (codigo)
);

create table usuario_moneda(
		fk_usuario int(11) not null,
		moneda varchar(3) not null,
		cantidad double(10,2) not null default 0.0,
		primary key (fk_usuario, moneda),
		constraint foreign key (fk_usuario) references usuario(id),
		constraint foreign key (moneda) references moneda(codigo)
);

create table configuracion(
		id int(11) auto_increment not null,
		fk_usuario int(11) not null,
		moneda varchar(3) not null,
		importe_compra double(9,2) not null,
		importe_venta double(9,2) not null,
		precio_max_venta double(9,2) not null,
		precio_min_venta double(9,2) not null,
		precio_max_compra double(9,2) not null,
		primary key (id),
		constraint foreign key (fk_usuario) references usuario(id),
		constraint foreign key (moneda) references moneda(codigo)
);