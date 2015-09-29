package gcm.dominio;

public enum SituacaoHomologacao {
	EM_HOMOLOGACAO ("Em homologação"), 
	HOMOLOGADA ("Homologada"), 
	NAO_HOMOLOGADA ("Não homologada");
	
	private String descricao;

	private SituacaoHomologacao (String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
