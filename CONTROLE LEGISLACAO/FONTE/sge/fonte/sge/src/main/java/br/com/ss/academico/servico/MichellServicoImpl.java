package br.com.ss.academico.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.ss.academico.dominio.Michell;
import br.com.ss.academico.repositorio.MichellRepositorio;
import br.com.ss.core.seguranca.repositorio.ServicoImpl;

@Service
public class MichellServicoImpl extends ServicoImpl<Michell, Long> implements
		MichellServico {

	private static final long serialVersionUID = 6122381696152668772L;

	@Autowired
	private MichellRepositorio repositorio;

	@Override
	protected JpaRepository<Michell, Long> getRepository() {
		return repositorio;
	}

	@Override
	public List<Michell> findByNomeLike(String nome) {
		return repositorio.findByNomeLike(nome);
	}

	@Override
	public List<Michell> pesquisar(Michell entity) {
		// TODO Auto-generated method stub
		return null;
	}

}