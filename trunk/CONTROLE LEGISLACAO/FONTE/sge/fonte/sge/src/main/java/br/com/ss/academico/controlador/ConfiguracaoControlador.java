package br.com.ss.academico.controlador;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import br.com.ss.academico.dominio.Configuracao;
import br.com.ss.academico.servico.ConfiguracaoServico;
import br.com.ss.core.seguranca.servico.IService;
import br.com.ss.core.web.controlador.ControladorGenerico;
import br.com.ss.core.web.enumerated.Constants;

@ManagedBean
@ApplicationScoped
public class ConfiguracaoControlador extends ControladorGenerico<Configuracao> {

	private static final long serialVersionUID = -6765503632927328716L;

	@ManagedProperty(value = "#{configuracaoServicoImpl}")
	private ConfiguracaoServico servico;
	
    private Map<String, String> themes;
    
	private String tema = "start"; //default

	private Integer diaVencimento = 10; // default
	
	/* Melhores temas:
	 * start
	 * bluesky
	 * cupertino
	 * redmond
	 * humanity
	 * hot-sneaks
	 * blitzer
	 * afternoon
	 * 
	 * ## Botoes:
	 * delta
	 * start
	 * flick
	 * 
	 * */
	
	@Override
	protected void init() {
		List<Configuracao> configs = servico.listarTodos();
		if (!configs.isEmpty()) {
			entidade = configs.get(0);	// deve haver apenas um registro
		} else {
			createConfiguracao();
		}
		
        carregarTemas();
	}

	private void createConfiguracao() {
		entidade = new Configuracao();
		entidade.setDiaVencimento(diaVencimento);
		entidade.setTema(tema);
		salvar();
	}
	
	
	@Override
	public String salvar() {
		
		getService().salvar(entidade);
		showMessage(Constants.MSG_SUCESSO, FacesMessage.SEVERITY_INFO);
		
		return null;
	}

	protected void carregarTemas() {
		themes = new TreeMap<String, String>();
        themes.put("Aristo", "aristo");
        themes.put("Afterdark", "afterdark");
        themes.put("Afternoon", "afternoon");
        themes.put("Afterwork", "afterwork");
        themes.put("Black-Tie", "black-tie");
        themes.put("Blitzer", "blitzer");
        themes.put("Bluesky", "bluesky");
        themes.put("Bootstrap", "bootstrap");
        themes.put("Casablanca", "casablanca");
        themes.put("Cruze", "cruze");
        themes.put("Cupertino", "cupertino");
        themes.put("Dark-Hive", "dark-hive");
        themes.put("Delta", "delta");
        themes.put("Dot-Luv", "dot-luv");
        themes.put("Eggplant", "eggplant");
        themes.put("Excite-Bike", "excite-bike");
        themes.put("Flick", "flick");
        themes.put("Glass-X", "glass-x");
        themes.put("Home", "home");
        themes.put("Hot-Sneaks", "hot-sneaks");
        themes.put("Humanity", "humanity");
        themes.put("Le-Frog", "le-frog");
        themes.put("Midnight", "midnight");
        themes.put("Mint-Choc", "mint-choc");
        themes.put("Overcast", "overcast");
        themes.put("Pepper-Grinder", "pepper-grinder");
        themes.put("Redmond", "redmond");
        themes.put("Rocket", "rocket");
        themes.put("Sam", "sam");
        themes.put("Smoothness", "smoothness");
        themes.put("South-Street", "south-street");
        themes.put("Start", "start");
        themes.put("Sunny", "sunny");
        themes.put("Swanky-Purse", "swanky-purse");
        themes.put("Trontastic", "trontastic");
        themes.put("UI-Darkness", "ui-darkness");
        themes.put("UI-Lightness", "ui-lightness");
        themes.put("Vader", "vader");
	}
	
	@Override
	protected String getNomeRelatorioJasper() {
		return null;
	}
	
	@Override
	public String getTituloRelatorio() {
		return null;
	}

	@Override
	protected IService<Configuracao, Long> getService() {
		return servico;
	}

	public Map<String, String> getThemes() {
		return themes;
	}

	public ConfiguracaoServico getServico() {
		return servico;
	}

	public void setServico(ConfiguracaoServico servico) {
		this.servico = servico;
	}
}
