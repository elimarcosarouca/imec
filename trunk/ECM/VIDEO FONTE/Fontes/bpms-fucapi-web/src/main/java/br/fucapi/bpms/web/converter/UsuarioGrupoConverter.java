package br.fucapi.bpms.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.fucapi.bpms.alfresco.dominio.UsuarioGrupo;

@FacesConverter(value = "usuarioGrupoConverter")
public class UsuarioGrupoConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent uiComponent,
			String value) {
		return UsuarioGrupo.fromJsonToUsuarioGrupo(value);
	}

	public String getAsString(FacesContext arg0, UIComponent uiComponent,
			Object usuarioGrupo) {
		return ((UsuarioGrupo) usuarioGrupo).toJson().replace("\"", "\'");
	}
}