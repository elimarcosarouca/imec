package br.fucapi.ads.assinaturadigital.applet.http;

import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

public class HTTPConnectionHandler {

	
	public static HttpResponse invokeURL(String url, 
										 String cookie) throws IOException,
																   ClientProtocolException {

		//Criando conexao http com o cookie de sessao
		HttpPost httpPost = new HttpPost( url  );
		httpPost.addHeader("Cookie", cookie);
		
		return new DefaultHttpClient().execute( httpPost );
	}
	
	public static HttpResponse verifyCertsOnServer( Certificate[] chain,
													String codeBase,
													String cookies) throws IOException, 
															 	   			  CertificateEncodingException {
		
		//Criando conexao http com o cookie de sessao		
		String urlSalvarDocumentosAssinados = codeBase + "vistoriarmanifestosuframa.do?metodo=verificarCadeiaCertificado";

		HttpPost httpPost = new HttpPost( urlSalvarDocumentosAssinados  );
		
		httpPost.addHeader("Cookie", cookies);
		
		MultipartEntity multipartEntity = new MultipartEntity();
		
		StringBody qtd = new StringBody( ""+chain.length);
		multipartEntity.addPart("qtd", qtd);
		
		for (int i = 0; i < chain.length; i++) {
			multipartEntity.addPart("Chain[" + i + "].pdf", 
					new ByteArrayBody(chain[i].getEncoded(), "application/pdf", "Chain[" + i + "].pdf"));
		}
		
		//Incluindo lista de parametros na requisicao
		httpPost.setEntity( multipartEntity );

		//Executando a conexao http e obtendo a resposta
		HttpResponse response = new DefaultHttpClient().execute( httpPost );
		
		return response;
	}
	
	
	public static HttpResponse enviaDocumentoAssinado(byte[] pdfAssinado,
													  String codeBase,
													  String idNumeroManifesto,
													  String dataGeracao,
													  String cookie) throws IOException {

		MultipartEntity multipartEntity = new MultipartEntity();
		
		ByteArrayBody bab = new ByteArrayBody(pdfAssinado, "application/pdf", idNumeroManifesto + ".pdf");
		StringBody data = new StringBody(dataGeracao);
		StringBody idManifesto = new StringBody(idNumeroManifesto);

		multipartEntity.addPart("pdf", bab);
		multipartEntity.addPart("data", data);
		multipartEntity.addPart("idManifesto", idManifesto);

		// Criando conexao http com o cookie de sessao
		String urlSalvarDocumentosAssinados = codeBase +
				"vistoriarmanifestosuframa.do?metodo=receberDocumentoAssinadoDigitalmente";
		HttpPost httpPost = new HttpPost(urlSalvarDocumentosAssinados);
		httpPost.addHeader("Cookie", cookie);

		// Incluindo lista de parametros na requisicao
		httpPost.setEntity(multipartEntity);

		// Executando a conexao http e obtendo a resposta
		HttpResponse response = new DefaultHttpClient().execute(httpPost);

		return response;
	}
	
	
}