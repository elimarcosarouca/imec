package br.com.ss.core.seguranca.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.ss.academico.dominio.Empresa;
import br.com.ss.academico.repositorio.EmpresaRepositorio;
import br.com.ss.core.seguranca.repositorio.ServicoImpl;

@Service
public class EmpresaServicoImpl extends ServicoImpl<Empresa, Long> implements EmpresaServico {

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