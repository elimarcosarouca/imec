package br.fucapi.ads.modelo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.fucapi.ads.modelo.dominio.Unidade;

@FacesConverter(value = "unidadeConverter")
public class UnidadeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		return Unidade.fromJsonToUnidade(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		return ((Unidade) value).toJson().replace("\"", "\'");

	}

}
