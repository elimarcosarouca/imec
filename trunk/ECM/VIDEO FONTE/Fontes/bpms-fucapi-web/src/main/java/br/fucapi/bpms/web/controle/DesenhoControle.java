package br.fucapi.bpms.web.controle;

import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.fucapi.bpms.alfresco.servico.AlfrescoServico;
import br.fucapi.bpms.web.dominio.MetadadoDesenho;
import br.fucapi.bpms.web.dominio.Origem;
import br.fucapi.bpms.web.dominio.Situacao;
import br.fucapi.bpms.web.dominio.TipoDocumento;
import br.fucapi.bpms.web.repositorio.MetadadoDesenhoRepositorio;
import br.fucapi.bpms.web.repositorio.OrigemRepositorio;
import br.fucapi.bpms.web.repositorio.TipoDocumentoRepositorio;

@ManagedBean(name = "desenhoControle")
@SessionScoped
public class DesenhoControle implements Serializable {

	private static final long serialVersionUID = 1L;

	private SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd/MM/yyyy - HH:mm:ss");

	private Origem origem;
	private List<Origem> origens;
	private TipoDocumento tipoDocumento;
	private List<TipoDocumento> tipoDocumentos;
	private MetadadoDesenho metadadoDesenho;
	private List<MetadadoDesenho> metadadoDesenhos;
	private boolean valido;
	private boolean invalido;
	private boolean transitorio;

	private StreamedContent file;

	private Situacao situacaos;

	@ManagedProperty(value = "#{origemRepositorioImpl}")
	private OrigemRepositorio origemRepositorio;

	@ManagedProperty(value = "#{tipoDocumentoRepositorioImpl}")
	private TipoDocumentoRepositorio tipoDocumentoRepositorio;

	@ManagedProperty(value = "#{metadadoDesenhoRepositorioImpl}")
	private MetadadoDesenhoRepositorio metadadoDesenhoRepositorio;

	@ManagedProperty(value = "#{alfrescoServicoImpl}")
	private AlfrescoServico alfrescoServico;

	public String init() {
		this.origem = new Origem();
		this.tipoDocumento = new TipoDocumento();
		this.metadadoDesenho = new MetadadoDesenho();
		this.origens = origemRepositorio.list();
		this.tipoDocumentos = tipoDocumentoRepositorio.list();
		
		return "/pages/desenho/search.xhtml";
	}

	public void pesquisarDesenhos() {
		this.metadadoDesenho.setOrigem(this.origem);
		this.metadadoDesenhos = metadadoDesenhoRepositorio.pesquisar(
				this.metadadoDesenho, valido, transitorio, invalido);
	}

	public void baixarDesenho() {
		String nomeArquivo = this.getMetadadoDesenho().getNomeArquivo();
		String uuidArquivo = this.getMetadadoDesenho().getUuid();

		InputStream temp = alfrescoServico.baixarArquivo(nomeArquivo,
				uuidArquivo);
		file = new DefaultStreamedContent(temp, null, nomeArquivo);
	}

	public String detalhe(MetadadoDesenho metadadoDesenho) {
		this.metadadoDesenho = metadadoDesenho;
		return "/pages/desenho/detalhe.xhtml";
	}

	public String telaSearch() {
		this.metadadoDesenho.setGrupo(null);
		this.metadadoDesenho.setComplemento(null);
		this.pesquisarDesenhos();
		return "/pages/desenho/search.xhtml";
	}

	public String alterarSituacao() {
		this.metadadoDesenho.setDataAlteracao(new Date());
		this.metadadoDesenhoRepositorio.merge(this.metadadoDesenho);
		this.metadadoDesenho = new MetadadoDesenho();
		return "/pages/desenho/search.xhtml";
	}

	public void imprimir() {

	}

	public OrigemRepositorio getOrigemRepositorio() {
		return origemRepositorio;
	}

	public void setOrigemRepositorio(OrigemRepositorio origemRepositorio) {
		this.origemRepositorio = origemRepositorio;
	}

	public Origem getOrigem() {
		return origem;
	}

	public void setOrigem(Origem origem) {
		this.origem = origem;
	}

	public List<Origem> getOrigens() {
		return origens;
	}

	public void setOrigens(List<Origem> origens) {
		this.origens = origens;
	}

	public MetadadoDesenho getMetadadoDesenho() {
		return metadadoDesenho;
	}

	public void setMetadadoDesenho(MetadadoDesenho metadadoDesenho) {
		this.metadadoDesenho = metadadoDesenho;
	}

	public List<MetadadoDesenho> getMetadadoDesenhos() {
		return metadadoDesenhos;
	}

	public void setMetadadodesenhos(List<MetadadoDesenho> metadadoDesenhos) {
		this.metadadoDesenhos = metadadoDesenhos;
	}

	public boolean getValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}

	public boolean getInvalido() {
		return invalido;
	}

	public void setInvalido(boolean invalido) {
		this.invalido = invalido;
	}

	public boolean getTransitorio() {
		return transitorio;
	}

	public void setTransitorio(boolean transitorio) {
		this.transitorio = transitorio;
	}

	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public MetadadoDesenhoRepositorio getMetadadoDesenhoRepositorio() {
		return metadadoDesenhoRepositorio;
	}

	public void setMetadadoDesenhoRepositorio(
			MetadadoDesenhoRepositorio metadadoDesenhoRepositorio) {
		this.metadadoDesenhoRepositorio = metadadoDesenhoRepositorio;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public List<TipoDocumento> getTipoDocumentos() {
		return tipoDocumentos;
	}

	public void setTipoDocumentos(List<TipoDocumento> tipoDocumentos) {
		this.tipoDocumentos = tipoDocumentos;
	}

	public TipoDocumentoRepositorio getTipoDocumentoRepositorio() {
		return tipoDocumentoRepositorio;
	}

	public void setTipoDocumentoRepositorio(
			TipoDocumentoRepositorio tipoDocumentoRepositorio) {
		this.tipoDocumentoRepositorio = tipoDocumentoRepositorio;
	}

	public void setMetadadoDesenhos(List<MetadadoDesenho> metadadoDesenhos) {
		this.metadadoDesenhos = metadadoDesenhos;
	}

	public AlfrescoServico getAlfrescoServico() {
		return alfrescoServico;
	}

	public void setAlfrescoServico(AlfrescoServico alfrescoServico) {
		this.alfrescoServico = alfrescoServico;
	}

	public List<SelectItem> getSituacaos() {
		List<SelectItem> enumSitucao = new ArrayList<SelectItem>();
		for (Situacao ms : Situacao.values()) {
			enumSitucao.add(new SelectItem(ms, ms.getNome()));
		}
		return enumSitucao;
	}

	public void setSituacaos(Situacao situacaos) {
		this.situacaos = situacaos;
	}
}
