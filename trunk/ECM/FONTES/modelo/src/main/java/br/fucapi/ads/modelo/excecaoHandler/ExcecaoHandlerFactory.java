package br.fucapi.ads.modelo.excecaoHandler;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class ExcecaoHandlerFactory extends ExceptionHandlerFactory {

	private ExceptionHandlerFactory parent;

	// Injeta handles no jsf
	public ExcecaoHandlerFactory(ExceptionHandlerFactory parent) {
		this.parent = parent;
	}

	// cria seu próprio ExceptionHandler
	@Override
	public ExceptionHandler getExceptionHandler() {
		ExceptionHandler result = new ExcecaoHandler(
				parent.getExceptionHandler());
		return result;
	}
}
