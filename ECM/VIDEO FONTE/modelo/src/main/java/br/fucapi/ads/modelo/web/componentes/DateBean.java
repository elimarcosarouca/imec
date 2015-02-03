package br.fucapi.ads.modelo.web.componentes;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.fucapi.ads.modelo.utils.DateUtil;
import br.fucapi.ads.modelo.utils.NumeroUtil;

@ManagedBean
@ViewScoped
public class DateBean implements Serializable {

	private static final long serialVersionUID = 2762092171307695271L;
	
	/** Data por extenso. */
	private String fullDate;
	
	public DateBean() {
		
	}
	public String getFullDate() {
		return fullDate;
	}

	
	public static void main(String[] args) {
		
		DateBean bean = new DateBean();
		System.out.println("Hoje: " + bean.fullDate);
		
	}
	
}
