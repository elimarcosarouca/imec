package br.com.ss.core.seguranca.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.ss.core.seguranca.dominio.Sistema;
import br.com.ss.core.seguranca.repositorio.ServicoImpl;
import br.com.ss.core.seguranca.repositorio.SistemaRepositorio;

@Service
public class SistemaServicoImpl extends ServicoImpl<Sistema, Long> implements SistemaServico {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SistemaRepositorio servico;

	
	@Override
	public List<Sistema> findByNomeLike(String nome) {
		return this.servico.findByNomeLike(nome);
	}

	@Override
	public Sistema findByCodigo(String codigo) {
		return this.servico.findByCodigo(codigo);
	}

	@Override
	public List<Sistema> pesquisar(Sistema entity) {
		return this.servico.findAll();
	}

	@Override
	protected JpaRepository<Sistema, Long> getRepository() {
		return servico;
	}

}