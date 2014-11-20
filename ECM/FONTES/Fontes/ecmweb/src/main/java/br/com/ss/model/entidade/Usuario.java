package br.com.ss.model.entidade;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.ss.enumerado.StatusUsuario;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@Entity
@Table(name = "SAA_USUARIO")
public class Usuario extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -3558406268122435705L;

	@Id
	@GeneratedValue(generator = "SEQ_SAA_USUARIO", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_SAA_USUARIO", name = "SEQ_SAA_USUARIO")
	@Column(name = "ID_SAA_USUARIO")
	private Long id;

	@Column(name = "nome", nullable = false, length = 60)
	private String nome;

	@Column(name = "login", nullable = false, length = 14, unique = true)
	private String login;

	@Column(name = "senha", nullable = false, length = 60)
	private String senha;

	@Column(name = "email", nullable = false, length = 60)
	private String email;

	@Column(name = "endereco", nullable = false, length = 60)
	private String endereco;

	@Column(name = "data_dadastro", nullable = false)
	private Date dataCadastro;

	@ManyToOne
	@JoinColumn(name = "ID_SAA_MUNICIPIO", nullable = false)
	private Municipio municipio;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 1)
	private StatusUsuario status;

	@OneToMany(mappedBy = "usuario")
	@Column(name = "ID_SAA_USUARIO")
	private Set<UsuarioSistema> usuarioSistemas;

	@OneToMany(mappedBy = "usuario")
	@Column(name = "ID_SAA_USUARIO")
	private Set<UsuarioPerfil> usuarioPerfiis;

	@ManyToOne
	@JoinColumn(name = "ID_SAA_EMPRESA", nullable = false)
	private Empresa empresa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public Set<UsuarioPerfil> getUsuarioPerfiis() {
		return usuarioPerfiis;
	}

	public void setUsuarioPerfiis(Set<UsuarioPerfil> usuarioPerfiis) {
		this.usuarioPerfiis = usuarioPerfiis;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public StatusUsuario getStatus() {
		return status;
	}

	public void setStatus(StatusUsuario status) {
		this.status = status;
	}

	public Set<UsuarioSistema> getUsuarioSistemas() {
		return usuarioSistemas;
	}

	public void setUsuarioSistemas(Set<UsuarioSistema> usuarioSistemas) {
		this.usuarioSistemas = usuarioSistemas;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public static Usuario fromJsonToObject(String json) {
		return new JSONDeserializer<Usuario>().use(null, Usuario.class)
				.deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer()
				.exclude("*.class")
				.transform(new DateTransformer("dd/MM/yyyy HH:mm:ss"),
						Date.class).serialize(this);
	}

	public static String toJsonArray(Collection<Usuario> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

}