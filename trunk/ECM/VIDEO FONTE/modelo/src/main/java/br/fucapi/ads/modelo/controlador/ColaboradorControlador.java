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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.security.core.context.SecurityContextHolder;

import net.sf.jasperreports.engine.JRException;
import br.fucapi.ads.modelo.dominio.Colaborador;
import br.fucapi.ads.modelo.dominio.PostoCopia;
import br.fucapi.ads.modelo.servico.ColaboradorServico;
import br.fucapi.ads.modelo.servico.PostoCopiaServico;
import br.fucapi.ads.modelo.servico.Servico;
import br.fucapi.bpms.alfresco.dominio.Usuario;

@ManagedBean
@SessionScoped
public class ColaboradorControlador extends ControladorGenerico<Colaborador> {

	private static final long serialVersionUID = -6832271293709421841L;

	private List<PostoCopia> postosCopia;

	@ManagedProperty(value = "#{colaboradorServicoImpl}")
	private ColaboradorServico servico;
	
	@ManagedProperty(value = "#{postoCopiaServicoImpl}")
	private PostoCopiaServico postoCopiaServico;

	private String nomeRelatorio = "REGISTRO_DE_TREINAMENTO.PDF";
	
	private static final String PATH_REPORT = "resources" + File.separator + "jasper" + File.separator;

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

	public void imprimirListaColaboradores() throws FileNotFoundException, JRException {
		
		PostoCopia postoCopia = new PostoCopia();
		postoCopia.setId(1l);
		this.entidade.setPostoCopia(postoCopia);
		this.listaPesquisa = servico.pesquisar(this.entidade);
		
		Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put(REPORT_TITLE, "REGISTRO DE TREINAMENTO");
		param.put("usuario", usuarioLogado.getFirstName() +" " + usuarioLogado.getLastName());
		

		gerarRelatorioWeb(this.listaPesquisa, param, "colaboradoresPostoCopia.jasper");

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

}