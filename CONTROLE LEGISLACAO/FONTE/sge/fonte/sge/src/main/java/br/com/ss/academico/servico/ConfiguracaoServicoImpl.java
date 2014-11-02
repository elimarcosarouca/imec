package br.com.ss.academico.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.ss.academico.dominio.Configuracao;
import br.com.ss.academico.repositorio.ConfiguracaoRepositorio;
import br.com.ss.core.seguranca.repositorio.ServicoImpl;

@Service
public class ConfiguracaoServicoImpl extends ServicoImpl<Configuracao, Long> implements ConfiguracaoServico {

	private static final long serialVersionUID = -4305564891244729963L;

	@Autowired
	private ConfiguracaoRepositorio repositorio;


	@Override
	protected JpaRepository<Configuracao, Long> getRepository() {
		return repositorio;
	}


	@Override
	public List<Configuracao> pesquisar(Configuracao entity) {
		return repositorio.findAll();
	}

}