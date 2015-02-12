package br.fucapi.ads.modelo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.fucapi.ads.modelo.dominio.Categoria;

@FacesConverter(value = "categoriaConverter")
public class CategoriaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		if ("".equals(value)) {
			return "";
		}

		return Categoria.fromJsonToObject(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if ("".equals(value)) {
			return "";
		}

		return ((Categoria) value).toJson().replace("\"", "\'");
	}

}
