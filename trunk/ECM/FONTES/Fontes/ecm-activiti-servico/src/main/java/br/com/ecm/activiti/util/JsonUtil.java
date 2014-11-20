package br.com.ecm.activiti.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsonUtil {

	/**
	 * Metodo responsvel por converter uma lista de variaveis para um json
	 * 
	 * @param variaveis
	 * @return String
	 * 
	 */
	public static String converterVariaveisToJson(Map<String, String> variaveis) {

		Set<String> chaves = variaveis.keySet();

		String json = " [ ";

		for (String chave : chaves) {
			json = json + "{ \"name" + "\":\"" + chave + "\", " + "\"value"
					+ "\":\"" + variaveis.get(chave) + "\"}, ";
		}
		json = json + "]";
		json = json.replace("}, ]", "}]");
		System.out.println(json);
		return json;
	}

	public static void main(String[] args) {
		Map<String, String> variaveis = new HashMap<String, String>();
		variaveis.put("emailRH", "agoravai1");
//		variaveis.put("emailDiretor", "agoravai2");
		variaveis.put("emailCoordenador", "null");

		converterVariaveisToJson(variaveis);

	}

}
