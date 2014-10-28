package br.com.saa.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.saa.modelo.entidade.Empresa;
import br.com.saa.modelo.repositorio.EmpresaRepositorio;

@Service
public class EmpresaServicoImpl extends ServicoImpl<Empresa, Long> implements
		EmpresaServico {

	private static final long serialVersionUID = -4305564891244729963L;

	@Autowired
	private EmpresaRepositorio repositorio;

	@Override
	public List<Empresa> findByNomeLike(String nome) {
		return this.repositorio.findByNomeLike(nome);
	}

	@Override
	public Empresa findOne(Long primaryKey) {
		return this.repositorio.findOne(primaryKey);
	}

	@Override
	public List<Empresa> pesquisar(Empresa entity) {
		return null;
	}

	@Override
	protected JpaRepository<Empresa, Long> getRepository() {
		return repositorio;
	}

}