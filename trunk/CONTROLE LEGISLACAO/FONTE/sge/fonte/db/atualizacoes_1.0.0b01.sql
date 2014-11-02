
-- Adicionado  campo acad_mensalidade.id_usuario_atualizacao
ALTER TABLE `sge`.`acad_mensalidade` ADD COLUMN `id_usuario_atualizacao` BIGINT AFTER `id_usuario`,
 ADD CONSTRAINT `FK_id_usuario_atualizacao` FOREIGN KEY `FK_id_usuario_atualizacao` (`id_usuario_atualizacao`)
    REFERENCES `saa_usuario` (`id_usuario`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;
	
-- Add media_escolar em configuracao
ALTER TABLE `sge`.`acad_configuracao` ADD COLUMN `media_escolar` FLOAT NOT NULL AFTER `dia_vencimento`;

-- Add maximo_faltas em disciplina
ALTER TABLE `sge`.`acad_disciplina` ADD COLUMN `maximo_faltas` INTEGER UNSIGNED AFTER `nome`;

-- Add faltas em boletim
ALTER TABLE `sge`.`acad_boletim` 
 ADD COLUMN `faltas_bimestre1` INTEGER UNSIGNED AFTER `nota8`,
 ADD COLUMN `faltas_bimestre2` INTEGER UNSIGNED AFTER `faltas_bimestre1`,
 ADD COLUMN `faltas_bimestre3` INTEGER UNSIGNED AFTER `faltas_bimestre2`,
 ADD COLUMN `faltas_bimestre4` INTEGER UNSIGNED AFTER `faltas_bimestre3`,
 ADD COLUMN `total_faltas` INTEGER UNSIGNED AFTER `faltas_bimestre4`;
