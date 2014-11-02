package br.com.ss.academico.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.ss.academico.controlador.AlunoControlador;
import br.com.ss.academico.dominio.Responsavel;

/**
 * Conversor para entidade 'Responsavel'.
 * @author robson.ramos
 */
@FacesConverter(value = "responsavelConverter")
public class ResponsavelConverter implements Converter {

	public Object getAsObject(FacesContext facesContext, UIComponent uicomp,
			String submittedValue) {
		if (submittedValue.trim().equals("")) {
			return null;
		}
		Long responsavelId = Long.parseLong(submittedValue);
		Responsavel resp = lookupResponsavel(responsavelId);
		
		if (resp == null) {
			throw new ConverterException(new FacesMessage( FacesMessage.SEVERITY_ERROR, "Conversion Error",
					"Id do Responsavel não válido"));
		}
		return resp;
	}

	private Responsavel lookupResponsavel(Long responsavelId) {
		for (Responsavel res : getRespList() ) {
			if(res.getId().equals(responsavelId)) {
				return res;
			}
		}
		return null;
	}

	/**
	 * Recupera a lista que foi carregada na lista retornada para o autocomplete do managed bean.
	 * @return
	 */
	private List<Responsavel> getRespList() {
		FacesContext context = FacesContext.getCurrentInstance();
		AlunoControlador controlador = (AlunoControlador) context.getELContext()
				.getELResolver().getValue(context.getELContext(), null, "alunoControlador");
		List<Responsavel> responsavelList = (List<Responsavel>) controlador.getResponsavelList();
		return responsavelList;
	}

	public String getAsString(FacesContext facesContext, UIComponent uicomp, Object entity) {
		return entity == null ? "-1" : ((Responsavel) entity).getId().toString();
	}

}
