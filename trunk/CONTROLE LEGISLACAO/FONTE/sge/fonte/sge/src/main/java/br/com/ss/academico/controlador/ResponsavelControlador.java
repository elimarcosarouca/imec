package br.com.ss.academico.controlador;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.ss.academico.dominio.Aluno;
import br.com.ss.academico.dominio.Responsavel;
import br.com.ss.academico.servico.ResponsavelServico;
import br.com.ss.core.seguranca.servico.IService;
import br.com.ss.core.web.controlador.ControladorGenerico;
import br.com.ss.core.web.enumerated.Constants;
import br.com.ss.core.web.utils.StringUtil;

@ManagedBean
@SessionScoped
public class ResponsavelControlador extends ControladorGenerico<Responsavel> {

	private static final long serialVersionUID = -6832271293709421841L;

	private Aluno aluno;

	@ManagedProperty(value = "#{responsavelServicoImpl}")
	private ResponsavelServico servico;


	@Override
	protected void init() {
		aluno = new Aluno();
	}
	
	@Override
	protected String getNomeRelatorioJasper() {
		return "responsavel.jasper";
	}

	@Override
	public String getTituloRelatorio() {
		return "RELATÓRIO DE RESPONSÁVEL";
	}

	@Override
	public void pesquisar() {
		this.listaPesquisa = ( ( ResponsavelServico ) getService() ).pesquisar(pesquisa, aluno.getNome());
	}


	public String salvar() {
		
		if (entidade.getDataNascimento() != null && isDataFuturo(entidade.getDataNascimento())) {
			showMessage(Constants.MSG_WARN_VALIDACAO, "Data de Nascimento é maior que a data atual." , FacesMessage.SEVERITY_WARN);
			return null;
		}
		
		if (!StringUtil.notEmpty(entidade.getCpf())) {
			entidade.setCpf(null);
		}
		if (!StringUtil.notEmpty(entidade.getEmail())) {
			entidade.setEmail(null);
		}
		return super.salvar();
	}

	
	@Override
	protected IService<Responsavel, Long> getService() {
		return servico;
	}

	public ResponsavelServico getServico() {
		return servico;
	}

	public void setServico(ResponsavelServico servico) {
		this.servico = servico;
	}

	public Aluno getAluno() {
		return aluno;
	}

}