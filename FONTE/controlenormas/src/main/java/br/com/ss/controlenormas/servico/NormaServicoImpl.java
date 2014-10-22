package br.com.ss.controlenormas.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.ss.controlenormas.dominio.Norma;
import br.com.ss.controlenormas.repositorio.NormaRepositorio;
import br.com.ss.core.seguranca.repositorio.ServicoImpl;

@Service
public class NormaServicoImpl extends ServicoImpl<Norma, Long> implements
		NormaServico {

	private static final long serialVersionUID = 1L;

	@Autowired
	private NormaRepositorio servico;

	@Override
	protected JpaRepository<Norma, Long> getRepository() {
		return servico;
	}

	@Override
	public List<Norma> pesquisar(Norma entity) {
		// TODO Auto-generated method stub
		return null;
	}

}