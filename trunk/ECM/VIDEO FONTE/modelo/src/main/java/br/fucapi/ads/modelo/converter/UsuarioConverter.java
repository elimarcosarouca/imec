package br.fucapi.ads.modelo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.fucapi.bpms.alfresco.dominio.Usuario;

@FacesConverter(value = "usuarioConverter")
public class UsuarioConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent uiComponent,
			String value) {
		return Usuario.fromJsonToUsuario(value);
	}

	public String getAsString(FacesContext arg0, UIComponent uiComponent,
			Object usuario) {
		return ((Usuario) usuario).toJson().replace("\"", "\'");
	}
}