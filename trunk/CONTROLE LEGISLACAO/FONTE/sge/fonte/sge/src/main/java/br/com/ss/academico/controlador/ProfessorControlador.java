package br.com.ss.academico.controlador;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.ss.academico.dominio.Professor;
import br.com.ss.academico.servico.ProfessorServico;
import br.com.ss.core.seguranca.servico.IService;
import br.com.ss.core.web.controlador.ControladorGenerico;
import br.com.ss.core.web.enumerated.Constants;
import br.com.ss.core.web.utils.StringUtil;

@ManagedBean
@SessionScoped
public class ProfessorControlador extends ControladorGenerico<Professor> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{professorServicoImpl}")
	private ProfessorServico servico;


	public String salvar() {
		
		if (isDataFuturo(entidade.getDataNascimento())) {
			showMessage(Constants.MSG_WARN_VALIDACAO, "Data de Nascimento é maior que a data atual." , FacesMessage.SEVERITY_WARN);
			return null;
		}

		if (!StringUtil.notEmpty(entidade.getEmail())) {
			entidade.setEmail(null);
		}
		
		if (this.entidade.getDataCadastro() == null) {
			this.entidade.setDataCadastro(new Date());
		}
		return super.salvar();
	}

	
	@Override
	protected String getNomeRelatorioJasper() {
		return "professor.jasper";
	}

	@Override
	public String getTituloRelatorio() {
		return "RELATÓRIO DE PROFESSOR";
	}

	@Override
	protected IService<Professor, Long> getService() {
		return servico;
	}
	

	public ProfessorServico getServico() {
		return servico;
	}

	public void setServico(ProfessorServico servico) {
		this.servico = servico;
	}
}