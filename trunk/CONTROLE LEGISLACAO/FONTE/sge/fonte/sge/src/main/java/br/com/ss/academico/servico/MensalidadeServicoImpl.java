package br.com.ss.academico.servico;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.ss.academico.dominio.Aluno;
import br.com.ss.academico.dominio.Matricula;
import br.com.ss.academico.dominio.Mensalidade;
import br.com.ss.academico.enumerated.StatusPagamento;
import br.com.ss.academico.enumerated.TipoPesquisaData;
import br.com.ss.academico.repositorio.MensalidadeRepositorio;
import br.com.ss.academico.repositorio.MensalidadeRepositorioHql;
import br.com.ss.core.seguranca.repositorio.ServicoImpl;

@Service
public class MensalidadeServicoImpl extends ServicoImpl<Mensalidade, Long> implements MensalidadeServico {

	private static final long serialVersionUID = -4305564891244729963L;

	@Autowired
	private MensalidadeRepositorio repositorio;
	
	@Autowired
	private MensalidadeRepositorioHql repositorioHql;

	@Override
	protected JpaRepository<Mensalidade, Long> getRepository() {
		return repositorio;
	}
	
	@Override
	public List<Mensalidade> pesquisar(Mensalidade entidade, Date dataInicio, Date dataFim, TipoPesquisaData tipoPesquisaData) {
		return this.repositorioHql.pesquisar(entidade, dataInicio, dataFim, tipoPesquisaData);
	}

	@Override
	public List<Mensalidade> loadMensalidades( Long idMatricula ) {
		return this.repositorio.loadMensalidades(idMatricula);
	}

	@Override
	public List<Mensalidade> pesquisar(Mensalidade entity) {
		return null;
	}

	@Override
	public List<Mensalidade> listarMensalidadesEmAtraso(Aluno aluno, StatusPagamento statusPagamento) {
		return repositorioHql.listarMensalidadesEmAtraso(aluno, statusPagamento);
	}
	
	@Override
	public Integer getMenorMensalidadeMatricula(Matricula matricula) {
		return repositorioHql.getMesMenorMensalidadeMatricula(matricula);
	}

}