package br.fucapi.bpms.alfresco.servico;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.List;

import org.alfresco.repo.webservice.accesscontrol.AccessControlFault;
import org.alfresco.repo.webservice.administration.AdministrationFault;
import org.apache.commons.httpclient.HttpException;
import org.dom4j.DocumentException;
import org.springframework.web.client.HttpClientErrorException;

import br.fucapi.bpms.alfresco.dominio.GrupoAlfresco;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.dominio.UsuarioGrupo;

/**
 * Interface responsavel por listar os metodos de servico do Alfresco
 * 
 * @author ELIMARCOSAROUCA
 * 
 */
public interface AlfrescoServico {

	/**
	 * Metodo responsavel por autenticar usuario
	 * 
	 * @param login
	 * @param senha
	 * @return
	 * @throws DocumentException
	 * @throws HttpClientErrorException
	 */
	Usuario autenticarUsuario(String login, String senha)
			throws HttpClientErrorException, DocumentException;

	/**
	 * Metodo responsavel por listar todos os usuarios cadastrados no Alfresco
	 * 
	 * @return
	 */
	List<Usuario> getUsuarios();

	/**
	 * Metodo responsavel por retirar o usuario da aplicacao
	 * 
	 * @param ticket
	 */
	void logout(String ticket);

	/**
	 * Metodo responsavel por listar todos os usuarios de um determinado grupo
	 * 
	 * @throws RemoteException
	 * @throws AccessControlFault
	 * @return
	 */
	List<Usuario> getUsuariosPorGrupo(String nomeGrupo)
			throws AdministrationFault, RemoteException;

	/**
	 * Metodo responsavel por listar grupos por usuario retorna um objeto json
	 * 
	 * @throws RemoteException
	 * @throws AccessControlFault
	 * @throws AdministrationFault
	 * 
	 * @return
	 */
	List<GrupoAlfresco> listarGruposPorUsuario(String userName)
			throws AdministrationFault, RemoteException;

	/**
	 * Metodo responsavel por listar todos grupos retorna um objeto json
	 * 
	 * @throws RemoteException
	 * @throws AccessControlFault
	 * @throws AdministrationFault
	 * 
	 * @return
	 */
	public List<UsuarioGrupo> listarGrupos() throws RemoteException;

	/**
	 * Metodo responsavel por consultar grupo por nome retorna um objeto json
	 * 
	 * @throws RemoteException
	 * @throws AccessControlFault
	 * @throws AdministrationFault
	 * @param shortName
	 * @return
	 */
	public UsuarioGrupo listarGrupoNome(String nome) throws RemoteException;

	/**
	 * Metodo responsavel por consultar usuario por nome retorna um objeto json
	 * 
	 * @throws RemoteException
	 * @throws AccessControlFault
	 * @throws AdministrationFault
	 * @param firstName
	 * @return
	 */
	public Usuario listarUsuarioNome(String userName) throws RemoteException;

	/**
	 * Metodo responsavel por deletar determinado grupo após prévia consulta.
	 * 
	 * @throws RemoteException
	 * @throws AccessControlFault
	 * @throws AdministrationFault
	 * @param shortName
	 * @return
	 */

	void deletarGrupoNome(String nome) throws RemoteException;

	/**
	 * Metodo responsavel por atualizar arquivo
	 * 
	 * @param arquivo
	 */

	void atualizarArquivo(byte[] arquivo);

	/**
	 * Metodo responsavel por enviar arquivos para o repositorio do Alfresco.
	 * 
	 * Se 'parentUUID' e 'nomePasta' estiverem null o parametro
	 * 'destinationUUID' devera ser informado.
	 * 
	 * @param parentUUID
	 * @param nomePasta
	 * @param destinationUUID
	 * @param descricao
	 * @param ticket
	 * @param file
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	String anexarArquivo(String parentUUID, String nomePasta,
			String destinationUUID, String descricao, String ticket, File file)
			throws HttpException, IOException;

	/**
	 * Metodo responsavel por criar pastas no Alfresco, dado o SiteId e o
	 * ContainerId.
	 * 
	 * @param site
	 * @param container
	 * @param nomePasta
	 * @return
	 */
	String criarPasta(String site, String container, String nomePasta);

	/**
	 * Metodo responsavel por criar pastas no Alfresco.
	 * 
	 * Se a pasta ja existir no Alfresco sera retornado null.
	 * 
	 * @param parentUUID
	 * @param nomePasta
	 * @return
	 */
	String criarPasta(String parentUUID, String nomePasta);

	/**
	 * Metodo responsavel por fazer download de arquivo
	 * 
	 * @param nomeArquivo
	 * @param idDocument
	 * @return
	 */
	InputStream baixarArquivo(String nomeArquivo, String idDocument);

	/**
	 * Metodo reponsavel por recuperar o usuario autenticado
	 * 
	 * @param userName
	 * @return
	 */
	Usuario getUsuarioAutenticado(String userName);

	/**
	 * Metodo responsavel por recuperar Documentos utilizando um metadado como
	 * parametro de pesquisa
	 * 
	 * @param metadado
	 * @return
	 */
	String getDocumentoPorMetadado(String metadado);

	/**
	 * Metodo responsavel por retornar XML referente a arvore de diretorios do
	 * alfresco (tree)
	 * 
	 * @param idSite
	 * @return
	 * @throws DocumentException
	 */
	String getExplorerSiteTree(String idSite) throws DocumentException;

	/**
	 * Metodo responsavel por retornar XML referente a arvore de diretorios do
	 * alfresco (children)
	 * 
	 * @param idSite
	 * @return
	 * @throws DocumentException
	 */
	String getExplorerChildren(String idSite);

	/**
	 * Metodo responsavel por retornar as informacoes de um NodeRef
	 * 
	 * @param nodeRef
	 * @return
	 */
	String getConteudoNodeRef(String nodeRef);

	/**
	 * Metodo responsavel por incluir um grupo no alfresco
	 * 
	 * @param nodeRef
	 * @return
	 */
	void incluirGrupo(String shortName);

	/**
	 * Método reponsável por incluir usuario em algum grupo especifico.
	 * 
	 * @param grupo
	 * @param nome
	 */
	void incluirUsuarioGrupo(String nome, String grupo);

	/**
	 * Método reponsável por excluir usuario em algum grupo especifico.
	 * 
	 * @param nome
	 * @param grupo
	 */
	void excluirUsuarioGrupo(String nome, String grupo);

	/**
	 *  Método responsável por editar um usuário no Alfresco.
	 *  
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param email
	 */
	void editarUsuario(String userName, String firstName, String lastName, String email, Boolean enable);

	/**
	 * Metodo responsavel por inserir um novo usuário no Alfresco.
	 * 
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @return
	 * @throws DocumentException
	 */
	void incluirUsuario(String firstName, String lastName, String email,
			String userName);

	/**
	 * Metodo responsavel por pesquisar um GRUPO DE USUARIO com resultado por
	 * aproximação
	 * 
	 * @param shortName
	 * @return
	 * @throws DocumentException
	 */

	public List<UsuarioGrupo> listarGruposLike(String shortName)
			throws RemoteException;

	/**
	 * Metodo responsavel por pesquisar USUÁRIOS com resultado por aproximação
	 * 
	 * @param userName
	 * @return
	 * @throws DocumentException
	 */

	public List<Usuario> listarUsuarioNomeLike(String userName)
			throws RemoteException;

	/**
	 * Metodo responsavel por habilitar USUÁRIOS em sua configuracao
	 * 
	 * @param userName
	 * @param enabled
	 * @return
	 */
	void habilitarUsuario(String userNome, Boolean enabled);
	
	/**
	 * Metodo responsavel por alterar senha de usuário 
	 * 
	 * @param userName
	 * @return
	 */
	
	void atualizarSenha(String userName, String password);

}
