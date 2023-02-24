-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: cuentabancaria
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `numberaccount` int NOT NULL AUTO_INCREMENT,
  `dnicustomer` varchar(9) NOT NULL,
  `actived` tinyint NOT NULL,
  PRIMARY KEY (`numberaccount`),
  KEY `fk_dni_idx` (`dnicustomer`),
  CONSTRAINT `fk_dni` FOREIGN KEY (`dnicustomer`) REFERENCES `customers` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (4,'61947188T',1),(5,'74083992A',1),(6,'20180236J',1);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `dni` varchar(9) NOT NULL,
  `name` varchar(45) NOT NULL,
  `address` varchar(100) NOT NULL,
  `phone` char(9) NOT NULL,
  PRIMARY KEY (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES ('20180236J','Mengana Tres','1 bach A','789768768'),('56220018Z','Mengana Cuatro','1 bach B','678699654'),('61947188T','Fulano Uno','clase 1DAW','678123456'),('74083992A','Fulano Dos','clase 1asir','786999999');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimientos`
--

DROP TABLE IF EXISTS `movimientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimientos` (
  `numerocuenta` int NOT NULL,
  `importe` float NOT NULL,
  `fechahora` datetime NOT NULL,
  `tipo` enum('ingreso','salida','transfenv','transrec') DEFAULT NULL,
  `numcuentatrans` int DEFAULT NULL,
  `concepto` varchar(100) DEFAULT NULL,
  KEY `fk_numcuenta_idx` (`numerocuenta`),
  CONSTRAINT `fk_numcuenta` FOREIGN KEY (`numerocuenta`) REFERENCES `account` (`numberaccount`),
  CONSTRAINT `movimientos_chk_1` CHECK ((`importe` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimientos`
--

LOCK TABLES `movimientos` WRITE;
/*!40000 ALTER TABLE `movimientos` DISABLE KEYS */;
INSERT INTO `movimientos` VALUES (4,100,'2022-05-23 09:33:47','ingreso',NULL,'Apertura de cuenta'),(5,100,'2022-05-23 09:35:06','ingreso',NULL,'Apertura de cuenta'),(6,100,'2022-05-23 09:36:13','ingreso',NULL,'Apertura de cuenta'),(4,30,'2022-05-23 09:38:24','salida',NULL,'Tasas universitarias'),(5,50,'2022-05-23 09:41:00','salida',NULL,'Multa fraccion 1'),(5,70,'2022-05-23 09:41:57','salida',NULL,'Multa fraccion 2'),(4,35,'2022-05-23 09:44:57','transfenv',5,'Pago deuda cena'),(5,35,'2022-05-23 09:44:57','transrec',4,'Pago deuda cena'),(5,15,'2022-05-23 09:46:23','transfenv',6,'Regalo cumpleaños'),(6,15,'2022-05-23 09:46:23','transrec',5,'Regalo cumpleaños'),(4,10,'2022-05-23 10:09:10','ingreso',NULL,'Bizum de Mariano'),(4,5,'2022-05-23 10:09:58','salida',NULL,'Prensa Digital'),(4,2,'2022-05-23 10:10:52','transfenv',5,'Desayuno'),(5,2,'2022-05-23 10:10:52','transrec',4,'Desayuno');
/*!40000 ALTER TABLE `movimientos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-23 11:05:01
