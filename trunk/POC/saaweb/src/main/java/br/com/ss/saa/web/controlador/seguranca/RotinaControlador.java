package br.com.ss.saa.web.controlador.seguranca;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.saa.modelo.entidade.AbstractEntity;
import br.com.saa.modelo.entidade.Perfil;
import br.com.saa.modelo.entidade.Rotina;
import br.com.saa.modelo.repositorio.GenericRepositorio;
import br.com.saa.servico.RotinaServico;
import br.com.saa.servico.Servico;
import br.com.ss.core.web.controlador.ControladorGenerico;

@ManagedBean
@SessionScoped
public class RotinaControlador extends ControladorGenerico<Rotina> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{rotinaServicoImpl}")
	private RotinaServico servico;

	private Perfil perfil;

	private int colunas;

	private final String LISTAR_ROTINAS = "listar_rotinas";

	@Override
	protected String getNomeRelatorioJasper() {
		// FIXME #Peninha: verificar relatorio
		return null;
	}

	@Override
	public String getTituloRelatorio() {
		return "RELATÓRIO DE ROTINA";
	}

	@Override
//	protected Servico<Rotina, Long> getService() {
	protected  GenericRepositorio<Rotina, Long> getService(){
//		return servico;
		return null;
	}

	public String listarRotinas(Perfil perfil) {
		this.perfil = perfil;
		return listarRotinas();
	}

	public String listarRotinas() {
		this.listaPesquisa = servico.listaRotinasPorPerfil(this.perfil.getId());
		this.colunas = 4; // Util.definirTamanhoColuna(rotinas.size());
		return LISTAR_ROTINAS;
	}

	public String telaRelatorio(Rotina rotina) {
		return rotina.getAcao();
	}

	/* --------------- Gets/Sets ---------------------- */

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public int getColunas() {
		return colunas;
	}

	public void setColunas(int colunas) {
		this.colunas = colunas;
	}

	public RotinaServico getServico() {
		return servico;
	}

	public void setServico(RotinaServico servico) {
		this.servico = servico;
	}

}