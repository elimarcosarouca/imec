package br.com.ss.core.web.controlador.listener;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name = "comboChangeListener", eager = true)
@ApplicationScoped
public class ComboChangeListener {

	public void valueChanged(ValueChangeEvent e) {
		// dummy - valueChangeListener padrao do combobox
	}
}
