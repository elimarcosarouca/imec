package br.com.ss.saa.web.controlador.seguranca;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.saa.core.web.utils.StringUtil;
import br.com.saa.modelo.entidade.Perfil;
import br.com.saa.modelo.entidade.Usuario;
import br.com.saa.modelo.repositorio.GenericRepositorio;
import br.com.saa.modelo.repositorio.UsuarioRepositorio;
import br.com.saa.servico.Servico;
import br.com.ss.core.web.controlador.ControladorGenerico;
import br.com.ss.saa.web.enumerated.StatusUsuario;
import br.com.ss.saa.web.enumerated.TipoUsuario;
import br.com.ss.saa.web.utils.CriptografiaUtil;

@ManagedBean
@SessionScoped
public class UsuarioControlador extends ControladorGenerico<Usuario> {

	private static final long serialVersionUID = -929165489387258837L;

	private List<br.com.saa.modelo.entidade.Perfil> perfis = new ArrayList<Perfil>();

	@ManagedProperty(value = "#{usuarioRepositorioImpl}")
	private UsuarioRepositorio servico;

	private int colunas;

	protected List<SelectItem> statusUsuarioList;

	protected List<SelectItem> tipoUsuarioList;
	
	public void init() {
		statusUsuarioList = new ArrayList<SelectItem>();
		for (StatusUsuario c : StatusUsuario.values()) {
			statusUsuarioList.add(new SelectItem(c, c.getDescricao()));
		}
		
		tipoUsuarioList = new ArrayList<SelectItem>();
		tipoUsuarioList.add(new SelectItem(TipoUsuario.ADMINISTRADOR, TipoUsuario.ADMINISTRADOR.getDescricao()));
		tipoUsuarioList.add(new SelectItem(TipoUsuario.SGE_SECRETARIA, TipoUsuario.SGE_SECRETARIA.getDescricao()));
		tipoUsuarioList.add(new SelectItem(TipoUsuario.SGE_PROFESSOR, TipoUsuario.SGE_PROFESSOR.getDescricao()));
		
	}
	
	
	@Override
	public String novo() {
		String page = super.novo();
//		entidade.setStatus(StatusUsuario.ATIVO);
		return page;
	}
	

	@Override
	protected String getNomeRelatorioJasper() {
		// FIXME #Peninha: relatorio
		return null;
	}

	@Override
	public String getTituloRelatorio() {
		return "RELATÓRIO DE USUÁRIO";
	}
	
	
	@Override
	public String salvar() {
		// criptografa a senha do usuario
		if (StringUtil.notEmpty(entidade.getSenha())) {
			entidade.setSenha( CriptografiaUtil.criptografar(entidade.getSenha()) );
		}
		
		return super.salvar();
	}
	
	@Override
	protected GenericRepositorio<Usuario, Long> getService() {
		return this.servico;
	}

	public String editarSenha() {
		this.entidade = (Usuario) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		
		return "paginas/usuario/alterarSenha.xhtml?faces-redirect=true";
	}

	/* --------------- Gets/Sets ---------------------- */

	public UsuarioRepositorio getServico() {
		return servico;
	}

	public void setServico(UsuarioRepositorio servico) {
		this.servico = servico;
	}


	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	public int getColunas() {
		return colunas;
	}

	public void setColunas(int colunas) {
		this.colunas = colunas;
	}

	public List<SelectItem> getStatusUsuarioList() {
		return statusUsuarioList;
	}

	public List<SelectItem> getTipoUsuarioList() {
		return tipoUsuarioList;
	}

}