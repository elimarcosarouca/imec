package br.com.ss.converter;

import java.lang.reflect.Field;
import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * 
 * @author Victor Lindberg
 * 
 */
@FacesConverter("selectOneUsingObjectConverter")
public class SelectOneUsingObjectConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value == null || value.equals(""))
			return null;

		try {
			Integer id = Integer.valueOf(value);
			Collection items = (Collection) component.getAttributes().get(
					"items");
			return findById(items, id);
		} catch (Exception ex) {
			throw new ConverterException(
					"Não foi possível aplicar conversão de item com valor ["
							+ value + "] no componente [" + component.getId()
							+ "]", ex);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value == null)
			return "";

		return getIdByReflection(value).toString();
	}

	private Object findById(Collection collection, int idToFind) {
		for (Object obj : collection) {
			Integer id = getIdByReflection(obj);
			if (id == idToFind)
				return obj;
		}

		return null;
	}

	private Integer getIdByReflection(Object bean) {
		try {
			Field idField = bean.getClass().getDeclaredField("id");
			idField.setAccessible(true);
			return (Integer) idField.get(bean);
		} catch (Exception ex) {
			throw new RuntimeException(
					"Não foi possível obter a propriedade 'id' do item", ex);
		}
	}

}