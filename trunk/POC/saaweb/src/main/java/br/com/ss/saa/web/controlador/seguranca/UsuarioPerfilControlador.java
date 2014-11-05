package br.com.ss.saa.web.controlador.seguranca;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import br.com.ss.core.seguranca.dominio.Perfil;
import br.com.ss.core.seguranca.dominio.Usuario;
import br.com.ss.core.seguranca.dominio.UsuarioPerfil;
import br.com.ss.core.seguranca.servico.UsuarioPerfilServico;
import br.com.ss.core.seguranca.servico.UsuarioServico;
import br.com.ss.core.web.utils.FacesUtils;

@ManagedBean
@SessionScoped
public class UsuarioPerfilControlador {

	private static final String MSG_ADICIONAR = "Perfil adicionado com sucesso.";
	private static final String MSG_REMOVER = "Perfil removido com sucesso.";

	private static final String MSG_ERRO = "Ocorreu um erro ao executar a operação!";


	@ManagedProperty(value = "#{usuarioServicoImpl}")
	private UsuarioServico usuarioServico;

	@ManagedProperty(value = "#{usuarioPerfilServicoImpl}")
	private UsuarioPerfilServico usuarioPerfilServico;

    private DualListModel<UsuarioPerfil> dualListPerfil;  
    
	private List<UsuarioPerfil> listaUsuPerfilNotInUsuario;
	
	@PostConstruct
	public void init() {
		dualListPerfil = new DualListModel<UsuarioPerfil>();
	}

	public void showModalPerfil( Usuario usuario ) {
		listaUsuPerfilNotInUsuario = new ArrayList<UsuarioPerfil>();
		List<Perfil> listaPerfilNotInUsuario = usuarioPerfilServico.listaPerfilNotInUsuario(usuario.getId());
		
		for ( Perfil perfil : listaPerfilNotInUsuario ) {
			UsuarioPerfil usuarioPerfil = createUsuarioPerfil(perfil, usuario);
			listaUsuPerfilNotInUsuario.add(usuarioPerfil);
		}
		// faz o fetch de UsuarioPerfil
		List<UsuarioPerfil> usuPerfis = usuarioPerfilServico.findByUsuario(usuario);
		usuario.setUsuarioPerfil(usuPerfis);
		dualListPerfil = new DualListModel<UsuarioPerfil>(listaUsuPerfilNotInUsuario, usuario.getUsuarioPerfil());
	}

	
	private UsuarioPerfil createUsuarioPerfil(Perfil perfil, Usuario usuario) {
		UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
		usuarioPerfil.setData(new Date());
		usuarioPerfil.setPerfil(perfil);
		usuarioPerfil.setUsuario(usuario);
		return usuarioPerfil;
	}

	
	@SuppressWarnings("unchecked")
	public void onTransfer(TransferEvent event) {  

		boolean add = event.isAdd();
		List<UsuarioPerfil> usuPerfis = (List<UsuarioPerfil>) event.getItems();
		for (UsuarioPerfil usuarioPerfil : usuPerfis) {
			salvarUsuario(usuarioPerfil, add);
		}

		String msg = MSG_ADICIONAR;
		if (!add) {
			msg = MSG_REMOVER;
		}

		FacesUtils.addMessage(msg, null, FacesMessage.SEVERITY_INFO);
	}
	
	
	private void salvarUsuario(UsuarioPerfil usuarioPerfil, boolean add ) {

		try {
			
			if ( add ) {
				usuarioPerfilServico.salvar(usuarioPerfil);
			} else {
				usuarioPerfilServico.remover(usuarioPerfil);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtils.addMessage(MSG_ERRO, null, FacesMessage.SEVERITY_ERROR);
		}
		
	}



	/* ---------- Gets/Sets --------------- */
	
	public UsuarioServico getUsuarioServico() {
		return usuarioServico;
	}

	public void setUsuarioServico(UsuarioServico usuarioServico) {
		this.usuarioServico = usuarioServico;
	}

	public UsuarioPerfilServico getUsuarioPerfilServico() {
		return usuarioPerfilServico;
	}

	public void setUsuarioPerfilServico(UsuarioPerfilServico usuarioPerfilServico) {
		this.usuarioPerfilServico = usuarioPerfilServico;
	}

    public DualListModel<UsuarioPerfil> getDualListPerfil() {
		return dualListPerfil;
	}

	public void setDualListPerfil(DualListModel<UsuarioPerfil> dualListPerfil) {
		this.dualListPerfil = dualListPerfil;
	}
}
