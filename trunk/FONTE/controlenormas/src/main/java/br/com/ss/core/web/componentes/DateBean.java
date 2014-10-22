package br.com.ss.core.web.componentes;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ss.core.web.enumerated.DiaSemana;
import br.com.ss.core.web.enumerated.Meses;
import br.com.ss.core.web.enumerated.NumeroUtil;
import br.com.ss.core.web.utils.DateUtil;

@ManagedBean
@ViewScoped
public class DateBean implements Serializable {

	private static final long serialVersionUID = 2762092171307695271L;
	
	/** Data por extenso. */
	private String fullDate;
	
	public DateBean() {
		
		preencherData();
	}

	private void preencherData() {
		Date now = new Date();
		int dia = DateUtil.getDiaMes(now);
		String diaStr  = dia+  "";
		if (dia < NumeroUtil.DEZ ) {
			diaStr = "0" + dia;
		}
		int diaSemana = DateUtil.getDiaSemana(now);
		int mes = DateUtil.getMes(now);
		int ano = DateUtil.getAno(now);
		
		DiaSemana[] diasSemana = DiaSemana.values();
		
		fullDate =  diasSemana[ diaSemana - 1 ].getDescricao() + ", " + diaStr + " de " + Meses.getEnum(mes + 1).getDescricao() + " de " + ano;
	}

	public String getFullDate() {
		return fullDate;
	}

	
	public static void main(String[] args) {
		
		DateBean bean = new DateBean();
		System.out.println("Hoje: " + bean.fullDate);
		
	}
	
}
