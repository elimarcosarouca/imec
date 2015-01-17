package br.fucapi.ads.modelo.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Classe utilitaria para ser utilizado em todo o projeto
 * 
 * @author Elimarcos Arouca
 *
 */
public class Utils {

	// Calcula a data certa para ser enviado notificacao
	public static Date gerarDataNotificacao(Date dataBase, int diasToRemove) {
		
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataBase);
        calendar.add(Calendar.DAY_OF_YEAR, -diasToRemove);
        
		return Calendar.getInstance().getTime();
	}
	
}
