package br.com.ss.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ss.enumerado.NaoSim;

@FacesConverter(value = "naoSimConverter")
public class NaoSimConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return NaoSim.getEnum(new Boolean(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		NaoSim enumerated = NaoSim.getEnum(new Boolean(value.toString()));
		if (enumerated != null) {
			return enumerated.getDescricao();
		}
		return null;
	}
}