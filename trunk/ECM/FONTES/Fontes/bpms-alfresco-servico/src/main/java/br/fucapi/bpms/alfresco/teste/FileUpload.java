package br.fucapi.bpms.alfresco.teste;

import java.io.File;
import java.util.Map;

import org.alfresco.cmis.client.AlfrescoDocument;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

import flexjson.JSONDeserializer;

/**
 * @author aaa
 * 
 */
public class FileUpload {

	public static String uploadDocument(String authTicket, File fileobj, 
			String filename, String filetype, String description, String destinationURI) {
	
		String resposta = null;
		Map<String, Object> deserialized = null;
		try {
			String urlString = destinationURI + authTicket;
			System.out.println("The upload url:::" + urlString);
			HttpClient client = new HttpClient();

			PostMethod mPost = new PostMethod(urlString);

			Part[] parts = {
					new FilePart("filedata", filename, fileobj, filetype, null),
					new StringPart("filename", filename),
					new StringPart("description", description),
					new StringPart("siteid", "Eng. Produtos"),
					new StringPart("containerid", "011_2013")
			};
			
			mPost.setRequestEntity(new MultipartRequestEntity(parts, mPost.getParams()));
			int statusCode1 = client.executeMethod(mPost);
			resposta = "statusLine>>>" + statusCode1 + "......" + "\n status line \n" + mPost.getStatusLine() + "\nbody \n" + mPost.getResponseBodyAsString();
			mPost.releaseConnection();
			
			System.out.println(resposta);
			
			deserialized = new JSONDeserializer<Map<String,Object>>()
					.deserialize(mPost.getResponseBodyAsString());

		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		return deserialized.get("nodeRef").toString();
	}
}