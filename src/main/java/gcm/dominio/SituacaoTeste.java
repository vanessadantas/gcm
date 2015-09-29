package gcm.dominio;

public enum SituacaoTeste {

	TESTE_SOLICITADO ("Teste solicitado"),
	EM_TESTE ("Em teste"), 
	LIBERADO ("Liberado"), 
	LIBERADO_COM_RESSALVAS ("Liberado com ressalvas"),
	REPROVADO ("Reprovado"), 
	RETESTE ("Reteste");

	private String descricao;

	private SituacaoTeste (String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
