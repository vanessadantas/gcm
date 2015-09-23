package gcm.dominio;

public enum SituacaoRelease {
	
	PRODUCAO_TESTADA_HOMOLOGADA("Em produção, testada e homologada"), 
	PRODUCAO_TESTADA("Em produção, testada"),
	PRODUCAO_HOMOLOGADA("Em produção, homologada"), 
	PRODUCAO_DEPLOYS_HOMOLOGACAO_TESTE("Em produção, com deploys em ambiente de teste e homologação"),
	PRODUCAO_DEPLOY_TESTE("Em produção, com deploy em ambiente de teste"),
	PRODUCAO_DEPLOY_HOMOLOGACAO("Em produção, com deploy em ambiente de homologação"),
	PRODUCAO_SEM_DEPLOY_HOMOLOGACAO_TESTE("Em produção, sem deploy nos ambientes de teste e homologação"),
	SEM_DEPLOY_PRODUCAO("Sem deploy em ambiente de produção");
	
	private String descricao;
	
	private SituacaoRelease(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}

}
