package br.com.saa.servico;

import java.util.List;

import br.com.saa.modelo.entidade.PerfilRotina;
import br.com.saa.modelo.entidade.Rotina;

public interface PerfilRotinaServico extends Servico<PerfilRotina, Long> {
	
	public List<Rotina> listaRotinaNotInPerfil(Long idPerfil);

}
