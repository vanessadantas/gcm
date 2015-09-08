package gcm.dominio;

public enum SituacaoSistema {

	EM_PROJETO("Em projeto"), EM_PRODUCAO("Em produção"), EM_DESATIVACAO("Em desativação"), DESATIVADO("Desativado");
	
	private String nome;
	
	SituacaoSistema(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return this.nome;
	}
	
}
