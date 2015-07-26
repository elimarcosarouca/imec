package br.fucapi.ads.modelo.dominio;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.fucapi.ads.modelo.enumerated.StatusProcesso;
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

	@Column(nullable = true)
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
	private String codigo;

	@Column(nullable = false)
	private String titulo;

	@Column(nullable = false)
	private int revisao;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length=15)
	private StatusProcesso status;

	@ManyToOne
	@JoinColumn(name = "ID_ECM_UNIDADE", nullable = false)
	private Unidade unidade;

	public String getColor() {
		Date dataConvertidaEmUtil = new Date(dataVencimento.getTime());
		if (dataConvertidaEmUtil.after(new Date())) {
			return "../../resources/imagens/icon-alert-icon.png";
		} else
			return "../../resources/imagens/action-stop-icon.png";
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	public int getRevisao() {
		return revisao;
	}

	public void setRevisao(int revisao) {
		this.revisao = revisao;
	}

	public StatusProcesso getStatus() {
		return status;
	}

	public void setStatus(StatusProcesso status) {
		this.status = status;
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

		this.codigo = ((VariavelPublicarDocumento) tarefaInstancia
				.getVariaveis()).getCodigo();

		this.titulo = ((VariavelPublicarDocumento) tarefaInstancia
				.getVariaveis()).getNomeDocumento();

		this.unidade = ((VariavelPublicarDocumento) tarefaInstancia
				.getVariaveis()).getUnidade();

		this.processInstanceId = tarefaInstancia.getProcessInstanceId();
	}

	public int getDias() {
		Calendar a = Calendar.getInstance();

		Calendar b = Calendar.getInstance();

		a.setTime(getDataVencimento());
		b.setTime(new Date());

		a.add(Calendar.DATE, -b.get(Calendar.DAY_OF_MONTH));
		System.out.println(a.get(Calendar.DAY_OF_MONTH));

		return a.get(Calendar.DAY_OF_MONTH);
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