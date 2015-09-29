package gcm.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * Representa um sistema ou um projeto de sistema dentro da organização.
 * É o principal artefato gerenciado pela equipe de GCM.  
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name=Sistema.PESQUISAR_POR_NOME, 
		query="select s from Sistema s where upper(s.nome) like :nome order by s.nome"),
	@NamedQuery(name=Sistema.PESQUISAR_POR_SIGLA_EXATA, 
		query="select s from Sistema s where upper(s.sigla) = :sigla"),
	@NamedQuery(name=Sistema.PESQUISAR_DEPLOYS_POR_PERIODO, 
		query="select s from Sistema s inner join s.releases r " +
				"left join r.deploysProducao dp " +
				"left join r.deploysHomologacao dh " +
				"left join r.deploysTeste dt " +
				"where (dp between :inicio and :fim) or " +
				"(dh between :inicio and :fim) or " +
				"(dt between :inicio and :fim) ")
})
public class Sistema {
	
	public static final String PESQUISAR_POR_NOME = "sistema.pesquisarPorNome";
	public static final String PESQUISAR_POR_SIGLA_EXATA = "sistema.pesquisarPorSiglaExata";
	public static final String PESQUISAR_DEPLOYS_POR_PERIODO = "sistema.pesquisarDeploysPorPeriodo";
	@Id
	@GeneratedValue
	private Long id;
	@Column
	private String nome;
	@Column
	private String sigla;
	@Column
	private String url;
	@Enumerated(EnumType.STRING)
	private SituacaoSistema situacao;
	@ManyToMany
	private Set<Responsavel> responsaveis = new HashSet<>();
	@OneToOne
	private Arquitetura arquitetura;	
	@Column
	private String observacao;	
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="sistema_id")
	private List<Release> releases = new ArrayList<>();
	
	@Transient
	private Set<PontoIntegracao> dependencias;
	@OneToMany
	private Set<PontoIntegracao> pontosIntegracao;
	
	@Transient
	private List<Deploy> deploys;
	
	public void adicionarRelease(Release release) {
		releases.add(release);
	}
	
	public List<Deploy> getDeploys() {
		if (deploys == null) {
			deploys = new ArrayList<>();
			for (Release release : releases) {
				for (Date data : release.getDeploysProducao()) {
					deploys.add(new Deploy(release.getNumero(), release.getId(), Ambiente.PRODUCAO, data, getSigla()));
				}
				for (Date data : release.getDeploysHomologacao()) {
					deploys.add(new Deploy(release.getNumero(), release.getId(), Ambiente.HOMOLOGACAO, data, getSigla()));
				}
				for (Date data : release.getDeploysTeste()) {
					deploys.add(new Deploy(release.getNumero(), release.getId(), Ambiente.TESTE, data, getSigla()));
				}
			}
		}
		return deploys;
	}

	public List<Deploy> getDeploysOrdenadosPorData() {
		List<Deploy> deploysOrdenados = new ArrayList<Deploy>();
		deploysOrdenados.addAll(getDeploys());
		Collections.sort(deploysOrdenados);
		return deploysOrdenados;
	}
	
	public List<Deploy> getDeploysOrdenadosPorDataDecresecente() {
		List<Deploy> deploysOrdenados = getDeploysOrdenadosPorData();
		Collections.reverse(deploysOrdenados);
		return deploysOrdenados;
	}
	
	public Deploy getUltimoDeploy(Ambiente ambiente) {
		List<Deploy> deploys = getDeploysOrdenadosPorDataDecresecente();
		for (Deploy deploy : deploys) {
			if (deploy.getAmbiente().equals(ambiente)) {
				return deploy;
			}
		}
		return null;
	}
	
	public void adicionarPontoIntegracao(PontoIntegracao pi) {
		if (pontosIntegracao == null) {
			pontosIntegracao =  new HashSet<>();
		}
		pontosIntegracao.add(pi);
	}

	@Override
	public String toString() {
		return this.sigla + " - " + this.nome;
	}
			
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arquitetura == null) ? 0 : arquitetura.hashCode());
		result = prime * result + ((dependencias == null) ? 0 : dependencias.hashCode());
		result = prime * result + ((deploys == null) ? 0 : deploys.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + ((responsaveis == null) ? 0 : responsaveis.hashCode());
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		if (arquitetura == null) {
			if (other.arquitetura != null)
				return false;
		} else if (!arquitetura.equals(other.arquitetura))
			return false;
		if (dependencias == null) {
			if (other.dependencias != null)
				return false;
		} else if (!dependencias.equals(other.dependencias))
			return false;
		if (deploys == null) {
			if (other.deploys != null)
				return false;
		} else if (!deploys.equals(other.deploys))
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
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (responsaveis == null) {
			if (other.responsaveis != null)
				return false;
		} else if (!responsaveis.equals(other.responsaveis))
			return false;
		if (sigla == null) {
			if (other.sigla != null)
				return false;
		} else if (!sigla.equals(other.sigla))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public SituacaoSistema getSituacao() {
		return situacao;
	}
	public void setSituacao(SituacaoSistema situacao) {
		this.situacao = situacao;
	}
	public List<Release> getReleases() {
		return releases;
	}
}
