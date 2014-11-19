package br.fucapi.ads.assinaturadigital.applet.worker;

import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.PrivateKey;
import java.security.cert.Certificate;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import br.fucapi.ads.assinaturadigital.applet.AbstractComponentSigner;
import br.fucapi.ads.assinaturadigital.applet.SwingSigner;
import br.fucapi.ads.assinaturadigital.applet.http.HTTPConnectionHandler;
import br.fucapi.ads.assinaturadigital.applet.util.Constants;
import br.fucapi.ads.assinaturadigital.applet.util.X509Constants;
import br.fucapi.ads.assinaturadigital.applet.x509.PDFSignerX509;

public class AssinarDocumentoWorker extends SwingWorker<String, Void> {

	private SwingSigner swingSigner;
	private AbstractComponentSigner viewer;
	private PrivateKey privateKey;
	private Certificate[] chain;

	private static final String MSG_ERRO_CERTIFICADO_EXPIRADO = "O certificado digital nao e valido pois sua validade expirou. ";
	private static final String MSG_ERRO_REPOSITORIO_CRL = "Diretorio de arquivos CRLs nao encontrado.";
	private static final String MSG_ERRO_RAIZ_INVALIDO = "Nao existe lista de certificados AC Raizes confiaveis no sistema.";
	private static final String MSG_ERRO_CPF_INVALIDO = "CPF do usuário não confere com o CPF do certificado digital.";
	private static final String MSG_ERRO_CADEIA_INVALIDA = "O certificado digital nao possui uma Cadeia de Confianca valida!";
	private static final String MSG_ERRO_REVOGADO = "Atenção: Este certificado não pode ser utilizado, pois foi revogado pela entidade certificadora!";
	private static final String MSG_ERRO_DEFAULT = "O serviço para validar o Certificado Digital encontra-se indisponível. Por favor, tente novamente!";

	public AssinarDocumentoWorker(AbstractComponentSigner viewer,
			SwingSigner swingSigner, PrivateKey privateKey, Certificate[] chain) {

		this.viewer = viewer;
		this.swingSigner = swingSigner;
		this.privateKey = privateKey;
		this.chain = chain;
	}

	protected void done() {

		String resp = null, msgErro = null, excecao = null;

		try {

			resp = get();

		} catch (Exception e) {
			e.printStackTrace();
			excecao = e.getMessage();
		}

		// IOException
		if (resp == null || resp.trim().length() == 0) {

			if (!(excecao
					.contains("ExceptionConverter: java.security.SignatureException: Ação cancelada pelo usuário."))) {
				msgErro = MSG_ERRO_DEFAULT;
			}

			// Analisa a resposta
		} else if (resp.equalsIgnoreCase(X509Constants.SUCESSO.toString())) {

			// Esconde panel de assinatura
			swingSigner.dispose();

			String urlAcaoAposAssinarDoc = "javascript:posFinalizarAssinarDocumento();";

			try {
				viewer.getAppletContext().showDocument(
						new URL(urlAcaoAposAssinarDoc));
			} catch (MalformedURLException ex) {
				JOptionPane.showMessageDialog(viewer, resp,
						"Ocorreu um erro no sistema!",
						JOptionPane.ERROR_MESSAGE);
			}

			return;

			// Data do certificado expirada
		} else if (resp.equals(X509Constants.EX_EXPIRADO.toString())) {

			msgErro = MSG_ERRO_CERTIFICADO_EXPIRADO;

			// Lista LCR Nao encontrada
		} else if (resp.equals(X509Constants.EX_REPOSITORIO.toString())) {

			msgErro = MSG_ERRO_REPOSITORIO_CRL;

			// Certificado Raiz invalido
		} else if (resp.equals(X509Constants.EX_INVALIDO_AC.toString())) {

			msgErro = MSG_ERRO_RAIZ_INVALIDO;

			// CPF Invalido
		} else if (resp.equals(X509Constants.EX_INVALIDO_CPF.toString())) {

			msgErro = MSG_ERRO_CPF_INVALIDO;

			// X509CertPathValidationException, KeyStoreException,
			// CertificateException, NoSuchAlgorithmException
		} else if (resp.equals(X509Constants.EX_INVALIDO_CERT_PATH.toString())
				|| resp.equals(X509Constants.EX_KEY_STORE.toString())
				|| resp.equals(X509Constants.EX_CERTIFICATE.toString())
				|| resp.equals(X509Constants.EX_NO_SUCH_ALGORITHM.toString())) {

			msgErro = MSG_ERRO_CADEIA_INVALIDA;

			// Revogado via OCSP
		} else if (resp.equals(X509Constants.EX_INVALIDO_OSCP_CHECK.toString())) {

			msgErro = MSG_ERRO_REVOGADO;

			// Revogado via LCR
		} else if (resp.equals(X509Constants.EX_REVOGADO.toString())) {

			msgErro = MSG_ERRO_REVOGADO;

		} else if (resp.equals(X509Constants.EX_APP_SERVICE_ERROR.toString())) {

			msgErro = MSG_ERRO_DEFAULT;

		} else {

			msgErro = "ERRO: " + resp;

		}

		if (msgErro != null) {
			JOptionPane.showMessageDialog(viewer, msgErro,
					"Ocorreu um erro no sistema !", JOptionPane.ERROR_MESSAGE);
		}

		// Esconde o panel de dialog
		swingSigner.getDialog().dispose();
	}

	protected String doInBackground() throws Exception {
		System.out.println("###########doInBackground");

		String urlFinalizarVistoria = viewer.getCodeBase()
				+ "/modelo/PdfServlet";

		HttpResponse httpResponse2 = HTTPConnectionHandler.invokeURL(
				"http://localhost:9090/modelo/PdfServlet", viewer.getParams()
						.get(Constants.PARAM_COOKIES));

		byte[] byteResposta = EntityUtils
				.toByteArray(httpResponse2.getEntity());

		PDFSignerX509 pdfSignerX509 = new PDFSignerX509(privateKey, chain);

		byte[] pdfAssinado = pdfSignerX509.assinarPDF(byteResposta);

		java.io.File file = new java.io.File("c:\\testeAssinado.pdf");
		FileOutputStream in = new FileOutputStream(file);
		in.write(pdfAssinado);
		in.close();

		System.out.println("CERTIFICADO GERADO COM SUCESSO");

		return "CERTIFICADO GERADO COM SUCESSO";
	}

}