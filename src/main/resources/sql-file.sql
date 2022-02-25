-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: live-guard
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `chip_associated_details`
--

DROP TABLE IF EXISTS `chip_associated_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chip_associated_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `age` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm3xwhsp71c8sn98l39cdrk1md` (`user_id`),
  CONSTRAINT `FKm3xwhsp71c8sn98l39cdrk1md` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chip_associated_details`
--

LOCK TABLES `chip_associated_details` WRITE;
/*!40000 ALTER TABLE `chip_associated_details` DISABLE KEYS */;
INSERT INTO `chip_associated_details` VALUES (1,'19','Mohamed','01012703497','/chip-associated_details-photos/1/DGEH1423.JPG',3),(2,'19','Mohamed','01012703497',NULL,3),(3,'19','Mohamed','01012703497',NULL,3),(4,'19','Mohamed','01012703497','/chip-associated_details-photos/4/DGEH1423.JPG',3);
/*!40000 ALTER TABLE `chip_associated_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chip_location_details`
--

DROP TABLE IF EXISTS `chip_location_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chip_location_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` datetime(6) DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `lng` double DEFAULT NULL,
  `status` int DEFAULT NULL,
  `chip_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK30un8m44emwplfaui4f0pnbru` (`chip_id`),
  CONSTRAINT `FK30un8m44emwplfaui4f0pnbru` FOREIGN KEY (`chip_id`) REFERENCES `chips` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chip_location_details`
--

LOCK TABLES `chip_location_details` WRITE;
/*!40000 ALTER TABLE `chip_location_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `chip_location_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chip_types`
--

DROP TABLE IF EXISTS `chip_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chip_types` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chip_types`
--

LOCK TABLES `chip_types` WRITE;
/*!40000 ALTER TABLE `chip_types` DISABLE KEYS */;
INSERT INTO `chip_types` VALUES (1,'Version 1'),(2,'Version 2'),(3,'Version 3');
/*!40000 ALTER TABLE `chip_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chips`
--

DROP TABLE IF EXISTS `chips`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chips` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `chip_associated_details_id` bigint DEFAULT NULL,
  `chip_type_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3tb0f4xacv70lwacxdjskbvjd` (`chip_associated_details_id`),
  KEY `FKn0vjjio4l63yykbh4m3if8uy0` (`chip_type_id`),
  CONSTRAINT `FK3tb0f4xacv70lwacxdjskbvjd` FOREIGN KEY (`chip_associated_details_id`) REFERENCES `chip_associated_details` (`id`),
  CONSTRAINT `FKn0vjjio4l63yykbh4m3if8uy0` FOREIGN KEY (`chip_type_id`) REFERENCES `chip_types` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chips`
--

LOCK TABLES `chips` WRITE;
/*!40000 ALTER TABLE `chips` DISABLE KEYS */;
INSERT INTO `chips` VALUES (1,'Chip 1','-1215439568','/chip-photos/1/DGEH1423.JPG',4,1),(2,'Chip 2','-1021554212','/chip-photos/2/DGEH1423.JPG',3,1);
/*!40000 ALTER TABLE `chips` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chips_users`
--

DROP TABLE IF EXISTS `chips_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chips_users` (
  `user_id` bigint NOT NULL,
  `chip_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`chip_id`),
  KEY `FK5jomigxmx752em1p5h2g49he6` (`chip_id`),
  CONSTRAINT `FK5jomigxmx752em1p5h2g49he6` FOREIGN KEY (`chip_id`) REFERENCES `chips` (`id`),
  CONSTRAINT `FKj77aweo4ufbhnin277wln08cs` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chips_users`
--

LOCK TABLES `chips_users` WRITE;
/*!40000 ALTER TABLE `chips_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `chips_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locations`
--

DROP TABLE IF EXISTS `locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `locations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` datetime(6) DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `lng` double DEFAULT NULL,
  `chip_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8y4j7e70db1ocudmd3irr18vc` (`chip_id`),
  CONSTRAINT `FK8y4j7e70db1ocudmd3irr18vc` FOREIGN KEY (`chip_id`) REFERENCES `chips` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locations`
--

LOCK TABLES `locations` WRITE;
/*!40000 ALTER TABLE `locations` DISABLE KEYS */;
/*!40000 ALTER TABLE `locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `sender` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9y21adhxn0ayjhfocscqox7bh` (`user_id`),
  CONSTRAINT `FK9y21adhxn0ayjhfocscqox7bh` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,NULL,'user');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settings`
--

DROP TABLE IF EXISTS `settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `settings` (
  `key` varchar(128) NOT NULL,
  `value` varchar(1024) NOT NULL,
  `category` varchar(45) NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings`
--

LOCK TABLES `settings` WRITE;
/*!40000 ALTER TABLE `settings` DISABLE KEYS */;
INSERT INTO `settings` VALUES ('MAIL_FROM','LiveGuard@gmail.com','MAIL_SERVER'),('MAIL_HOST','smtp.gmail.com','MAIL_SERVER'),('MAIL_PASSWORD','MO0420sara0420','MAIL_SERVER'),('MAIL_PORT','587','MAIL_SERVER'),('MAIL_SENDER_NAME','Live Guard','MAIL_SERVER'),('MAIL_USERNAME','mohamed085555@gmail.com','MAIL_SERVER'),('SMTP_AUTH','true','MAIL_SERVER'),('SMTP_SECURED','true','MAIL_SERVER'),('USER_VERIFY_CONTENT','Dear [[name]] your code [[Verify Code]]','MAIL_TEMPLATES'),('USER_VERIFY_SUBJECT','Pleas verify your register code','MAIL_TEMPLATES');
/*!40000 ALTER TABLE `settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_days`
--

DROP TABLE IF EXISTS `task_days`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_days` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `day` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_days`
--

LOCK TABLES `task_days` WRITE;
/*!40000 ALTER TABLE `task_days` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_days` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_repeat`
--

DROP TABLE IF EXISTS `task_repeat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_repeat` (
  `task_id` bigint NOT NULL,
  `day_id` bigint NOT NULL,
  PRIMARY KEY (`task_id`,`day_id`),
  KEY `FKpf2g6y4p14ty6ou6m34mpqs7f` (`day_id`),
  CONSTRAINT `FK9lyuc4w2duhril3ve0pbxc0ee` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`),
  CONSTRAINT `FKpf2g6y4p14ty6ou6m34mpqs7f` FOREIGN KEY (`day_id`) REFERENCES `task_days` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_repeat`
--

LOCK TABLES `task_repeat` WRITE;
/*!40000 ALTER TABLE `task_repeat` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_repeat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasks`
--

DROP TABLE IF EXISTS `tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tasks` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `area` double DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `lng` double DEFAULT NULL,
  `mute` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `ringtone` varchar(255) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `chip_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6s1ob9k4ihi75xbxe2w0ylsdh` (`user_id`),
  KEY `FKci44c0xrf04f8i1yc0ut0wot4` (`chip_id`),
  CONSTRAINT `FK6s1ob9k4ihi75xbxe2w0ylsdh` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKci44c0xrf04f8i1yc0ut0wot4` FOREIGN KEY (`chip_id`) REFERENCES `chips` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasks`
--

LOCK TABLES `tasks` WRITE;
/*!40000 ALTER TABLE `tasks` DISABLE KEYS */;
/*!40000 ALTER TABLE `tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_non_expired` bit(1) DEFAULT NULL,
  `account_non_locked` bit(1) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `authentication_type` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `created_time` datetime(6) DEFAULT NULL,
  `credentials_non_expired` bit(1) DEFAULT NULL,
  `dob` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `enable` bit(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  `reset_password_token` varchar(255) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (3,_binary '',_binary '','Sadat city','DATABASE','/user-photos/3/DGEH1423.JPG','2022-02-21 23:22:52.719570',_binary '',NULL,'mohamed085555@gmail.com',_binary '','Mohamed Emad','$2a$10$Mnwx0aS1SXjdNqWNHlatMONEIwRPe25LfzZVUBOBSDzNTHaQIm4Va','01012703497',NULL,'1256905571',NULL),(4,_binary '',_binary '',NULL,'DATABASE',NULL,'2022-02-21 23:28:15.982627',_binary '',NULL,'mohamed.emad627@ci.menofia.edu.eg',_binary '','Mohamed Emad','$2a$10$Y5paq.yhCEVScV8IAe60ueT0oE1mDZfE5HtiMayotAqLJ9nLbJ7/u',NULL,NULL,NULL,NULL),(5,_binary '',_binary '',NULL,'DATABASE',NULL,'2022-02-25 01:39:47.670427',_binary '',NULL,'mohamed.emad6',_binary '\0','Mohamed Emad','$2a$10$pjv1HCVdJ8ErtWKjqZxEpejZEHhkpkhm6ENqEBbEkcpX6M5XfPrWu',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
  CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (3,1),(4,1),(5,1);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verification_codes`
--

DROP TABLE IF EXISTS `verification_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `verification_codes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `email_send_status` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `temp_verify` int NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa4qo6nts1xd94owirq5evcpda` (`user_id`),
  CONSTRAINT `FKa4qo6nts1xd94owirq5evcpda` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verification_codes`
--

LOCK TABLES `verification_codes` WRITE;
/*!40000 ALTER TABLE `verification_codes` DISABLE KEYS */;
INSERT INTO `verification_codes` VALUES (1,'490065','2022-02-21 23:24:13.762402','SEND','ACTIVE',0,3),(2,'112586','2022-02-21 23:28:55.531707','SEND','ACTIVE',0,4),(3,'528471',NULL,'SEND','ACTIVE',0,5);
/*!40000 ALTER TABLE `verification_codes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-25 15:32:50
