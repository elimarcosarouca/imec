package br.com.ecm.activiti.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DataUtil {

	public static String incremetarData(String data, int dias) throws ParseException {
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");  
		Date dt1 = sd.parse(data);  
		GregorianCalendar dataParaCalculo = new GregorianCalendar();
		dataParaCalculo.setTime(dt1);
		dataParaCalculo.add(GregorianCalendar.DAY_OF_MONTH, dias);
		return sd.format(dataParaCalculo.getTime());
	}
	
	public static String formatarData(String data) {  
        try {  
        	data = new SimpleDateFormat("dd/MM/yyyy")  
                    .format(new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.US)  
                            .parse(data));  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        return data;
    }

	public static void main(String args[]) throws ParseException {
		System.out.println(new Date().toString());  
		formatarData(new Date().toString());  

	}
}