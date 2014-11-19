package br.fucapi.ads.modelo.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class UploadServlet extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1771850075191242427L;

	private List<FileItem> items;
	
	@SuppressWarnings("unchecked")
	@Override
	public void service(final ServletRequest req, final ServletResponse res) throws ServletException, IOException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) req;
		HttpSession httpSession = httpServletRequest.getSession();	
		
		TarefaControle productSelection = (TarefaControle) httpSession.getAttribute("tarefaControle");
	
		if (ServletFileUpload.isMultipartContent(httpServletRequest)) {
            
			try {
			
				items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(httpServletRequest);

				if (productSelection.getItens() == null)
					
					productSelection.setItens(new ArrayList<FileItem>());
					
				productSelection.getItens().addAll(items);
				
				//productSelection.atualizar();
				
				/* for (FileItem item : items) {
		            	byte [] b = IOUtils.toByteArray(item.getInputStream());
		            	
		            	System.out.println(b);
		            	System.out.println(item.getName());
		            	System.out.println(item.getSize());
		            	System.out.println(item.getContentType());
	            	
		               // productSelection.SalvarPDF(IOUtils.toByteArray(item.getInputStream()), item.getName());
		            }	*/
				
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }
		
	}
	
	

}
