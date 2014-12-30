package br.fucapi.bpms.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.fucapi.bpms.web.dominio.TipoDocumento;

@FacesConverter(value = "tipoDocumentoConverter")
public class TipoDocumentoConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent uiComponent,
			String value) {
		return TipoDocumento.fromJsonToTipoDocumento(value);
	}

	public String getAsString(FacesContext arg0, UIComponent uiComponent,
			Object tipoDocumento) {
		return ((TipoDocumento) tipoDocumento).toJson().replace("\"", "\'");
	}
}