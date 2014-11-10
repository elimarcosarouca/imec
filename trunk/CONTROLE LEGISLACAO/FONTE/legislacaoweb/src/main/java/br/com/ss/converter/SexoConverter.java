package br.com.ss.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ss.enumerado.Sexo;

@FacesConverter(value = "sexoConverter")
public class SexoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return Sexo.getEnum(new Integer(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		Sexo sexo = Sexo.getEnum(new Integer(value.toString()));
		if (sexo != null) {
			return sexo.getDescricao();
		}
		return null;
	}
}