package br.fucapi.bpms.alfresco.teste;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
 
public class CreateFolder {
 
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
 
		String host = "170.10.200.224:8080";
		int port = 8080;
		String username = "admin";
		String password = "admin";
		String parentFolder = "2d5ca5f7-5dfb-4b88-bd1a-95f1683f37d5";
		String folderName = "sales3";
		String description = "sales space3";
		String url = "http://" + host + ":" + port + "/alfresco/service/api/path/workspace:SpacesStore/i/" + parentFolder + "/children";
		String contentType = "application/atom+xml;type=entry";
 
		String xml = 
			"<?xml version='1.0' encoding='utf-8'?>\n" +
			"<entry xmlns='http://www.w3.org/2005/Atom' xmlns:cmis='http://www.cmis.org/2008/05'>\n" +
			"<title>" + folderName + "</title>\n" +
			"<summary>" + description + "</summary>\n" +
			"<cmis:object>\n"+
			"<cmis:properties>\n" +
			"<cmis:propertyString cmis:name='ObjectTypeId'>\n" +
			"<cmis:value>folder</cmis:value>\n" +
			"</cmis:propertyString>\n" +
			"</cmis:properties>\n" +
			"</cmis:object>\n" +
			"</entry>\n";
 
		DefaultHttpClient httpclient = new DefaultHttpClient();
 
		httpclient.getCredentialsProvider().setCredentials(
				new AuthScope(host, port),
				new UsernamePasswordCredentials(username, password));
 
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Content-type", contentType);
 
		StringEntity requestEntity = new StringEntity(xml, "UTF-8");
		httppost.setEntity(requestEntity);
 
 
		System.out.println("executing request" + httppost.getRequestLine());
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
 
		System.out.println("----------------------------------------");
		System.out.println(response.getStatusLine());
		if (entity != null) {
			System.out.println("Response content type: " + entity.getContentType());
			long contentLength = entity.getContentLength();
			System.out.println("Response content length: "
					+ entity.getContentLength());
 
			if (contentLength > 0) {
				byte [] b = new byte[(int) contentLength];
				entity.getContent().read(b);
				System.out.println("Response content: " + new String(b));
			}
 
			entity.writeTo(System.out);
		}
 
		// When HttpClient instance is no longer needed,
		// shut down the connection manager to ensure
		// immediate deallocation of all system resources
		httpclient.getConnectionManager().shutdown();
		long end = System.currentTimeMillis();
		System.out.println("Time spend: " + (end-start) + "ms");
	}
}