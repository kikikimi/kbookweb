-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: localhost    Database: kbook
-- ------------------------------------------------------
-- Server version	5.7.9

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `model`
--

DROP TABLE IF EXISTS `model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `model` (
  `BasePrice` decimal(12,2) NOT NULL,
  `Make` varchar(100) NOT NULL,
  `ModelName` varchar(255) NOT NULL,
  `ModelID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ModelID`),
  UNIQUE KEY `uc_MakeModel` (`ModelName`,`Make`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `model`
--

LOCK TABLES `model` WRITE;
/*!40000 ALTER TABLE `model` DISABLE KEYS */;
INSERT INTO `model` VALUES (15999.00,'','Ford Focus Wagon ZTW',5),(17999.00,'','Ford Focus Sedan',6),(13595.00,'','Ford Avenger Wagon',7);
/*!40000 ALTER TABLE `model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `optionset`
--

DROP TABLE IF EXISTS `optionset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `optionset` (
  `OptionSetID` int(11) NOT NULL AUTO_INCREMENT,
  `OptionName` varchar(100) NOT NULL,
  `ModelID` int(11) NOT NULL,
  PRIMARY KEY (`OptionSetID`),
  KEY `fk_ModOptSet` (`ModelID`),
  CONSTRAINT `fk_ModOptSet` FOREIGN KEY (`ModelID`) REFERENCES `Model` (`ModelID`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `optionset`
--

LOCK TABLES `optionset` WRITE;
/*!40000 ALTER TABLE `optionset` DISABLE KEYS */;
INSERT INTO `optionset` VALUES (21,'Color',5),(22,'Transmission',5),(23,'Brakes/Traction Control',5),(24,'Side Impact Air Bags',5),(25,'Power Moonroof',5),(26,'Color',6),(27,'Transmission',6),(28,'Brakes/Traction Control',6),(29,'Side Impact Air Bags',6),(30,'Power Moonroof',6),(31,'Color',7),(32,'Transmission',7),(33,'Brakes/Traction Control',7),(34,'Side Impact Air Bags',7),(35,'Power Moonroof',7);
/*!40000 ALTER TABLE `optionset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `opts`
--

DROP TABLE IF EXISTS `opts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `opts` (
  `OptionID` int(11) NOT NULL AUTO_INCREMENT,
  `OptionPrice` decimal(9,2) NOT NULL,
  `OptionValue` varchar(150) NOT NULL,
  `OptionSetID` int(11) NOT NULL,
  PRIMARY KEY (`OptionID`),
  KEY `fk_OpSetOpt` (`OptionSetID`),
  CONSTRAINT `fk_OpSetOpt` FOREIGN KEY (`OptionSetID`) REFERENCES `optionset` (`OptionSetID`)
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `opts`
--

LOCK TABLES `opts` WRITE;
/*!40000 ALTER TABLE `opts` DISABLE KEYS */;
INSERT INTO `opts` VALUES (77,0.00,'Fort Knox Gold Clearcoat Metallic',21),(78,0.00,'Liquid Grey Clearcoat Metallic',21),(79,0.00,'Infra-Red Clearcoat',21),(80,0.00,'Grabber Green Clearcoat Metallic',21),(81,0.00,'Sangria Red Clearcoat Metallic',21),(82,0.00,'French Blue Clearcoat Metallic',21),(83,0.00,'Twilight Blue Clearcoat Metallic',21),(84,0.00,'CD Silver Clearcoat Metallic',21),(85,0.00,'Pitch Black Clearcoat',21),(86,0.00,'Cloud 9 White Clearcoat',21),(87,0.00,'Automatic',22),(88,-815.00,'Standard',22),(89,0.00,'Standard',23),(90,400.00,'ABS',23),(91,1625.00,'ABS with Advance Trac',23),(92,0.00,'None',24),(93,350.00,'Select',24),(94,0.00,'None',25),(95,595.00,'Select',25),(96,0.00,'Fort Knox Gold Clearcoat Metallic',26),(97,0.00,'Liquid Grey Clearcoat Metallic',26),(98,0.00,'Infra-Red Clearcoat',26),(99,0.00,'Grabber Green Clearcoat Metallic',26),(100,0.00,'Sangria Red Clearcoat Metallic',26),(101,0.00,'French Blue Clearcoat Metallic',26),(102,0.00,'Twilight Blue Clearcoat Metallic',26),(103,0.00,'CD Silver Clearcoat Metallic',26),(104,0.00,'Pitch Black Clearcoat',26),(105,0.00,'Cloud 9 White Clearcoat',26),(106,0.00,'Automatic',27),(107,-815.00,'Standard',27),(108,0.00,'Standard',28),(109,400.00,'ABS',28),(110,1625.00,'ABS with Advance Trac',28),(111,0.00,'None',29),(112,350.00,'Select',29),(113,0.00,'None',30),(114,595.00,'Select',30),(115,0.00,'Fort Knox Gold Clearcoat Metallic',31),(116,0.00,'Liquid Grey Clearcoat Metallic',31),(117,0.00,'Infra-Red Clearcoat',31),(118,0.00,'Grabber Green Clearcoat Metallic',31),(119,0.00,'Sangria Red Clearcoat Metallic',31),(120,0.00,'French Blue Clearcoat Metallic',31),(121,0.00,'Twilight Blue Clearcoat Metallic',31),(122,0.00,'CD Silver Clearcoat Metallic',31),(123,0.00,'Pitch Black Clearcoat',31),(124,0.00,'Cloud 9 White Clearcoat',31),(125,1.00,'Automatic',32),(126,-815.00,'Standard',32),(127,0.00,'Standard',33),(128,400.00,'ABS',33),(129,1625.00,'ABS with Advance Trac',33),(130,0.00,'None',34),(131,350.00,'Select',34),(132,0.00,'None',35),(133,595.00,'Select',35);
/*!40000 ALTER TABLE `opts` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-03 19:03:39
