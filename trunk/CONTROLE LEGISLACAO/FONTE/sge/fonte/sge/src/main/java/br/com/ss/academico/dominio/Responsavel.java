package br.com.ss.academico.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Email;

import br.com.ss.academico.enumerated.EstadoCivil;
import br.com.ss.core.seguranca.dominio.AbstractEntity;
import br.com.ss.core.web.enumerated.Sexo;

/**
 * The persistent class for the acad_responsavel database table.
 */
@Entity
@Table(name = "acad_responsavel")
public class Responsavel extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -3097639190127014748L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idResponsavel;

	@Column(nullable = false, length = 60)
	private String bairro;

	@Column(length = 9)
	private String celular;

	@Column(nullable = true, length = 9)
	private String cep;

	@Column(nullable = false, length = 14)
	private String cpf;

	@Enumerated
	@Column(nullable = false, length = 1)
	private Sexo sexo;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataNascimento;

	@Email(message="E-mail inválido", regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
	@Column(length = 40)
	private String email;

	@Column(nullable = false, length = 60)
	private String endereco;

	@Column(length = 9)
	private String foneComercial;

	@Column(length = 9)
	private String foneResidencial;

	@Column(nullable = false, length = 60)
	private String nome;

	@Column(nullable = false, length = 30)
	private String profissao;

	@Column(nullable = false, length = 10)
	private String rg;

	@Transient
	private String dataInicioFormatada;

	@Transient
	private String dataFimFormatada;

	@Transient
	private String dataNascimentoFormatada;

	@OneToMany(mappedBy = "responsavel", fetch = FetchType.EAGER)
	private List<Aluno> alunos = new ArrayList<Aluno>();
	
	 @Enumerated(EnumType.ORDINAL)
	 @Column(name = "estado_civil", nullable = false, columnDefinition = "INT(1)")
	 private EstadoCivil estadoCivil;
	
	
	@Override
	public Long getId() {
		return idResponsavel;
	}
	
	
	@Transient
	public String getAlunosAsString() {
		StringBuilder sb = new StringBuilder();
		for (Aluno al : alunos) {
			sb.append(al.getNome() + "; ");
		}
		return sb.toString();
	}

	public Long getIdResponsavel() {
		return idResponsavel;
	}

	public void setIdResponsavel(Long idResponsavel) {
		this.idResponsavel = idResponsavel;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getFoneComercial() {
		return foneComercial;
	}

	public void setFoneComercial(String foneComercial) {
		this.foneComercial = foneComercial;
	}

	public String getFoneResidencial() {
		return foneResidencial;
	}

	public void setFoneResidencial(String foneResidencial) {
		this.foneResidencial = foneResidencial;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getDataInicioFormatada() {
		return dataInicioFormatada;
	}

	public void setDataInicioFormatada(String dataInicioFormatada) {
		this.dataInicioFormatada = dataInicioFormatada;
	}

	public String getDataFimFormatada() {
		return dataFimFormatada;
	}

	public void setDataFimFormatada(String dataFimFormatada) {
		this.dataFimFormatada = dataFimFormatada;
	}

	public String getDataNascimentoFormatada() {
		return dataNascimentoFormatada;
	}

	public void setDataNascimentoFormatada(String dataNascimentoFormatada) {
		this.dataNascimentoFormatada = dataNascimentoFormatada;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}


	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

}