package br.com.ss.core.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import br.com.ss.core.seguranca.dominio.AbstractEntity;

/**
 * Converter para entidades do tipo {@link AbstractEntity}.
 * @author Robson
 */
@FacesConverter(value = "primeFacesPickListConverter")
public class PrimeFacesPickListConverter implements Converter {

	@SuppressWarnings("rawtypes")
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		Object ret = null;
		if (arg1 instanceof PickList) {
			Object dualList = ((PickList) arg1).getValue();
			DualListModel dl = (DualListModel) dualList;
			for (Object o : dl.getSource()) {;
				String id = (new StringBuilder()).append( ((AbstractEntity) o).getId()).toString();
				if (arg2.equals(id)) {
					ret = o;
					break;
				}
			}

			if (ret == null) {
				for (Object o : dl.getTarget()) {
					String id = (new StringBuilder()).append( ((AbstractEntity) o).getId()).toString();
					if (arg2.equals(id)) {
						ret = o;
						break;
					}
				}

			}
		}
		return ret;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		String str = "";
		if (arg2 instanceof AbstractEntity) {
			str = ((AbstractEntity) arg2).getId().toString();
		}
		return str;
	}
}