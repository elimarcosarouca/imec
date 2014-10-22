package br.com.ss.core.web.controlador;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import br.com.ss.core.seguranca.dominio.Sistema;
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

	private Sistema sistema;

	
	@PostConstruct
	public void init() {
		
		// lê a configuracao da empresa e sistema do messages_pt.properties.
		String idSistema = MessageUtils.getMessageResourceString( MessageUtils.ID_SISTEMA );
		
		// adiciona variaveis no context da aplicacao
		FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("sistema", sistema);
		
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
	
	public Sistema getSistema() {
		return sistema;
	}
	
}
