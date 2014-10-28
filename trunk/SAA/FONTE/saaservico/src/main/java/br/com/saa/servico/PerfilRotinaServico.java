package br.com.saa.servico;

import java.util.List;

import br.com.saa.modelo.entidade.PerfilRotina;
import br.com.saa.modelo.entidade.Rotina;

public interface PerfilRotinaServico {

	public List<PerfilRotina> listarTodos();

	public PerfilRotina salvar(PerfilRotina usuarioPerfil);

	public void remover(PerfilRotina usuarioPerfil);

	public List<Rotina> listaRotinaNotInPerfil(Long idPerfil);
	
}