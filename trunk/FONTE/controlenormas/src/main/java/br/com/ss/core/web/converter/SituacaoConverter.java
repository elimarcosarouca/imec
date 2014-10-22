package br.com.ss.core.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ss.core.web.enumerated.Situacao;

@FacesConverter(value = "situacaoConverter")
public class SituacaoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return Situacao.getEnum(new Integer(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Situacao sit = ( Situacao )value;
		if (sit != null) {
			return sit.getDescricao();
		}
		return "";
	}
}