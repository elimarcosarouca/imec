package br.fucapi.ads.modelo.utils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

public abstract class GeralUtils {

	public static Date gerarDataNotificacao(Date dataBase, int dias){
		Calendar c = Calendar.getInstance();
		c.setTime(dataBase);
		c.add(Calendar.DAY_OF_YEAR, -dias);
		return c.getTime();
	}
	

	public static File converterFileUploadToFile(UploadedFile uploadFile) throws IOException {
		File fileTemp = new File(uploadFile.getFileName());
		FileUtils.copyInputStreamToFile(uploadFile.getInputstream(), fileTemp);
		return fileTemp;
	}
	
	public static File criarDocumentoTemporario (String nomeTemp, String extensao) throws IOException {
		return File.createTempFile(nomeTemp, "."+extensao);
	}
	
	public static UploadedFile converterFileToUploadedFile(File file) {

		return null;
	}
}
