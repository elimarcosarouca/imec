package br.fucapi.ads.modelo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.fucapi.ads.modelo.dominio.PostoCopia;

@FacesConverter(value = "postoCopiaConverter")
public class PostoCopiaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		return PostoCopia.fromJsonToPostoCopia(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		return ((PostoCopia) value).toJson().replace("\"", "\'");

	}

}
