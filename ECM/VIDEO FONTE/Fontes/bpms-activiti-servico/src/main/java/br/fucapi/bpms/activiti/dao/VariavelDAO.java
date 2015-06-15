package br.fucapi.bpms.activiti.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import br.fucapi.bpms.activiti.enumerated.NomeVariavel;

public class VariavelDAO {
	
	private JdbcTemplate jdbcTemplate;

	public void atualizarStatusProcesso(String processoInstanceId, String valor) {

		String SQL_STATUS_PROCESSO = "update act_hi_varinst set text_= '"
				+ valor + "' where proc_inst_id_ = '" + processoInstanceId
				+ "' and name_='statusProcesso'";
		System.out.println(SQL_STATUS_PROCESSO);
		jdbcTemplate.update(SQL_STATUS_PROCESSO);
	}

	public void atualizarVariavelProcesso(String processoInstanceId,
			NomeVariavel nomeVariavel, String status) {
		String SQL_STATUS_PROCESSO = "update act_hi_varinst set text_= '"
				+ status + "' where proc_inst_id_ = '" + processoInstanceId
				+ "' and name_='" + nomeVariavel.getNome() + "'";
		System.out.println(SQL_STATUS_PROCESSO);
		jdbcTemplate.update(SQL_STATUS_PROCESSO);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}