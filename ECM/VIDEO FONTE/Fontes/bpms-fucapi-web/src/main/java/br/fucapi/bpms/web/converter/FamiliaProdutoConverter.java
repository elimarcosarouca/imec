package br.fucapi.bpms.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.fucapi.bpms.web.dominio.FamiliaProduto;

@FacesConverter(value = "familiaProdutoConverter")
public class FamiliaProdutoConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent uiComponent,
			String value) {
		return FamiliaProduto.fromJsonToFamiliaProduto(value);
	}

	public String getAsString(FacesContext arg0, UIComponent uiComponent,
			Object familiaProduto) {
		return ((FamiliaProduto) familiaProduto).toJson().replace("\"", "\'");
	}
}