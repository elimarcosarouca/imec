package br.com.ss.saa.web.controlador.seguranca;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.saa.core.web.utils.MessageUtils;
import br.com.saa.modelo.entidade.Perfil;
import br.com.saa.modelo.entidade.Usuario;
import br.com.saa.modelo.repositorio.GenericRepositorio;
import br.com.saa.modelo.repositorio.PerfilRepositorio;
import br.com.saa.modelo.repositorio.RotinaRepositorio;
import br.com.ss.core.web.controlador.ControladorGenerico;

@ManagedBean
@SessionScoped
public class PerfilControlador extends ControladorGenerico<Perfil> {

	private static final long serialVersionUID = -6832271293709421841L;

	/** Lista exibida na tela inicial (lista.xhtml). */
	private List<Perfil> listaPerfilUsuario;

	private Usuario usuario;

	private int colunas = 3;

	@ManagedProperty(value = "#{perfilRepositorioImpl}")
	private PerfilRepositorio servico;

	@ManagedProperty(value = "#{rotinaRepositorioImpl}")
	private RotinaRepositorio rotinaRepositorio;

	@Override
	public void init() {
		carregarPerfis();
	}

	public void carregarPerfis() {
		Object userSession = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (userSession instanceof Usuario) {
			this.usuario = (Usuario) userSession;
			String idSistema = MessageUtils
					.getMessageResourceString(MessageUtils.ID_SISTEMA);
			this.listaPerfilUsuario = this.servico
					.listaPerfilPorSistemaPorUsuario(new Integer(idSistema),
							usuario.getId());
		}
	}

	@Override
	protected String getNomeRelatorioJasper() {
		// FIXME #Peninha relatorio
		return null;
	}

	@Override
	public String getTituloRelatorio() {
		return "RELATÓRIO DE PERFIL";
	}

	@Override
	protected GenericRepositorio<Perfil, Long> getService() {
		return this.servico;
	}

	@Override
	public String detalhe(Perfil perfil) {
		return super.detalhe(perfil);
	}

	public String salvar() {
		return super.salvar();
	}

	/* --------------- Gets/Sets ---------------------- */

	public int getColunas() {
		return colunas;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public PerfilRepositorio getServico() {
		return servico;
	}

	public void setServico(PerfilRepositorio servico) {
		this.servico = servico;
	}

	public RotinaRepositorio getRotinaRepositorio() {
		return rotinaRepositorio;
	}

	public void setRotinaRepositorio(RotinaRepositorio rotinaRepositorio) {
		this.rotinaRepositorio = rotinaRepositorio;
	}

	public void setColunas(int colunas) {
		this.colunas = colunas;
	}

	public Perfil getEntidade() {
		return entidade;
	}

	public void setEntidade(Perfil entidade) {
		this.entidade = entidade;
	}

	public Perfil getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(Perfil pesquisa) {
		this.pesquisa = pesquisa;
	}

	public List<Perfil> getListaPerfilUsuario() {
		return listaPerfilUsuario;
	}

	public void setListaPerfilUsuario(List<Perfil> listaPerfilUsuario) {
		this.listaPerfilUsuario = listaPerfilUsuario;
	}

}