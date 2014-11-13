package br.com.ss.componentes;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ss.enumerado.Constants;


@ManagedBean
@SessionScoped
public class NavigationHistory {

	private List<NavigationDTO> history;

	@PostConstruct
	public void init() {
		history = new ArrayList<NavigationDTO>();
		addHome();
	}
	
	
	public void addHistory(String label, String url) {
		NavigationDTO dto = new NavigationDTO( label, url );
		if (!history.contains(dto)) {
			history.add( dto );
		}
		
		clearAfterUrl(dto);
	}
	
	/**
	 * Remove os links após o link informado.
	 * @param dto
	 */
	public void clearAfterUrl(NavigationDTO dto) {
		int indexOf = history.indexOf(dto);
		clearAfterPosition(++indexOf);
	}

	private void clearAfterPosition( Integer position ) {
		history = history.subList(0, position);
	}

	private void addHome() {
		history.add( new NavigationDTO("Home", Constants.INDEX_EXTENSION ) );
	}
	
	

	/* ------- Inner DTO ------------ */
	public class NavigationDTO {
		private String label;
		private String url;
		
		public NavigationDTO(String label, String url) {
			super();
			this.label = label;
			this.url = url;
		}

		public String getLabel() {
			return label;
		}
		public String getUrl() {
			return url;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((url == null) ? 0 : url.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			NavigationDTO other = (NavigationDTO) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (url == null) {
				if (other.url != null)
					return false;
			} else if (!url.equals(other.url))
				return false;
			return true;
		}

		private NavigationHistory getOuterType() {
			return NavigationHistory.this;
		}
		
	}


	public List<NavigationDTO> getHistory() {
		return history;
	}
	
}
