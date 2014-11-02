package br.com.ss.academico.repositorio;

import java.util.Date;
import java.util.List;

import br.com.ss.academico.dominio.Aluno;
import br.com.ss.academico.dominio.Matricula;
import br.com.ss.academico.dominio.Mensalidade;
import br.com.ss.academico.enumerated.StatusPagamento;
import br.com.ss.academico.enumerated.TipoPesquisaData;

public interface MensalidadeRepositorioHql {

	List<Mensalidade> pesquisar(Mensalidade entidade, Date dataInicio, Date dataFim, TipoPesquisaData tipoPesquisaData);

	List<Mensalidade> listarMensalidadesEmAtraso(Aluno aluno, StatusPagamento statusPagamento);

	Integer getMesMenorMensalidadeMatricula(Matricula matricula);
	
}
