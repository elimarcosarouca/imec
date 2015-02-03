package br.fucapi.ads.modelo.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.fucapi.ads.modelo.controlador.SetorControlador;
import br.fucapi.ads.modelo.dominio.Unidade;

@FacesConverter(value = "unidadeConverter")
public class UnidadeConverter implements Converter {

	/*@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		if ("".equals(value) ) {
			return "";
		}
		
		return Unidade.fromJsonToUnidade(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if ("".equals(value) ) {
			return "";
		}
		
		return ((Unidade) value).toJson().replace("\"", "\'");

	}*/
	
	public Object getAsObject(FacesContext facesContext, UIComponent uicomp,
			String submittedValue) {
		if (submittedValue.trim().equals("")) {
			return null;
		}
		Long unidadeId = Long.parseLong(submittedValue);
		Unidade unidade = lookupUnidade(unidadeId);
		
		if (unidade == null) {
			throw new ConverterException(new FacesMessage( FacesMessage.SEVERITY_ERROR, "Conversion Error",
					"Id do Responsavel não válido"));
		}
		return unidade;
	}

	private Unidade lookupUnidade(Long responsavelId) {
		for (Unidade unid : getUnidadeList() ) {
			if(unid.getId().equals(responsavelId)) {
				return unid;
			}
		}
		return null;
	}

	/**
	 * Recupera a lista que foi carregada na lista retornada para o autocomplete do managed bean.
	 * @return
	 */
	private List<Unidade> getUnidadeList() {
		FacesContext context = FacesContext.getCurrentInstance();
		SetorControlador controlador = (SetorControlador) context.getELContext()
				.getELResolver().getValue(context.getELContext(), null, "setorControlador");
		List<Unidade> unidadeList = (List<Unidade>) controlador.getUnidades();
		return unidadeList;
	}

	public String getAsString(FacesContext facesContext, UIComponent uicomp, Object entity) {
		return entity == null ? "-1" : ((Unidade) entity).getId().toString();
	}

}
