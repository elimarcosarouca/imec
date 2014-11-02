package br.com.ss.academico.dominio;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.ss.academico.enumerated.StatusBoletim;
import br.com.ss.core.seguranca.dominio.AbstractEntity;

@Entity
@Table(name = "acad_boletim")
public class Boletim extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -6438912749527248323L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idBoletim;

	@Enumerated
	@Column
	private StatusBoletim statusBoletim = StatusBoletim.LANCAMENTOS_PENDENTES;
	
	@ManyToOne
	@JoinColumn(name = "id_matricula", nullable = false)
	private Matricula matricula;

	@OneToMany(mappedBy = "boletim", cascade = CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval = true )
	private Set<DetalheBoletim> detalheBoletims = new HashSet<DetalheBoletim>();

	public Boletim() { }


	/**
	 * Atualiza o boletim (final) do aluno.
	 * @param mediaEscolar
	 */
	public void atualizarBoletim(final Float mediaEscolar) {
		
		for(DetalheBoletim det : detalheBoletims ) {
			det.calcularMedias();
			det.calcularMediaFinal(mediaEscolar);
		}
		
		// atualiza o status se o aluno esta aprovado ou reprovado no ano letivo
		statusBoletim = StatusBoletim.APROVADO;
		for(DetalheBoletim det : detalheBoletims ) {
			if(StatusBoletim.REPROVADO.equals( det.getStatusDisciplina()) ) { 
				statusBoletim = StatusBoletim.REPROVADO;
			}
		}
	}
	

	/* -------- Gets/Sets ---------------- */
	@Override
	public Long getId() {
		return idBoletim;
	}

	public Long getIdBoletim() {
		return idBoletim;
	}

	public void setIdBoletim(Long idBoletim) {
		this.idBoletim = idBoletim;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public Set<DetalheBoletim> getDetalheBoletims() {
		return detalheBoletims;
	}

	public void setDetalheBoletims(Set<DetalheBoletim> detalheBoletims) {
		this.detalheBoletims = detalheBoletims;
	}


	public StatusBoletim getStatusBoletim() {
		return statusBoletim;
	}


	public void setStatusBoletim(StatusBoletim statusBoletim) {
		this.statusBoletim = statusBoletim;
	}

}