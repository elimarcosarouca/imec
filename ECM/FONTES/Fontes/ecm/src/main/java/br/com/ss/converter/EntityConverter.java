package br.com.ss.converter;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;

import br.com.ss.util.StringUtil;

/**
 * Conversor para entidades. Utilizado nos combos.
 * Entidades devem herdar de {@link AbstractEntity}.
 * 
 * Deprecated: utilizar o genericConverter
 * @author robson.ramos
 */
@Deprecated
@FacesConverter(value = "entityConverter")
public class EntityConverter implements Converter {

	private int index;

	@SuppressWarnings("unchecked")
	public Object getAsObject( FacesContext facesContext, UIComponent uicomp, String value ) {
		/* => o item 0 no combo eh nulo (o "Selecione um registro" ou "Todos os registros" ) */
		if ( !StringUtil.notEmpty(value) || value.equals( ( ( Integer ) 0 ).toString() ) ) {
			return null;
		}
		List<Object> items = new ArrayList<Object>();
		List<UIComponent> uicompList = uicomp.getChildren();
		for (UIComponent comp : uicompList) {
			if (comp instanceof UISelectItems) {
				items.addAll( ( List<SelectItem> ) ( ( UISelectItems ) comp).getValue());
			}
		}
		/* -1 : para nao considerar o item 0 do combo */ 
		return "-1".equals(value) ? null : items.get(Integer.valueOf(value) -1); 
	}

	public String getAsString( FacesContext facesContext, UIComponent uicomp, Object entity ) {
		return entity == null ? "-1" : String.valueOf( index++ );
	}

}