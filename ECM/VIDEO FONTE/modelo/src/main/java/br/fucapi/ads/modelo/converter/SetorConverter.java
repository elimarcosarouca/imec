package br.fucapi.ads.modelo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.fucapi.ads.modelo.dominio.Setor;

@FacesConverter(value = "setorConverter")
public class SetorConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		return Setor.fromJsonToObject(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		return ((Setor) value).toJson().replace("\"", "\'");

	}

}
