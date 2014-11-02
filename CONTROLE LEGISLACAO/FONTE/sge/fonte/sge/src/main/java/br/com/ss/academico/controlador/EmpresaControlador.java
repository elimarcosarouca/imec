package br.com.ss.academico.controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.ss.academico.dominio.Empresa;
import br.com.ss.core.seguranca.servico.EmpresaServico;
import br.com.ss.core.seguranca.servico.IService;
import br.com.ss.core.web.controlador.ControladorGenerico;

@ManagedBean
@SessionScoped
public class EmpresaControlador extends ControladorGenerico<Empresa> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{empresaServicoImpl}")
	private EmpresaServico servico;

	private String nomeRelatorio = "empresa.jasper";
	
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
		return "RELATÓRIO DE EMPRESA";
	}

	@Override
	protected IService<Empresa, Long> getService() {
		return servico;
	}

	public EmpresaServico getServico() {
		return servico;
	}

	public void setServico(EmpresaServico servico) {
		this.servico = servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

}