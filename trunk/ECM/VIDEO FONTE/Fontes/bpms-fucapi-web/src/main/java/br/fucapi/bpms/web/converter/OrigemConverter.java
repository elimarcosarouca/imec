package br.fucapi.bpms.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.fucapi.bpms.web.dominio.Origem;

@FacesConverter(value = "origemConverter")
public class OrigemConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent uiComponent,
			String value) {
		return Origem.fromJsonToOrigem(value);
	}

	public String getAsString(FacesContext arg0, UIComponent uiComponent,
			Object origem) {
		return ((Origem) origem).toJson().replace("\"", "\'");
	}
}