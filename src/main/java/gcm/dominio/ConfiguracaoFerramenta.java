package gcm.dominio;

import java.util.Map;

public class ConfiguracaoFerramenta {
	private Long id;
	private Sistema sistema;
	private Ferramenta ferramenta;
	private String observacao;
	private Map<FerramentaConfiguracao, String> configuracao;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Sistema getSistema() {
		return sistema;
	}
	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}
	public Ferramenta getFerramenta() {
		return ferramenta;
	}
	public void setFerramenta(Ferramenta ferramenta) {
		this.ferramenta = ferramenta;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Map<FerramentaConfiguracao, String> getConfiguracao() {
		return configuracao;
	}
	public void setConfiguracao(Map<FerramentaConfiguracao, String> configuracao) {
		this.configuracao = configuracao;
	}
}
