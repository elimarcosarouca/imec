package br.com.ss.core.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import br.com.ss.core.seguranca.dominio.PerfilRotina;

@FacesConverter(value = "perfilRotinaConverter")
public class PerfilRotinaConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		Object ret = null;
		if (arg1 instanceof PickList) {
			Object dualList = ((PickList) arg1).getValue();
			@SuppressWarnings("rawtypes")
			DualListModel dl = (DualListModel) dualList;
			for (Object o : dl.getSource()) {;
				String id = ( (PerfilRotina) o).hashCode() + "";
				if (arg2.equals(id)) {
					ret = o;
					break;
				}
			}

			if (ret == null) {
				for (Object o : dl.getTarget()) {
					String id = ( (PerfilRotina) o).hashCode() + "";
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
		if (arg2 instanceof PerfilRotina) {
			str = ((PerfilRotina) arg2).hashCode() + "";
		}
		return str;
	}
}