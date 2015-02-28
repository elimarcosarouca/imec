package br.com.jm.conversor.pdf;

public enum TipoDeDocumento {
  
	TXT("txt"), RTF("rtf"), DOC("doc"), ODT("odt"), //texto
	GIF("gif"), JPG("jpg", "jpeg"), PNG("png"), TIFF("tif", "tiff"), //imagem 
	XLS("xls"), ODS("ods"), //planilha
	PDF("pdf"); //pdf
	
	private String[] extensoes;
	
	TipoDeDocumento(String... extensoes) {
	    this.extensoes = extensoes;
	}
	
	public static TipoDeDocumento getTipoDeDocumento(String extensao) {
	    for (TipoDeDocumento doc : values()) {
	        for (String ext : doc.extensoes) {
	            if (ext.equalsIgnoreCase(extensao)) {
	                return doc;
	            }
	        }
	    }
	    throw new IllegalArgumentException("Tipo de documento nao suportado para conversao para PDF");
	}
	
}