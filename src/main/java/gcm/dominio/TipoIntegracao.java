package gcm.dominio;

public enum TipoIntegracao {
	WEB_SERVICE("Web Service"), BANCO_DE_DADOS("Banco de dados"), ARQUIVO("Arquivo");
	
	private String descricao;
	
	TipoIntegracao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
}