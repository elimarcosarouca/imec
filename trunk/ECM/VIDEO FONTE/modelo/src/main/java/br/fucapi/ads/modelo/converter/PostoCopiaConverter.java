package br.fucapi.ads.modelo.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.fucapi.ads.modelo.controlador.PostoCopiaControlador;
import br.fucapi.ads.modelo.dominio.Setor;

@FacesConverter(value = "postoCopiaConverter")
public class PostoCopiaConverter implements Converter {

	public Object getAsObject(FacesContext facesContext, UIComponent uicomp,
			String submittedValue) {
		if (submittedValue.trim().equals("")) {
			return null;
		}
		Long setorId = Long.parseLong(submittedValue);
		Setor setor = lookupSetor(setorId);

		if (setor == null) {
			throw new ConverterException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Conversion Error",
					"Id do Responsavel não válido"));
		}
		return setor;
	}

	private Setor lookupSetor(Long setorId) {
		for (Setor unid : getSetorList()) {
			if (unid.getId().equals(setorId)) {
				return unid;
			}
		}
		return null;
	}

	/**
	 * Recupera a lista que foi carregada na lista retornada para o autocomplete
	 * do managed bean.
	 * 
	 * @return
	 */
	private List<Setor> getSetorList() {
		FacesContext context = FacesContext.getCurrentInstance();
		PostoCopiaControlador controlador = (PostoCopiaControlador) context
				.getELContext()
				.getELResolver()
				.getValue(context.getELContext(), null, "postoCopiaControlador");
		List<Setor> setoresList = (List<Setor>) controlador.getSetores();
		return setoresList;
	}

	public String getAsString(FacesContext facesContext, UIComponent uicomp,
			Object entity) {
		return entity == null ? "-1" : ((Setor) entity).getId().toString();
	}

}
