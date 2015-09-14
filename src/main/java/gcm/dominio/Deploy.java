package gcm.dominio;

import java.util.Date;

/**
 * Value Object que representa o deploy de uma release de sistema em um ambiente.  
 *
 */
public final class Deploy implements Comparable<Deploy> {
	private Ambiente ambiente;
	private String versao;
	private Date dataDeploy;
	
	public Deploy(String versao, Ambiente ambiente, Date dataDeploy) {
		this.versao = versao;
		this.ambiente = ambiente;
		this.dataDeploy = dataDeploy;
	}
	
	@Override
	public String toString() {
		return ambiente + ", " + versao + ", " + dataDeploy;
	}

	@Override
	public int compareTo(Deploy outro) {
		return this.dataDeploy.compareTo(outro.dataDeploy);
	}
	
	public Ambiente getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}

	public String getVersao() {
		return versao;
	}

	public Date getDataDeploy() {
		return dataDeploy;
	}
	
}
