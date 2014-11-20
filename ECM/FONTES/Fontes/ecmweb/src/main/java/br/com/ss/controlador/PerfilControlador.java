package br.com.ss.controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.ss.model.entidade.Perfil;
import br.com.ss.model.servico.PerfilServico;
import br.com.ss.model.servico.Servico;

@ManagedBean
@SessionScoped
public class PerfilControlador extends ControladorGenerico<Perfil> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{perfilServicoImpl}")
	private PerfilServico servico;

	private String nomeRelatorio = "rerfil.jasper";

	@Override
	protected String getNomeRelatorioJasper() {
		return this.nomeRelatorio;
	}

	@Override
	public String getTituloRelatorio() {
		return "RELATÓRIO DE CURSO";
	}

	@Override
	protected Servico<Perfil, Long> getService() {
		return this.servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public PerfilServico getServico() {
		return servico;
	}

	public void setServico(PerfilServico servico) {
		this.servico = servico;
	}

}