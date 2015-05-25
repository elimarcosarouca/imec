package br.fucapi.ads.modelo.dominio;

import java.io.Serializable;
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

import br.fucapi.bpms.activiti.dominio.TarefaInstancia;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

/**
 * The persistent class for the ecm_alerta database table.
 * 
 */
@Entity
@Table(name = "ECM_ALERTA")
public class Alerta extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 7516652276680517473L;

	@Id
	@GeneratedValue(generator = "SEQ_ECM_ALERTA", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_ECM_ALERTA", name = "SEQ_ECM_ALERTA")
	@Column(name = "ID_ECM_ALERTA")
	private Long id;
	
	@Column(nullable=true)
	private String processInstanceId;

	@Column(length = 10, nullable = false, unique = true)
	private String protocolo;
	
	@Column(nullable = false)
	private Date dataAlerta;

	@Column(nullable = false)
	private Date dataVencimento;

	@Column(nullable = false)
	private Date dataCadastro;

	@Column(nullable = false)
	private String nomenclatura;
	
	@Column(nullable = false)
	private String titulo;
	
	@ManyToOne
	@JoinColumn(name = "ID_ECM_UNIDADE", nullable=false)
	private Unidade unidade; 
	
	public String getColor() {
		Date dataConvertidaEmUtil = new Date(dataVencimento.getTime());
		if (dataConvertidaEmUtil.after(new Date())) {
			return "yellow";
		} else
			return "red";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public Date getDataAlerta() {
		return dataAlerta;
	}

	public void setDataAlerta(Date dataAlerta) {
		this.dataAlerta = dataAlerta;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getNomenclatura() {
		return nomenclatura;
	}

	public void setNomenclatura(String nomenclatura) {
		this.nomenclatura = nomenclatura;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public Alerta() {
	}

	public void converterTarefaInstanciaToAlerta(TarefaInstancia tarefaInstancia) {
		this.dataAlerta = ((VariavelPublicarDocumento) tarefaInstancia
				.getVariaveis()).getDataNotificacao();
		this.dataCadastro = new Date();
		this.dataVencimento = ((VariavelPublicarDocumento) tarefaInstancia
				.getVariaveis()).getDataVencimento();
		this.protocolo = ((VariavelPublicarDocumento) tarefaInstancia
				.getVariaveis()).getProtocolo();
		
		this.nomenclatura = ((VariavelPublicarDocumento) tarefaInstancia
				.getVariaveis()).getNomenclatura();
		
		this.titulo = ((VariavelPublicarDocumento) tarefaInstancia
				.getVariaveis()).getArquivoDoc().getNomeArquivo();
		
		this.unidade = ((VariavelPublicarDocumento) tarefaInstancia
				.getVariaveis()).getUnidade();
		
		this.processInstanceId = tarefaInstancia
				.getProcessInstanceId();
	}

	public boolean equals(Object o) {
		if (o instanceof Alerta && ((Alerta) o).getId() == this.id)
			return true;
		else
			return false;
	}

	public static Alerta fromJsonToObject(String json) {
		return new JSONDeserializer<Alerta>().use(null, Alerta.class)
				.deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}
}