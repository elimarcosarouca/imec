package br.com.ss.academico.controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.ss.academico.dominio.Michell;
import br.com.ss.academico.servico.MichellServico;
import br.com.ss.core.seguranca.servico.IService;
import br.com.ss.core.web.controlador.ControladorGenerico;

@ManagedBean
@SessionScoped
public class MichellControlador extends ControladorGenerico<Michell> {

	private static final long serialVersionUID = 3197997812890527309L;

	@ManagedProperty(value = "#{michellServicoImpl}")
	private MichellServico servico;

	@Override
	public void pesquisar() {
		 this.listaPesquisa = getService().listarTodos();
	}

	public MichellServico getServico() {
		return servico;
	}

	public void setServico(MichellServico servico) {
		this.servico = servico;
	}

	@Override
	protected String getNomeRelatorioJasper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getTituloRelatorio() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IService<Michell, Long> getService() {
		// TODO Auto-generated method stub
		return this.servico;
	}

}
