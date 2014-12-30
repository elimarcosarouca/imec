package br.fucapi.ads.modelo.servico;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.ads.modelo.dominio.Protocolo;
import br.fucapi.ads.modelo.repositorio.ProtocoloRepositorio;

@Service
public class ProtocoloServicoImpl implements ProtocoloServico {

	@Autowired
	ProtocoloRepositorio protocoloRepositorio;

	@Override
	public List<Protocolo> listarTodos() {
		return protocoloRepositorio.findAll();
	}

	@Override
	public Protocolo salvar(Protocolo protocolo) {
		return protocoloRepositorio.saveAndFlush(protocolo);
	}

	@Override
	public void remover(Protocolo protocolo) {
		protocoloRepositorio.delete(protocolo);
	}

	@Override
	@Transactional
	public Protocolo gerarProtocolo() {
		Calendar cal = Calendar.getInstance();
		int ano = cal.get(Calendar.YEAR);

		Protocolo protocolo = protocoloRepositorio.findByAno(ano);

		// zera o sequencial do ano
		if (protocolo == null) {
			protocolo = new Protocolo();
			protocolo.setAno(ano);
			protocolo.setId(1);
			protocolo.setSequencial(1);

		} else {
			// atualiza o sequencial
			protocolo.setSequencial(protocolo.getSequencial() + 1);

		}

		protocoloRepositorio.save(protocolo);

		return protocolo;
	}

	@Override
	/**
	 * Metodo responsavel por gerar o protocolo
	 * @param int tipoProtocolo
	 * @return protocolo (non-Javadoc)
	 * @see br.fucapi.ads.modelo.servico.ProtocoloServico#gerarProtocolo(String)
	 */
	@Transactional
	public Protocolo gerarProtocolo(String tipoProtocolo) {
		Calendar cal = Calendar.getInstance();
		int ano = cal.get(Calendar.YEAR);

		Protocolo protocolo = protocoloRepositorio.findByAnoAndTipoProtocolo(
				ano, tipoProtocolo);

		// zera o sequencial do ano
		if (protocolo == null) {
			// pega o id do registro para o tipo do servico
			protocolo = protocoloRepositorio.findByTipoProtocolo(tipoProtocolo);

			if (protocolo == null)
				protocolo = new Protocolo();

			protocolo.setAno(ano);
			protocolo.setTipoProtocolo(tipoProtocolo);
			protocolo.setSequencial(1);

		} else {
			// atualiza o sequencial
			protocolo.setSequencial(protocolo.getSequencial() + 1);

		}

		protocoloRepositorio.save(protocolo);
		System.out.println(protocolo.toString());
		return protocolo;
	}

	@Override
	public Protocolo findOne(Integer id) {
		Protocolo protocolo = protocoloRepositorio.findOne(1);
		return protocolo;
	}

	@Override
	/**
	 * Metodo responsavel por pegar o protocolo
	 * @param int ano
	 * @return protocolo (non-Javadoc)
	 * @see br.fucapi.ads.modelo.servico.ProtocoloServico#findByAno(int)
	 */
	public Protocolo findByAno(int ano) {
		Protocolo protocolo = protocoloRepositorio.findByAno(ano);
		return protocolo;
	}

	@Override
	/**
	 * Metodo responsavel por pegar o protocolo
	 * @param int tipoProtocolo
	 * @return protocolo (non-Javadoc)
	 * @see br.fucapi.ads.modelo.servico.ProtocoloServico#findByTipoProtocolo(String)
	 */
	public Protocolo findByTipoProtocolo(String tipoProtocolo) {
		Protocolo protocolo = protocoloRepositorio
				.findByTipoProtocolo(tipoProtocolo);
		return protocolo;
	}

}