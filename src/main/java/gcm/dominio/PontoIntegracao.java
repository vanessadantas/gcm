package gcm.dominio;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType=DiscriminatorType.STRING, name="tipoIntegracao")
public class PontoIntegracao {
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(name="tipoIntegracao", insertable=false, updatable=false)
	protected String tipoIntegracao;
	
	private String endereco;
	private String observacao;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public String getTipoIntegracao() {
		return this.tipoIntegracao;
	}
	
}
