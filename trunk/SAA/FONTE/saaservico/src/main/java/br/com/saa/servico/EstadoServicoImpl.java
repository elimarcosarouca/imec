package br.com.saa.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.saa.modelo.entidade.Estado;
import br.com.saa.modelo.repositorio.EstadoRepositorio;

@Service
public class EstadoServicoImpl extends ServicoImpl<Estado, Long> implements
		EstadoServico {

	private static final long serialVersionUID = -4305564891244729963L;

	@Autowired
	private EstadoRepositorio repositorio;

	@Override
	public List<Estado> findByNomeLike(String nome) {
		return this.repositorio.findByNomeLike(nome);
	}

	@Override
	public Estado findOne(Long primaryKey) {
		return this.repositorio.findOne(primaryKey);
	}

	@Override
	public List<Estado> pesquisar(Estado entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected JpaRepository<Estado, Long> getRepository() {
		// TODO Auto-generated method stub
		return null;
	}

}