package br.com.ss.academico.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.ss.academico.dominio.Emissor;
import br.com.ss.academico.repositorio.EmissorRepositorio;
import br.com.ss.core.seguranca.repositorio.ServicoImpl;

@Service
public class EmissorServicoImpl extends ServicoImpl<Emissor, Long> implements
		EmissorServico {

	private static final long serialVersionUID = 6122381696152668772L;

	@Autowired
	private EmissorRepositorio repositorio;

	@Override
	protected JpaRepository<Emissor, Long> getRepository() {
		return repositorio;
	}

	@Override
	public List<Emissor> findByNomeLike(String nome) {
		return repositorio.findByNomeLike(nome);
	}

	@Override
	public List<Emissor> pesquisar(Emissor entity) {
		// TODO Auto-generated method stub
		return null;
	}

}