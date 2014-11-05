package br.com.ss.core.web.controlador;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import br.com.saa.core.web.utils.MessageUtils;
import br.com.saa.modelo.entidade.Empresa;
import br.com.saa.modelo.entidade.Sistema;
import br.com.saa.modelo.repositorio.EmpresaRepositorio;
import br.com.saa.modelo.repositorio.SistemaRepositorio;

/**
 * ManagedBean para carregamento de dados para armazenar no escopo de
 * Aplicação.
 * 
 * @author robson.ramos
 */

@ManagedBean
@ApplicationScoped
public class ApplicationBean {

	@ManagedProperty(value = "#{sistemaRepositorioImpl}")
	private SistemaRepositorio sistemaRepositorio;

	@ManagedProperty(value = "#{empresaRepositorioImpl}")
	private EmpresaRepositorio empresaRepositorio;

	// @ManagedProperty(value = "#{configuracaoServicoImpl}")
	// private ConfiguracaoRepositorio servicoConfiguracao;

	private Sistema sistema;

	private Empresa empresa;

	// private Configuracao configuracao;

	@PostConstruct
	public void init() {

		// lê a configuracao da empresa e sistema do messages_pt.properties.
		String idEmpresa = MessageUtils
				.getMessageResourceString(MessageUtils.ID_EMPRESA);
		String idSistema = MessageUtils
				.getMessageResourceString(MessageUtils.ID_SISTEMA);

		/*
		 * sistema = sistemaServico.findByPrimaryKey(new Long(idSistema));
		 * empresa = empresaServico.findByPrimaryKey(new Long(idEmpresa));
		 * 
		 * 
		 * configuracao = servicoConfiguracao.listarTodos().get(0);
		 */

		// adiciona variaveis no context da aplicacao
		/*
		 * FacesContext.getCurrentInstance().getExternalContext()
		 * .getApplicationMap().put("sistema", sistema);
		 * FacesContext.getCurrentInstance().getExternalContext()
		 * .getApplicationMap().put("empresa", empresa);
		 * FacesContext.getCurrentInstance().getExternalContext()
		 * .getApplicationMap().put("configuracao", configuracao);
		 */

	}

	public void reload() {
		// add regra de reload caso necessario.
	}

	/* --------- Gets/Sets --------------- */

	public Sistema getSistema() {
		return sistema;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public SistemaRepositorio getSistemaRepositorio() {
		return sistemaRepositorio;
	}

	public void setSistemaRepositorio(SistemaRepositorio sistemaRepositorio) {
		this.sistemaRepositorio = sistemaRepositorio;
	}

	public EmpresaRepositorio getEmpresaRepositorio() {
		return empresaRepositorio;
	}

	public void setEmpresaRepositorio(EmpresaRepositorio empresaRepositorio) {
		this.empresaRepositorio = empresaRepositorio;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}