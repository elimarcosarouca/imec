package br.fucapi.bpms.activiti.util;

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
	public static String converterVariaveisToJson(Map<String, Object> variaveis) {

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
		Map<String, Object> variaveis = new HashMap<String, Object>();
		variaveis.put("emailRH", "agoravai1");
//		variaveis.put("emailDiretor", "agoravai2");
		variaveis.put("emailCoordenador", "null");

		converterVariaveisToJson(variaveis);

	}

}
