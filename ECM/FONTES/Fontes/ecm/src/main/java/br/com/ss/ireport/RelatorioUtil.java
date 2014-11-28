package br.com.ss.ireport;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import br.com.ss.util.FacesUtils;

@ManagedBean
@ApplicationScoped
public class RelatorioUtil implements Serializable {

	private static final long serialVersionUID = 7268492679002219059L;

	@ManagedProperty(value = "#{dataSource}")
	private DriverManagerDataSource dataSource;

	/** Resource path dos relatorio: /resources/jasper/ */
	private static final String PATH_REPORT = "resources" + File.separator
			+ "jasper" + File.separator;

	public byte[] gerarRelatorioWebBytes(Map parametros, String arquivo)
			throws FileNotFoundException {

		JasperPrint print;
		byte[] relatorio = null;
		try {
			print = JasperFillManager.fillReport(new FileInputStream(new File(
					arquivo)), parametros, this.dataSource.getConnection());

			relatorio = JasperExportManager.exportReportToPdf(print);

		} catch (JRException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return relatorio;

	}

	public byte[] gerarRelatorioWebBytes(Collection<Object> lista,
			Map parametros, String arquivo) throws FileNotFoundException {

		JasperPrint print;
		byte[] relatorio = null;
		try {

			print = JasperFillManager.fillReport(new FileInputStream(new File(
					arquivo)), parametros,
					new JRBeanCollectionDataSource(lista));

			relatorio = JasperExportManager.exportReportToPdf(print);

		} catch (JRException e) {
			e.printStackTrace();
		}

		return relatorio;

	}

	public void gerarRelatorioWebDatasource(JRDataSource jrRS, Map parametros,
			String arquivo) {
		ServletOutputStream servletOutputStream = null;
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context
				.getExternalContext().getResponse();

		try {
			servletOutputStream = response.getOutputStream();
			JasperRunManager.runReportToPdfStream(new FileInputStream(new File(
					arquivo)), response.getOutputStream(), parametros,
					this.dataSource.getConnection());

			response.setContentType("application/pdf");
			servletOutputStream.flush();
			servletOutputStream.close();
			context.renderResponse();
			context.responseComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FileInputStream gerarRelatorio(List lista, Map parametros,
			String arquivo) {

		FileInputStream fis = null;
		JRDataSource jrRS = new JRBeanCollectionDataSource(lista);
//		Empresa empresa = (Empresa) FacesUtils.getApplicationParam("empresa");
//		parametros.put("empresa", empresa);

		try {

			JasperPrint print = JasperFillManager.fillReport(arquivo,
					parametros, jrRS);

			File arquivoGerado = File.createTempFile("relatorio.", ".pdf");

			JasperExportManager.exportReportToPdfStream(print,
					new FileOutputStream(arquivoGerado));

			fis = new FileInputStream(arquivoGerado);

			// Verificar
			arquivoGerado.delete();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fis;
	}

	public void gerarRelatorioWeb(List lista, Map parametros,
			String nomeRelatorio) throws JRException {

//		Empresa empresa = (Empresa) FacesUtils.getApplicationParam("empresa");
//		parametros.put("empresa", empresa);

		JRDataSource jrRS = new JRBeanCollectionDataSource(lista);

		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context
				.getExternalContext().getResponse();

		BufferedOutputStream output = null;
		BufferedInputStream input = null;

		try {

			String webPath = context.getExternalContext().getRealPath("/");
			String reportPath = webPath + PATH_REPORT + nomeRelatorio;

			input = new BufferedInputStream(new FileInputStream(reportPath),
					DEFAULT_BUFFER_SIZE);

			File fileReport = new File(reportPath);

			response.reset();
			response.setHeader("Content-Type", "application/pdf");
			response.setHeader("Content-Length",
					String.valueOf(fileReport.length()));
			response.setHeader("Content-Disposition", "inline; filename=\""
					+ fileReport.getName() + "\"");

			JasperRunManager.runReportToPdfStream(new FileInputStream(
					fileReport), response.getOutputStream(), parametros, jrRS);

			output = new BufferedOutputStream(response.getOutputStream(),
					DEFAULT_BUFFER_SIZE);

			// Write file contents to response.
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}

			// Finalize task.
			output.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Gently close streams.
			close(output);
			close(input);
		}

		context.renderResponse();
		context.responseComplete();
	}

	/**
	 * Metodo utilizar para gerar o relatorio e realizar o download do mesmo
	 * 
	 * @param lista
	 * @param parametros
	 * @param nome
	 */
	@SuppressWarnings("unchecked")
	public void gerarRelatorioComDownload(List lista, Map parametros,
			String nome) {

//		Empresa empresa = (Empresa) FacesUtils.getApplicationParam("empresa");
//
//		parametros.put("empresa", empresa);

		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance()
					.getExternalContext();
			ServletContext context = (ServletContext) externalContext
					.getContext();
			FacesContext facesContext = FacesContext.getCurrentInstance();
			// String arquivo =
			// context.getRealPath("/WEB-INF/reports/tarefa.jasper");
			// System.out.println(arquivo);
			String arquivo = "c:/relatorio/" + nome;

			FacesContext fc = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) fc
					.getExternalContext().getResponse();

			response.setContentType("application/pdf");
			response.addHeader("Content-disposition",
					"attachment; filename=\"pendencia.pdf\"");

			JasperPrint impressao = JasperFillManager.fillReport(
					new FileInputStream(new File(arquivo)), parametros,
					new JRBeanCollectionDataSource(lista, false));

			JasperExportManager.exportReportToPdfStream(impressao,
					response.getOutputStream());

			facesContext.responseComplete();

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (JRException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public DriverManagerDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DriverManagerDataSource dataSource) {
		this.dataSource = dataSource;
	}


	public byte[] gerarRelatorioWebBytes(List listaPesquisa,
			Map<String, Object> params, String arquivo)
			throws FileNotFoundException {
		JasperPrint print;
		byte[] relatorio = null;

		FacesContext context = FacesContext.getCurrentInstance();

		try {
			String webPath = context.getExternalContext().getRealPath("/");
			String reportPath = webPath + PATH_REPORT + arquivo;

			print = JasperFillManager.fillReport(new FileInputStream(new File(
					reportPath)), params, new JRBeanCollectionDataSource(
					listaPesquisa));

			relatorio = JasperExportManager.exportReportToPdf(print);

		} catch (JRException e) {
			e.printStackTrace();
		}

		return relatorio;
	}

	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

	public void testPrintPdf() {

		// Prepare.
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext
				.getResponse();

		String contextPath = externalContext.getRequestContextPath();

		String filePath = contextPath;
		String fileName = "escala_pregadores.pdf";

		File file = new File(filePath, fileName);
		file.exists();
		file.getAbsolutePath();
		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		try {
			// Open file.
			input = new BufferedInputStream(new FileInputStream(file),
					DEFAULT_BUFFER_SIZE);

			// Init servlet response.
			response.reset();
			response.setHeader("Content-Type", "application/pdf");
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=\""
					+ fileName + "\"");

			output = new BufferedOutputStream(response.getOutputStream(),
					DEFAULT_BUFFER_SIZE);

			// Write file contents to response.
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}

			// Finalize task.
			output.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Gently close streams.
			close(output);
			close(input);
		}

		// Inform JSF that it doesn't need to handle response.
		// This is very important, otherwise you will get the following
		// exception in the logs:
		// java.lang.IllegalStateException: Cannot forward after response has
		// been committed.
		facesContext.responseComplete();
	}

	private static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				// Do your thing with the exception. Print it, log it or mail
				// it. It may be useful to
				// know that this will generally only be thrown when the client
				// aborted the download.
				e.printStackTrace();
			}
		}
	}

}