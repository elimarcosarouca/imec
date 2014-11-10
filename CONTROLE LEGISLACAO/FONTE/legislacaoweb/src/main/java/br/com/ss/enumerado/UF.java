package br.com.ss.enumerado;

public enum UF {

	AC("AC","Acre"),
    AL("AL","Alagoas"),
    AM("AM","Amazonas"),
    AP("AP","Amapa"),
    BA("BA","Bahia"),
    CE("CE","Ceara"),
    DF("DF","Distrito Federal"),
    ES("ES","Espirito Santo"),
    GO("GO","Goias"),
    MA("MA","Maranhao"),
    MG("MG","Minas Gerais"),
    MT("MT","Mato Grosso"),
    MS("MS","Mato Grosso do Sul"),
    PA("PA","Para"),
    PB("PB","Paraiba"),
    PE("PE","Pernambuco"),
    PI("PI","Piaui"),
    PR("PR","Parana"),
    RJ("RJ","Rio de Janeiro"),
    RN("RN","Rio Grande do Norte"),
    RO("RO","Rondonia"),
    RR("RR","Roraima"),
    RS("RS","Rio Grande do Sul"),
    SC("SC","Santa Catarina"),
    SE("SE","Sergipe"),
    SP("SP","Sao Paulo"),
    TO("TO","Tocantins");


	private String id;
	private String descricao;

	private UF(String id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public static UF getEnum(String id) {
		for (UF sit : UF.values()) {
			if (sit.id == id)
				return sit;
		}
		return null;
	}

	public String getId() {
		return id;
	}
	public String getDescricao() {
		return this.descricao;
	}
}