-- Creación de la base de datos si no existe
CREATE DATABASE IF NOT EXISTS TopogigiosDB;

-- Uso de la base de datos creada
USE TopogigiosDB;

-- Script SQL modificado para las tablas del proyecto 'topogigios'

-- Creación de la tabla Jugos
CREATE TABLE IF NOT EXISTS jugos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    sabor VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

-- Creación de la tabla Producción Diaria
CREATE TABLE IF NOT EXISTS produccióndiaria (
    id BIGINT NOT NULL AUTO_INCREMENT,
    jugo_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    fecha DATE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (jugo_id) REFERENCES jugos (id)
);

-- Creación de la tabla Gastos
CREATE TABLE IF NOT EXISTS gastos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    descripcion VARCHAR(255) NOT NULL,
    valor DECIMAL(38,2) NOT NULL,
    fecha DATE NOT NULL,
    PRIMARY KEY (id)
);

-- Creación de la tabla Ventas
CREATE TABLE IF NOT EXISTS ventas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    jugo_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    valor_unitario DECIMAL(38,2) NOT NULL,
    fecha DATE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (jugo_id) REFERENCES jugos (id)
);

-- Creación de la tabla Insumos
CREATE TABLE IF NOT EXISTS insumos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    descripcion TINYTEXT NOT NULL,
    unidad_medida text NOT NULL,
    PRIMARY KEY (id)
);

-- Creación de la tabla Compras Insumos
CREATE TABLE IF NOT EXISTS comprasinsumos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    insumo_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(38,2) not null,
    valor_total DECIMAL(38,2) NOT NULL,
    fecha DATE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (insumo_id) REFERENCES insumos (id)
);

CREATE TABLE `dinero` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `saldo` decimal(38,2) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `movimientos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha` datetime(6) DEFAULT NULL,
  `saldo_antes` decimal(38,2) DEFAULT NULL,
  `saldo_despues` decimal(38,2) DEFAULT NULL,
  `tipo_movimiento` varchar(255) DEFAULT NULL,
  `valor` decimal(38,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

