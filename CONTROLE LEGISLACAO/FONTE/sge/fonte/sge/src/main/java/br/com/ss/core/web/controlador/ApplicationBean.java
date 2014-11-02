package br.com.ss.core.web.controlador;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import br.com.ss.academico.dominio.Configuracao;
import br.com.ss.academico.dominio.Empresa;
import br.com.ss.academico.servico.ConfiguracaoServico;
import br.com.ss.core.seguranca.dominio.Sistema;
import br.com.ss.core.seguranca.servico.EmpresaServico;
import br.com.ss.core.seguranca.servico.SistemaServico;
import br.com.ss.core.web.utils.MessageUtils;

/**
 * ManagedBean para carregamento de dados para armazenar no escopo de Aplicação.
 * @author robson.ramos
 */
@ManagedBean
@ApplicationScoped
public class ApplicationBean {

	@ManagedProperty(value = "#{sistemaServicoImpl}")
	private SistemaServico sistemaServico;

	@ManagedProperty(value = "#{empresaServicoImpl}")
	private EmpresaServico empresaServico;

	@ManagedProperty(value = "#{configuracaoServicoImpl}")
	private ConfiguracaoServico servicoConfiguracao;
	
	private Sistema sistema;

	private Empresa empresa;

	private Configuracao configuracao;
	
	
	@PostConstruct
	public void init() {
		
		// lê a configuracao da empresa e sistema do messages_pt.properties.
		String idEmpresa = MessageUtils.getMessageResourceString( MessageUtils.ID_EMPRESA );
		String idSistema = MessageUtils.getMessageResourceString( MessageUtils.ID_SISTEMA );
		
		sistema = sistemaServico.findByPrimaryKey(new Long(idSistema));
		empresa = empresaServico.findByPrimaryKey(new Long(idEmpresa));

		configuracao = servicoConfiguracao.listarTodos().get(0);
		
		// adiciona variaveis no context da aplicacao
		FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("sistema", sistema);
		FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("empresa", empresa);
		FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("configuracao", configuracao);
		
	}
	
	public void reload() {
		// add regra de reload caso necessario.
	}

	
	/* --------- Gets/Sets --------------- */

	public SistemaServico getSistemaServico() {
		return sistemaServico;
	}
	public void setSistemaServico(SistemaServico sistemaServico) {
		this.sistemaServico = sistemaServico;
	}
	public EmpresaServico getEmpresaServico() {
		return empresaServico;
	}
	public void setEmpresaServico(EmpresaServico empresaServico) {
		this.empresaServico = empresaServico;
	}
	public Sistema getSistema() {
		return sistema;
	}
	public Empresa getEmpresa() {
		return empresa;
	}

	public ConfiguracaoServico getServicoConfiguracao() {
		return servicoConfiguracao;
	}

	public void setServicoConfiguracao(ConfiguracaoServico servicoConfiguracao) {
		this.servicoConfiguracao = servicoConfiguracao;
	}
}
