package gcm.dominio;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;

@Entity
public class Release {

	@Id
	@GeneratedValue
	private Long id;
	private String numero;
	private Date dataCriacao;
	private String notas;
	private boolean testada;
	private boolean homologada;
	private String observacao;
	@Transient
	private Map<SituacaoTesteRelease, Date> historicoSituacaoTeste;
	
	@ElementCollection
	@CollectionTable(name="DeploysProducao", joinColumns=@JoinColumn(name="release_id"))
	@Column(name="deploysProducao")
	private Set<Date> deploysProducao = new HashSet<>();
	@ElementCollection
	@CollectionTable(name="DeploysHomologacao", joinColumns=@JoinColumn(name="release_id"))
	@Column(name="deploysHomologacao")
	private Set<Date> deploysHomologacao = new HashSet<>();
	@ElementCollection
	@CollectionTable(name="DeploysTeste", joinColumns=@JoinColumn(name="release_id"))
	@Column(name="deploysTeste")
	private Set<Date> deploysTeste = new HashSet<>();

	public void adicionarDeployProducao() {
		this.deploysProducao.add(new Date());
	}
	
	public void adicionarDeployHomologacao() {
		this.deploysHomologacao.add(new Date());
	}

	public void adicionarDeployTeste() {
		this.deploysTeste.add(new Date());
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public String getNotas() {
		return notas;
	}
	public void setNotas(String notas) {
		this.notas = notas;
	}
	public boolean isTestada() {
		return testada;
	}
	public void setTestada(boolean testada) {
		this.testada = testada;
	}
	public boolean isHomologada() {
		return homologada;
	}
	public void setHomologada(boolean homologada) {
		this.homologada = homologada;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Map<SituacaoTesteRelease, Date> getHistoricoSituacaoTeste() {
		return historicoSituacaoTeste;
	}
	public void setHistoricoSituacaoTeste(
			Map<SituacaoTesteRelease, Date> historicoSituacaoTeste) {
		this.historicoSituacaoTeste = historicoSituacaoTeste;
	}
	public Set<Date> getDeploysProducao() {
		return deploysProducao;
	}
	public Set<Date> getDeploysHomologacao() {
		return deploysHomologacao;
	}
	public Set<Date> getDeploysTeste() {
		return deploysTeste;
	}
}
