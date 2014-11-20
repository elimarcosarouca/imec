package br.com.ss.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ss.enumerado.UF;

@FacesConverter(value = "ufConverter")
public class UFConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return UF.getEnum(new String(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		UF enumerated = UF.getEnum(new String(value.toString()));
		if (enumerated != null) {
			return enumerated.getDescricao();
		}
		return null;
	}
}