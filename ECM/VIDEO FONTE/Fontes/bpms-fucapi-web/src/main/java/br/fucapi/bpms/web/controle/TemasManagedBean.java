package br.fucapi.bpms.web.controle;

import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class TemasManagedBean {
	
	 private String meuTema;
	 private Map<String, String> themes;
	 private GuestPreferences gp;
	 
	    public TemasManagedBean() {
	    	gp = new GuestPreferences();
	    	gp.setTheme("black-tie");
	        themes = new TreeMap<String, String>();  
	        themes.put("South Street", "south-street");  
	        themes.put("Casablanca", "casablanca"); 
	        themes.put("Cupertino", "cupertino"); 
	        themes.put("Vader", "vader"); 
	        themes.put("Trontastic", "trontastic"); 
	        themes.put("Swanky Purse", "swanky-purse"); 
	        themes.put("Overcast", "overcast"); 
	        themes.put("Bluesky", "bluesky"); 
	        themes.put("Black Tie", "black-tie"); 
	        themes.put("Flick", "flick");
	        themes.put("Hot Sneaks", "hot-sneaks");
	        themes.put("Home", "home");
	        themes.put("Sunny", "sunny");
	        themes.put("Smoothness", "smoothness");
	        themes.put("Glass X", "glass-x");
	        themes.put("Excite Bike", "excite-bike");
	 
	        //aqui voce pode retornar seu tema atual do banco
	    }
	    
	    @PostConstruct 
	    public void init() {  
	        meuTema = gp.getTheme();  
	    }
	 
	    public void salvarTema() {  
	    	gp.setTheme(meuTema);
	    }
	 
	    public String getMeuTema() {
	        meuTema = gp.getTheme();
	        return meuTema;
	    }
	 
	    public void setMeuTema(String meuTema) {
	    	
			this.meuTema = meuTema; 
	    }
	 
	    public void setGp(GuestPreferences gp) {
	        this.gp = gp;
	    }
	    
	    public Map<String, String> getThemes() {
	        return themes;
	    }

		public GuestPreferences getGp() {
			return gp;
		}

}
