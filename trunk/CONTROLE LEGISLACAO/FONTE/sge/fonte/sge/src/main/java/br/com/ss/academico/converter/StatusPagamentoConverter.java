package br.com.ss.academico.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ss.academico.enumerated.StatusPagamento;

@FacesConverter(value = "statusPagamentoConverter")
public class StatusPagamentoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return StatusPagamento.getEnum(new Integer(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		StatusPagamento sit = StatusPagamento.getEnum(new Integer(value
				.toString()));
		if (sit != null) {
			return sit.getDescricao();
		}
		return null;
	}
}