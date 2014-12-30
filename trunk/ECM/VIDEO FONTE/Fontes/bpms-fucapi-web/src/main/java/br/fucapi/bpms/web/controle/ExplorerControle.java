package br.fucapi.bpms.web.controle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;

import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;
import br.fucapi.bpms.web.dominio.dto.DocumentoResumoDTO;
import br.fucapi.bpms.web.dominio.dto.Entry;
import br.fucapi.bpms.web.dominio.dto.Feed;
import br.fucapi.bpms.web.dominio.dto.PropertyDateTime;
import br.fucapi.bpms.web.dominio.dto.PropertyId;
import br.fucapi.bpms.web.dominio.dto.PropertyString;
import br.fucapi.bpms.web.repositorio.OrigemRepositorio;
import br.fucapi.bpms.web.xml.CastorUtil;


@ManagedBean(name = "explorerControle")
@SessionScoped
public class ExplorerControle implements Serializable {

	private static final long serialVersionUID = 1L;

	private TreeNode root;
	private TreeNode selectedNode;
	
	private Feed feedTree;
	private String nomeDesenho;
	private DocumentoResumoDTO documentoResumo;
	private List<DocumentoResumoDTO> documentos;
	
	private UploadedFile arquivoUpload;
	
	@ManagedProperty(value = "#{origemRepositorioImpl}")
	private OrigemRepositorio origemRepositorio;

	@ManagedProperty(value = "#{alfrescoServicoImpl}")
	private AlfrescoServico alfrescoServico;
	
	@ManagedProperty(value = "#{bpmswebproperties}")
	private Properties bpmswebproperties;
	
	@ManagedProperty(value = "#{castorUtil}")
	private CastorUtil castorUtil;
	
	private Map<String, DocumentoResumoDTO> paramsDocumentosUUI;
	private Map<String, String> paramsPastasUUI;
	private String pastaSelecionada;
	
	private StreamedContent file;
	
	private Usuario usuario;
	
	public String init() {
		
		this.usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuarioLogado");
		
		this.paramsDocumentosUUI = new HashMap<String, DocumentoResumoDTO>();  
		this.paramsPastasUUI = new HashMap<String, String>();
		this.documentoResumo = new DocumentoResumoDTO();
		this.documentos = new ArrayList<DocumentoResumoDTO>();
		this.treeAlfresco();
		
		return "pages/repositorio/desenho.xhtml";
	}
		
	public void treeAlfresco() {
		try {
			String responseFeed = alfrescoServico.getExplorerChildren(bpmswebproperties.getProperty("bpms.fucapi.siemens.uuid"));
			
			this.feedTree = (Feed) castorUtil.convertFromXMLToObject(Feed.class, getClass().getResource("/castor/feed.xml").getFile(), responseFeed);
			
			root = new DefaultTreeNode("root", null);
			
			DocumentoResumoDTO documentoDTO = new DocumentoResumoDTO();
			
			if (this.feedTree.getTitle() != null && this.feedTree.getTitle().contains(" Children")){
				this.feedTree.setTitle(this.feedTree.getTitle().replace(" Children", ""));
			}
			
			documentoDTO.setNomeDocumento(this.feedTree.getTitle());
			
			if (this.feedTree.getId() != null) {
				if (this.feedTree.getId().contains("urn:uuid:")) {
					this.feedTree.setId(this.feedTree.getId().replace("urn:uuid:", ""));
				} 
				if (this.feedTree.getId().contains("-children")) {
					this.feedTree.setId(this.feedTree.getId().replace("-children", ""));					
				}
			}
			documentoDTO.setUuidDocumento(this.feedTree.getId());
			
			TreeNode documents = new DefaultTreeNode(documentoDTO, root);
			this.tree(feedTree.getEntrys(), documents);
		} catch (MarshalException e) { 
			e.printStackTrace();
		} catch (ValidationException e) { 
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		} catch (MappingException e) { 
			e.printStackTrace(); 
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void tree(List<Entry> entrys, TreeNode documents) throws MarshalException, ValidationException, IOException, MappingException, ParseException {
		
		if (entrys!= null) {
			for (Entry entry : entrys) {
				String tipo = null;
				String nomeObject = null;
				String uuidObject = null;
				String lastVersion = null;
				DocumentoResumoDTO documentoDTO = null;
				Date dataModificacao = null;
	
				for (PropertyId propertyId : entry.getObject().getProperties().getPropertiesId()) {
					if (propertyId.getDefinitionId() != null && propertyId.getDefinitionId().equals("cmis:objectTypeId")) {
						if (propertyId.getValue() != null && propertyId.getValue().equals("cmis:folder")) {
							tipo = "Folder";
							continue;
						}
					} else if (propertyId.getDefinitionId() != null && propertyId.getDefinitionId().equals("cmis:objectId")) {
						if (propertyId.getValue() != null)
							uuidObject = propertyId.getValue();
					}
					if (tipo != null && uuidObject != null) break;
				}
				
				for (PropertyString propertyString : entry.getObject().getProperties().getPropertiesString()) {
					if (propertyString.getDefinitionId() != null && propertyString.getDefinitionId().equals("cmis:name") && propertyString.getValue() != null) {
						nomeObject = propertyString.getValue();
					} else if (propertyString.getDefinitionId().equals("cmis:versionLabel") && propertyString.getValue() != null) {
						lastVersion = propertyString.getValue();
					}
					
					if ((tipo != null && tipo.equals("Folder") && nomeObject != null) ||
							(nomeObject != null && lastVersion != null)) break;
				}
				
				for (PropertyDateTime propertyDateTime : entry.getObject().getProperties().getPropertiesDateTime()) {
					if(propertyDateTime.getDefinitionId().equals("cmis:lastModificationDate") && propertyDateTime.getValue() != null) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
						dataModificacao = sdf.parse(propertyDateTime.getValue());
					}
				}
				
				if (tipo != null && tipo.equals("Folder")) {
					DocumentoResumoDTO d = new DocumentoResumoDTO();
					d.setNomeDocumento(nomeObject);
					d.setUuidDocumento(uuidObject);
					
					TreeNode work = new DefaultTreeNode(d, documents);
					
					// Seta no node uma pasta vazia. (com isso a função expandir da pasta e habilitada)
					new DefaultTreeNode(null, work);
					
					this.paramsPastasUUI.put(nomeObject, uuidObject);
					
				} else {
					documentoDTO = new DocumentoResumoDTO();
					documentoDTO.setNomeDocumento(nomeObject);
					documentoDTO.setUuidDocumento(uuidObject);
					documentoDTO.setLastVersion(lastVersion);
					documentoDTO.setLastModification(dataModificacao);
					
					// o correto eh setarmos a variavel "tipoArquivo"
					new DefaultTreeNode("document", documentoDTO, documents);
					
					paramsDocumentosUUI.put(documentoDTO.getNomeDocumento(), documentoDTO);
				}
			}
		}
	}
	
	public void baixarArquivo(DocumentoResumoDTO documentoResumoDTO) {
		String nomeDocumento = documentoResumoDTO.getNomeDocumento();
		InputStream stream = alfrescoServico.baixarArquivo(nomeDocumento, 
				((DocumentoResumoDTO)this.paramsDocumentosUUI.get(nomeDocumento)).getUuidDocumento());
		
		this.file = new DefaultStreamedContent(stream, null, nomeDocumento);
	}
	
	public String anexarArquivo() {
		if (this.pastaSelecionada != null && this.paramsPastasUUI.get(pastaSelecionada) != null) {
			if (this.arquivoUpload != null) {
				File fileUploadAux = new File(this.arquivoUpload.getFileName());
				try {
					FileUtils.copyInputStreamToFile(this.arquivoUpload.getInputstream(), fileUploadAux);
					String uuid = alfrescoServico.anexarArquivo(null, "", this.paramsPastasUUI.get(pastaSelecionada), "Novo arquivo", this.usuario.getTicket(), fileUploadAux);
					
					if (!uuid.equals(null)) {
						DocumentoResumoDTO doc = new DocumentoResumoDTO();
						doc.setUuidDocumento(uuid);
						doc.setNomeDocumento(this.arquivoUpload.getFileName());
						paramsDocumentosUUI.put(this.arquivoUpload.getFileName(), doc);
						this.init();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				FacesMessage msg = new FacesMessage("O arquivo " + this.arquivoUpload.getFileName() + " foi incluído na pasta " + "", this.arquivoUpload.getFileName() + " ");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O arquivo não foi selecionado.", ""));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "A pasta de destino não foi selecionada.", ""));
		}

		return "pages/repositorio/desenhos.xhtml";
	}
	
	public void pesquisarDesenhos() {
		if (paramsDocumentosUUI.containsKey(this.nomeDesenho)) {
			documentos = new ArrayList<DocumentoResumoDTO>();
			documentos.add(paramsDocumentosUUI.get(this.nomeDesenho));
		} else {
			documentos = new ArrayList<DocumentoResumoDTO>();
		}
	}
	
    public void onNodeExpand(NodeExpandEvent event) {  

    	this.selectedNode = event.getTreeNode();  
    	
    	try {
	    	if (!"root".equals(this.selectedNode.getParent().toString()) && !"document".equals(this.selectedNode.getType())) {
	    		DocumentoResumoDTO documento = (DocumentoResumoDTO)event.getTreeNode().getData();
	    		
	    		Feed feed = (Feed) castorUtil.convertFromXMLToObject(Feed.class, getClass().getResource("/castor/feed.xml").getFile(), 
	    				alfrescoServico.getConteudoNodeRef(documento.getUuidDocumento())); 
	    		
	    		// Remove os objetos Auxiliares
	    		if (this.selectedNode.getChildren().size() != 0) {
	    			this.selectedNode.getChildren().remove(0);
	    		}
	    		
				this.tree(feed.getEntrys(), this.selectedNode);
			}
    	} catch (MarshalException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MappingException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }  
  
    public void onNodeCollapse(NodeCollapseEvent event) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Collapsed", event.getTreeNode().toString());  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }  
  
    public String onNodeSelect(NodeSelectEvent event) {  
    	
    	this.selectedNode = event.getTreeNode();  
    	
    	DocumentoResumoDTO documento = (DocumentoResumoDTO)event.getTreeNode().getData();
    	if (!"document".equals(this.selectedNode.getType())) {
    		documentos = new ArrayList<DocumentoResumoDTO>();
    		this.pastaSelecionada = documento.getNomeDocumento();
    	} else { 
    		this.pastaSelecionada = null;
    		this.documentos = new ArrayList<DocumentoResumoDTO>();
    		documentos.add(paramsDocumentosUUI.get(documento.getNomeDocumento()));
    	}
    	return this.reload(); 	
    }  
  
    public String reload() {
    	return "/pages/repositorio/desenhos.xhtml";
    }
    
//    public void onNodeUnselect(NodeUnselectEvent event) {  
//        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unselected", event.getTreeNode().toString());  
//  
//        FacesContext.getCurrentInstance().addMessage(null, message);  
//    }    
    
	public String typeDocumento (String mimeType) {
		
		String retornoType = "document";
		
		if (mimeType.equals("image/png")  ||mimeType.equals("image/bmp") || mimeType.equals("image/cis-cod") || mimeType.equals("image/gif") || mimeType.equals("image/ief") || mimeType.equals("image/jpeg") ||
				mimeType.equals("image/pipeg") || mimeType.equals("image/svg+xml") || mimeType.equals("image/tiff") || mimeType.equals("image/x-cmu-raster") || mimeType.equals("image/x-cmx") ||
				mimeType.equals("image/x-icon") || mimeType.equals("image/x-portable-anymap") || mimeType.equals("image/x-portable-bitmap") ||
				mimeType.equals("image/x-portable-graymap") || mimeType.equals("image/x-portable-pixmap") || mimeType.equals("image/x-rgb") ||
				mimeType.equals("image/x-xbitmap") || mimeType.equals("image/x-xpixmap") || mimeType.equals("image/x-xwindowdump")) {
			retornoType = "picture";
		}
		
		return retornoType;
	}
    
	public TreeNode getRoot() {
		return root;
	}

	public TreeNode getSelectedNode() {  
        return selectedNode;  
    }  
  
    public void setSelectedNode(TreeNode selectedNode) {  
        this.selectedNode = selectedNode;  
    }  
	
	public String getNomeDesenho() {
		return nomeDesenho;
	}

	public void setNomeDesenho(String nomeDesenho) {
		this.nomeDesenho = nomeDesenho;
	}

	public OrigemRepositorio getOrigemRepositorio() {
		return origemRepositorio;
	}

	public void setOrigemRepositorio(OrigemRepositorio origemRepositorio) {
		this.origemRepositorio = origemRepositorio;
	}
	
	public Properties getBpmswebproperties() {
		return bpmswebproperties;
	}

	public void setBpmswebproperties(Properties bpmswebproperties) {
		this.bpmswebproperties = bpmswebproperties;
	}
	
	public AlfrescoServico getAlfrescoServico() {
		return alfrescoServico;
	}

	public void setAlfrescoServico(AlfrescoServico alfrescoServico) {
		this.alfrescoServico = alfrescoServico;
	}

	public CastorUtil getCastorUtil() {
		return castorUtil;
	}

	public List<DocumentoResumoDTO> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<DocumentoResumoDTO> documentos) {
		this.documentos = documentos;
	}

	public void setCastorUtil(CastorUtil castorUtil) {
		this.castorUtil = castorUtil;
	}

	public DocumentoResumoDTO getDocumentoResumo() {
		return documentoResumo;
	}

	public void setDocumentoResumo(DocumentoResumoDTO documentoResumo) {
		this.documentoResumo = documentoResumo;
	}

	public Map<String, DocumentoResumoDTO> getParamsDocumentosUUI() {
		return paramsDocumentosUUI;
	}

	public void setParamsDocumentosUUI(Map<String, DocumentoResumoDTO> paramsDocumentosUUI) {
		this.paramsDocumentosUUI = paramsDocumentosUUI;
	}

	public Map<String, String> getParamsPastasUUI() {
		return paramsPastasUUI;
	}

	public void setParamsPastasUUI(Map<String, String> paramsPastasUUI) {
		this.paramsPastasUUI = paramsPastasUUI;
	}

	public StreamedContent getFile() {
		return this.file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public UploadedFile getArquivoUpload() {
		return arquivoUpload;
	}

	public void setArquivoUpload(UploadedFile arquivoUpload) {
		this.arquivoUpload = arquivoUpload;
	}

	public String getPastaSelecionada() {
		return pastaSelecionada;
	}

	public void setPastaSelecionada(String pastaSelecionada) {
		this.pastaSelecionada = pastaSelecionada;
	}
}