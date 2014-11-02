package br.com.ss.academico.controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.ss.academico.dominio.Emissor;
import br.com.ss.academico.servico.EmissorServico;
import br.com.ss.core.seguranca.servico.IService;
import br.com.ss.core.web.controlador.ControladorGenerico;

@ManagedBean
@SessionScoped
public class EmissorControlador extends ControladorGenerico<Emissor> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{emissorServicoImpl}")
	private EmissorServico servico;

	private String nomeRelatorio = "emissor.jasper";
	
	@Override
	public void pesquisar() {
		this.listaPesquisa = getService().listarTodos();
	}

	@Override
	protected String getNomeRelatorioJasper() {
		return this.nomeRelatorio;
	}

	@Override
	public String getTituloRelatorio() {
		return "RELATÓRIO DE EMISSOR";
	}

	@Override
	protected IService<Emissor, Long> getService() {
		return servico;
	}

	public EmissorServico getServico() {
		return servico;
	}

	public void setServico(EmissorServico servico) {
		this.servico = servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

}