package br.fucapi.ads.modelo.utils;

import java.util.Calendar;
import java.util.Date;

public class GeralUtils {

	public static Date gerarDataNotificacao(Date dataBase, int dias){
		Calendar c = Calendar.getInstance();
		c.setTime(dataBase);
		c.add(Calendar.DAY_OF_YEAR, -dias);
		return c.getTime();
	}
}
