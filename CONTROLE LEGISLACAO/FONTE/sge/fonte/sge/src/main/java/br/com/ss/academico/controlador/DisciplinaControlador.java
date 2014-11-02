package br.com.ss.academico.controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.ss.academico.dominio.Disciplina;
import br.com.ss.academico.servico.DisciplinaServico;
import br.com.ss.core.seguranca.servico.IService;
import br.com.ss.core.web.controlador.ControladorGenerico;

@ManagedBean
@SessionScoped
public class DisciplinaControlador extends ControladorGenerico<Disciplina> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{disciplinaServicoImpl}")
	private DisciplinaServico servico;


	@Override
	protected String getNomeRelatorioJasper() {
		return "disciplina.jasper";
	}

	@Override
	public String getTituloRelatorio() {
		return "RELATÓRIO DE DISCIPLINA";
	}
	
	@Override
	protected IService<Disciplina, Long> getService() {
		return servico;
	}

	public DisciplinaServico getServico() {
		return servico;
	}

	public void setServico(DisciplinaServico servico) {
		this.servico = servico;
	}

}