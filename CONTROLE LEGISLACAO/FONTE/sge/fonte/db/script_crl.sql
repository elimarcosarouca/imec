CREATE DATABASE  IF NOT EXISTS `sge` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `sge`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: sge
-- ------------------------------------------------------
-- Server version	5.5.36

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
-- Table structure for table `acad_aluno`
--

DROP TABLE IF EXISTS `acad_aluno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acad_aluno` (
  `id_aluno` bigint(20) NOT NULL AUTO_INCREMENT,
  `alergia` int(11) NOT NULL,
  `bairro` varchar(60) NOT NULL,
  `celular` varchar(9) DEFAULT NULL,
  `cep` varchar(9) DEFAULT NULL,
  `complemento` varchar(100) NOT NULL,
  `cpf` varchar(14) DEFAULT NULL,
  `data_cadastro` datetime NOT NULL,
  `data_nascimento` datetime DEFAULT NULL,
  `descricao_alegia` varchar(200) DEFAULT NULL,
  `descricao_remedio` varchar(200) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `endereco` varchar(60) NOT NULL,
  `fone_mae` varchar(12) DEFAULT NULL,
  `fone_pai` varchar(12) DEFAULT NULL,
  `fone_residencial` varchar(9) DEFAULT NULL,
  `grau_parentesco` int(11) NOT NULL,
  `mae` varchar(60) DEFAULT NULL,
  `naturalidade` varchar(30) DEFAULT NULL,
  `nome` varchar(60) NOT NULL,
  `numero` varchar(10) NOT NULL,
  `pai` varchar(60) DEFAULT NULL,
  `rg` varchar(10) DEFAULT NULL,
  `sexo` int(11) NOT NULL,
  `uf` int(11) NOT NULL,
  `id_responsavel` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_aluno`),
  KEY `FK_1ofh34p98750kva4x143lrqjd` (`id_responsavel`),
  CONSTRAINT `FK_1ofh34p98750kva4x143lrqjd` FOREIGN KEY (`id_responsavel`) REFERENCES `acad_responsavel` (`id_responsavel`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acad_aluno`
--

LOCK TABLES `acad_aluno` WRITE;
/*!40000 ALTER TABLE `acad_aluno` DISABLE KEYS */;
INSERT INTO `acad_aluno` VALUES (1,0,'898989','9999-9999','99999-999','98988','607.350.902-20','2014-08-03 10:36:10','2014-08-03 00:00:00','2121','2121','9999@gmail.com','89898','9999-9999','9999-9999','9999-9999',0,'juuu','909','claudemir ramos ferreira','88','joao','99999999-9',0,2,1),(2,0,'7777','8888-8888','88888-888','77','607.350.902-20','2014-08-05 10:06:29','2014-08-05 00:00:00','WWW','WWW','88@fmail.com','9999','8888-8888','8888-8888','8888-8888',0,'8888888888888','7777','qqqq','77','OOOOOO','88888888-8',0,0,1);
/*!40000 ALTER TABLE `acad_aluno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acad_boletim`
--

DROP TABLE IF EXISTS `acad_boletim`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acad_boletim` (
  `id_boletim` bigint(20) NOT NULL AUTO_INCREMENT,
  `media1` float NOT NULL,
  `media2` float NOT NULL,
  `media3` float NOT NULL,
  `media4` float NOT NULL,
  `media_final` float NOT NULL,
  `nota1` float NOT NULL,
  `nota2` float NOT NULL,
  `nota3` float NOT NULL,
  `nota4` float NOT NULL,
  `nota5` float NOT NULL,
  `nota6` float NOT NULL,
  `nota7` float NOT NULL,
  `nota8` float NOT NULL,
  `id_disciplina` bigint(20) NOT NULL,
  `id_matricula` bigint(20) NOT NULL,
  `status_boletim` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_boletim`),
  KEY `FK_p8d9kggflelbutgso6bn9mpek` (`id_disciplina`),
  KEY `FK_mq5q6rbkuuuum3poa3ojvag6u` (`id_matricula`),
  CONSTRAINT `FK_mq5q6rbkuuuum3poa3ojvag6u` FOREIGN KEY (`id_matricula`) REFERENCES `acad_matricula` (`id_matricula`),
  CONSTRAINT `FK_p8d9kggflelbutgso6bn9mpek` FOREIGN KEY (`id_disciplina`) REFERENCES `acad_disciplina` (`id_disciplina`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acad_boletim`
--

LOCK TABLES `acad_boletim` WRITE;
/*!40000 ALTER TABLE `acad_boletim` DISABLE KEYS */;
INSERT INTO `acad_boletim` VALUES (1,7.5,5,7,9,0,10,5,4,6,6,8,9,9,1,1,NULL),(2,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,NULL),(3,0,0,0,0,0,0,0,0,0,0,0,0,0,3,1,NULL),(4,0,0,0,0,0,0,0,0,0,0,0,0,0,4,1,NULL);
/*!40000 ALTER TABLE `acad_boletim` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acad_configuracao`
--

DROP TABLE IF EXISTS `acad_configuracao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acad_configuracao` (
  `id_configuracao` bigint(20) NOT NULL AUTO_INCREMENT,
  `dia_vencimento` int(11) NOT NULL,
  `tema` varchar(20) DEFAULT NULL,
  `media_escolar` float NOT NULL,
  PRIMARY KEY (`id_configuracao`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acad_configuracao`
--

LOCK TABLES `acad_configuracao` WRITE;
/*!40000 ALTER TABLE `acad_configuracao` DISABLE KEYS */;
INSERT INTO `acad_configuracao` VALUES (1,10,'aristo',0);
/*!40000 ALTER TABLE `acad_configuracao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acad_curso`
--

DROP TABLE IF EXISTS `acad_curso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acad_curso` (
  `id_curso` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `valor` double NOT NULL,
  PRIMARY KEY (`id_curso`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acad_curso`
--

LOCK TABLES `acad_curso` WRITE;
/*!40000 ALTER TABLE `acad_curso` DISABLE KEYS */;
INSERT INTO `acad_curso` VALUES (1,'pré 1',1000),(2,'pré 2',1000),(3,'www',0),(4,'www',0);
/*!40000 ALTER TABLE `acad_curso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acad_curso_disciplina`
--

DROP TABLE IF EXISTS `acad_curso_disciplina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acad_curso_disciplina` (
  `data` datetime DEFAULT NULL,
  `id_curso` bigint(20) NOT NULL DEFAULT '0',
  `id_disciplina` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_curso`,`id_disciplina`),
  KEY `FK_gvsiecwjppuxl61exe7xlblfk` (`id_curso`),
  KEY `FK_j9mg4c35y4ye8nkqvaoqcouyx` (`id_disciplina`),
  CONSTRAINT `FK_gvsiecwjppuxl61exe7xlblfk` FOREIGN KEY (`id_curso`) REFERENCES `acad_curso` (`id_curso`),
  CONSTRAINT `FK_j9mg4c35y4ye8nkqvaoqcouyx` FOREIGN KEY (`id_disciplina`) REFERENCES `acad_disciplina` (`id_disciplina`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acad_curso_disciplina`
--

LOCK TABLES `acad_curso_disciplina` WRITE;
/*!40000 ALTER TABLE `acad_curso_disciplina` DISABLE KEYS */;
INSERT INTO `acad_curso_disciplina` VALUES ('2014-08-03 10:38:54',1,1),('2014-08-03 10:38:54',1,2),('2014-08-03 10:38:54',1,3),('2014-08-03 10:38:54',1,4),('2014-08-03 10:39:00',2,1),('2014-08-03 10:39:00',2,2),('2014-08-03 10:39:00',2,3),('2014-08-03 10:39:00',2,4);
/*!40000 ALTER TABLE `acad_curso_disciplina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acad_detalhe_boletim`
--

DROP TABLE IF EXISTS `acad_detalhe_boletim`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acad_detalhe_boletim` (
  `id_detalhe_boletim` bigint(20) NOT NULL AUTO_INCREMENT,
  `faltas_bimestre1` int(11) DEFAULT NULL,
  `faltas_bimestre2` int(11) DEFAULT NULL,
  `faltas_bimestre3` int(11) DEFAULT NULL,
  `faltas_bimestre4` int(11) DEFAULT NULL,
  `media1` float DEFAULT NULL,
  `media2` float DEFAULT NULL,
  `media3` float DEFAULT NULL,
  `media4` float DEFAULT NULL,
  `media_final` float DEFAULT NULL,
  `media_geral` float DEFAULT NULL,
  `nota1` float DEFAULT NULL,
  `nota2` float DEFAULT NULL,
  `nota3` float DEFAULT NULL,
  `nota4` float DEFAULT NULL,
  `nota5` float DEFAULT NULL,
  `nota6` float DEFAULT NULL,
  `nota7` float DEFAULT NULL,
  `nota8` float DEFAULT NULL,
  `nota_recuperacao` float DEFAULT NULL,
  `recuperacao` tinyint(1) DEFAULT NULL,
  `status_disciplina` int(11) DEFAULT NULL,
  `total_faltas` int(11) DEFAULT NULL,
  `id_boletim` bigint(20) NOT NULL,
  `id_disciplina` bigint(20) NOT NULL,
  PRIMARY KEY (`id_detalhe_boletim`),
  KEY `FK_2ljwldoj52dave1ia30mddt6b` (`id_boletim`),
  KEY `FK_cfn0hbkjn4rhn6ym4chfmadgy` (`id_disciplina`),
  CONSTRAINT `FK_cfn0hbkjn4rhn6ym4chfmadgy` FOREIGN KEY (`id_disciplina`) REFERENCES `acad_disciplina` (`id_disciplina`),
  CONSTRAINT `FK_2ljwldoj52dave1ia30mddt6b` FOREIGN KEY (`id_boletim`) REFERENCES `acad_boletim` (`id_boletim`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acad_detalhe_boletim`
--

LOCK TABLES `acad_detalhe_boletim` WRITE;
/*!40000 ALTER TABLE `acad_detalhe_boletim` DISABLE KEYS */;
/*!40000 ALTER TABLE `acad_detalhe_boletim` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acad_disciplina`
--

DROP TABLE IF EXISTS `acad_disciplina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acad_disciplina` (
  `id_disciplina` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(100) DEFAULT NULL,
  `nome` varchar(50) NOT NULL,
  `maximo_faltas` int(11) NOT NULL,
  PRIMARY KEY (`id_disciplina`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acad_disciplina`
--

LOCK TABLES `acad_disciplina` WRITE;
/*!40000 ALTER TABLE `acad_disciplina` DISABLE KEYS */;
INSERT INTO `acad_disciplina` VALUES (1,'','Matemática',0),(2,'','Portugues',0),(3,'','Fisíca',0),(4,'','Historia',0);
/*!40000 ALTER TABLE `acad_disciplina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acad_matricula`
--

DROP TABLE IF EXISTS `acad_matricula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acad_matricula` (
  `id_matricula` bigint(20) NOT NULL AUTO_INCREMENT,
  `data` datetime NOT NULL,
  `integral` tinyint(1) NOT NULL,
  `status` int(11) NOT NULL,
  `texto_relatorio` varchar(255) DEFAULT NULL,
  `valor` double NOT NULL,
  `id_aluno` bigint(20) NOT NULL,
  `id_turma` bigint(20) NOT NULL,
  PRIMARY KEY (`id_matricula`),
  KEY `FK_psjpgyxt6vvv2jfsfii1jvpa6` (`id_aluno`),
  KEY `FK_4fr9y18xpqymx6wyobq1ixdn7` (`id_turma`),
  CONSTRAINT `FK_4fr9y18xpqymx6wyobq1ixdn7` FOREIGN KEY (`id_turma`) REFERENCES `acad_turma` (`id_turma`),
  CONSTRAINT `FK_psjpgyxt6vvv2jfsfii1jvpa6` FOREIGN KEY (`id_aluno`) REFERENCES `acad_aluno` (`id_aluno`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acad_matricula`
--

LOCK TABLES `acad_matricula` WRITE;
/*!40000 ALTER TABLE `acad_matricula` DISABLE KEYS */;
INSERT INTO `acad_matricula` VALUES (1,'2014-08-23 00:00:00',0,0,NULL,1000,2,1);
/*!40000 ALTER TABLE `acad_matricula` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acad_mensalidade`
--

DROP TABLE IF EXISTS `acad_mensalidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acad_mensalidade` (
  `id_mensalidade` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_pagamento` date DEFAULT NULL,
  `data_vencimento` date NOT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `sequencial` int(11) NOT NULL,
  `status_pagamento` int(11) NOT NULL,
  `valor_pagamento` double NOT NULL,
  `valor_vencimento` double NOT NULL,
  `id_matricula` bigint(20) NOT NULL,
  `id_usuario` bigint(20) DEFAULT NULL,
  `id_usuario_atualizacao` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_mensalidade`),
  KEY `FK_5ohqk2wplxluosa2ixutna7jk` (`id_matricula`),
  KEY `FK_1r9uok0uhbqsqkxiwqiycls8h` (`id_usuario`),
  KEY `FK_mnjgbwawshf8ktwdhfq3du88p` (`id_usuario_atualizacao`),
  CONSTRAINT `FK_1r9uok0uhbqsqkxiwqiycls8h` FOREIGN KEY (`id_usuario`) REFERENCES `saa_usuario` (`id_usuario`),
  CONSTRAINT `FK_5ohqk2wplxluosa2ixutna7jk` FOREIGN KEY (`id_matricula`) REFERENCES `acad_matricula` (`id_matricula`),
  CONSTRAINT `FK_mnjgbwawshf8ktwdhfq3du88p` FOREIGN KEY (`id_usuario_atualizacao`) REFERENCES `saa_usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acad_mensalidade`
--

LOCK TABLES `acad_mensalidade` WRITE;
/*!40000 ALTER TABLE `acad_mensalidade` DISABLE KEYS */;
INSERT INTO `acad_mensalidade` VALUES (1,NULL,'2015-08-10',NULL,8,0,0,200,1,1,NULL),(2,NULL,'2015-09-10',NULL,9,0,0,200,1,1,NULL),(3,NULL,'2015-10-10',NULL,10,0,0,200,1,1,NULL),(4,NULL,'2015-11-10',NULL,11,0,0,200,1,1,NULL),(5,NULL,'2015-12-10',NULL,12,0,0,200,1,1,NULL),(6,'2014-11-02','2014-11-02',NULL,1,0,0,222,1,NULL,NULL),(7,'2014-11-02','2014-11-02',NULL,1,0,0,222,1,NULL,NULL),(8,'2014-11-02','2014-11-02',NULL,1,0,0,222,1,NULL,NULL);
/*!40000 ALTER TABLE `acad_mensalidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acad_observacao`
--

DROP TABLE IF EXISTS `acad_observacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acad_observacao` (
  `id_observacao` bigint(20) NOT NULL AUTO_INCREMENT,
  `tx_observacao` varchar(255) NOT NULL,
  `matricula` bigint(20) DEFAULT NULL,
  `id_usuario` bigint(20) NOT NULL,
  PRIMARY KEY (`id_observacao`),
  KEY `FK_mihvaxpbu34ne567r35y46qmn` (`matricula`),
  KEY `FK_q8h5b61shdfwk5ispw07dnqvn` (`id_usuario`),
  CONSTRAINT `FK_mihvaxpbu34ne567r35y46qmn` FOREIGN KEY (`matricula`) REFERENCES `acad_matricula` (`id_matricula`),
  CONSTRAINT `FK_q8h5b61shdfwk5ispw07dnqvn` FOREIGN KEY (`id_usuario`) REFERENCES `saa_usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acad_observacao`
--

LOCK TABLES `acad_observacao` WRITE;
/*!40000 ALTER TABLE `acad_observacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `acad_observacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acad_professor`
--

DROP TABLE IF EXISTS `acad_professor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acad_professor` (
  `id_professor` bigint(20) NOT NULL AUTO_INCREMENT,
  `bairro` varchar(60) NOT NULL,
  `celular` varchar(9) DEFAULT NULL,
  `cep` varchar(9) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `data_cadastro` datetime NOT NULL,
  `data_nascimento` datetime NOT NULL,
  `email` varchar(60) DEFAULT NULL,
  `endereco` varchar(60) NOT NULL,
  `fone_comercial` varchar(9) DEFAULT NULL,
  `fone_residencial` varchar(9) DEFAULT NULL,
  `nome` varchar(60) NOT NULL,
  `rg` varchar(10) NOT NULL,
  `sexo` int(11) NOT NULL,
  PRIMARY KEY (`id_professor`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acad_professor`
--

LOCK TABLES `acad_professor` WRITE;
/*!40000 ALTER TABLE `acad_professor` DISABLE KEYS */;
INSERT INTO `acad_professor` VALUES (1,'999','9999-9999','99999-999','607.350.902-20','2014-08-03 10:17:15','2014-08-03 00:00:00','9999@gmail.com','99999','9999-9999','9999-9999','wwwwwwwww','9999999999',0);
/*!40000 ALTER TABLE `acad_professor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acad_responsavel`
--

DROP TABLE IF EXISTS `acad_responsavel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acad_responsavel` (
  `id_responsavel` bigint(20) NOT NULL AUTO_INCREMENT,
  `bairro` varchar(60) NOT NULL,
  `celular` varchar(9) DEFAULT NULL,
  `cep` varchar(9) DEFAULT NULL,
  `cpf` varchar(14) NOT NULL,
  `data_nascimento` datetime DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `endereco` varchar(60) NOT NULL,
  `fone_comercial` varchar(9) DEFAULT NULL,
  `fone_residencial` varchar(9) DEFAULT NULL,
  `nome` varchar(60) NOT NULL,
  `profissao` varchar(30) NOT NULL,
  `rg` varchar(10) NOT NULL,
  `sexo` int(11) NOT NULL,
  `estado_civil` char(1) NOT NULL,
  PRIMARY KEY (`id_responsavel`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acad_responsavel`
--

LOCK TABLES `acad_responsavel` WRITE;
/*!40000 ALTER TABLE `acad_responsavel` DISABLE KEYS */;
INSERT INTO `acad_responsavel` VALUES (1,'99999','9999-9999','99999-999','607.350.902-20','2014-08-03 00:00:00','9999@gmail.com','90999','9999-9999','9999-9999','joao','999999999','9999999999',0,'O');
/*!40000 ALTER TABLE `acad_responsavel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acad_turma`
--

DROP TABLE IF EXISTS `acad_turma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acad_turma` (
  `id_turma` bigint(20) NOT NULL AUTO_INCREMENT,
  `ano` int(11) NOT NULL,
  `numero_vagas` int(11) NOT NULL,
  `situacao` int(11) NOT NULL,
  `turno` int(11) NOT NULL,
  `id_curso` bigint(20) NOT NULL,
  `sala` varchar(10) NOT NULL,
  PRIMARY KEY (`id_turma`),
  KEY `FK_2qed6ismnk8dlxohh3ie29tb5` (`id_curso`),
  CONSTRAINT `FK_2qed6ismnk8dlxohh3ie29tb5` FOREIGN KEY (`id_curso`) REFERENCES `acad_curso` (`id_curso`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acad_turma`
--

LOCK TABLES `acad_turma` WRITE;
/*!40000 ALTER TABLE `acad_turma` DISABLE KEYS */;
INSERT INTO `acad_turma` VALUES (1,2015,30,0,0,1,'A');
/*!40000 ALTER TABLE `acad_turma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `acad_turma_disciplina`
--

DROP TABLE IF EXISTS `acad_turma_disciplina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acad_turma_disciplina` (
  `data` datetime DEFAULT NULL,
  `id_turma` bigint(20) NOT NULL DEFAULT '0',
  `id_disciplina` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_disciplina`,`id_turma`),
  KEY `FK_tgmm7cchb962gh5lvjqdaim3w` (`id_turma`),
  KEY `FK_tmf6l1l872tifrujdj883egvs` (`id_disciplina`),
  CONSTRAINT `FK_tgmm7cchb962gh5lvjqdaim3w` FOREIGN KEY (`id_turma`) REFERENCES `acad_turma` (`id_turma`),
  CONSTRAINT `FK_tmf6l1l872tifrujdj883egvs` FOREIGN KEY (`id_disciplina`) REFERENCES `acad_disciplina` (`id_disciplina`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acad_turma_disciplina`
--

LOCK TABLES `acad_turma_disciplina` WRITE;
/*!40000 ALTER TABLE `acad_turma_disciplina` DISABLE KEYS */;
/*!40000 ALTER TABLE `acad_turma_disciplina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `michell`
--

DROP TABLE IF EXISTS `michell`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `michell` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `michell`
--

LOCK TABLES `michell` WRITE;
/*!40000 ALTER TABLE `michell` DISABLE KEYS */;
INSERT INTO `michell` VALUES (1,'micheel telo'),(2,'micheel telo'),(3,'eeee');
/*!40000 ALTER TABLE `michell` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `norm_emissor`
--

DROP TABLE IF EXISTS `norm_emissor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `norm_emissor` (
  `id_norm_emissor` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(30) NOT NULL,
  PRIMARY KEY (`id_norm_emissor`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `norm_emissor`
--

LOCK TABLES `norm_emissor` WRITE;
/*!40000 ALTER TABLE `norm_emissor` DISABLE KEYS */;
INSERT INTO `norm_emissor` VALUES (1,'emissor 1'),(2,'rrrr'),(3,'ze'),(4,'rrrr');
/*!40000 ALTER TABLE `norm_emissor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `norm_filial`
--

DROP TABLE IF EXISTS `norm_filial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `norm_filial` (
  `id_norm_filial` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(30) NOT NULL,
  PRIMARY KEY (`id_norm_filial`),
  UNIQUE KEY `UK_8xlfhj1uxbyiqva4nokdr7tkl` (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `norm_filial`
--

LOCK TABLES `norm_filial` WRITE;
/*!40000 ALTER TABLE `norm_filial` DISABLE KEYS */;
/*!40000 ALTER TABLE `norm_filial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `norm_legislacao`
--

DROP TABLE IF EXISTS `norm_legislacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `norm_legislacao` (
  `id_norm_legislacao` bigint(20) NOT NULL AUTO_INCREMENT,
  `competencia` varchar(1) NOT NULL,
  `data_alerta` date DEFAULT NULL,
  `data_inicio` date DEFAULT NULL,
  `descricao` varchar(200) NOT NULL,
  `numero` varchar(20) NOT NULL,
  `path_file` varchar(60) NOT NULL,
  PRIMARY KEY (`id_norm_legislacao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `norm_legislacao`
--

LOCK TABLES `norm_legislacao` WRITE;
/*!40000 ALTER TABLE `norm_legislacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `norm_legislacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `norm_licenca`
--

DROP TABLE IF EXISTS `norm_licenca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `norm_licenca` (
  `id_norm_licenca` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_fim` date DEFAULT NULL,
  `data_inicio` date NOT NULL,
  PRIMARY KEY (`id_norm_licenca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `norm_licenca`
--

LOCK TABLES `norm_licenca` WRITE;
/*!40000 ALTER TABLE `norm_licenca` DISABLE KEYS */;
/*!40000 ALTER TABLE `norm_licenca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `norm_palavra_chave`
--

DROP TABLE IF EXISTS `norm_palavra_chave`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `norm_palavra_chave` (
  `id_norm_palavra_chave` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) NOT NULL,
  PRIMARY KEY (`id_norm_palavra_chave`),
  UNIQUE KEY `UK_sbihc9v3qt2lv7p3otxi7jjyd` (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `norm_palavra_chave`
--

LOCK TABLES `norm_palavra_chave` WRITE;
/*!40000 ALTER TABLE `norm_palavra_chave` DISABLE KEYS */;
/*!40000 ALTER TABLE `norm_palavra_chave` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `norm_requisito`
--

DROP TABLE IF EXISTS `norm_requisito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `norm_requisito` (
  `id_norm_requisito` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_alerta` date DEFAULT NULL,
  `data_cadastro` date DEFAULT NULL,
  `data_validade` date DEFAULT NULL,
  `descricao` varchar(200) NOT NULL,
  `path_file` varchar(200) DEFAULT NULL,
  `resposta` varchar(200) DEFAULT NULL,
  `secao_atendida` varchar(200) DEFAULT NULL,
  `id_filial` bigint(20) DEFAULT NULL,
  `id_legislacao` bigint(20) DEFAULT NULL,
  `id_responsavel` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_norm_requisito`),
  KEY `FK_5lb6ifu1qc2o4pbk3aa7iov4i` (`id_filial`),
  KEY `FK_6m70cuhv9kx52uj6caeipldef` (`id_legislacao`),
  KEY `FK_b5ck920y2v7syghv8bpkkedur` (`id_responsavel`),
  CONSTRAINT `FK_b5ck920y2v7syghv8bpkkedur` FOREIGN KEY (`id_responsavel`) REFERENCES `acad_responsavel` (`id_responsavel`),
  CONSTRAINT `FK_5lb6ifu1qc2o4pbk3aa7iov4i` FOREIGN KEY (`id_filial`) REFERENCES `norm_filial` (`id_norm_filial`),
  CONSTRAINT `FK_6m70cuhv9kx52uj6caeipldef` FOREIGN KEY (`id_legislacao`) REFERENCES `norm_legislacao` (`id_norm_legislacao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `norm_requisito`
--

LOCK TABLES `norm_requisito` WRITE;
/*!40000 ALTER TABLE `norm_requisito` DISABLE KEYS */;
/*!40000 ALTER TABLE `norm_requisito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `norm_responsavel`
--

DROP TABLE IF EXISTS `norm_responsavel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `norm_responsavel` (
  `id_norm_requisito` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(30) NOT NULL,
  PRIMARY KEY (`id_norm_requisito`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `norm_responsavel`
--

LOCK TABLES `norm_responsavel` WRITE;
/*!40000 ALTER TABLE `norm_responsavel` DISABLE KEYS */;
/*!40000 ALTER TABLE `norm_responsavel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `norm_tipo`
--

DROP TABLE IF EXISTS `norm_tipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `norm_tipo` (
  `id_norm_tipo` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(30) NOT NULL,
  PRIMARY KEY (`id_norm_tipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `norm_tipo`
--

LOCK TABLES `norm_tipo` WRITE;
/*!40000 ALTER TABLE `norm_tipo` DISABLE KEYS */;
/*!40000 ALTER TABLE `norm_tipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `norm_tipo_documento`
--

DROP TABLE IF EXISTS `norm_tipo_documento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `norm_tipo_documento` (
  `id_norm_tipo_documento` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) NOT NULL,
  PRIMARY KEY (`id_norm_tipo_documento`),
  UNIQUE KEY `UK_d6cg6d1cqsnd1ipirrncvkypa` (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `norm_tipo_documento`
--

LOCK TABLES `norm_tipo_documento` WRITE;
/*!40000 ALTER TABLE `norm_tipo_documento` DISABLE KEYS */;
/*!40000 ALTER TABLE `norm_tipo_documento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saa_empresa`
--

DROP TABLE IF EXISTS `saa_empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `saa_empresa` (
  `id_empresa` bigint(20) NOT NULL AUTO_INCREMENT,
  `bairro` varchar(60) DEFAULT NULL,
  `cnpj` varchar(14) NOT NULL,
  `email` varchar(60) DEFAULT NULL,
  `endereco` varchar(60) DEFAULT NULL,
  `nome` varchar(50) NOT NULL,
  `celular` varchar(10) DEFAULT NULL,
  `fone` varchar(10) DEFAULT NULL,
  `razao_social` varchar(60) DEFAULT NULL,
  `nome_fantasia` varchar(45) NOT NULL,
  PRIMARY KEY (`id_empresa`),
  UNIQUE KEY `UK_80r8bcl23aucmghxjqt8ckfcc` (`cnpj`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saa_empresa`
--

LOCK TABLES `saa_empresa` WRITE;
/*!40000 ALTER TABLE `saa_empresa` DISABLE KEYS */;
INSERT INTO `saa_empresa` VALUES (1,'cidade nova','10557321000145','sge@gmail.com','Rua 27','CENTRO EDUCACIONAL SHEKINAH','2121212121','212121',NULL,'CENTRO EDUCACIONAL SHEKINAH');
/*!40000 ALTER TABLE `saa_empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saa_perfil`
--

DROP TABLE IF EXISTS `saa_perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `saa_perfil` (
  `id_perfil` bigint(20) NOT NULL AUTO_INCREMENT,
  `imagem` varchar(60) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `id_sistema` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_perfil`),
  KEY `FK_4hy4snh41hywvhfpa8omdlnqm` (`id_sistema`),
  CONSTRAINT `FK_4hy4snh41hywvhfpa8omdlnqm` FOREIGN KEY (`id_sistema`) REFERENCES `saa_sistema` (`id_sistema`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saa_perfil`
--

LOCK TABLES `saa_perfil` WRITE;
/*!40000 ALTER TABLE `saa_perfil` DISABLE KEYS */;
INSERT INTO `saa_perfil` VALUES (1,'resources/imagens/perfil/secretaria.png','SECRETARIA',2),(2,'resources/imagens/perfil/tesouraria.png','FINANCEIRO',2),(3,'resources/imagens/perfil/administrativo.png','ADMINISTRATIVO',2),(4,'resources/imagens/perfil/configuracao.png','CONFIGURAÇÃO',2);
/*!40000 ALTER TABLE `saa_perfil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saa_perfil_rotina`
--

DROP TABLE IF EXISTS `saa_perfil_rotina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `saa_perfil_rotina` (
  `data` datetime DEFAULT NULL,
  `id_perfil` bigint(20) NOT NULL DEFAULT '0',
  `id_rotina` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_perfil`,`id_rotina`),
  KEY `FK_kh4c63uwlsyajnqj66sv6btu5` (`id_perfil`),
  KEY `FK_4ey8g8nxrvbs411mm26qda1bf` (`id_rotina`),
  CONSTRAINT `FK_4ey8g8nxrvbs411mm26qda1bf` FOREIGN KEY (`id_rotina`) REFERENCES `saa_rotina` (`id_rotina`),
  CONSTRAINT `FK_kh4c63uwlsyajnqj66sv6btu5` FOREIGN KEY (`id_perfil`) REFERENCES `saa_perfil` (`id_perfil`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saa_perfil_rotina`
--

LOCK TABLES `saa_perfil_rotina` WRITE;
/*!40000 ALTER TABLE `saa_perfil_rotina` DISABLE KEYS */;
INSERT INTO `saa_perfil_rotina` VALUES (NULL,1,1),(NULL,1,2),(NULL,1,3),(NULL,1,4),(NULL,1,5),(NULL,1,9),(NULL,1,10),('2014-06-17 16:56:21',1,12),(NULL,2,5),(NULL,2,11),(NULL,4,6),(NULL,4,7),(NULL,4,8),('2014-07-24 19:01:43',4,13),('2014-11-02 09:04:41',4,14),('2014-11-02 12:36:52',4,15);
/*!40000 ALTER TABLE `saa_perfil_rotina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saa_rotina`
--

DROP TABLE IF EXISTS `saa_rotina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `saa_rotina` (
  `id_rotina` bigint(20) NOT NULL AUTO_INCREMENT,
  `acao` varchar(100) NOT NULL,
  `imagem` varchar(100) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `status` tinyint(4) NOT NULL,
  `id_sistema` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_rotina`),
  KEY `FK_54mvu1oxkbqkwg0ifkj9867ar` (`id_sistema`),
  CONSTRAINT `FK_54mvu1oxkbqkwg0ifkj9867ar` FOREIGN KEY (`id_sistema`) REFERENCES `saa_sistema` (`id_sistema`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saa_rotina`
--

LOCK TABLES `saa_rotina` WRITE;
/*!40000 ALTER TABLE `saa_rotina` DISABLE KEYS */;
INSERT INTO `saa_rotina` VALUES (1,'/paginas/aluno/pesquisa.xhtml','/resources/imagens/rotina/aluno.png','ALUNO',0,2),(2,'/paginas/professor/pesquisa.xhtml','/resources/imagens/rotina/professor.png','PROFESSOR',0,2),(3,'/paginas/responsavel/pesquisa.xhtml','/resources/imagens/rotina/responsavel.png','RESPONSAVEL',0,2),(4,'/paginas/curso/pesquisa.xhtml','/resources/imagens/rotina/curso.png','CURSO',0,2),(5,'/paginas/matricula/pesquisa.xhtml','/resources/imagens/rotina/turma.png','MATRICULA',0,2),(6,'/paginas/rotina/pesquisa.xhtml','/resources/imagens/rotina/rotina.png','ROTINAS',0,2),(7,'/paginas/perfil/pesquisa.xhtml','/resources/imagens/rotina/perfil.png','PERFIL',0,2),(8,'/paginas/usuario/pesquisa.xhtml','/resources/imagens/rotina/usuario.png','USUARIO',0,2),(9,'/paginas/disciplina/pesquisa.xhtml','/resources/imagens/rotina/disciplina.png','DISCIPLINA',0,2),(10,'/paginas/turma/pesquisa.xhtml','/resources/imagens/rotina/turma.png','TURMA',0,2),(11,'/paginas/mensalidade/pesquisa.xhtml','/resources/imagens/rotina/curso.png','MENSALIDADE',0,2),(12,'/paginas/boletim/pesquisa.xhtml','/resources/imagens/rotina/curso.png','Boletim',0,2),(13,'/paginas/empresa/pesquisa.xhtml','/paginas/empresa/pesquisa.xhtml','Empresa',0,NULL),(14,'/paginas/emissor/pesquisa.xhtml','/paginas/aluno/pesquisa.xhtml','Emissor',0,NULL),(15,'/paginas/michell/pesquisa.xhtml','/paginas/aluno/pesquisa.xhtml','Michell',0,NULL);
/*!40000 ALTER TABLE `saa_rotina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saa_sistema`
--

DROP TABLE IF EXISTS `saa_sistema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `saa_sistema` (
  `id_sistema` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(6) NOT NULL,
  `descricao` varchar(200) NOT NULL,
  `imagem` varchar(100) NOT NULL,
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY (`id_sistema`),
  UNIQUE KEY `UK_ghac1kmyt6ytqt8bxwmxy0dfb` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saa_sistema`
--

LOCK TABLES `saa_sistema` WRITE;
/*!40000 ALTER TABLE `saa_sistema` DISABLE KEYS */;
INSERT INTO `saa_sistema` VALUES (1,'SAA','SISTEMA DE AUTENTICAÇÃO E AUTORIZAÇÃO','WWWW','SISTEMA DE AUTENTICAÇÃO E AUTORIZAÇÃO'),(2,'SGE','SISTEMA DE GERENCIAMENTO ESCOLAR','WWWW','SISTEMA DE GERENCIAMENTO ESCOLAR'),(3,'SISTST','Sistema Teste','Sistema Teste','Sistema Teste');
/*!40000 ALTER TABLE `saa_sistema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saa_usuario`
--

DROP TABLE IF EXISTS `saa_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `saa_usuario` (
  `id_usuario` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(30) DEFAULT NULL,
  `nome` varchar(30) NOT NULL,
  `senha` varchar(64) DEFAULT NULL,
  `status` smallint(5) unsigned NOT NULL,
  `tipo_usuario` int(11) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `UK_ckrgsi99ta2s36tkydejud4wy` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saa_usuario`
--

LOCK TABLES `saa_usuario` WRITE;
/*!40000 ALTER TABLE `saa_usuario` DISABLE KEYS */;
INSERT INTO `saa_usuario` VALUES (1,'admin','ADMINISTRADOR','8C6976E5B5410415BDE908BD4DEE15DFB167A9C873FC4BB8A81F6F2AB448A918',0,0),(2,'Usuario Teste','Usuario Teste','46070D4BF934FB0D4B06D9E2C46E346944E322444900A435D7D9A95E6D7435F5',0,0);
/*!40000 ALTER TABLE `saa_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saa_usuario_perfil`
--

DROP TABLE IF EXISTS `saa_usuario_perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `saa_usuario_perfil` (
  `data` datetime DEFAULT NULL,
  `id_perfil` bigint(20) NOT NULL DEFAULT '0',
  `id_usuario` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_perfil`,`id_usuario`),
  KEY `FK_fpjsxglutlk7m5aho59hito8q` (`id_perfil`),
  KEY `FK_ooqm83bohskwfg89ng9g3kgra` (`id_usuario`),
  CONSTRAINT `FK_fpjsxglutlk7m5aho59hito8q` FOREIGN KEY (`id_perfil`) REFERENCES `saa_perfil` (`id_perfil`),
  CONSTRAINT `FK_ooqm83bohskwfg89ng9g3kgra` FOREIGN KEY (`id_usuario`) REFERENCES `saa_usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saa_usuario_perfil`
--

LOCK TABLES `saa_usuario_perfil` WRITE;
/*!40000 ALTER TABLE `saa_usuario_perfil` DISABLE KEYS */;
INSERT INTO `saa_usuario_perfil` VALUES (NULL,1,1),('2014-07-24 20:06:36',1,2),(NULL,2,1),('2014-10-19 15:17:51',3,1),(NULL,4,1);
/*!40000 ALTER TABLE `saa_usuario_perfil` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-11-02 13:54:46
