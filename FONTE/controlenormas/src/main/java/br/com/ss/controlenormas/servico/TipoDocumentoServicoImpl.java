package br.com.ss.controlenormas.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.ss.controlenormas.dominio.TipoDocumento;
import br.com.ss.controlenormas.repositorio.TipoDocumentoRepositorio;
import br.com.ss.core.seguranca.repositorio.ServicoImpl;

@Service
public class TipoDocumentoServicoImpl extends ServicoImpl<TipoDocumento, Long>
		implements TipoDocumentoServico {

	private static final long serialVersionUID = 1L;

	@Autowired
	private TipoDocumentoRepositorio servico;

	@Override
	protected JpaRepository<TipoDocumento, Long> getRepository() {
		return servico;
	}

	@Override
	public List<TipoDocumento> pesquisar(TipoDocumento entity) {
		// TODO Auto-generated method stub
		return null;
	}

}