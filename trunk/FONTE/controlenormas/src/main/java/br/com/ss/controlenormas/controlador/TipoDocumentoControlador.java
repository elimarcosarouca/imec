package br.com.ss.controlenormas.controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.ss.controlenormas.dominio.TipoDocumento;
import br.com.ss.controlenormas.servico.TipoDocumentoServico;
import br.com.ss.core.seguranca.servico.IService;
import br.com.ss.core.web.controlador.ControladorGenerico;

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
		return "RELATÓRIO DE NORMA";
	}

	@Override
	protected IService<TipoDocumento, Long> getService() {
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