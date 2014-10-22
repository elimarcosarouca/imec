package br.com.ss.core.web.componentes;

import javax.el.MethodExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

@FacesComponent("combobox")
public class ComboboxComponent extends UINamingContainer {

	// FIXME validar se ainda é necessario - foi criado para invocar o action listener
	
	@Override
	public String getFamily() {
		return "javax.faces.NamingContainer";
	}

	public boolean isHasListener() {
		return getValueExpression("listener") != null;
	}

	public void actionListener(AjaxBehaviorEvent event) {
		System.out.println(" ###### ");
		invokeMethod(event);
	}

	public void actionListener() {
		System.out.println(" @@@@@ ");
		invokeMethod(null);
	}

	private void invokeMethod(AjaxBehaviorEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		MethodExpression ajaxEventListener = (MethodExpression) getAttributes()
				.get("closeListener");
		if (ajaxEventListener != null) {
			ajaxEventListener.invoke(context.getELContext(),
					new Object[] { event });
		}
	}

}
