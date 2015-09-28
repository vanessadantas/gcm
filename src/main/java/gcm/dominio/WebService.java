package gcm.dominio;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="Web Service")
public class WebService extends PontoIntegracao {
	@Column(name="tipoIntegracao", insertable=false, updatable=false)
	protected String tipoIntegracao;

	private boolean rest;
	private boolean publico;
	public boolean isRest() {
		return rest;
	}
	public void setRest(boolean rest) {
		this.rest = rest;
	}
	public boolean isPublico() {
		return publico;
	}
	public void setPublico(boolean publico) {
		this.publico = publico;
	}
	public String getTipoIntegracao() {
		return tipoIntegracao;
	}
	
}
