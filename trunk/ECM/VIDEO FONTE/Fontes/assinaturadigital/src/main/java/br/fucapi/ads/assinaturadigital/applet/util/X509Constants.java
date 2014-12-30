package br.fucapi.ads.assinaturadigital.applet.util;

public enum X509Constants {
	
	SUCESSO("1001"),
	EX_EXPIRADO("1002"),
	EX_REPOSITORIO("1003"),
	EX_INVALIDO_AC("1004"),
	EX_INVALIDO_CPF("1005"),
	EX_INVALIDO_CERT_PATH("1006"),
	EX_INVALIDO_OSCP_CHECK("1007"),
	EX_KEY_STORE("1008"),
	EX_IO("1009"),
	EX_CERTIFICATE("1010"),
	EX_NO_SUCH_ALGORITHM("1011"),
	EX_REVOGADO("1012"),
	EX_APP_SERVICE_ERROR("1013")
	;
	
	
	private String codigo;
	
	private X509Constants(String codigo) {
		this.codigo = codigo;
	}
	
	public String toString() {		
		return codigo;
	};

}