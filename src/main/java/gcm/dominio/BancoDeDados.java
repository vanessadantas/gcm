package gcm.dominio;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="Banco de dados")
public class BancoDeDados extends PontoIntegracao {
	
	private String banco;
	
	@ElementCollection
	private Set<String> tabelas;

	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public Set<String> getTabelas() {
		return tabelas;
	}
	public void setTabelas(Set<String> tabelas) {
		this.tabelas = tabelas;
	}
}