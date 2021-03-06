package br.com.saa.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.saa.modelo.entidade.Rotina;
import br.com.saa.modelo.repositorio.RotinaRepositorio;
import br.com.saa.modelo.repositorio.RotinaRepositorioSql;

@Service
public class RotinaServicoImpl extends ServicoImpl<Rotina, Long> implements
		RotinaServico {

	private static final long serialVersionUID = -4128328556228022891L;

	@Autowired
	private RotinaRepositorio rotinaRepositorio;

	@Autowired
	private RotinaRepositorioSql rotinaRepositorioSql;

	@Override
	public List<Rotina> pesquisar(Rotina entity) {
		return rotinaRepositorioSql.pesquisar(entity);
	}

	@Override
	protected JpaRepository<Rotina, Long> getRepository() {
		return rotinaRepositorio;
	}

	@Override
	public List<Rotina> listaRotinasPorPerfil(Long id) {
		return rotinaRepositorioSql.listaRotinasPorPerfil(id);
	}

	@Override
	public List<Rotina> findByNomeLike(String nome) {
		return this.rotinaRepositorio.findByNomeLike(nome);
	}

}