package br.com.ss.academico.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ss.academico.enumerated.Turno;

@FacesConverter(value = "turnoConverter")
public class TurnoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return Turno.getEnum(new Integer(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Turno turno = (Turno) value;
		if (turno != null) {
			return turno.getDescricao();
		}
		return null;
	}
}