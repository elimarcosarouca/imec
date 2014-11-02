-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.5.36


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema shekinah
--

CREATE DATABASE IF NOT EXISTS shekinah;
USE shekinah;

--
-- Definition of table `acad_aluno`
--

DROP TABLE IF EXISTS `acad_aluno`;
CREATE TABLE `acad_aluno` (
  `id_aluno` bigint(20) NOT NULL AUTO_INCREMENT,
  `bairro` varchar(60) NOT NULL,
  `celular` varchar(8) DEFAULT NULL,
  `cep` varchar(8) NOT NULL,
  `cpf` varchar(11) NOT NULL,
  `data_cadastro` datetime NOT NULL,
  `data_nascimento` datetime DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `endereco` varchar(60) NOT NULL,
  `fone_comercial` varchar(8) DEFAULT NULL,
  `fone_residencial` varchar(8) DEFAULT NULL,
  `nome` varchar(60) NOT NULL,
  `rg` varchar(11) NOT NULL,
  `sexo` varchar(1) NOT NULL,
  `grau_parentesco` varchar(11) NOT NULL,
  `id_usuario` bigint(20) DEFAULT NULL,
  `id_responsavel` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_aluno`),
  KEY `FK_pb7agtmuos3c5in2fv769qiey` (`id_usuario`),
  KEY `FK_1ofh34p98750kva4x143lrqjd` (`id_responsavel`),
  CONSTRAINT `FK_1ofh34p98750kva4x143lrqjd` FOREIGN KEY (`id_responsavel`) REFERENCES `acad_responsavel` (`id_responsavel`),
  CONSTRAINT `FK_pb7agtmuos3c5in2fv769qiey` FOREIGN KEY (`id_usuario`) REFERENCES `saa_usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acad_aluno`
--

/*!40000 ALTER TABLE `acad_aluno` DISABLE KEYS */;
INSERT INTO `acad_aluno` (`id_aluno`,`bairro`,`celular`,`cep`,`cpf`,`data_cadastro`,`data_nascimento`,`email`,`endereco`,`fone_comercial`,`fone_residencial`,`nome`,`rg`,`sexo`,`grau_parentesco`,`id_usuario`,`id_responsavel`) VALUES 
 (1,'YYY','43243242','434343','7878777','2014-02-26 14:54:30',NULL,'EEWEWE','WWWW',NULL,'RWQERWQR','CLAUDEMIR RAMOS FERREIRA','6666','M','',NULL,NULL),
 (2,'43242','43243242','9999','9999','2014-02-27 17:35:48','2014-02-02 00:00:00','EEWEWE','WWWW',NULL,'3232','ROBSON RAMOS FERREIRA','999','M','',NULL,1);
/*!40000 ALTER TABLE `acad_aluno` ENABLE KEYS */;


--
-- Definition of table `acad_boletim`
--

DROP TABLE IF EXISTS `acad_boletim`;
CREATE TABLE `acad_boletim` (
  `id_boletim` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id_boletim`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acad_boletim`
--

/*!40000 ALTER TABLE `acad_boletim` DISABLE KEYS */;
/*!40000 ALTER TABLE `acad_boletim` ENABLE KEYS */;


--
-- Definition of table `acad_curso`
--

DROP TABLE IF EXISTS `acad_curso`;
CREATE TABLE `acad_curso` (
  `id_curso` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `valor` double NOT NULL,
  PRIMARY KEY (`id_curso`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acad_curso`
--

/*!40000 ALTER TABLE `acad_curso` DISABLE KEYS */;
INSERT INTO `acad_curso` (`id_curso`,`nome`,`valor`) VALUES 
 (1,'1 ANO',150);
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
 (1,'OIIIIIIIIIIIIIIIIII','MATEMÉTICA');
/*!40000 ALTER TABLE `acad_disciplina` ENABLE KEYS */;


--
-- Definition of table `acad_empresa`
--

DROP TABLE IF EXISTS `acad_empresa`;
CREATE TABLE `acad_empresa` (
  `id_empresa` bigint(20) NOT NULL AUTO_INCREMENT,
  `bairro` varchar(60) DEFAULT NULL,
  `cnpj` varchar(14) NOT NULL,
  `email` varchar(60) DEFAULT NULL,
  `endereco` varchar(60) DEFAULT NULL,
  `nome` varchar(50) NOT NULL,
  `celular` varchar(10) DEFAULT NULL,
  `fone` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id_empresa`),
  UNIQUE KEY `UK_80r8bcl23aucmghxjqt8ckfcc` (`cnpj`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acad_empresa`
--

/*!40000 ALTER TABLE `acad_empresa` DISABLE KEYS */;
INSERT INTO `acad_empresa` (`id_empresa`,`bairro`,`cnpj`,`email`,`endereco`,`nome`,`celular`,`fone`) VALUES 
 (1,'cidade nova','10557321000145',NULL,'Rua 27','CENTRO EDUCACIONAL SHEKINAH',NULL,NULL);
/*!40000 ALTER TABLE `acad_empresa` ENABLE KEYS */;


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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acad_matricula`
--

/*!40000 ALTER TABLE `acad_matricula` DISABLE KEYS */;
INSERT INTO `acad_matricula` (`id_matricula`,`data`,`status`,`valor`,`id_aluno`,`id_turma`,`integral`) VALUES 
 (1,NULL,'0',22,1,1,0);
/*!40000 ALTER TABLE `acad_matricula` ENABLE KEYS */;


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
  PRIMARY KEY (`id_mensalidade`),
  KEY `FK_5ohqk2wplxluosa2ixutna7jk` (`id_matricula`),
  KEY `FK_1r9uok0uhbqsqkxiwqiycls8h` (`id_usuario`),
  CONSTRAINT `FK_1r9uok0uhbqsqkxiwqiycls8h` FOREIGN KEY (`id_usuario`) REFERENCES `saa_usuario` (`id_usuario`),
  CONSTRAINT `FK_5ohqk2wplxluosa2ixutna7jk` FOREIGN KEY (`id_matricula`) REFERENCES `acad_matricula` (`id_matricula`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acad_mensalidade`
--

/*!40000 ALTER TABLE `acad_mensalidade` DISABLE KEYS */;
INSERT INTO `acad_mensalidade` (`id_mensalidade`,`data_pagamento`,`data_vencimento`,`sequencial`,`valor_pagamento`,`valor_vencimento`,`id_matricula`,`id_usuario`,`status_pagamento`) VALUES 
 (1,'2014-03-06','2014-03-06',2,2223,22,1,1,0),
 (2,'2014-03-11','2014-03-02',3,3,3,1,NULL,0),
 (3,NULL,'2014-03-03',1,0,222,1,NULL,0),
 (4,NULL,'2014-03-03',1,0,222,1,NULL,0),
 (5,'2014-03-03','2014-03-03',1,0,222,1,NULL,0),
 (6,'2014-03-03','2014-03-03',1,0,222,1,NULL,0);
/*!40000 ALTER TABLE `acad_mensalidade` ENABLE KEYS */;


--
-- Definition of table `acad_professor`
--

DROP TABLE IF EXISTS `acad_professor`;
CREATE TABLE `acad_professor` (
  `id_professor` bigint(20) NOT NULL AUTO_INCREMENT,
  `bairro` varchar(60) NOT NULL,
  `celular` varchar(8) DEFAULT NULL,
  `cep` varchar(8) NOT NULL,
  `cpf` varchar(11) NOT NULL,
  `data_cadastro` datetime NOT NULL,
  `data_nascimento` datetime DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `endereco` varchar(60) NOT NULL,
  `fone_comercial` varchar(8) DEFAULT NULL,
  `fone_residencial` varchar(8) DEFAULT NULL,
  `nome` varchar(60) NOT NULL,
  `rg` varchar(11) NOT NULL,
  `sexo` varchar(1) NOT NULL,
  PRIMARY KEY (`id_professor`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acad_professor`
--

/*!40000 ALTER TABLE `acad_professor` DISABLE KEYS */;
INSERT INTO `acad_professor` (`id_professor`,`bairro`,`celular`,`cep`,`cpf`,`data_cadastro`,`data_nascimento`,`email`,`endereco`,`fone_comercial`,`fone_residencial`,`nome`,`rg`,`sexo`) VALUES 
 (1,'YYY','43243242','434343','4323243','2014-02-26 16:22:14','2014-02-12 00:00:00','EEWEWE','WWWW','999','RWQERWQR','ROBSON RAMOS FERREIRA','6666','M');
/*!40000 ALTER TABLE `acad_professor` ENABLE KEYS */;


--
-- Definition of table `acad_responsavel`
--

DROP TABLE IF EXISTS `acad_responsavel`;
CREATE TABLE `acad_responsavel` (
  `id_responsavel` bigint(20) NOT NULL AUTO_INCREMENT,
  `bairro` varchar(60) NOT NULL,
  `celular` varchar(8) DEFAULT NULL,
  `cep` varchar(8) NOT NULL,
  `cpf` varchar(11) NOT NULL,
  `data_nascimento` datetime DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `endereco` varchar(60) NOT NULL,
  `fone_comercial` varchar(8) DEFAULT NULL,
  `fone_residencial` varchar(8) DEFAULT NULL,
  `nome` varchar(60) NOT NULL,
  `profissao` varchar(30) DEFAULT NULL,
  `rg` varchar(11) NOT NULL,
  `sexo` varchar(1) NOT NULL,
  PRIMARY KEY (`id_responsavel`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acad_responsavel`
--

/*!40000 ALTER TABLE `acad_responsavel` DISABLE KEYS */;
INSERT INTO `acad_responsavel` (`id_responsavel`,`bairro`,`celular`,`cep`,`cpf`,`data_nascimento`,`email`,`endereco`,`fone_comercial`,`fone_residencial`,`nome`,`profissao`,`rg`,`sexo`) VALUES 
 (1,'YYY','43243242','434343','7878777','2014-02-01 00:00:00','EEWEWE','WWWW','999','RWQERWQR','MARCELO','EWQEQW','6666','M');
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
  PRIMARY KEY (`id_turma`),
  KEY `FK_2qed6ismnk8dlxohh3ie29tb5` (`id_curso`),
  CONSTRAINT `FK_2qed6ismnk8dlxohh3ie29tb5` FOREIGN KEY (`id_curso`) REFERENCES `acad_curso` (`id_curso`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acad_turma`
--

/*!40000 ALTER TABLE `acad_turma` DISABLE KEYS */;
INSERT INTO `acad_turma` (`id_turma`,`ano`,`turno`,`id_curso`,`numero_vagas`) VALUES 
 (1,2015,'0',1,30),
 (2,2015,'1',1,10);
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
  CONSTRAINT `FK_tmf6l1l872tifrujdj883egvs` FOREIGN KEY (`id_disciplina`) REFERENCES `acad_disciplina` (`id_disciplina`),
  CONSTRAINT `FK_tgmm7cchb962gh5lvjqdaim3w` FOREIGN KEY (`id_turma`) REFERENCES `acad_turma` (`id_turma`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `acad_turma_disciplina`
--

/*!40000 ALTER TABLE `acad_turma_disciplina` DISABLE KEYS */;
/*!40000 ALTER TABLE `acad_turma_disciplina` ENABLE KEYS */;


--
-- Definition of table `saa_perfil`
--

DROP TABLE IF EXISTS `saa_perfil`;
CREATE TABLE `saa_perfil` (
  `id_perfil` bigint(20) NOT NULL AUTO_INCREMENT,
  `imagem` varchar(60) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `id_sistema` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_perfil`),
  KEY `FK_4hy4snh41hywvhfpa8omdlnqm` (`id_sistema`),
  CONSTRAINT `FK_4hy4snh41hywvhfpa8omdlnqm` FOREIGN KEY (`id_sistema`) REFERENCES `saa_sistema` (`id_sistema`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `saa_perfil`
--

/*!40000 ALTER TABLE `saa_perfil` DISABLE KEYS */;
INSERT INTO `saa_perfil` (`id_perfil`,`imagem`,`nome`,`id_sistema`) VALUES 
 (1,'resources/imagens/perfil/secretaria.png','SECRETARIA',2),
 (2,'resources/imagens/perfil/tesouraria.png','FINANCEIRO',2),
 (3,'resources/imagens/perfil/administrativo.png','ADMINISTRATIVO',2),
 (4,'resources/imagens/perfil/configuracao.png','CONFIGURAÇÃO',2),
 (5,'Perfil Teste','Perfil Teste',1);
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
INSERT INTO `saa_perfil_rotina` (`data`,`id_perfil`,`id_rotina`) VALUES 
 (NULL,1,1),
 (NULL,1,2),
 (NULL,1,3),
 (NULL,1,4),
 (NULL,1,5),
 (NULL,1,9),
 (NULL,1,10),
 (NULL,1,12),
 (NULL,2,5),
 (NULL,2,11),
 (NULL,4,6),
 (NULL,4,7),
 (NULL,4,8);
/*!40000 ALTER TABLE `saa_perfil_rotina` ENABLE KEYS */;


--
-- Definition of table `saa_rotina`
--

DROP TABLE IF EXISTS `saa_rotina`;
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `saa_rotina`
--

/*!40000 ALTER TABLE `saa_rotina` DISABLE KEYS */;
INSERT INTO `saa_rotina` (`id_rotina`,`acao`,`imagem`,`nome`,`status`,`id_sistema`) VALUES 
 (1,'alunoControlador.init','resources/imagens/rotina/aluno.png','ALUNO',0,2),
 (2,'professorControlador.init','resources/imagens/rotina/professor.png','PROFESSOR',0,2),
 (3,'responsavelControlador.init','resources/imagens/rotina/responsavel.png','RESPONSAVEL',0,2),
 (4,'cursoControlador.init','resources/imagens/rotina/curso.png','CURSO',0,2),
 (5,'matriculaControlador.init','resources/imagens/rotina/turma.png','MATRICULA',0,2),
 (6,'rotinaControlador.init','resources/imagens/rotina/rotina.png','ROTINAS',0,2),
 (7,'perfilControlador.init','resources/imagens/rotina/perfil.png','PERFIL',0,2),
 (8,'usuarioControlador.init','resources/imagens/rotina/usuario.png','USUARIO',0,2),
 (9,'disciplinaControlador.init','resources/imagens/rotina/disciplina.png','DISCIPLINA',0,2),
 (10,'turmaControlador.init','resources/imagens/rotina/turma.png','TURMA',0,2),
 (11,'mensalidadeControlador.init','resources/imagens/rotina/curso.png','MENSALIDADE',0,2),
 (12,'blockUIController.init','resources/imagens/rotina/curso.png','TESTE',0,2);
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `saa_sistema`
--

/*!40000 ALTER TABLE `saa_sistema` DISABLE KEYS */;
INSERT INTO `saa_sistema` (`id_sistema`,`codigo`,`descricao`,`imagem`,`nome`) VALUES 
 (1,'SAA','SISTEMA DE AUTENTICAÇÃO E AUTORIZAÇÃO','WWWW','SISTEMA DE AUTENTICAÇÃO E AUTORIZAÇÃO'),
 (2,'SHEKHI','SISTEMA DE CONTROLE DE ALUNOS','WWWW','SISTEMA DE CONTROLE DE ALUNOS');
/*!40000 ALTER TABLE `saa_sistema` ENABLE KEYS */;


--
-- Definition of table `saa_usuario`
--

DROP TABLE IF EXISTS `saa_usuario`;
CREATE TABLE `saa_usuario` (
  `id_usuario` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(30) DEFAULT NULL,
  `nome` varchar(30) NOT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `UK_ckrgsi99ta2s36tkydejud4wy` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `saa_usuario`
--

/*!40000 ALTER TABLE `saa_usuario` DISABLE KEYS */;
INSERT INTO `saa_usuario` (`id_usuario`,`login`,`nome`,`senha`,`status`) VALUES 
 (1,'admin','ADMINISTRADOR','admin','A');
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
 (NULL,4,1);
/*!40000 ALTER TABLE `saa_usuario_perfil` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
