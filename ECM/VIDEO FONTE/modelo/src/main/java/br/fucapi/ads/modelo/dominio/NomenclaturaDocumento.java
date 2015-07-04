package br.fucapi.ads.modelo.dominio;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * The persistent class for the saa_sistema database table.
 * 
 */
@Entity
@Table(name = "ECM_NOMENCLATURA_DOCUMENTO")
public class NomenclaturaDocumento extends AbstractEntity implements
		Serializable {

	private static final long serialVersionUID = 7516652276680517473L;

	@Id
	@GeneratedValue(generator = "SEQ_ECM_NOMENCLATURA_DOCUMENTO", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_ECM_NOMENCLATURA_DOCUMENTO", name = "SEQ_ECM_NOMENCLATURA_DOCUMENTO")
	@Column(name = "ID_ECM_NOMENCLATURA_DOCUMENTO")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ID_ECM_UNIDADE", nullable = false)
	private Unidade unidade;

	@ManyToOne
	@JoinColumn(name = "ID_ECM_SETOR", nullable = false)
	private Setor setor;

	@ManyToOne
	@JoinColumn(name = "ID_ECM_CATEGORIA", nullable = false)
	private Categoria categoria;

	@Column(nullable = false)
	private int sequencial;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public NomenclaturaDocumento() {
		super();
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public int getSequencial() {
		return sequencial;
	}

	public void setSequencial(int sequencial) {
		this.sequencial = sequencial;
	}

	public String getSequencialFormatado() {
		String var = String.valueOf(this.getSequencial());

		if (var.length() == 1)
			return "00" + var;
		else if (var.length() == 2)
			return "0" + var;
		else
			return var;
	}

	public String toString() {
		return getUnidade().getSigla() + "." + getSetor().getSigla() + "."
				+ getCategoria().getSigla() + "." + getSequencialFormatado();
	}

	public boolean equals(Object o) {
		if (o instanceof NomenclaturaDocumento
				&& ((NomenclaturaDocumento) o).getId() == this.id)
			return true;
		else
			return false;
	}

	public static NomenclaturaDocumento fromJsonToObject(String json) {
		return new JSONDeserializer<NomenclaturaDocumento>().use(null,
				NomenclaturaDocumento.class).deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer()
				.exclude("*.class")
				.transform(new DateTransformer("dd/MM/yyyy HH:mm:ss"),
						Date.class).serialize(this);
	}

	public static String toJsonArray(Collection<NomenclaturaDocumento> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

}