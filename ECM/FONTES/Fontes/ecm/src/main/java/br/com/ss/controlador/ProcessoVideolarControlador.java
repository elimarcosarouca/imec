package br.com.ss.controlador;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.ss.model.servico.PostoCopiaServico;
import br.com.ss.model.servico.SetorServico;
import br.com.ss.model.servico.TipoDocumentoServico;
import br.com.ss.model.servico.UnidadeServico;
import br.com.ss.processo.ProcessoVideolar;
import br.com.ss.servicoimpl.ActivitiServicoImpl;

@ManagedBean
@SessionScoped
public class ProcessoVideolarControlador implements Serializable {

	private static final long serialVersionUID = 5739593327217530162L;

	private ProcessoVideolar processo;

	@ManagedProperty(value = "#{unidadeServicoImpl}")
	private UnidadeServico unidadeServico;

	@ManagedProperty(value = "#{tipoDocumentoServicoImpl}")
	private TipoDocumentoServico tipoDocumentoServico;

	@ManagedProperty(value = "#{setorServicoImpl}")
	private SetorServico setorServico;

	@ManagedProperty(value = "#{postoCopiaServicoImpl}")
	private PostoCopiaServico postoCopiaServico;

	private ActivitiServicoImpl activitiServico = new ActivitiServicoImpl();

	@PostConstruct
	public void inti() {
		this.processo = new ProcessoVideolar();
		this.processo.inicializarObjetos();
		this.activitiServico.listarTodasTarefas();

	}

	public void upload() {
		if (this.processo.getFile() != null) {
			FacesMessage message = new FacesMessage("Succesful", this.processo
					.getFile().getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public UnidadeServico getUnidadeServico() {
		return unidadeServico;
	}

	public void setUnidadeServico(UnidadeServico unidadeServico) {
		this.unidadeServico = unidadeServico;
	}

	public TipoDocumentoServico getTipoDocumentoServico() {
		return tipoDocumentoServico;
	}

	public void setTipoDocumentoServico(
			TipoDocumentoServico tipoDocumentoServico) {
		this.tipoDocumentoServico = tipoDocumentoServico;
	}

	public SetorServico getSetorServico() {
		return setorServico;
	}

	public void setSetorServico(SetorServico setorServico) {
		this.setorServico = setorServico;
	}

	public PostoCopiaServico getPostoCopiaServico() {
		return postoCopiaServico;
	}

	public void setPostoCopiaServico(PostoCopiaServico postoCopiaServico) {
		this.postoCopiaServico = postoCopiaServico;
	}

	public ProcessoVideolar getProcesso() {
		return processo;
	}

	public void setProcesso(ProcessoVideolar processo) {
		this.processo = processo;
	}

}