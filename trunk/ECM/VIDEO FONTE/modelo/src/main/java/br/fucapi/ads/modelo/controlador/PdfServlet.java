package br.fucapi.ads.modelo.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

public class PdfServlet extends GenericServlet {

    private static final long serialVersionUID = 1L;

    private static final String COD_SUCESSO_FILE_WRITE = "1001";

    private static final String COD_ERRO_FILE_WRITE = "1013";

    @SuppressWarnings("unchecked")
	@Override
    public void service(final ServletRequest req, final ServletResponse res) throws ServletException, IOException {
    	

        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpSession httpSession = httpRequest.getSession();

        TarefaControle productSelection = (TarefaControle) httpSession.getAttribute("tarefaControle");
        
        System.out.println("\n\n"+productSelection+"\n\n");

        String acao = req.getParameter("action");

        
        //TODO - lista item 
        byte[] pdf = null;

        if (acao.equalsIgnoreCase("r")) {
        	pdf = productSelection.criarPDF();
            res.setContentType("application/pdf");
            ServletOutputStream sos = res.getOutputStream();
            sos.write(pdf);

        } else {
            try {
                if (ServletFileUpload.isMultipartContent(httpRequest)) {
                    List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(httpRequest);
                    for (FileItem item : items) {
                        //productSelection.SalvarPDF(IOUtils.toByteArray(item.getInputStream()), item.getName());
                    }

                }
            } catch (Exception e) {
                // Escreve o erro padrao para application services error na resposta
                PrintWriter out = res.getWriter();
                out.write(COD_ERRO_FILE_WRITE);
                out.flush();
                out.close();
                e.printStackTrace();
            }
            // Escreve na resposta
            PrintWriter out = res.getWriter();
            out.write(COD_SUCESSO_FILE_WRITE);
            out.flush();
            out.close();

        }

    }

}

