-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.6.15-log


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema sge
--

CREATE DATABASE IF NOT EXISTS sge;
USE sge;

--
-- Definition of table `acad_aluno`
--

DROP TABLE IF EXISTS `acad_aluno`;
CREATE TABLE `acad_aluno` (
  `id_aluno` bigint(20) NOT NULL AUTO_INCREMENT,
  `bairro` varchar(60) NOT NULL,
  `celular` varchar(9) DEFAULT NULL,
  `cep` varchar(9) NOT NULL,
  `cpf` varchar(14) DEFAULT NULL,
  `data_cadastro` datetime NOT NULL,
  `data_nascimento` datetime DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `endereco` varchar(60) NOT NULL,
  `fone_comercial` varchar(9) DEFAULT NULL,
  `fone_residencial` varchar(9) DEFAULT NULL,
  `nome` varchar(60) NOT NULL,
  `rg` varchar(10) NOT NULL,
  `sexo` smallint(1) unsigned NOT NULL,
  `grau_parentesco` smallint(1) unsigned NOT NULL,
  `id_usuario` bigint(20) DEFAULT NULL,
  `id_responsavel` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_aluno`),
  KEY `FK_pb7agtmuos3c5in2fv769qiey` (`id_usuario`),
  KEY `FK_1ofh34p98750kva4x143lrqjd` (`id_responsavel`),
  CONSTRAINT `FK_1ofh34p98750kva4x143lrqjd` FOREIGN KEY (`id_responsavel`) REFERENCES `acad_responsavel` (`id_responsavel`),
  CONSTRAINT `FK_pb7agtmuos3c5in2fv769qiey` FOREIGN KEY (`id_usuario`) REFERENCES `saa_usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acad_aluno`
--

--
-- Definition of table `acad_boletim`
--

DROP TABLE IF EXISTS `acad_boletim`;
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
  PRIMARY KEY (`id_boletim`),
  KEY `FK_p8d9kggflelbutgso6bn9mpek` (`id_disciplina`),
  KEY `FK_mq5q6rbkuuuum3poa3ojvag6u` (`id_matricula`),
  CONSTRAINT `FK_mq5q6rbkuuuum3poa3ojvag6u` FOREIGN KEY (`id_matricula`) REFERENCES `acad_matricula` (`id_matricula`),
  CONSTRAINT `FK_p8d9kggflelbutgso6bn9mpek` FOREIGN KEY (`id_disciplina`) REFERENCES `acad_disciplina` (`id_disciplina`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acad_boletim`
--

/*!40000 ALTER TABLE `acad_boletim` DISABLE KEYS */;
/*!40000 ALTER TABLE `acad_boletim` ENABLE KEYS */;


--
-- Definition of table `acad_configuracao`
--

DROP TABLE IF EXISTS `acad_configuracao`;
CREATE TABLE `acad_configuracao` (
  `id_configuracao` bigint(20) NOT NULL AUTO_INCREMENT,
  `dia_vencimento` int(11) NOT NULL,
  `tema` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_configuracao`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acad_configuracao`
--

/*!40000 ALTER TABLE `acad_configuracao` DISABLE KEYS */;
INSERT INTO `acad_configuracao` (`id_configuracao`,`dia_vencimento`,`tema`) VALUES 
 (1,10,'start');
/*!40000 ALTER TABLE `acad_configuracao` ENABLE KEYS */;


--
-- Definition of table `acad_curso`
--

DROP TABLE IF EXISTS `acad_curso`;
CREATE TABLE `acad_curso` (
  `id_curso` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `valor` double NOT NULL,
  PRIMARY KEY (`id_curso`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acad_curso`
--

/*!40000 ALTER TABLE `acad_curso` DISABLE KEYS */;
INSERT INTO `acad_curso` (`id_curso`,`nome`,`valor`) VALUES 
 (1,'1º ANO',1500),
 (2,'2º ANO',1600),
 (3,'3º Ano',1700),
 (4,'4º Ano',1800),
 (5,'5º Ano',1900);
/*!40000 ALTER TABLE `acad_curso` ENABLE KEYS */;


--
-- Definition of table `acad_curso_disciplina`
--

DROP TABLE IF EXISTS `acad_curso_disciplina`;
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

--
-- Dumping data for table `acad_curso_disciplina`
--

/*!40000 ALTER TABLE `acad_curso_disciplina` DISABLE KEYS */;
/*!40000 ALTER TABLE `acad_curso_disciplina` ENABLE KEYS */;


--
-- Definition of table `acad_disciplina`
--

DROP TABLE IF EXISTS `acad_disciplina`;
CREATE TABLE `acad_disciplina` (
  `id_disciplina` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(100) DEFAULT NULL,
  `nome` varchar(50) NOT NULL,
  PRIMARY KEY (`id_disciplina`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acad_disciplina`
--

/*!40000 ALTER TABLE `acad_disciplina` DISABLE KEYS */;
INSERT INTO `acad_disciplina` (`id_disciplina`,`descricao`,`nome`) VALUES 
 (1,'PORTUGUÊS','PORTUGUÊS'),
 (2,'MATEMÁTICA','MATEMÁTICA'),
 (3,'CIÊNCIAS','CIÊNCIAS'),
 (4,'INGLÊS','INGLÊS'),
 (5,'INFORMÁTICA','INFORMÁTICA');
/*!40000 ALTER TABLE `acad_disciplina` ENABLE KEYS */;

--
-- Definition of table `acad_matricula`
--

DROP TABLE IF EXISTS `acad_matricula`;
CREATE TABLE `acad_matricula` (
  `id_matricula` bigint(20) NOT NULL AUTO_INCREMENT,
  `data` datetime DEFAULT NULL,
  `status` varchar(1) NOT NULL,
  `valor` double NOT NULL,
  `id_aluno` bigint(20) NOT NULL,
  `id_turma` bigint(20) NOT NULL,
  `integral` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_matricula`),
  KEY `FK_psjpgyxt6vvv2jfsfii1jvpa6` (`id_aluno`),
  KEY `FK_4fr9y18xpqymx6wyobq1ixdn7` (`id_turma`),
  CONSTRAINT `FK_4fr9y18xpqymx6wyobq1ixdn7` FOREIGN KEY (`id_turma`) REFERENCES `acad_turma` (`id_turma`),
  CONSTRAINT `FK_psjpgyxt6vvv2jfsfii1jvpa6` FOREIGN KEY (`id_aluno`) REFERENCES `acad_aluno` (`id_aluno`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acad_matricula`
--

--
-- Definition of table `acad_mensalidade`
--

DROP TABLE IF EXISTS `acad_mensalidade`;
CREATE TABLE `acad_mensalidade` (
  `id_mensalidade` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_pagamento` date DEFAULT NULL,
  `data_vencimento` date DEFAULT NULL,
  `sequencial` int(11) NOT NULL,
  `valor_pagamento` double DEFAULT NULL,
  `valor_vencimento` double NOT NULL,
  `id_matricula` bigint(20) NOT NULL,
  `id_usuario` bigint(20) DEFAULT NULL,
  `status_pagamento` int(11) NOT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_mensalidade`),
  KEY `FK_5ohqk2wplxluosa2ixutna7jk` (`id_matricula`),
  KEY `FK_1r9uok0uhbqsqkxiwqiycls8h` (`id_usuario`),
  CONSTRAINT `FK_1r9uok0uhbqsqkxiwqiycls8h` FOREIGN KEY (`id_usuario`) REFERENCES `saa_usuario` (`id_usuario`),
  CONSTRAINT `FK_5ohqk2wplxluosa2ixutna7jk` FOREIGN KEY (`id_matricula`) REFERENCES `acad_matricula` (`id_matricula`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acad_mensalidade`
--


--
-- Definition of table `acad_observacao`
--

DROP TABLE IF EXISTS `acad_observacao`;
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

--
-- Dumping data for table `acad_observacao`
--

/*!40000 ALTER TABLE `acad_observacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `acad_observacao` ENABLE KEYS */;


--
-- Definition of table `acad_professor`
--

DROP TABLE IF EXISTS `acad_professor`;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `acad_professor`
--


/*!40000 ALTER TABLE `acad_professor` DISABLE KEYS */;
INSERT INTO `acad_professor` (`id_professor`,`bairro`,`celular`,`cep`,`cpf`,`data_cadastro`,`data_nascimento`,`email`,`endereco`,`fone_comercial`,`fone_residencial`,`nome`,`rg`,`sexo`) VALUES 
 (1,'ssss','','12345-678','661.065.162-00','2014-06-25 18:45:00','2014-05-01 00:00:00',NULL,'sssssssss','','','Ana Maria','11111111-1',1);
/*!40000 ALTER TABLE `acad_professor` ENABLE KEYS */;

/*!40000 ALTER TABLE `acad_professor` DISABLE KEYS */;
/*!40000 ALTER TABLE `acad_professor` ENABLE KEYS */;


--
-- Definition of table `acad_responsavel`
--

DROP TABLE IF EXISTS `acad_responsavel`;
CREATE TABLE `acad_responsavel` (
  `id_responsavel` bigint(20) NOT NULL AUTO_INCREMENT,
  `bairro` varchar(60) NOT NULL,
  `celular` varchar(9) DEFAULT NULL,
  `cep` varchar(9) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `data_nascimento` datetime DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `endereco` varchar(60) NOT NULL,
  `fone_comercial` varchar(9) DEFAULT NULL,
  `fone_residencial` varchar(9) DEFAULT NULL,
  `nome` varchar(60) NOT NULL,
  `profissao` varchar(30) NOT NULL,
  `rg` varchar(10) NOT NULL,
  `sexo` smallint(1) unsigned NOT NULL,
  PRIMARY KEY (`id_responsavel`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acad_responsavel`
--

/*!40000 ALTER TABLE `acad_responsavel` DISABLE KEYS */;
INSERT INTO `acad_responsavel` (`id_responsavel`,`bairro`,`celular`,`cep`,`cpf`,`data_nascimento`,`email`,`endereco`,`fone_comercial`,`fone_residencial`,`nome`,`profissao`,`rg`,`sexo`) VALUES 
 (1,'YYY','4324-3242','','541.223.421-63','1980-01-10 00:00:00','email@sss.ss','WWWW','','','CLAUDEMIR RAMOS FEREIRA','EWQEQW','11111111-1',0),
 (2,'dfdsfs','','','688.435.473-99','2014-04-01 00:00:00',NULL,'sfd','','','Robson Ramos Ferreira','Analista de Sistemas','21321312-3',0),
 (3,'adf fd fasf a','6666-6666','66666-666','189.750.981-25','1980-03-15 00:00:00','raraa@sdaf.sd','sdfa afafda  ','6666-6666','6666-6666','Rangel Ramos Ferreira','Estudante','66666666-6',0);
/*!40000 ALTER TABLE `acad_responsavel` ENABLE KEYS */;

--
-- Definition of table `acad_turma`
--

DROP TABLE IF EXISTS `acad_turma`;
CREATE TABLE `acad_turma` (
  `id_turma` bigint(20) NOT NULL AUTO_INCREMENT,
  `ano` int(11) NOT NULL,
  `turno` varchar(1) NOT NULL,
  `id_curso` bigint(20) NOT NULL,
  `numero_vagas` int(11) NOT NULL,
  `situacao` smallint(1) NOT NULL,
  PRIMARY KEY (`id_turma`),
  KEY `FK_2qed6ismnk8dlxohh3ie29tb5` (`id_curso`),
  CONSTRAINT `FK_2qed6ismnk8dlxohh3ie29tb5` FOREIGN KEY (`id_curso`) REFERENCES `acad_curso` (`id_curso`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acad_turma`
--

/*!40000 ALTER TABLE `acad_turma` DISABLE KEYS */;
INSERT INTO `acad_turma` (`id_turma`,`ano`,`turno`,`id_curso`,`numero_vagas`,`situacao`) VALUES 
 (1,2015,'0',1,30,0),
 (2,2015,'1',2,20,0),
 (3,2014,'2',1,5,0),
 (4,2013,'1',2,20,1);
/*!40000 ALTER TABLE `acad_turma` ENABLE KEYS */;

--
-- Definition of table `acad_turma_disciplina`
--

DROP TABLE IF EXISTS `acad_turma_disciplina`;
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

--
-- Dumping data for table `acad_turma_disciplina`
--

/*!40000 ALTER TABLE `acad_turma_disciplina` DISABLE KEYS */;
/*!40000 ALTER TABLE `acad_turma_disciplina` ENABLE KEYS */;


--
-- Definition of table `saa_empresa`
--

DROP TABLE IF EXISTS `saa_empresa`;
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

--
-- Dumping data for table `saa_empresa`
--

/*!40000 ALTER TABLE `saa_empresa` DISABLE KEYS */;
INSERT INTO `saa_empresa` (`id_empresa`,`bairro`,`cnpj`,`email`,`endereco`,`nome`,`celular`,`fone`,`razao_social`,`nome_fantasia`) VALUES 
-- (1,'Cidade Nova 2','12345678912345',NULL,'Rua XXXX, YY - ZZZZZZZ','NOME_DA_EMPRESA',NULL,NULL,NULL,'NOME_FANTASIA_DA_EMPRESA');
 (1,'cidade nova','10557321000145',NULL,'Rua 27','CENTRO EDUCACIONAL SHEKINAH',NULL,NULL, NULL, 'CENTRO EDUCACIONAL SHEKINAH');	-- #### FIXME remover
/*!40000 ALTER TABLE `saa_empresa` ENABLE KEYS */;


--
-- Definition of table `saa_perfil`
--

DROP TABLE IF EXISTS `saa_perfil`;
CREATE TABLE `saa_perfil` (
  `id_perfil` bigint(20) NOT NULL AUTO_INCREMENT,
  `imagem` varchar(60),
  `nome` varchar(30) NOT NULL,
  `id_sistema` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_perfil`),
  KEY `FK_4hy4snh41hywvhfpa8omdlnqm` (`id_sistema`),
  CONSTRAINT `FK_4hy4snh41hywvhfpa8omdlnqm` FOREIGN KEY (`id_sistema`) REFERENCES `saa_sistema` (`id_sistema`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `saa_perfil`
--

/*!40000 ALTER TABLE `saa_perfil` DISABLE KEYS */;
INSERT INTO `saa_perfil` (`id_perfil`,`imagem`,`nome`,`id_sistema`) VALUES 
 (1,'resources/imagens/perfil/administrativo.png','Secretaria',2),
 (2,'resources/imagens/perfil/administrativo.png','Financeiro',2),
 (3,'resources/imagens/perfil/configuracao.png','Segurança',2);
/*!40000 ALTER TABLE `saa_perfil` ENABLE KEYS */;


--
-- Definition of table `saa_perfil_rotina`
--

DROP TABLE IF EXISTS `saa_perfil_rotina`;
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

--
-- Dumping data for table `saa_perfil_rotina`
--

/*!40000 ALTER TABLE `saa_perfil_rotina` DISABLE KEYS */;
/*
FIXME
 */
INSERT INTO `saa_perfil_rotina` (`data`,`id_perfil`,`id_rotina`) VALUES
 /* Segurança */
 (NULL,3,1),
 (NULL,3,3),
 (NULL,3,13),
 
 /* Secretaria */
 (NULL,1,4),
 (NULL,1,5),
 (NULL,1,6),
 (NULL,1,7),
 (NULL,1,8),
 (NULL,1,10),
 (NULL,1,11),
 (NULL,1,12),
 
 /* Financeiro */
 (NULL,2,9); /* mensalidade */
 

/*!40000 ALTER TABLE `saa_perfil_rotina` ENABLE KEYS */;

--
-- Definition of table `saa_rotina`
--

DROP TABLE IF EXISTS `saa_rotina`;
CREATE TABLE `saa_rotina` (
  `id_rotina` bigint(20) NOT NULL AUTO_INCREMENT,
  `acao` varchar(100) NOT NULL,
  `imagem` varchar(60),
  `nome` varchar(100) NOT NULL,
  `status` tinyint(4) NOT NULL,
  `id_sistema` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_rotina`),
  KEY `FK_54mvu1oxkbqkwg0ifkj9867ar` (`id_sistema`),
  CONSTRAINT `FK_54mvu1oxkbqkwg0ifkj9867ar` FOREIGN KEY (`id_sistema`) REFERENCES `saa_sistema` (`id_sistema`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `saa_rotina`
--

/*!40000 ALTER TABLE `saa_rotina` DISABLE KEYS */;
INSERT INTO `saa_rotina` (`id_rotina`,`acao`,`imagem`,`nome`,`status`,`id_sistema`) VALUES 
 
 /*----- SEGURANÇA -------- */
 (1,'/paginas/perfil/pesquisa.xhtml','/resources/imagens/rotina/perfil.png','Perfil',0,2),
 (2,'/paginas/rotina/pesquisa.xhtml','/resources/imagens/rotina/rotina.png','Rotina',0,2),
 (3,'/paginas/usuario/pesquisa.xhtml','/resources/imagens/rotina/usuario.png','Usuário',0,2),
 
  /*----- SGE -------- */
 (4,'/paginas/aluno/pesquisa.xhtml','/resources/imagens/rotina/aluno.png','Aluno',0,2),
 (5,'/paginas/boletim/pesquisa.xhtml','/resources/imagens/rotina/curso.png','Boletim',0,2),
 (6,'/paginas/curso/pesquisa.xhtml','/resources/imagens/rotina/curso.png','Curso',0,2),
 (7,'/paginas/disciplina/pesquisa.xhtml','/resources/imagens/rotina/disciplina.png','Disciplina',0,2),
 (8,'/paginas/matricula/pesquisa.xhtml','/resources/imagens/rotina/turma.png','Matrícula',0,2),
 (9,'/paginas/mensalidade/pesquisa.xhtml','/resources/imagens/rotina/curso.png','Mensalidade',0,2),
 (10,'/paginas/professor/pesquisa.xhtml','/resources/imagens/rotina/professor.png','Professor',0,2),
 (11,'/paginas/responsavel/pesquisa.xhtml','/resources/imagens/rotina/responsavel.png','Responsavel',0,2),
 (12,'/paginas/turma/pesquisa.xhtml','/resources/imagens/rotina/turma.png','Turma',0,2),
 (13,'/paginas/configuracao/configuracao.xhtml','/resources/imagens/rotina/configuracao.png','Configuração',0,2);
/*!40000 ALTER TABLE `saa_rotina` ENABLE KEYS */;


--
-- Definition of table `saa_sistema`
--

DROP TABLE IF EXISTS `saa_sistema`;
CREATE TABLE `saa_sistema` (
  `id_sistema` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(6) NOT NULL,
  `descricao` varchar(200) NOT NULL,
  `imagem` varchar(100) NOT NULL,
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY (`id_sistema`),
  UNIQUE KEY `UK_ghac1kmyt6ytqt8bxwmxy0dfb` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `saa_sistema`
--

/*!40000 ALTER TABLE `saa_sistema` DISABLE KEYS */;
INSERT INTO `saa_sistema` (`id_sistema`,`codigo`,`descricao`,`imagem`,`nome`) VALUES 
 (1,'SAA','SISTEMA DE AUTENTICAÇÃO E AUTORIZAÇÃO','#','SISTEMA DE AUTENTICAÇÃO E AUTORIZAÇÃO'),
 (2,'SGE','SISTEMA DE GERENCIAMENTO ESCOLAR','#','SISTEMA DE GERENCIAMENTO ESCOLAR');
/*!40000 ALTER TABLE `saa_sistema` ENABLE KEYS */;


--
-- Definition of table `saa_usuario`
--

DROP TABLE IF EXISTS `saa_usuario`;
CREATE TABLE `saa_usuario` (
  `id_usuario` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(30) DEFAULT NULL,
  `nome` varchar(30) NOT NULL,
  `senha` varchar(64) DEFAULT NULL,
  `status` smallint(5) unsigned NOT NULL,
  `tipo_usuario` smallint(5) unsigned NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `UK_ckrgsi99ta2s36tkydejud4wy` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `saa_usuario`
--

/*!40000 ALTER TABLE `saa_usuario` DISABLE KEYS */;
INSERT INTO `saa_usuario` (`id_usuario`,`login`,`nome`,`senha`,`status`,`tipo_usuario`) VALUES 
 /* master= 123qwe */ 
 (1,'master','Master','FBFB386EFEA67E816F2DDA0A8C94A98EB203757AEBB3F55F183755A192D44467',0, 0),
 /* admin= admin */ 
 (2,'admin','Administrador','8C6976E5B5410415BDE908BD4DEE15DFB167A9C873FC4BB8A81F6F2AB448A918',0, 1),
 /* secretaria e professor: 123456 = 8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92*/
 (3,'secretaria','Secretaria - Paula','8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92',0, 2),
 (4,'professor','Professora - Ana Maria','8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92',0, 3);
 
/*!40000 ALTER TABLE `saa_usuario` ENABLE KEYS */;


--
-- Definition of table `saa_usuario_perfil`
--

DROP TABLE IF EXISTS `saa_usuario_perfil`;
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

--
-- Dumping data for table `saa_usuario_perfil`
--

/*!40000 ALTER TABLE `saa_usuario_perfil` DISABLE KEYS */;
INSERT INTO `saa_usuario_perfil` (`data`,`id_perfil`,`id_usuario`) VALUES 
 (NULL,1,1),
 (NULL,2,1),
 (NULL,3,1),
 (NULL,1,2),
 (NULL,2,2),
 (NULL,3,2),
 (NULL,1,3),
 (NULL,2,3),
 (NULL,1,4);
/*!40000 ALTER TABLE `saa_usuario_perfil` ENABLE KEYS */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
