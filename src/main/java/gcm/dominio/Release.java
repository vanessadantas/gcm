package gcm.dominio;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	
	@ElementCollection(fetch=FetchType.EAGER)
	@MapKeyEnumerated(EnumType.STRING)
	@CollectionTable(name="HistoricoSituacaoTeste", joinColumns=@JoinColumn(name="release_id"))
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data")
	private Map<SituacaoTeste, Date> historicoSituacaoTeste;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@MapKeyEnumerated(EnumType.STRING)
	@CollectionTable(name="HistoricoSituacaoHomologacao", joinColumns=@JoinColumn(name="release_id"))
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data")
	private Map<SituacaoHomologacao, Date> historicoSituacaoHomologacao;

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
	
	public void adicionarSituacaoTeste (SituacaoTeste situacaoTeste, Date data) {
		if (historicoSituacaoTeste == null) {
			historicoSituacaoTeste = new HashMap<>();
		}
		historicoSituacaoTeste.put(situacaoTeste, data);
	}

	public SituacaoRelease getSituacao() {
		if (! deploysProducao.isEmpty()) {
			if (testada && homologada) {
				return SituacaoRelease.PRODUCAO_TESTADA_HOMOLOGADA;
			} else if (testada) {
				return SituacaoRelease.PRODUCAO_TESTADA;
			} else if (homologada) {
				return SituacaoRelease.PRODUCAO_HOMOLOGADA;
			} else if ( !deploysTeste.isEmpty() && !deploysHomologacao.isEmpty() ) {
				return SituacaoRelease.PRODUCAO_DEPLOYS_HOMOLOGACAO_TESTE;
			} else if (! deploysTeste.isEmpty()) {
				return SituacaoRelease.PRODUCAO_DEPLOY_TESTE;
			} else if (! deploysHomologacao.isEmpty()) {
				return SituacaoRelease.PRODUCAO_DEPLOY_HOMOLOGACAO;
			} else if (deploysHomologacao.isEmpty() && deploysTeste.isEmpty()) {
				return SituacaoRelease.PRODUCAO_SEM_DEPLOY_HOMOLOGACAO_TESTE;
			}
		}
		return SituacaoRelease.SEM_DEPLOY_PRODUCAO;
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
		if (testada) {
			if (deploysTeste.isEmpty()) {
				throw new GcmException("Não é possível definir que a release está testada pois não existe deploy em ambiente de teste");
			}
		}
		this.testada = testada;
	}
	public boolean isHomologada() {
		return homologada;
	}
	public void setHomologada(boolean homologada) {
		if (homologada) {
			if (deploysHomologacao.isEmpty()) {
				throw new GcmException("Não é possível definir que a release está homologada pois não existe deploy em ambiente de homologação");
			}
		}
		this.homologada = homologada;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Map<SituacaoTeste, Date> getHistoricoSituacaoTeste() {
		return historicoSituacaoTeste;
	}
	public void setHistoricoSituacaoTeste(
			Map<SituacaoTeste, Date> historicoSituacaoTeste) {
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
