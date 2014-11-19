package br.fucapi.ads.modelo.excecaoHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.primefaces.context.RequestContext;

import br.fucapi.ads.mail.MailService;
import br.fucapi.ads.mail.MailServiceImpl;
import br.fucapi.ads.modelo.controlador.ErroControladorBean;
import br.fucapi.ads.modelo.controlador.PaginaCentralControladorBean;

public class ExcecaoHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;
	private static final Logger log = Logger.getLogger(ExcecaoHandler.class
			.getCanonicalName());
	
	public ExcecaoHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	@Override
	public void handle() throws FacesException {
		// Iterar sobre todas as exceções nao tratadas
		Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents()
				.iterator();
		while (i.hasNext()) {
			long tempo = System.currentTimeMillis();
			ExceptionQueuedEvent event = (ExceptionQueuedEvent) i.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event
					.getSource();

			// obtem um objeto throwable
			Throwable throwable = context.getException();

			FacesContext fc = FacesContext.getCurrentInstance();

			try {
				StringWriter stackTrace = new StringWriter(1024);

				PrintWriter writer = new PrintWriter(stackTrace);

				writer.println(throwable.getCause());

				for (StackTraceElement element : throwable.getCause()
						.getStackTrace())
					writer.println("\tat " + element);

				log.severe("[Exception ID: #" + tempo + "]\n\t"
						+ stackTrace.toString());

//				PaginaCentralControladorBean paginaCentralControladorBean = (PaginaCentralControladorBean) fc
//						.getApplication().evaluateExpressionGet(fc,
//								"#{paginaCentralControladorBean}",
//								PaginaCentralControladorBean.class);

//				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//				ErroControladorBean erroControladorBean = (ErroControladorBean) fc
//						.getApplication().evaluateExpressionGet(fc,
//								"#{erroControladorBean}",
//								ErroControladorBean.class);
				
//				MailService mailService = (MailService) fc.getApplication().evaluateExpressionGet(fc, "#{mailService}", MailService.class);
//
//				erroControladorBean.setData(new Date(tempo));
//				erroControladorBean.setPilhaExcecao(stackTrace.toString());
////				erroControladorBean.setUsuario(authentication.getName());
//				erroControladorBean.setEmail(((MailServiceImpl) mailService).getSender());
//				erroControladorBean.setClassName(throwable.getCause()
//						.getClass().getCanonicalName());
//				erroControladorBean.setIdException(tempo);
//
//				paginaCentralControladorBean
//						.setPaginaCentral("paginas/error.xhtml");
//				RequestContext.getCurrentInstance().update("paginaCentral");

			} finally {
				// Remove-lo da fila
				i.remove();
			}
		}
		// parent handle
		getWrapped().handle();
	}

}
