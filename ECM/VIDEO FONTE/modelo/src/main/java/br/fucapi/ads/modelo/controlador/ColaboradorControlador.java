package br.fucapi.ads.modelo.controlador;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JRException;

import org.springframework.security.core.context.SecurityContextHolder;

import br.fucapi.ads.modelo.dominio.Colaborador;
import br.fucapi.ads.modelo.dominio.PostoCopia;
import br.fucapi.ads.modelo.dominio.VariavelPublicarDocumento;
import br.fucapi.ads.modelo.servico.ColaboradorServico;
import br.fucapi.ads.modelo.servico.PostoCopiaServico;
import br.fucapi.ads.modelo.servico.Servico;
import br.fucapi.bpms.activiti.dominio.ProcessoInstancia;
import br.fucapi.bpms.alfresco.dominio.Usuario;

@ManagedBean
@SessionScoped
public class ColaboradorControlador extends ControladorGenerico<Colaborador> {

	private static final long serialVersionUID = -6832271293709421841L;

	private List<PostoCopia> postosCopia;

	private PostoCopia postoCopia = new PostoCopia();

	private ProcessoInstancia processo;

	@ManagedProperty(value = "#{colaboradorServicoImpl}")
	private ColaboradorServico servico;

	@ManagedProperty(value = "#{postoCopiaServicoImpl}")
	private PostoCopiaServico postoCopiaServico;

	private String nomeRelatorio = "REGISTRO_DE_TREINAMENTO.PDF";

	private static final String PATH_REPORT = "resources" + File.separator
			+ "jasper" + File.separator;
	
	public String telaPesquisa() {
		super.setup();
		return "/paginas/colaborador/pesquisa.xhtml?faces-redirect=true";
	}

	@Override
	@PostConstruct
	public String novo() {
		this.postosCopia = postoCopiaServico.listAll();
		return super.novo();

	}

	/**
	 * Lista os Responsaveis - para a lista do auto-complete da tela de
	 * pesquisa.
	 */
	public List<PostoCopia> listarPostoCopia(String nome) {
		return postoCopiaServico.pesquisar(new PostoCopia(nome));
	}

	public void imprimirListaColaboradores(ProcessoInstancia processo)
			throws FileNotFoundException, JRException {

		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		Long idPostoCopia = Long.valueOf(params.get("idPostoCopia"));
		this.postoCopia.setId(idPostoCopia);

		this.processo = processo;
		this.entidade.setPostoCopia(postoCopia);
		this.listaPesquisa = servico.pesquisar(this.entidade);

		Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Map<String, Object> param = new HashMap<String, Object>();
		param = pegarParametro(param, processo);
		param.put(REPORT_TITLE, "REGISTRO DE TREINAMENTO");
		param.put("nomePostoCopia", params.get("nomePostoCopia"));
		param.put(
				"usuario",
				usuarioLogado.getFirstName() + " "
						+ usuarioLogado.getLastName());
		

		gerarRelatorioWeb(this.listaPesquisa, param,
				"colaboradoresPostoCopia.jasper");

	}

	public Map<String, Object> pegarParametro(Map<String, Object> param,
			ProcessoInstancia processo) {

		param.put("versaoRevisao", ((VariavelPublicarDocumento) processo
				.getVariaveis()).getVersaoRevisao());
		
		param.put("codigo", ((VariavelPublicarDocumento) processo
				.getVariaveis()).getCodigo());
		
		param.put("nomeArquivo", ((VariavelPublicarDocumento) processo
				.getVariaveis()).getArquivoDoc().getNomeArquivo());

		return param;

	}

	@Override
	protected String getNomeRelatorioJasper() {
		return this.nomeRelatorio;
	}

	@Override
	public String getTituloRelatorio() {
		return "RELATÓRIO DE Tipo Documento";
	}

	@Override
	protected Servico<Colaborador, Long> getService() {
		return servico;
	}

	public ColaboradorServico getServico() {
		return servico;
	}

	public void setServico(ColaboradorServico servico) {
		this.servico = servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public List<PostoCopia> getPostosCopia() {
		return postosCopia;
	}

	public void setPostosCopia(List<PostoCopia> postosCopia) {
		this.postosCopia = postosCopia;
	}

	public PostoCopiaServico getPostoCopiaServico() {
		return postoCopiaServico;
	}

	public void setPostoCopiaServico(PostoCopiaServico postoCopiaServico) {
		this.postoCopiaServico = postoCopiaServico;
	}

	public PostoCopia getPostoCopia() {
		return postoCopia;
	}

	public void setPostoCopia(PostoCopia postoCopia) {
		this.postoCopia = postoCopia;
	}

}