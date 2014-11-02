package br.com.ss.academico.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ss.academico.enumerated.GrauParentesco;

@FacesConverter(value = "grauParentescoConverter")
public class GrauParentescoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return GrauParentesco.getEnum(new Integer(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		GrauParentesco sexo = GrauParentesco.getEnum(new Integer(value.toString()));
		if (sexo != null) {
			return sexo.getDescricao();
		}
		return null;
	}
}