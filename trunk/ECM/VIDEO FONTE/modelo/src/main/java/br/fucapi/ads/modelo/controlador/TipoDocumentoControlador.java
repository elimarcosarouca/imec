package br.fucapi.ads.modelo.controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.fucapi.ads.modelo.dominio.TipoDocumento;
import br.fucapi.ads.modelo.servico.Servico;
import br.fucapi.ads.modelo.servico.TipoDocumentoServico;

@ManagedBean
@SessionScoped
public class TipoDocumentoControlador extends
		ControladorGenerico<TipoDocumento> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{tipoDocumentoServicoImpl}")
	private TipoDocumentoServico servico;

	private String nomeRelatorio = "tipoDocumento.jasper";

	@Override
	protected String getNomeRelatorioJasper() {
		return this.nomeRelatorio;
	}

	@Override
	public String getTituloRelatorio() {
		return "RELATÓRIO DE Tipo Documento";
	}

	@Override
	protected Servico<TipoDocumento, Long> getService() {
		return servico;
	}

	public TipoDocumentoServico getServico() {
		return servico;
	}

	public void setServico(TipoDocumentoServico servico) {
		this.servico = servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

}