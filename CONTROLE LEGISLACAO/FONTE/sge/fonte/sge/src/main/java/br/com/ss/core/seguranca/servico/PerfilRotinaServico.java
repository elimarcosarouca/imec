package br.com.ss.core.seguranca.servico;

import java.util.List;

import br.com.ss.core.seguranca.dominio.PerfilRotina;
import br.com.ss.core.seguranca.dominio.Rotina;

public interface PerfilRotinaServico {

	public List<PerfilRotina> listarTodos();

	public PerfilRotina salvar(PerfilRotina usuarioPerfil);

	public void remover(PerfilRotina usuarioPerfil);

	public List<Rotina> listaRotinaNotInPerfil(Long idPerfil);
	
}
