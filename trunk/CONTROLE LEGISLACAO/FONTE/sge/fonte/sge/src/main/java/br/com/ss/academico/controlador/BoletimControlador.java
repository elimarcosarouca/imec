package br.com.ss.academico.controlador;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.event.RowEditEvent;

import br.com.ss.academico.dominio.Aluno;
import br.com.ss.academico.dominio.Boletim;
import br.com.ss.academico.dominio.Configuracao;
import br.com.ss.academico.dominio.Empresa;
import br.com.ss.academico.dominio.Matricula;
import br.com.ss.academico.dominio.Turma;
import br.com.ss.academico.dominio.TurmaDisciplina;
import br.com.ss.academico.servico.BoletimServico;
import br.com.ss.academico.servico.TurmaServico;
import br.com.ss.core.seguranca.servico.IService;
import br.com.ss.core.web.controlador.ControladorGenerico;
import br.com.ss.core.web.enumerated.Constants;
import br.com.ss.core.web.utils.FacesUtils;
import br.com.ss.core.web.utils.Util;

import com.lowagie.text.DocumentException;

@ManagedBean
@SessionScoped
public class BoletimControlador extends ControladorGenerico<Boletim> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{boletimServicoImpl}")
	private BoletimServico service;

	@ManagedProperty(value = "#{turmaServicoImpl}")
	private TurmaServico turmaServico;

	private String nomeRelatorio = "boletim.jasper";

	private Turma turma;

	private List<Turma> turmas;

	private List<Matricula> filteredTurmas;

	private Configuracao configuracao;
	

	@PostConstruct
	@Override
	public void setup() {
		super.setup();
		this.turmas = turmaServico.listarTodos();
		configuracao = (Configuracao) FacesUtils.getApplicationParam("configuracao");
		
	}


	protected String getNomeRelatorioJasper() {
		return this.nomeRelatorio;
	}

	@Override
	public String getTituloRelatorio() {
		return "RELATÓRIO DE BOLETIM";
	}
	
	@Override
	public void pesquisar() {
		if (this.turma != null) {
			this.listaPesquisa = service.listBoletimPorTurma(this.turma);
		
			// fetch de disciplina 
			for (Boletim bol : listaPesquisa ) {
				for ( TurmaDisciplina td : bol.getMatricula().getTurma().getTurmaDisciplina() ) {
					td.getDisciplina();
				}
			}
		}
		
	}

	public String pesquisarBoletim(Boletim boletim) {
		entidade = boletim;
		return "boletim?faces-redirect=true";
	}

	
	public void onEdit(RowEditEvent event) {
		salvarBoletim();
	}

	public void onCancel(RowEditEvent event) {
		showMessage("Ediçao de nota cancelada!", FacesMessage.SEVERITY_WARN);
	}

	
	// FIXME chamar na tela
	public void salvarBoletim() {
		
		entidade.atualizarBoletim(configuracao.getMediaEscolar());
		getService().salvar(entidade);
		showMessage(Constants.MSG_SUCESSO, FacesMessage.SEVERITY_INFO);
		
	}
	
	
	public void imprimirBoletim() {

		try {
			
			Map<String, Object> param = new HashMap<String, Object>();
			Empresa empresa = (Empresa) FacesUtils.getApplicationParam("empresa");
	
			// parametros usados no relatorio
			param.put(REPORT_TITLE, getTituloRelatorio());
			param.put(EMPRESA, empresa);
			param.put(USUARIO, getUsuarioLogado());
			
			gerarRelatorioWeb(listaPesquisa, param, true);
			
		} catch (JRException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public void imprimir() throws FileNotFoundException, IOException, DocumentException, JRException {
		try {
			
			Map<String, Object> param = new HashMap<String, Object>();
			Empresa empresa = (Empresa) FacesUtils.getApplicationParam("empresa");
	
			// parametros usados no relatorio
			param.put(REPORT_TITLE, getTituloRelatorio());
			param.put(EMPRESA, empresa);
			param.put(USUARIO, getUsuarioLogado());
			
			List<Aluno> alunos = new ArrayList<Aluno>();
			for (Boletim bol: listaPesquisa ) {
				alunos.add(bol.getMatricula().getAluno());
			}
			
			gerarRelatorioWeb(alunos, param, false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void gerarRelatorioWeb(List<?> lista, Map<String, Object> parametros, boolean boletim) throws JRException {
		nomeRelatorio = "boletim.jasper";
		if (!boletim) {
			nomeRelatorio = "turma-aluno.jasper";
		}
		
		JRDataSource jrRS = new JRBeanCollectionDataSource(lista);

		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context
				.getExternalContext().getResponse();

		BufferedOutputStream output = null;
		BufferedInputStream input = null;

		String webPath = context.getExternalContext().getRealPath("/");
		String reportPath = webPath + PATH_REPORT + nomeRelatorio;

		try {

			input = new BufferedInputStream(new FileInputStream(reportPath), DEFAULT_BUFFER_SIZE);

			File fileReport = new File(reportPath);

			response.reset();
			response.setHeader("Content-Type", "application/pdf");
			response.setHeader("Content-Length", String.valueOf(fileReport.length()));
			response.setHeader("Content-Disposition", "inline; filename=\"" + fileReport.getName() + "\"");

			JasperRunManager.runReportToPdfStream(new FileInputStream(
					fileReport), response.getOutputStream(), parametros, jrRS);

			output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

			// Write file contents to response.
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}

			// Finalize task.
			output.flush();
		} catch (FileNotFoundException e) {
			System.out.println("Erro : Relatorio não foi encontrado: "
					+ reportPath);
			showMessage(Constants.MSG_ERRO, FacesMessage.SEVERITY_ERROR);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Gently close streams.
			close(output);
			close(input);
		}

		context.renderResponse();
		context.responseComplete();
	}

	

	public List<Double> getNotas() {
		return Util.gerarNotas();
	}
	
	
	
	
	/* ----------- Gets/Sets ------------------ */

	@Override
	protected IService<Boletim, Long> getService() {
		return service;
	}

	public void setService(BoletimServico service) {
		this.service = service;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public TurmaServico getTurmaServico() {
		return turmaServico;
	}

	public void setTurmaServico(TurmaServico turmaServico) {
		this.turmaServico = turmaServico;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public List<Matricula> getFilteredTurmas() {
		return filteredTurmas;
	}

	public void setFilteredTurmas(List<Matricula> filteredTurmas) {
		this.filteredTurmas = filteredTurmas;
	}

}