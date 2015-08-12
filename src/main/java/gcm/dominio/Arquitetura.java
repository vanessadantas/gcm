package gcm.dominio;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name=Arquitetura.PESQUISAR_POR_NOME, 
query="select a from Arquitetura a where upper(a.nome) like :nome order by a.nome")
public class Arquitetura {
	public static final String PESQUISAR_POR_NOME = "arquitetura.pesquisarPorNome";
	
	@Id
	@GeneratedValue
	private Long id;
	@Column(length=100, nullable=false)
	private String nome;
	
	@OneToMany
	private Set<Framework> frameworks = new HashSet<>();
	@Column(length=1024)
	private String descricao;	
	
	@Override
	public String toString() {
		return nome;
	}	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result
				+ ((frameworks == null) ? 0 : frameworks.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Arquitetura other = (Arquitetura) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (frameworks == null) {
			if (other.frameworks != null)
				return false;
		} else if (!frameworks.equals(other.frameworks))
			return false;
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

	public Set<Framework> getFrameworks() {
		return frameworks;
	}

	public void setFrameworks(Set<Framework> frameworks) {
		this.frameworks = frameworks;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
			
}
