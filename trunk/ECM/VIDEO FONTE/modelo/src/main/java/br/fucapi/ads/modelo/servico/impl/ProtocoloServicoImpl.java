package br.fucapi.ads.modelo.servico.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.ads.modelo.dominio.Protocolo;
import br.fucapi.ads.modelo.repositorio.GenericRepositorio;
import br.fucapi.ads.modelo.repositorio.ProtocoloRepositorio;
import br.fucapi.ads.modelo.servico.ProtocoloServico;

@Service
@Transactional
public class ProtocoloServicoImpl extends GenericServico<Protocolo, Long>
		implements ProtocoloServico {

	private static final long serialVersionUID = -1380136082551143545L;

	@Autowired
	private ProtocoloRepositorio repositorio;

	@Override
	protected GenericRepositorio<Protocolo, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<Protocolo> pesquisar(Protocolo abstractEntity) {
		return repositorio.pesquisar(abstractEntity);
	}

	@Override
	public Protocolo pesquisarPorAno(Protocolo abstractEntity) {
		return repositorio.pesquisarPorAno(abstractEntity);
	}

	@Override
	public Protocolo gerarProtocolo() {
		Calendar cal = Calendar.getInstance();
		int ano = cal.get(Calendar.YEAR);
		Protocolo protocolo = new Protocolo();
		protocolo.setAno(ano);
		protocolo = repositorio.pesquisarPorAno(protocolo);

		// zera o sequencial do ano
		if (protocolo == null) {
			protocolo = new Protocolo();
			protocolo.setAno(ano);
			protocolo.setId(1l);
			protocolo.setSequencial(1);
		} else {
			// atualiza o sequencial
			protocolo.setSequencial(protocolo.getSequencial() + 1);
		}
		repositorio.save(protocolo);
		return protocolo;
	}

}