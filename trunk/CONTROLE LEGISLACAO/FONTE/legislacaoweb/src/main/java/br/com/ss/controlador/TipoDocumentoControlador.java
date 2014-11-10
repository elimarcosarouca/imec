package br.com.ss.controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.ss.model.entidade.TipoDocumento;
import br.com.ss.model.servico.Servico;
import br.com.ss.model.servico.TipoDocumentoServico;

@ManagedBean
@SessionScoped
public class TipoDocumentoControlador extends
		ControladorGenerico<TipoDocumento> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{tipoDocumentoServicoImpl}")
	private TipoDocumentoServico servico;

	private String nomeRelatorio = "estado.jasper";

	@Override
	protected String getNomeRelatorioJasper() {
		return this.nomeRelatorio;
	}

	@Override
	public String getTituloRelatorio() {
		return "RELATÓRIO DE CURSO";
	}

	@Override
	protected Servico<TipoDocumento, Long> getService() {
		return this.servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public TipoDocumentoServico getServico() {
		return servico;
	}

	public void setServico(TipoDocumentoServico servico) {
		this.servico = servico;
	}
}