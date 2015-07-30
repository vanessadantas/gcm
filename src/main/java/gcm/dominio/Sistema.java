package gcm.dominio;

import java.util.Set;

/**
 * Representa um sistema ou um projeto de sistema dentro da organização.
 * É o principal artefato gerenciado pela equipe de GCM.  
 * @author vanessa
 *
 */
public class Sistema {
	
	private Long id;
	private String nome;
	private String sigla;
	private Set<Responsavel> responsaveis;
	private Arquitetura arquitetura;
	private Set<Versao> versoes;
	private Set<Sistema> dependencias;
	
	@Override
	public String toString() {
		return this.sigla + " - " + this.nome;
	}
			
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sistema other = (Sistema) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (sigla == null) {
			if (other.sigla != null)
				return false;
		} else if (!sigla.equals(other.sigla))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public Set<Responsavel> getResponsaveis() {
		return responsaveis;
	}
	public void setResponsaveis(Set<Responsavel> responsaveis) {
		this.responsaveis = responsaveis;
	}
	public Arquitetura getArquitetura() {
		return arquitetura;
	}
	public void setArquitetura(Arquitetura arquitetura) {
		this.arquitetura = arquitetura;
	}
	public Set<Versao> getVersoes() {
		return versoes;
	}
	public void setVersoes(Set<Versao> versoes) {
		this.versoes = versoes;
	}
	public Set<Sistema> getDependencias() {
		return dependencias;
	}
	public void setDependencias(Set<Sistema> dependencias) {
		this.dependencias = dependencias;
	}

	
	
}
