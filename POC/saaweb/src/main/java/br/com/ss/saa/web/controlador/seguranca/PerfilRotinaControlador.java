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

import br.com.saa.modelo.entidade.Perfil;
import br.com.saa.modelo.entidade.PerfilRotina;
import br.com.saa.modelo.entidade.Rotina;
import br.com.saa.modelo.entidade.Usuario;
import br.com.saa.modelo.repositorio.GenericRepositorio;
import br.com.saa.servico.PerfilRotinaServico;
import br.com.ss.core.web.controlador.ControladorGenerico;
import br.com.ss.saa.web.utils.FacesUtils;

@ManagedBean
@SessionScoped
public class PerfilRotinaControlador  extends ControladorGenerico<PerfilRotina> {

	private static final long serialVersionUID = -9100344258312363107L;
//	public class PerfilControlador extends ControladorGenerico<Perfil> {

	private static final String MSG_ADICIONAR = "Rotina adicionada com sucesso.";
	private static final String MSG_REMOVER = "Rotina removida com sucesso.";

	private static final String MSG_ERRO = "Ocorreu um erro ao executar a opera��o!";

	@ManagedProperty(value = "#{perfilRotinaServicoImpl}")
	private PerfilRotinaServico servico;
	

	private DualListModel<PerfilRotina> dualListRotina;

	private List<PerfilRotina> listaPerfilRotinaNotInPerfil;
	private Perfil perfilModalRotina;

	@PostConstruct
	public void init() {
		dualListRotina = new DualListModel<PerfilRotina>();
	}
	
	@Override
	protected GenericRepositorio<PerfilRotina, Long> getService() {
//		return this.servico;
	}
	
	public void showModalRotina(Perfil perfil) {
		/*this.perfilModalRotina = perfil;
		listaPerfilRotinaNotInPerfil = new ArrayList<PerfilRotina>();
		List<Rotina> listaRotinaNotInPerfil = perfilRotinaServico
				.listaRotinaNotInPerfil(perfil.getId());

		for (Rotina rot : listaRotinaNotInPerfil) {
			PerfilRotina perfilRotina = createPerfilRotina(rot, perfil);
			listaPerfilRotinaNotInPerfil.add(perfilRotina);
		}

		dualListRotina = new DualListModel<PerfilRotina>(
				listaPerfilRotinaNotInPerfil, perfil.getPerfilRotinas());*/
	}

	private PerfilRotina createPerfilRotina(Rotina rotina, Perfil perfil) {
		PerfilRotina usuarioRotina = new PerfilRotina();
		usuarioRotina.setData(new Date());
		usuarioRotina.setRotina(rotina);
		usuarioRotina.setPerfil(perfil);
		return usuarioRotina;
	}

	@SuppressWarnings("unchecked")
	public void onTransfer(TransferEvent event) {

		boolean add = event.isAdd();

		List<PerfilRotina> perRots = (List<PerfilRotina>) event.getItems();
		for (PerfilRotina usuarioRotina : perRots) {
			salvarUsuario(usuarioRotina, add);
		}

		String msg = MSG_ADICIONAR;
		if (!add) {
			msg = MSG_REMOVER;
		}

		FacesUtils.addMessage(msg, null, FacesMessage.SEVERITY_INFO);
	}

	private void salvarUsuario(PerfilRotina perfilRotina, boolean add) {
		/*try {

			Perfil perfil = perfilRotina.getPerfil();

			if (add) {
				perfil.getPerfilRotina().add(perfilRotina);
				perfilRotinaServico.salvar(perfilRotina);

			} else {
				perfil.getPerfilRotina().remove(perfilRotina);
				perfilRotinaServico.remover(perfilRotina);

			}

		} catch (Exception e) {
			e.printStackTrace();
			FacesUtils.addMessage(MSG_ERRO, null, FacesMessage.SEVERITY_ERROR);
		}*/

	}

	/* ---------- Gets/Sets --------------- */

	

	public DualListModel<PerfilRotina> getDualListRotina() {
		return dualListRotina;
	}

	public PerfilRotinaServico getServico() {
		return servico;
	}

	public void setServico(PerfilRotinaServico servico) {
		this.servico = servico;
	}

	public void setDualListRotina(DualListModel<PerfilRotina> dualListRotina) {
		this.dualListRotina = dualListRotina;
	}

	public Perfil getPerfilModalRotina() {
		return perfilModalRotina;
	}

	@Override
	protected String getNomeRelatorioJasper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getTituloRelatorio() {
		// TODO Auto-generated method stub
		return null;
	}
}
