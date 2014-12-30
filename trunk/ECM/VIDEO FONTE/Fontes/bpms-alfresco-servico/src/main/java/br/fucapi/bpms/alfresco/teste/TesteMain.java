package br.fucapi.bpms.alfresco.teste;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.alfresco.repo.search.AVMSnapShotTriggeredIndexingMethodInterceptorImpl.StoreType;
import org.alfresco.repo.webservice.accesscontrol.SiblingAuthorityFilter;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.dom4j.DocumentException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.extensions.surf.util.XMLUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.dominio.UsuarioGrupo;
import br.fucapi.bpms.alfresco.dominio.UsuarioGrupoEntidade;
import br.fucapi.bpms.alfresco.util.CustomHttpState;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class TesteMain {
	
	private static RestTemplate restTemplate;
	private static CustomHttpState httpState;
	private static Properties properties;
	private static ApplicationContext applicationContext;
	
	private static String xml = 
			"<?xml version='1.0' encoding='utf-8'?>\n" +
			"<entry xmlns='http://www.w3.org/2005/Atom' xmlns:cmis='http://www.cmis.org/2008/05'>\n" +
			"<title>{titulo}</title>\n" +
			"<summary>{sumario}</summary>\n" +
			"<cmis:object>\n"+
			"<cmis:properties>\n" +
			"<cmis:propertyString cmis:name='ObjectTypeId'>\n" +
			"<cmis:value>{tipo}</cmis:value>\n" +
			"</cmis:propertyString>\n" +
			"</cmis:properties>\n" +
			"</cmis:object>\n" +
			"</entry>\n";
	
	public static void main(String[] args) throws DocumentException {
		applicationContext = new ClassPathXmlApplicationContext(
				"META-INF/spring/applicationContext.xml");

		restTemplate = (RestTemplate) applicationContext.getBean("restTemplateAlfresco");
		properties = (Properties) applicationContext.getBean("alfrescoproperties");
		
		String ticket = autenticarUsuario("admin","admin");
//		String ticket = autenticarUsuarioViaPost();
//		Usuario usuario = getUsuarioAutenticado("admin");
//		usuario.setTicket(ticket);
		
//		upload();
//		uploadArquivo(ticket);
		createDocument("workspace://SpacesStore/9b9a11aa-3f64-4d1b-b2db-efaa30391392", xml);
//		createFolder("02_2013", "Eng. Produtos", "SOLICITACOES");
	}

	
	public static String createFolder(String nomePasta, String site, String container) {
		
		String uri = "http://localhost:9090/alfresco/service/api/site/folder/"+site+"/"+container;
//		String uri = "http://localhost:9090/alfresco/service/cmis/s/workspace:SpacesStore/i/6f00ba2e-86c0-46f7-b062-d229ca78f8e8/children";
		
		String json = "{\"name\": \""+nomePasta+"\" }";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set( "Content-type", "application/json");
		
		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		
		ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

		System.out.println(response.getBody());
		
		return null;
	}

	
	/*public static void teste() {
		Store STORE = new Store(Constants.WORKSPACE_STORE, "SpacesStore");
		
		 // Create  reference to Contratos
		Reference catRef = new Reference(STORE,"79839838-563e-48e3-a5f6-a836cd68c0e6", null);
		
		Predicate predicate = new Predicate(new Reference[] { catRef },
				STORE, null);
		
		Pasta pasta = new Pasta(AuthenticationUtils.getAuthenticationDetails());
		pasta.criarPasta(parent,"teste"+System.currentTimeMillis());
		
		org.alfresco.repo.webservice.types.NamedValue[] listaMetadados = pasta.getListaPropriedadesNo(predicate);
		System.out.println("Nome Metadado:" + listaMetadados[0].getName());
		System.out.println("Valor Metadado:" + listaMetadados[0].getValue());
	
	}*/

	public static Usuario getUsuarioAutenticado(String userName) {
		String uri = MessageFormat.format(properties.getProperty("alfresco.server.usuario.autenticado"), userName);
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		return Usuario.fromJsonToUsuario(response.getBody());
	}
	
	/**
	 * Metodo responsavel por retornar um novo ticket de autenticacao via POST 
	 * @throws DocumentException 
	 */
	public static String autenticarUsuarioViaPost() throws DocumentException {
		String uri = "http://localhost:9090/alfresco/service/api/login";
		
		String json = "{\"username\":\"admin\",\"password\":\"admin\"}";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set( "Content-type", "application/json");
		
		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
		
		System.out.println(response.getBody());
		
		Map<String, Object> deserialized = new JSONDeserializer<Map<String,Map<String,Object>>>()
			    .use("data.values", Object.class ).deserialize(response.getBody()).get("data");
		
		return null; //parceXML(response.getBody());
	}
	
	/**
	 * Metodo responsavel por autenticar usuario
	 * @throws DocumentException
	 */
	public static String autenticarUsuario(String usuario, String senha) throws DocumentException{
		
		String ticket = null;
		System.out.println("\nMETODO AUTENTICAR USUARIO\n");
		try {
			String uri = MessageFormat.format(properties.getProperty("alfresco.server.login.get"), usuario, senha);
//			String uri = "http://localhost:9090/alfresco/service/api/login?u=admin&pw=admin";
			
			System.out.println(uri);
			ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
			ticket = parceXML(response.getBody());
			System.out.println(ticket);
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		// Bloco responsavel por setar as credenciais do usuario no restTemplate
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(usuario,senha);
		httpState = (CustomHttpState) applicationContext.getBean("stateAlfresco");
				
		httpState.setCredentials(credentials);
		
		return ticket;
	}
	
	/**
	 * Metodo responsavel por validar o usuario
	 * @param ticket
	 * @param properties
	 */
	public static void validarUsuario(String ticket) {
		System.out.println("\nMETODO VALIDAR USUARIO\n");
		String uri = MessageFormat.format(properties.getProperty("alfresco.server.ticket"), ticket);
		ResponseEntity<String> response = null;
		String result = null;
		try {
			response = restTemplate.getForEntity(uri, String.class);
			result = response.getStatusCode().toString();
		} catch (HttpClientErrorException e) {
			result = e.getMessage();
		}
		
		System.out.println("Status Code: " +result);
	}
	
	/**
	 * Metodo responsavel por retirar o usuario da sessao do alfresco
	 * @param ticket
	 */
	public static void logout(String ticket) {
		System.out.println("\nMETODO LOGOUT\n");
		try {
			URI u = new URI(MessageFormat.format(properties.getProperty("alfresco.server.ticket"), ticket));
			restTemplate.delete(u);
			limparCredenciais();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public static void repositorio() {
		
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:9090/alfresco/service/api/cmis", String.class);
			
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(response.getBody());
			System.out.println(document);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void postMetadado(){
//		http://localhost:9090/alfresco/service/api/metadata/node/{store_type}/{store_id}/{id}
		
		String storeId = "427e12a7-e2e5-4adb-ad0e-a7f60d4e3ea5", id = "null";
		
		String uri = "http://localhost:9090/alfresco/service/api/metadata/node/"+StoreType.AUTHOR+"/"+storeId+"/"+id;
		
		ResponseEntity<String> response = restTemplate.postForEntity(uri, null, String.class);
		
		System.out.println(response);
	
//		workspace://SpacesStore/427e12a7-e2e5-4adb-ad0e-a7f60d4e3ea5
		
//		new NodeServiceImpl().
		
	}
	
	/**
	 * Metodo responsavel por listar todas as pessoas do repositorio Alfresco
	 */
	public static void getPeople() {
//		http://localhost:9090/alfresco/service/api/people?filter={filterQuery?}
		
//		FilteredQuery filter = new FilteredQuery(new Quer, filter)
		SiblingAuthorityFilter filter = new SiblingAuthorityFilter();
		filter.setAuthorityType("ADMIN");
		
		// Retornar todos os usuarios
//		String uri = SERVER_PATH+"/people";
		
		// Retornar usuario especifico utilizando o primeiro nome ou o ultimo nome
		String uri = "http://localhost:9090/alfresco/service/api/people?filter=ferreira";
		
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		
		System.out.println(response.getBody());
	}
	
	/**
	 * Metodo reponsavel por recuperar um usuario especifico pelo userName
	 * @param userName
	 */
	public static void getPerson(String userName) {
		
		String uri = "http://localhost:9090/alfresco/service/api/people/claudemirferreira";
		
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		
		System.out.println(response.getBody());
	}
	
	// TODO - Problemas com arquivos nao encontrado (template org/alfresco/cmis/checkedout.post.atomentry)
	public static void checkedoutDocuments() {
//		String uri = "http://localhost:9090/alfresco/service/cmis/checkedout?folderId=workspace://SpacesStore/6e3fc0ee-893d-4c40-891d-8ff9cb79cc90";

		String uri = "http://localhost:9090/alfresco/s/cmis/checkedout"; // Url correta
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		
		//content-type:application/atom+xml;type=entry
		
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_ATOM_XML);
//		HttpHeaders headers = new HttpHeaders();
//		headers.set( "Accept", "application/atom+xml;type=entry;charset=UTF-8" );
//		HttpEntity<String> request = new HttpEntity<String>(headers);
//		Object response = restTemplate.postForEntity(uri, request, Object.class);
		
		System.out.println(response.getBody());
		
//		workspace://SpacesStore/2d5ca5f7-5dfb-4b88-bd1a-95f1683f37d5
//		/cmis/checkedout?folderId={folderId?}&amp;includeDescendants={includeDescendants?}&amp;filter={filter?}&amp;skipCount={skipCount?}&amp;maxItems={maxItems?}&amp;includeAllowableActions={includeAllowableActions?}&amp;includeRelationships={includeRelationships?}&amp;renditionFilter={renditionFilter?}
//		http://localhost:9090/alfresco/service/api/checkedout?folderId={folderId?}&includeDescendants={includeDescendants?}&filter={filter?}&skipCount={skipCount?}&maxItems={maxItems?}
		
	}
	
	/**
	 * Metodo responsavel por recuperar as informações de um folder
	 * @param codigoFolder
	 */
	public static void getFolder(String folderId) {
		//http://localhost:9090/alfresco/service/api/node/{store_type}/{store_id}/{id}/children?types={types}&filter={filter?}&skipCount={skipCount?}&maxItems={maxItems?}
		String uri = "http://localhost:9090/alfresco/service/cmis/s/workspace:SpacesStore/i/"+folderId;

		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		
		System.out.println(response);
	}
	
	/**
	 * Metodo responsavel por recuperar todos os descendentes de um folder
	 * @param folderId
	 */
	public static void getDescendentes(String folderId) {
		String uri = "http://localhost:9090/alfresco/service/cmis/s/workspace:SpacesStore/i/"+folderId+"/descendants";

		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		
		System.out.println(response);
	}
	
	/**
	 * Metodo responsavel por recuperar as informações de um node especifico
	 * @param childrenId
	 */
	public static ResponseEntity<String> getChildren(String childrenId) {
		String uri = "http://localhost:9090/alfresco/service/cmis/s/workspace:SpacesStore/i/"+childrenId;
		
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		
		System.out.println(response);
		
		return response;
	}
	
	/**
	 * Metodo responsavel por realizar upload de documentos
	 */
	public static void createDocument(String folderId, String xml) {
		String uri = "http://localhost:9090/alfresco/service/cmis/s/workspace:SpacesStore/i/"+folderId+"/children";
		
		String xml2 = xml.replace("{titulo}", "Document"+System.currentTimeMillis()+".txt").replace("{sumario}", "Sumario"+System.currentTimeMillis()).replace("{tipo}", "Document");
		
		HttpHeaders headers = new HttpHeaders();
		headers.set( "Content-type", "application/atom+xml;type=entry");
		
		HttpEntity<String> request = new HttpEntity<String>(xml2, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
		System.out.println(response);
	}
	
	/**
	 * Metodo responsavel por criar pasta
	 */
	public static void createFolder(String folderId, String xml) {
		String uri = "http://localhost:9090/alfresco/service/cmis/s/workspace:SpacesStore/i/"+folderId+"/children";
		
		String xml2 = xml.replace("{titulo}", "Folder"+System.currentTimeMillis()).replace("{sumario}", "Sumario"+System.currentTimeMillis()).replace("{tipo}", "Document");
		
		HttpHeaders headers = new HttpHeaders();
		headers.set( "Content-type", "application/atom+xml;type=entry");
		
		HttpEntity<String> request = new HttpEntity<String>(xml2, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
		System.out.println(response);
	}
	
	/**
	 * Metodo responsavel por realizar o download do arquivo
	 * @param idDocument
	 * @param nomeArquivo
	 * @param pathLocal
	 * @return
	 */
	public static InputStream baixarDocumento(String idDocument, String nomeArquivo, String pathLocal) {
		
		nomeArquivo = "logoSIEMENS.pdf";

		idDocument = "a2b5bc88-e35f-4408-b854-77ec1606bfac";
		
		String repositorioId = "73ee8c7e-16ef-47a6-86b4-7248f9e32bef";
		
		String uri = "http://localhost:9090/alfresco/cmisatom/"+repositorioId+"/content/"+nomeArquivo+"?id=workspace://SpacesStore/"+idDocument;
		
		ResponseEntity<byte[]> response = restTemplate.getForEntity(uri, byte[].class);
		
		byte[] documento = response.getBody();
		
		
//		FileInputStream inputStream = FileInputStream()
		
//		try {			
//			FileOutputStream fos = new FileOutputStream(pathLocal+nomeArquivo);
//			fos.write(documento);
//			fos.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
//		File file = new File(pathLocal+nomeArquivo);
		
		InputStream inputStream = new ByteArrayInputStream(documento);
		
		return inputStream; 
	}
	
	/**
	 * Metodo responsavel por limpar as credenciais do usuario
	 */
	private static void limparCredenciais(){
		httpState.clear();
		httpState.clearCredentials();	
		httpState.clearProxyCredentials();
		httpState.clearCookies();
	}
	
	public static String toJson(Object o) {
		return new JSONSerializer().exclude("*.class").deepSerialize(o);
	}
	
	public static String parceXML(String body) throws DocumentException {
		return XMLUtil.parse(body).getRootElement().getText();
	}
	
	public static void uploadArquivo(String ticket) {

		File f = new File("C:/WSF_Client.pdf");

//		String destinoURI = "http://localhost:9090/alfresco/service/api/upload?alf_ticket=";
//		String destinoURI = "http://localhost:9090/alfresco/upload/workspace/SpacesStore/9d5578a0-af8c-4a7d-9127-6b6231ffa292/WSF_Client.pdf?"+ ticket;
		
		try {
			String destinoURI = "http://localhost:9090/alfresco/service/api/upload?alf_ticket=";
			
			HttpClient client = new HttpClient();
			PostMethod mPost = new PostMethod(destinoURI);
			
			Part[] parts = {
					new FilePart("filedata", f.getName(), f, null, null),
					new StringPart("filename", f.getName()),
					new StringPart("description", "TESTE"),
					new StringPart("siteid", "Eng. Produtos"),
					new StringPart("containerid", "02_2013")
//					,new StringPart("path", "")
			};
			
			mPost.setRequestEntity(new MultipartRequestEntity(parts, mPost.getParams()));
			client.executeMethod(mPost);
			
			Map<String, Object> deserialized = new JSONDeserializer<Map<String,Object>>()
					.deserialize(mPost.getResponseBodyAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		HttpHeaders headers = new HttpHeaders();
//		headers.set( "Content-type", "application/pdf");
		
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("filedata", f);
//		params.put("filename", f.getName());
//		params.put("siteid", "Eng. Produtos");
//		params.put("containerid", "011_2013");
		
//		HttpEntity<String> request = new HttpEntity<String>(null, headers);
		
//		restTemplate.put(destinoURI, request, params);
//		ResponseEntity<String> resposta = restTemplate.postForEntity(destinoURI, request, String.class, params);
		
		
//		String resposta = FileUpload.uploadDocument(ticket, f, "Upload_Content.pdf",
//				"application/pdf", "description", destinoURI);
		
//		System.out.println(resposta);
		
	}
	
	/*public static String upload(File file, String siteId, String containerId, String uploadDirectory, String ticket) {
		 
		file = new File("C:/WSF_Client.pdf");
		siteId = "2d5ca5f7-5dfb-4b88-bd1a-95f1683f37d5";
		containerId = "";
		uploadDirectory = "";
		
		String json = null;
		
//		workspace://SpacesStore/2d5ca5f7-5dfb-4b88-bd1a-95f1683f37d5
  
		String uri = "http://localhost:9090/alfresco/service/api/upload?alf_ticket=" + ticket;
//		String uri = "http://localhost:9090/alfresco/service/api/upload?alf_ticket=" + ticket;

		HttpHeaders headers = new HttpHeaders();
		headers.set( "Content-type", "application/pdf");
 
//			FileBody bin = new FileBody(file);
//			StringBody siteid = new StringBody(siteId);
//			StringBody containerid = new StringBody(containerId);
//			StringBody uploaddirectory = new StringBody(uploadDirectory);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("filedata", file);
		params.put("siteid", siteId);
		params.put("containerid", containerId);
		params.put("uploaddirectory", uploadDirectory);
		
		HttpEntity<String> request = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class, params);
		
//			MultipartRequestEntity reqEntity = new MultipartRequestEntity();
//			reqEntity.addPart("filedata", bin);
//			reqEntity.addPart("siteid", siteid);
//			reqEntity.addPart("containerid", containerid);
//			reqEntity.addPart("uploaddirectory", uploaddirectory);
  
		return json;
    }*/
	
	public static void upload() throws DocumentException {
		
		File f = new File("C:/WSF_Client.pdf");

		// FileInputStream is=new FileInputStream(f);
//		FileUpload.uploadDocument(autenticarUsuario("admin", "admin"), f, "Upload_Content.pdf",
//				"application/pdf", "description", "http://localhost:9090/alfresco/service/api/upload?alf_ticket=");

		String ticket = autenticarUsuario("admin", "admin");
		String uri = MessageFormat.format(properties.getProperty("alfresco.server.upload"), ticket);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set( "Content-type", "application/pdf");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("filedata", f);
//		params.put("filename", f.getName());
		params.put("siteid", "Eng. Produtos");
		params.put("containerid", "011_2013");
		
		String json = "{\"filedata\" : \""+f+"\"," +
				"\"siteid\" : \"Eng. Produtos\"," +
				"\"containerid\" : \""+System.currentTimeMillis()+"_2013"+"\"}";
		
		HttpEntity<String> request = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class, params);
		
//		ResponseEntity<String> response = restTemplate.postForEntity(uri, request, responseType);
		
	}

	
	public static void getUsuariosPorGrupo(String nomeGrupo) {
		String uri = MessageFormat.format(properties.getProperty("alfresco.server.usuarios.grupo"), "REVISOR_APROVADOR");
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		
		UsuarioGrupoEntidade usuarioEntidade = UsuarioGrupoEntidade.fromJsonToUsuarioGrupoEntidade(response.getBody());
		
		for (UsuarioGrupo usuarioGrupo : usuarioEntidade.getData()) {
			System.out.println("Nome do usuario: "+ usuarioGrupo.getFullName());
			System.out.println("login do usuario: "+ usuarioGrupo.getShortName());
		}
	}
}
