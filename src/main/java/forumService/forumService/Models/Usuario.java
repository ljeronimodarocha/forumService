package forumService.forumService.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.UniqueElements;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotBlank
	private String name;

	@NotBlank
	@Email
	@Column(unique = true)
	private String email;

	@NotBlank
	// @JsonIgnore
	private String password;

	@Enumerated(EnumType.STRING)
	private Estado estado;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public Estado getEstado() {
		return estado;
	}

	@Override
	public String toString() {
		return "Usuario [codigo=" + codigo + ", email=" + email + ", estado=" + estado + ", name=" + name
				+ ", password=" + password + "]";
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}
