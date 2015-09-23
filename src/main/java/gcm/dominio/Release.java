package gcm.dominio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
	private List<Date> deploysProducao = new ArrayList<>();

	@ElementCollection
	@CollectionTable(name="DeploysHomologacao", joinColumns=@JoinColumn(name="release_id"))
	@Column(name="deploysHomologacao")
	private List<Date> deploysHomologacao = new ArrayList<>();
	
	@ElementCollection
	@CollectionTable(name="DeploysTeste", joinColumns=@JoinColumn(name="release_id"))
	@Column(name="deploysTeste")
	private List<Date> deploysTeste = new ArrayList<>();

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
	public List<Date> getDeploysProducao() {
		return deploysProducao;
	}
	public List<Date> getDeploysHomologacao() {
		return deploysHomologacao;
	}
	public List<Date> getDeploysTeste() {
		return deploysTeste;
	}
}
