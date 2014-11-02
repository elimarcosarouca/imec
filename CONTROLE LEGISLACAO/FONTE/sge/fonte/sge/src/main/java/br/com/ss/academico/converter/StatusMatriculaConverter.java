package br.com.ss.academico.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ss.academico.enumerated.StatusMatricula;

@FacesConverter(value = "statusMatriculaConverter")
public class StatusMatriculaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return StatusMatricula.getEnum(new Integer(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		StatusMatricula sit =  (StatusMatricula ) value;
		if (sit != null) {
			return sit.getDescricao();
		}
		return null;
	}
}