package br.fucapi.ads.modelo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.fucapi.ads.modelo.dominio.TipoDocumento;

@FacesConverter(value = "tipoDocumentoConverter")
public class TipoDocumentoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		return TipoDocumento.fromJsonToObject(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		return ((TipoDocumento) value).toJson().replace("\"", "\'");

	}

}
