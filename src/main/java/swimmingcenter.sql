-- MySQL dump 10.13  Distrib 5.7.44, for Win64 (x86_64)
--
-- Host: localhost    Database: swimmingcenter
-- ------------------------------------------------------
-- Server version	5.7.44-log

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
-- Table structure for table `auth`
--

DROP TABLE IF EXISTS `auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auth` (
  `AID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Auth id',
  `title` varchar(250) DEFAULT NULL COMMENT '路径名',
  `PID` int(11) DEFAULT NULL COMMENT '父ID',
  `path` varchar(250) DEFAULT NULL,
  `componentName` varchar(250) DEFAULT NULL,
  `type` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`AID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth`
--

LOCK TABLES `auth` WRITE;
/*!40000 ALTER TABLE `auth` DISABLE KEYS */;
INSERT INTO `auth` VALUES (0,NULL,NULL,NULL,NULL,NULL),(1,'DashBoard',0,'/admin','dashboard.vue','Button'),(2,'AdminCardManage',0,'/admin/cardManagement',NULL,'Tab'),(3,'blockList',2,'/admin/cardManagement','blockList.vue','Button'),(4,'operate',2,'/admin/cardManagement','operate.vue','Button'),(5,'transaction',2,'/admin/cardManagement','transaction.vue','Button'),(6,'profile',0,'/admin/profile',NULL,'Tab'),(7,'profileEdit',6,'/admin/profile','profile.vue','Button'),(8,'userManagement',0,'/admin/userManagement',NULL,'Tab'),(9,'blockedUser',8,'/admin/userManagement','blockedUser.vue','Button'),(10,'resetPassword',6,'/admin/profile','resetPassword.vue','Button'),(11,'CardManage',0,'/user/cardManagement',NULL,'Tab'),(12,'operate',11,'/user/cardManagement','operate.vue','Button'),(13,'query',11,'/user/cardManagement','query.vue','Button'),(14,'profile',0,'/user/profile',NULL,'Tab'),(15,'resetPassword',14,'/user/profile','resetPassword.vue','Button'),(16,'userProfile',14,'/user/profile','profile.vue','Button'),(17,'dashboard',0,'/user','dashboard.vue','Button'),(18,'Management',8,'/admin/userManagement','userManagement.vue','Button');
/*!40000 ALTER TABLE `auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blockedcardlistinfo`
--

DROP TABLE IF EXISTS `blockedcardlistinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blockedcardlistinfo` (
  `BID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'BlockId',
  `CID` varchar(250) DEFAULT NULL COMMENT '卡号',
  `BDescription` varchar(1500) DEFAULT NULL COMMENT '封禁理由',
  `BCreateTime` datetime DEFAULT NULL COMMENT '封禁时间',
  `BExpire` datetime DEFAULT NULL COMMENT '解禁时间',
  PRIMARY KEY (`BID`),
  KEY `CID` (`CID`),
  CONSTRAINT `blockedcardlistinfo_ibfk_1` FOREIGN KEY (`CID`) REFERENCES `card` (`CID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blockedcardlistinfo`
--

LOCK TABLES `blockedcardlistinfo` WRITE;
/*!40000 ALTER TABLE `blockedcardlistinfo` DISABLE KEYS */;
INSERT INTO `blockedcardlistinfo` VALUES (1,'479f8602c3d249bf94e6377baf5150a9','违反规则','2024-08-28 12:57:35','2024-08-28 13:14:15');
/*!40000 ALTER TABLE `blockedcardlistinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `card`
--

DROP TABLE IF EXISTS `card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `card` (
  `CID` varchar(250) NOT NULL COMMENT 'CARD ID',
  `UID` bigint(20) DEFAULT NULL COMMENT '用户id',
  `Balance` double DEFAULT NULL COMMENT '金额',
  `TID` bigint(20) DEFAULT NULL COMMENT '卡类型ID',
  `Discount` double DEFAULT NULL COMMENT '折扣力度',
  `Status` tinyint(4) DEFAULT NULL COMMENT '能否使用',
  PRIMARY KEY (`CID`),
  KEY `UID` (`UID`),
  KEY `card_card_type_TID_fk` (`TID`),
  CONSTRAINT `card_card_type_TID_fk` FOREIGN KEY (`TID`) REFERENCES `card_type` (`TID`),
  CONSTRAINT `card_ibfk_1` FOREIGN KEY (`UID`) REFERENCES `sys_user_login` (`UID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `card`
--

LOCK TABLES `card` WRITE;
/*!40000 ALTER TABLE `card` DISABLE KEYS */;
INSERT INTO `card` VALUES ('2da19d794d12438a809dbf0f77e5942c',5,10000,1,1,1),('2e306c3ed42c4a4ab1f604345f11a938',10,100000,1,1,1),('2e92d21840ed48f7bee170bce2eabc0d',12,20950,1,1,1),('3977930932504ddd9735d8fb42728f21',1,10301000,1,1,1),('3ee800cd029f41b2bb0b44e82d34d84d',1,1312312.45,1,1,3),('479f8602c3d249bf94e6377baf5150a9',1,1332412312.45,2,0.5,1),('518d699abe564fc28a41c7a8ab52109c',1,10311000,1,1,1),('69ef91c34bc54d65b4e313c9524076c5',1,1031999,1,1,2),('7b586887fd9d457fb1a7fbcfbba4d5af',1,100000,1,1,1),('99e98330e3bc4c62aad298a0cd6e49bb',1,10000,1,0.8,1),('d4cd13a264f54505b22242fa9780e703',9,11111,2,0.5,1),('d78f9b8ffe4846e2b0fb429e79d73f5c',12,460,2,0.8,4),('e2cfc59268de492f891346534c0003f6',1,1.45,2,0.5,1),('f71e2ba8a0cc4e2c83eb13e61fe6649c',5,1000000,1,1,1);
/*!40000 ALTER TABLE `card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `card_type`
--

DROP TABLE IF EXISTS `card_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `card_type` (
  `TID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '类型ID',
  `TDescription` varchar(250) DEFAULT NULL COMMENT '类型说明',
  PRIMARY KEY (`TID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `card_type`
--

LOCK TABLES `card_type` WRITE;
/*!40000 ALTER TABLE `card_type` DISABLE KEYS */;
INSERT INTO `card_type` VALUES (1,'储值卡'),(2,'折扣卡');
/*!40000 ALTER TABLE `card_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consume`
--

DROP TABLE IF EXISTS `consume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `consume` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `UID` bigint(20) NOT NULL COMMENT '用户ID',
  `CID` varchar(250) NOT NULL COMMENT '卡号',
  `Consume_Save_Balance` double DEFAULT NULL COMMENT '存款/消费金额',
  `CreateTime` datetime DEFAULT NULL COMMENT '消费时间',
  `currentBalance` double DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UID` (`UID`),
  KEY `CID` (`CID`),
  CONSTRAINT `consume_ibfk_1` FOREIGN KEY (`UID`) REFERENCES `sys_user_login` (`UID`),
  CONSTRAINT `consume_ibfk_2` FOREIGN KEY (`CID`) REFERENCES `card` (`CID`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consume`
--

LOCK TABLES `consume` WRITE;
/*!40000 ALTER TABLE `consume` DISABLE KEYS */;
INSERT INTO `consume` VALUES (8,1,'3ee800cd029f41b2bb0b44e82d34d84d',1312312.45,'2024-07-28 11:49:51',1312312.45,'新卡办理'),(9,1,'479f8602c3d249bf94e6377baf5150a9',1332412312.45,'2024-07-28 11:50:00',1332412312.45,'新卡办理'),(10,1,'e2cfc59268de492f891346534c0003f6',1.45,'2024-07-28 11:50:06',1.45,'新卡办理'),(11,1,'7b586887fd9d457fb1a7fbcfbba4d5af',100000,'2024-07-28 11:50:17',100000,'新卡办理'),(12,1,'69ef91c34bc54d65b4e313c9524076c5',1030000,'2024-07-28 11:50:20',1030000,'新卡办理'),(13,1,'518d699abe564fc28a41c7a8ab52109c',10301000,'2024-07-28 11:50:25',10301000,'新卡办理'),(14,1,'3977930932504ddd9735d8fb42728f21',10301000,'2024-07-28 11:50:27',10301000,'新卡办理'),(15,1,'518d699abe564fc28a41c7a8ab52109c',10000,'2024-07-28 11:53:39',10311000,'存钱'),(16,1,'99e98330e3bc4c62aad298a0cd6e49bb',10000,'2024-08-03 13:51:27',10000,'新卡办理'),(17,5,'2da19d794d12438a809dbf0f77e5942c',10000,'2024-08-03 14:15:14',10000,'新卡办理'),(18,9,'d4cd13a264f54505b22242fa9780e703',11111,'2024-08-03 14:24:19',11111,'新卡办理'),(19,10,'2e306c3ed42c4a4ab1f604345f11a938',100000,'2024-08-03 14:25:58',100000,'新卡办理'),(20,5,'f71e2ba8a0cc4e2c83eb13e61fe6649c',1000000,'2024-08-03 21:59:53',1000000,'新卡办理'),(21,1,'69ef91c34bc54d65b4e313c9524076c5',1999,'2024-08-04 15:45:01',1031999,'普通存钱'),(22,12,'2e92d21840ed48f7bee170bce2eabc0d',11000,'2024-08-11 20:57:27',11000,'新卡办理'),(23,12,'d78f9b8ffe4846e2b0fb429e79d73f5c',500,'2024-08-11 21:01:23',500,'新卡办理'),(24,12,'2e92d21840ed48f7bee170bce2eabc0d',10000,'2024-08-11 22:29:14',21000,'普通存钱'),(25,12,'2e92d21840ed48f7bee170bce2eabc0d',-50,'2024-08-11 22:56:36',20950,'畅游2小时'),(26,12,'d78f9b8ffe4846e2b0fb429e79d73f5c',-40,'2024-08-11 22:56:39',460,'畅游2小时');
/*!40000 ALTER TABLE `consume` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `RID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'role id',
  `Description` varchar(25) DEFAULT NULL COMMENT '描述',
  `CreateTime` datetime DEFAULT NULL COMMENT '建立日期',
  PRIMARY KEY (`RID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'管理员','2024-07-12 22:22:59'),(2,'员工','2024-07-12 22:22:59'),(3,'普通用户','2024-07-12 22:22:59'),(4,'会员','2024-07-12 22:22:59');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_auth`
--

DROP TABLE IF EXISTS `role_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_auth` (
  `RID` bigint(20) NOT NULL COMMENT 'ROLE_ID',
  `AID` bigint(20) NOT NULL COMMENT 'AUTH_ID',
  PRIMARY KEY (`RID`,`AID`),
  KEY `AID` (`AID`),
  CONSTRAINT `role_auth_ibfk_1` FOREIGN KEY (`RID`) REFERENCES `role` (`RID`),
  CONSTRAINT `role_auth_ibfk_2` FOREIGN KEY (`AID`) REFERENCES `auth` (`AID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_auth`
--

LOCK TABLES `role_auth` WRITE;
/*!40000 ALTER TABLE `role_auth` DISABLE KEYS */;
INSERT INTO `role_auth` VALUES (1,1),(2,1),(1,2),(2,2),(1,3),(2,3),(1,4),(2,4),(1,5),(2,5),(1,6),(2,6),(1,7),(2,7),(1,8),(1,9),(1,10),(2,10),(3,11),(4,11),(3,12),(4,12),(3,13),(4,13),(3,14),(4,14),(3,15),(4,15),(3,16),(4,16),(3,17),(4,17),(1,18);
/*!40000 ALTER TABLE `role_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_login`
--

DROP TABLE IF EXISTS `sys_user_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_login` (
  `UID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `UserName` varchar(250) DEFAULT NULL COMMENT '用户登录名',
  `UserPassWord` varchar(250) DEFAULT NULL COMMENT '用户密码',
  `UserState` tinyint(1) DEFAULT NULL COMMENT '是否被禁用',
  `RID` bigint(20) DEFAULT NULL COMMENT '用户角色',
  PRIMARY KEY (`UID`),
  KEY `RID` (`RID`),
  CONSTRAINT `sys_user_login_ibfk_1` FOREIGN KEY (`RID`) REFERENCES `role` (`RID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_login`
--

LOCK TABLES `sys_user_login` WRITE;
/*!40000 ALTER TABLE `sys_user_login` DISABLE KEYS */;
INSERT INTO `sys_user_login` VALUES (1,'ZlcAdmin','951c2e4a71f6b6d551b6b4d41ecf9483',1,1),(2,'test123','fcea920f7412b5da7be0cf42b8c93759',1,4),(3,'testUser1','25d55ad283aa400af464c76d713c07ad',1,3),(4,'testUser02','5e543256c480ac577d30f76f9120eb74',1,3),(5,'testUser03','5e543256c480ac577d30f76f9120eb74',1,4),(6,'testUser05','5e543256c480ac577d30f76f9120eb74',1,3),(7,'testUser06','5e543256c480ac577d30f76f9120eb74',1,3),(8,'testUser07','5e543256c480ac577d30f76f9120eb74',1,3),(9,'testUser09','7299f2d3cbd3384e59a057b0cb55af2e',1,4),(10,'testuser11','a94ff64b3f4784a3f62058dd4099af8f',1,4),(11,'testUser00001','b278d4c934d61b4d745d30af22577e8b',1,1),(12,'testuuu','25f9e794323b453885f5181f1b624d0b',1,4);
/*!40000 ALTER TABLE `sys_user_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `UID` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `UNAME` varchar(250) DEFAULT NULL COMMENT '用户姓名',
  `UPHONE` varchar(250) DEFAULT NULL COMMENT '用户手机号',
  `UIDENTITY` varchar(250) DEFAULT NULL COMMENT '用户证件',
  `UAVATAR` varchar(500) DEFAULT NULL COMMENT '用户头像',
  `UDescription` varchar(1500) DEFAULT NULL COMMENT '用户介绍',
  PRIMARY KEY (`ID`),
  KEY `UID` (`UID`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`UID`) REFERENCES `sys_user_login` (`UID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,1,'ZlcAdmin','1234567','123456789','https://cos.hpcow.cn/cowimg.jpg','32432432234'),(2,2,'test123',NULL,'1221231',NULL,NULL),(3,3,'憨批cow','17717699396','28937483274893',NULL,NULL),(4,4,'testUser02',NULL,NULL,NULL,NULL),(5,5,'dhu12','12321223','123123212',NULL,NULL),(6,6,'hey','11111','122222',NULL,NULL),(7,7,'testUser06','11111111111','6666666666','https://hpcow-1316827225.cos.ap-shanghai.myqcloud.com/java/bfb56458-7148-468e-889a-b21e957413f9document.jpg',NULL),(8,8,'1231','111','111',NULL,NULL),(9,9,'12321321','12321213','1231232121',NULL,NULL),(10,10,'中国工业','21321321','213213123',NULL,NULL),(11,11,'testUser00001',NULL,NULL,NULL,NULL),(12,12,'testuuu','2132132','12321321','https://hpcow-1316827225.cos.ap-shanghai.myqcloud.com/java/dfbf7b51-679a-4f79-b447-68286d94e751document.jpg','21321321312');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-12  0:09:48
