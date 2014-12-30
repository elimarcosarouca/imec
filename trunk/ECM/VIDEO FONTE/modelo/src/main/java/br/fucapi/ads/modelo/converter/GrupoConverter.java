package br.fucapi.ads.modelo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.fucapi.bpms.alfresco.dominio.Grupo;
import br.fucapi.bpms.alfresco.dominio.UsuarioGrupo;

@FacesConverter(value = "grupoConverter")
public class GrupoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		
		return Grupo.fromJsonToUsuarioGrupo(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,Object value) {

		return ((UsuarioGrupo) value).toJson().replace("\"", "\'");

	}

}
