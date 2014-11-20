package br.com.ss.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ss.enumerado.StatusRotina;

@FacesConverter(value = "statusRotinaConverter")
public class StatusRotinaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return StatusRotina.getEnum(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		StatusRotina enumerated = StatusRotina.getEnum(value.toString());
		if (enumerated != null) {
			return enumerated.getDescricao();
		}
		return null;
	}
}