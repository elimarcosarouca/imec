package br.com.ss.academico.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.ss.academico.dominio.Professor;
import br.com.ss.academico.repositorio.ProfessorRepositorio;
import br.com.ss.core.seguranca.repositorio.ServicoImpl;

@Service
public class ProfessorServicoImpl extends ServicoImpl<Professor, Long> implements ProfessorServico {

	private static final long serialVersionUID = -4305564891244729963L;

	@Autowired
	private ProfessorRepositorio repositorio;

	@Override
	public List<Professor> findByNomeLike(String nome) {
		return this.repositorio.findByNomeLike(nome);
	}

	@Override
	public List<Professor> pesquisar(Professor entity) {
		return listarTodos();
	}

	@Override
	protected JpaRepository<Professor, Long> getRepository() {
		return repositorio;
	}
}