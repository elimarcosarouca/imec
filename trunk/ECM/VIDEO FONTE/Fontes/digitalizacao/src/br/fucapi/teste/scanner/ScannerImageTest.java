package br.fucapi.teste.scanner;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;

import uk.co.mmscomputing.device.scanner.Scanner;
import uk.co.mmscomputing.device.scanner.ScannerIOException;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata;
import uk.co.mmscomputing.device.scanner.ScannerListener;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Lauro Uchoa
 * 
 *         Applet que busca devices conectados que podem fornecer documentos
 *         digitalizados - Scaner e cameras digitais extraindo arquivos em
 *         memória e convertendo em um único arquivo pdf enviando um script de
 *         carregamento de pagina.
 * 
 */
public class ScannerImageTest extends Applet implements ActionListener,
		ScannerListener {

	private static final long serialVersionUID = -8066857644028371604L;

	private List<BufferedImage> bufferedImageList = new ArrayList<BufferedImage>();

	private Scanner scanner;
	private Button digitalizar, finalizar, selecionar;

	private String cookie;
	private String codeBase;
	private String atualizar;

	Component frame = null;

	private DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy HH_mm_ss");
	private Calendar cal = Calendar.getInstance();

	public static void main(String[] args) {

		new ScannerImageTest();

	}

	public ScannerImageTest() {

		scanner = Scanner.getDevice();
		System.out.println(Scanner.getDevice());

		scanner.addListener(this);
		setVisible(true);
	}

	/**
	 * Função init - Adição de botões e actionListener para conversao de dados
	 * recebidos em um documento .pdf que será salvo na memória - baos.
	 * 
	 */

	public void init() {

		codeBase = this.getParameter("PARAM_URL_UPDATE_SERVLET");
		cookie = this.getParameter("PARAM_COOKIES");
		atualizar = this.getParameter("PARAM_UPDATE");

		setLayout(new GridLayout(1, 1));
		setSize(180, 80);

		digitalizar = new Button("Capturar");
		add(digitalizar);
		digitalizar.addActionListener(this);

		finalizar = new Button("Finalizar");
		add(finalizar);
		finalizar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				//TODO - Area onde as medidas estao sendo atribuidas (Nesse caso FOLHA A4)

				Document document = new Document(new Rectangle(2550, 3510));

				try {

					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					PdfWriter.getInstance(document, baos);
					document.open();

					for (BufferedImage bufferedImage : bufferedImageList) {
						document.add(Image.getInstance(bufferedImage, null));

					}

					document.close();

					// TODO ->MOCK PARA TESTE DE VERIFICAÇÃO EM DISCO
					/*
					 * FileOutputStream fos = new
					 * FileOutputStream("C:\\testeWindows\\Certificado em_"
					 * dateFormat.format(cal.getTime()) +".pdf");
					 * fos.write(baos.toByteArray()); fos.close();
					 */

					enviaDocumento(baos.toByteArray());

					JOptionPane.showMessageDialog(frame,
							"Documento salvo com sucesso!");
					System.out.println("pdf gerado em memória");

				} catch (Exception e2) {
					e2.printStackTrace();

					JOptionPane.showMessageDialog(frame,
							"Digitalizar uma imagem pressionando Capturar.");
					System.out.println("Documento vazio");

				}

				executarScriptAtualizar();

			}

		});

		selecionar = new Button("selecionar");
		add(selecionar);
		selecionar.addActionListener(this);

	}

	private void executarScriptAtualizar() {
		try {
			getAppletContext().showDocument(new URL(atualizar));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public HttpResponse enviaDocumento(byte[] pdfAssinado) throws IOException {

		// TODO MOCK - teste
		// String cookie2 = "JSESSIONID=BA4606167F9D2DE5B8C5A498FC7A8E30";
		// String codeBase2 = "http://170.10.84.189:9090/modelo/UploadServlet";

		String nomeArquivo = "Certificado " + dateFormat.format(cal.getTime());

		MultipartEntity multipartEntity = new MultipartEntity();

		ByteArrayBody bab = new ByteArrayBody(pdfAssinado, "application/pdf",
				nomeArquivo + ".pdf");

		multipartEntity.addPart("pdf", bab);

		// Criando conexao http com o cookie de sessao
		String urlSalvarDocumentosAssinados = codeBase;
		HttpPost httpPost = new HttpPost(urlSalvarDocumentosAssinados);
		httpPost.addHeader("Cookie", cookie);

		// Incluindo lista de parametros na requisicao
		httpPost.setEntity(multipartEntity);

		// Executando a conexao http e obtendo a resposta
		HttpResponse response = new DefaultHttpClient().execute(httpPost);
		return response;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {

		try {
			if (evt.getSource() == selecionar) {
				scanner.select();

			} else if (evt.getSource() == digitalizar) {
				scanner.acquire();
			}

		} catch (ScannerIOException se) {
			se.printStackTrace();
		}
	}

	@Override
	public void update(ScannerIOMetadata.Type type, ScannerIOMetadata siom) {

		if (type.equals(ScannerIOMetadata.ACQUIRED)) {

			this.bufferedImageList.add(siom.getImage());

			System.out.println(siom);
			System.out.println("lista");

		}

	}

}
