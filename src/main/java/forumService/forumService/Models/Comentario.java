package forumService.forumService.Models;

import java.time.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotNull
    @ManyToOne
    private Usuario usuario;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "codigo")
    private Post post;

    @NotBlank
    private String comentario;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataComentario;

    @JsonProperty(access = Access.READ_ONLY)
    private boolean resolucao;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public OffsetDateTime getDataComentario() {
        return dataComentario;
    }

    public void setDataComentario(OffsetDateTime dataComentario) {
        this.dataComentario = dataComentario;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public boolean isResolucao() {
        return resolucao;
    }

    public void setResolucao(boolean resolucao) {
        this.resolucao = resolucao;
    }

}
