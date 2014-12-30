package br.fucapi.bpms.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.fucapi.bpms.web.dominio.TipoDesenho;

@FacesConverter(value = "tipoDesenhoConverter")
public class TipoDesenhoConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent uiComponent,
			String value) {
		return TipoDesenho.fromJsonToTipoDesenho(value);
	}

	public String getAsString(FacesContext arg0, UIComponent uiComponent,
			Object tipoDesenho) {
		return ((TipoDesenho) tipoDesenho).toJson().replace("\"", "\'");
	}
}