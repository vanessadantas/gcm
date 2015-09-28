package gcm.dominio;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="Arquivo")
public class Arquivo extends PontoIntegracao {

	@Column(name="tipoIntegracao", insertable=false, updatable=false)
	protected String tipoIntegracao;

	private String formato;

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getTipoIntegracao() {
		return tipoIntegracao;
	}
	
}
