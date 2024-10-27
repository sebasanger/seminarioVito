-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: hoteldb
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cajas`
--

DROP TABLE IF EXISTS `cajas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cajas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `activa` tinyint DEFAULT NULL,
  `montoApertura` varchar(45) DEFAULT NULL,
  `montoCierre` varchar(45) DEFAULT NULL,
  `fechaApertura` datetime DEFAULT NULL,
  `fechaCierre` datetime DEFAULT NULL,
  `usuarios_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cajas_usuarios1_idx` (`usuarios_id`),
  CONSTRAINT `fk_cajas_usuarios1` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cajas`
--

LOCK TABLES `cajas` WRITE;
/*!40000 ALTER TABLE `cajas` DISABLE KEYS */;
INSERT INTO `cajas` VALUES (1,0,'5000','8000','2024-09-27 00:00:00','2024-09-28 00:00:00',1),(2,0,'8000','10000','2024-09-28 00:00:00','2024-09-29 00:00:00',2),(3,1,'10000','15000','2024-09-29 00:00:00',NULL,1);
/*!40000 ALTER TABLE `cajas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `id` int NOT NULL AUTO_INCREMENT,
  `categoria` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'bebidas'),(2,'comida'),(3,'servicio'),(4,'Lacteos');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `apellido` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `documento` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `dni_UNIQUE` (`documento`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'esteban','gutierrez','esteban@hotmail.com','101010'),(2,'lucas','fernandez','lucas@hotmail.com','202020'),(3,'santiago','fernandez','santiago@hotmail.com','303030'),(4,'lucia','lopez','lucia@hotmail.com','404040'),(5,'carla','fritz','carla@hotmail.com','505050'),(6,'nicole','sanchez','nicole@hotmail.com','606060'),(7,'renata','gritz','renata@hotmail.com','707070'),(8,'sofia','landa','sofia@hotmail.com','808080'),(9,'arthur','morgan','arthur@hotmail.com','909090'),(10,'jhon','marston','jhon@hotmail.com','111111'),(12,'Juan','Perez','juan.perez@example.com','123456789'),(13,'chico','lopez','chico@example.com','1141411'),(18,'chico','lopez','chico@example.com','112112'),(20,'chico','lopez','chico@example.com','1222333'),(23,'chico','lopez','chico@example.com','1'),(57,'chico','lopez','chico@example.com','444'),(60,'sebastian gabriel','sangermano','sebas_sanger@hotmail.com ','41616608'),(61,'patricio','lopez','patolop','845454'),(63,'carlos','lopez','car@gmail.com','787878'),(64,'Carlos','gutierrez','marques','741822'),(65,'carlos gutierrez','','','4545441'),(68,'Matias Nicolas','Sangemano','mati@gmail.com','39221242'),(69,'carlos alberto','juarez','carlitos@gmail.com','748596'),(70,'lala','lola','lasd@gmail.com','4152653'),(71,'asda','qwe','sdf@gmai..com','8778787');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consumiciones`
--

DROP TABLE IF EXISTS `consumiciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consumiciones` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cantidad` int DEFAULT NULL,
  `precioTotal` double DEFAULT NULL,
  `precioUnitario` double DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `reservas_id` int NOT NULL,
  `productos_id` int NOT NULL,
  `usuarios_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_consumiciones_reservas1_idx` (`reservas_id`),
  KEY `fk_consumiciones_productos1_idx` (`productos_id`),
  KEY `fk_consumiciones_usuarios1_idx` (`usuarios_id`),
  CONSTRAINT `fk_consumiciones_productos1` FOREIGN KEY (`productos_id`) REFERENCES `productos` (`id`),
  CONSTRAINT `fk_consumiciones_reservas1` FOREIGN KEY (`reservas_id`) REFERENCES `reservas` (`id`),
  CONSTRAINT `fk_consumiciones_usuarios1` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consumiciones`
--

LOCK TABLES `consumiciones` WRITE;
/*!40000 ALTER TABLE `consumiciones` DISABLE KEYS */;
INSERT INTO `consumiciones` VALUES (1,1,800,800,'2024-09-27 00:00:00',1,1,1),(2,1,1400,1400,'2024-09-28 05:00:00',2,2,2),(3,1,800,800,'2024-10-25 00:00:00',7,1,1),(4,1,800,800,'2024-10-26 00:00:00',7,1,1),(5,2,1600,800,'2024-10-26 00:00:00',7,1,1),(6,1,800,800,'2024-10-27 00:00:00',12,1,5),(7,1,800,800,'2024-10-27 00:00:00',13,1,5);
/*!40000 ALTER TABLE `consumiciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entradas_productos`
--

DROP TABLE IF EXISTS `entradas_productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entradas_productos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `precioUnitario` double DEFAULT NULL,
  `cantidad` int DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `productos_id` int NOT NULL,
  `usuarios_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_entradas_productos_productos1_idx` (`productos_id`),
  KEY `fk_entradas_productos_usuarios1_idx` (`usuarios_id`),
  CONSTRAINT `fk_entradas_productos_productos1` FOREIGN KEY (`productos_id`) REFERENCES `productos` (`id`),
  CONSTRAINT `fk_entradas_productos_usuarios1` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entradas_productos`
--

LOCK TABLES `entradas_productos` WRITE;
/*!40000 ALTER TABLE `entradas_productos` DISABLE KEYS */;
INSERT INTO `entradas_productos` VALUES (1,500,15,'2024-09-27 00:00:00',1,1),(2,1000,5,'2024-09-27 00:00:00',2,2),(3,1200,6,'2024-09-27 00:00:00',3,1),(4,700,5,'2024-09-27 00:00:00',4,2),(5,1000,5,'2024-09-27 00:00:00',5,1);
/*!40000 ALTER TABLE `entradas_productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `habitaciones`
--

DROP TABLE IF EXISTS `habitaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `habitaciones` (
  `id` int NOT NULL AUTO_INCREMENT,
  `numeroHabitacion` varchar(45) NOT NULL,
  `disponible` tinyint DEFAULT NULL,
  `piso` int DEFAULT NULL,
  `camasSingles` int DEFAULT NULL,
  `camasDobles` varchar(45) DEFAULT NULL,
  `habilitada` tinyint DEFAULT NULL,
  `capacidad` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `habitaciones`
--

LOCK TABLES `habitaciones` WRITE;
/*!40000 ALTER TABLE `habitaciones` DISABLE KEYS */;
INSERT INTO `habitaciones` VALUES (1,'101',1,1,1,'0',1,1),(2,'102',0,1,0,'1',1,2),(3,'103',0,1,1,'1',1,3),(4,'104',0,1,0,'2',1,4),(5,'105',1,1,0,'2',1,4),(6,'201',0,2,1,'0',1,1),(7,'202',0,2,0,'1',1,2),(8,'203',1,2,1,'1',1,3),(9,'204',0,2,2,'1',1,4),(10,'205',1,2,0,'2',1,4);
/*!40000 ALTER TABLE `habitaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `habitaciones_imagenes`
--

DROP TABLE IF EXISTS `habitaciones_imagenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `habitaciones_imagenes` (
  `habitaciones_id` int NOT NULL,
  `imagenes_id` int NOT NULL,
  PRIMARY KEY (`habitaciones_id`,`imagenes_id`),
  KEY `fk_habitaciones_has_imagenes_imagenes1_idx` (`imagenes_id`),
  KEY `fk_habitaciones_has_imagenes_habitaciones1_idx` (`habitaciones_id`),
  CONSTRAINT `fk_habitaciones_has_imagenes_habitaciones1` FOREIGN KEY (`habitaciones_id`) REFERENCES `habitaciones` (`id`),
  CONSTRAINT `fk_habitaciones_has_imagenes_imagenes1` FOREIGN KEY (`imagenes_id`) REFERENCES `imagenes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `habitaciones_imagenes`
--

LOCK TABLES `habitaciones_imagenes` WRITE;
/*!40000 ALTER TABLE `habitaciones_imagenes` DISABLE KEYS */;
INSERT INTO `habitaciones_imagenes` VALUES (1,1),(1,2),(2,3);
/*!40000 ALTER TABLE `habitaciones_imagenes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `imagenes`
--

DROP TABLE IF EXISTS `imagenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `imagenes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `path` varchar(255) DEFAULT NULL,
  `titulo` varchar(45) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `imagenes`
--

LOCK TABLES `imagenes` WRITE;
/*!40000 ALTER TABLE `imagenes` DISABLE KEYS */;
INSERT INTO `imagenes` VALUES (1,'camahab101','cama','png'),(2,'tvhab101','tv','png'),(3,'camahab102','cama doble','jpg');
/*!40000 ALTER TABLE `imagenes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marcas`
--

DROP TABLE IF EXISTS `marcas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `marcas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `marca` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marcas`
--

LOCK TABLES `marcas` WRITE;
/*!40000 ALTER TABLE `marcas` DISABLE KEYS */;
INSERT INTO `marcas` VALUES (1,'cocacola'),(2,'pepsi'),(3,'la serenisima'),(4,'dolca'),(5,'nescafe'),(6,'servicio');
/*!40000 ALTER TABLE `marcas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagos`
--

DROP TABLE IF EXISTS `pagos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pagos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cantidad` int DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `usuarios_id` int NOT NULL,
  `cajas_id` int NOT NULL,
  `reservas_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pagos_usuarios1_idx` (`usuarios_id`),
  KEY `fk_pagos_cajas1_idx` (`cajas_id`),
  KEY `fk_pagos_reservas1_idx` (`reservas_id`),
  CONSTRAINT `fk_pagos_cajas1` FOREIGN KEY (`cajas_id`) REFERENCES `cajas` (`id`),
  CONSTRAINT `fk_pagos_reservas1` FOREIGN KEY (`reservas_id`) REFERENCES `reservas` (`id`),
  CONSTRAINT `fk_pagos_usuarios1` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagos`
--

LOCK TABLES `pagos` WRITE;
/*!40000 ALTER TABLE `pagos` DISABLE KEYS */;
INSERT INTO `pagos` VALUES (4,10000,'2024-09-27 05:00:00','habitacion 101 cliente esteban gutierrez',1,1,1),(5,800,'2024-09-27 05:00:00','habitacion 101 cliente esteban gutierrez consumicion cocacola 500 ml',1,1,1),(6,20000,'2024-09-28 00:00:00','habitacion 102 cliente lucas',1,2,2),(7,1400,'2024-09-28 00:00:00','habitacion 102 consumicion cocacola 1 litro cliente santiago',1,2,2),(12,20000,'2024-10-25 00:00:00','cuouta',1,3,7),(13,5000,'2024-10-25 00:00:00','asd',1,3,7),(14,500,'2024-10-25 00:00:00','1asd',1,3,7),(15,500,'2024-10-25 00:00:00','as',1,3,7),(16,15000,'2024-10-25 00:00:00','primera',1,3,7),(17,5000,'2024-10-25 00:00:00','c',1,3,7),(18,5000,'2024-10-25 00:00:00','',1,3,7),(19,2000,'2024-10-25 00:00:00','cuota numero 2',1,3,7),(20,20000,'2024-10-27 00:00:00','primera cuouta',5,3,12),(21,10000,'2024-10-27 00:00:00','couta',5,3,12),(22,10000,'2024-10-27 00:00:00','asdsa',5,3,12),(23,5000,'2024-10-27 00:00:00','asd',5,3,12),(24,5000,'2024-10-27 00:00:00','asd',5,3,12),(25,5000,'2024-10-27 00:00:00','jhg',5,3,12),(26,40000,'2024-10-27 00:00:00','pago completo',5,3,13),(27,800,'2024-10-27 00:00:00','pago gaseosa',5,3,13);
/*!40000 ALTER TABLE `pagos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `precios_habitaciones`
--

DROP TABLE IF EXISTS `precios_habitaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `precios_habitaciones` (
  `id` int NOT NULL AUTO_INCREMENT,
  `precio` double DEFAULT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  `disponible` tinyint DEFAULT NULL,
  `fechaCreacion` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `precios_habitaciones`
--

LOCK TABLES `precios_habitaciones` WRITE;
/*!40000 ALTER TABLE `precios_habitaciones` DISABLE KEYS */;
INSERT INTO `precios_habitaciones` VALUES (1,10000,'habitacion single',1,'2024-09-27 00:00:00'),(2,20000,'habitacion doble',1,'2024-09-27 00:00:00'),(3,30000,'habitacion triple',1,'2024-09-27 00:00:00'),(4,40000,'habitacion cuadruple',1,'2024-09-27 00:00:00'),(5,8000,'habitacion single viajante',1,'2024-09-27 00:00:00'),(6,7000,'habitacion single socio',1,'2024-09-27 00:00:00'),(7,19500,'habitacion doble julio a agosto',1,'2024-10-26 00:00:00');
/*!40000 ALTER TABLE `precios_habitaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  `precio` double DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `marcas_id` int NOT NULL,
  `categorias_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_productos_marcas1_idx` (`marcas_id`),
  KEY `fk_productos_categorias1_idx` (`categorias_id`),
  CONSTRAINT `fk_productos_categorias1` FOREIGN KEY (`categorias_id`) REFERENCES `categorias` (`id`),
  CONSTRAINT `fk_productos_marcas1` FOREIGN KEY (`marcas_id`) REFERENCES `marcas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (1,'cocacola 500ml','botella de coacola 500ml',800,28,1,1),(2,'cocacola 1 litro','botella de cocacola de 1 litro',1400,4,1,1),(3,'cocacola 2 litro','botella de cocacola de 2 litros',2000,6,1,1),(4,'pepsi 1 litro','botella de pepsi 1 litro',900,5,2,1),(5,'pepsi 2 litros','botella de pepsi de 2 litros',1300,5,2,1),(6,'Lavado de ropa','Lavado de tambor completo',950,850,6,3);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservas`
--

DROP TABLE IF EXISTS `reservas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `checkIn` datetime DEFAULT NULL,
  `checkOut` datetime DEFAULT NULL,
  `fechaCreacion` datetime DEFAULT NULL,
  `fechaInicio` datetime NOT NULL,
  `fechaFin` datetime DEFAULT NULL,
  `origen` varchar(45) DEFAULT NULL,
  `destino` varchar(45) DEFAULT NULL,
  `precioDiario` double DEFAULT NULL,
  `totalHuespedes` int DEFAULT NULL,
  `precioTotal` double DEFAULT NULL,
  `pagadoTotal` double DEFAULT NULL,
  `estado` varchar(45) DEFAULT NULL,
  `habitaciones_id` int NOT NULL,
  `precios_habitaciones_id` int NOT NULL,
  `usuarios_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_reservas_habitaciones1_idx` (`habitaciones_id`),
  KEY `fk_reservas_precios_habitaciones1_idx` (`precios_habitaciones_id`),
  KEY `fk_reservas_usuarios1_idx` (`usuarios_id`),
  CONSTRAINT `fk_reservas_habitaciones1` FOREIGN KEY (`habitaciones_id`) REFERENCES `habitaciones` (`id`),
  CONSTRAINT `fk_reservas_precios_habitaciones1` FOREIGN KEY (`precios_habitaciones_id`) REFERENCES `precios_habitaciones` (`id`),
  CONSTRAINT `fk_reservas_usuarios1` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservas`
--

LOCK TABLES `reservas` WRITE;
/*!40000 ALTER TABLE `reservas` DISABLE KEYS */;
INSERT INTO `reservas` VALUES (1,'2024-09-27 00:00:00','2024-09-28 00:00:00','2024-09-27 00:00:00','2024-09-27 00:00:00','2024-09-28 00:00:00','buenos aires','brasil',10000,1,10800,10800,'pendiente',1,1,1),(2,'2024-09-28 05:00:00','2024-09-29 10:00:00','2024-09-28 00:00:00','2024-09-28 00:00:00','2024-10-25 00:00:00','corrientes','santa fe',20000,2,20000,15000,'activa',2,2,2),(3,'2024-10-01 00:00:00','2024-10-03 00:00:00','2024-10-01 00:00:00','2024-10-01 00:00:00','2024-10-03 00:00:00','chaco','formosa',30000,3,30000,0,'pendiente',3,3,2),(4,'2024-10-04 00:00:00','2024-10-05 00:00:00','2024-10-04 00:00:00','2024-10-04 00:00:00','2024-10-05 00:00:00','iguazu','brasil',40000,4,40000,0,'pendiente',4,4,2),(5,'2024-10-23 00:00:00',NULL,'2024-10-23 00:00:00','2024-10-23 00:00:00','2024-10-25 00:00:00','b','a',10000,NULL,0,0,'elimnada',1,1,1),(6,'2024-10-23 00:00:00',NULL,'2024-10-23 00:00:00','2024-10-23 00:00:00','2024-10-25 00:00:00','sanmar','holanda',10000,NULL,20000,0,'finalizada',2,1,1),(7,'2024-10-24 00:00:00',NULL,'2024-10-24 00:00:00','2024-10-24 00:00:00','2024-10-26 00:00:00','carlos paz','chaco',20000,NULL,52900,47000,'activa',3,2,1),(8,'2024-10-26 00:00:00',NULL,'2024-10-26 00:00:00','2024-10-26 00:00:00','2024-10-28 00:00:00','San Juan','Carlos paz',10000,NULL,0,20000,'activa',2,1,1),(9,NULL,NULL,'2024-10-26 00:00:00','2024-10-26 00:00:00','2024-10-28 00:00:00','El Salvador','San Carlos',10000,NULL,0,20000,'activa',1,1,1),(11,'2024-10-26 00:00:00',NULL,'2024-10-26 00:00:00','2024-10-26 00:00:00','2024-10-28 00:00:00','espania','portugal',20000,NULL,40000,0,'activa',7,2,1),(12,NULL,NULL,'2024-10-26 00:00:00','2024-10-26 00:00:00','2024-10-28 00:00:00','hgfv','hjgfv',10000,NULL,45800,40000,'activa',3,1,1),(13,'2024-10-27 00:00:00',NULL,'2024-10-27 00:00:00','2024-10-27 00:00:00','2024-10-29 00:00:00','formosa','chaco',20000,NULL,40800,40800,'activa',9,2,5);
/*!40000 ALTER TABLE `reservas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservas_clientes`
--

DROP TABLE IF EXISTS `reservas_clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservas_clientes` (
  `reservas_id` int NOT NULL,
  `clientes_id` int NOT NULL,
  PRIMARY KEY (`reservas_id`,`clientes_id`),
  KEY `fk_reservas_has_clientes_clientes1_idx` (`clientes_id`),
  KEY `fk_reservas_has_clientes_reservas_idx` (`reservas_id`),
  CONSTRAINT `fk_reservas_has_clientes_clientes1` FOREIGN KEY (`clientes_id`) REFERENCES `clientes` (`id`),
  CONSTRAINT `fk_reservas_has_clientes_reservas` FOREIGN KEY (`reservas_id`) REFERENCES `reservas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservas_clientes`
--

LOCK TABLES `reservas_clientes` WRITE;
/*!40000 ALTER TABLE `reservas_clientes` DISABLE KEYS */;
INSERT INTO `reservas_clientes` VALUES (1,1),(2,2),(2,3),(3,4),(7,4),(3,5),(3,6),(4,7),(4,8),(4,9),(4,10),(7,60),(12,60),(13,60),(9,68),(11,70),(11,71);
/*!40000 ALTER TABLE `reservas_clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `apellido` varchar(45) DEFAULT NULL,
  `rol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'admin@hotmail.com','admin','admin','admin','admin'),(2,'recepcionista','recepcionista','recepcionista','recepcionista','recepcionista'),(3,'seba@hotmail.com','sebaseba','sebastian','sangermano','admin'),(4,'limpieza','limpieza','limpieza','limpieza','limpieza'),(5,'admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','sebas','sanger','admin');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-27 19:50:25
