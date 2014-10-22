package br.com.ss.core.web.componentes;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.component.export.ExporterFactory;

import br.com.ss.core.web.utils.StringUtil;

import com.sun.faces.component.visit.FullVisitContext;

@Named("componentUtil")
public class ComponentUtil {
	
	/** se nao for informado update de componente faz update do proprio componente. */
	private static String empty = "@this";
	

	/**
	 * Retorna o component buscando pelo id informado.
	 * @param id
	 * @return
	 */
	public static UIComponent getUIComponent(final String id) {  
  
		
		FacesContext context = FacesContext.getCurrentInstance(); 
	    UIViewRoot root = context.getViewRoot();

	    // busca apenas o primeiro componente com o id informado.  
	    final UIComponent[] found = new UIComponent[1];
	    
	    root.visitTree(new FullVisitContext(context), new VisitCallback() {  
	    	
	        @Override
	        public VisitResult visit(VisitContext context, UIComponent component) {
	            if(component != null && component.getId() != null && component.getId().equals(id)){
	                found[0] = component;
	                return VisitResult.COMPLETE;
	            }
	            return VisitResult.ACCEPT;              
	        }
	        
	    });
	    
	    return found[0];
		
	}
	
	
	public String getComponentId(String idComp) {
		 return getUIComponent(idComp).getId() ;  
	}
	
	

	public DataTable getDataTableComponent( Object comp ) {
		getUIComponent(comp.toString());
		DataTable dataTable = (DataTable) comp;
		
		return dataTable;
	}
	
	public DataTable getDataTableComponent( String gridId ) {
		
		UIComponent component = getUIComponent(gridId);
		
//		String tableId = (String) target.getValue(elContext);
//
//		Exporter exporter = ExporterFactory.getExporterForType(exportAs);
//
//		UIComponent component = event.getComponent().findComponent(tableId);


		return null;
	}
	
	
	
	/**
	 * Retorna os nomes dos componentes a serem buscados pela classe org.primefaces.util.ComponentUtils do Primefaces.
	 * Uso: update="pnlObservacao pnlTotal"
	 * @param componentNames
	 * @return
	 */
	public String splitNameComponent( String componentNames ) {
		if (StringUtil.notEmpty(componentNames)) {
			
			// find do componente  
			String findCompCall = ":#{p:component(cc.attrs.update)}";
			String[] names = componentNames.split(" ");	// splits by empty spaces
			StringBuilder sb = new StringBuilder();
			for (short i = 0; i < names.length; i++ ) {
				String comp = names[i];
				sb.append( findCompCall.replace("cc.attrs.update", "'" + comp + "'") );
			}
			return sb.toString();
		}
		return empty;
	}
	
	

}
