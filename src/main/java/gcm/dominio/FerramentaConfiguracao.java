package gcm.dominio;

public enum FerramentaConfiguracao {
	NOME_PROJETO("Nome do projeto"), LOCALIZACAO("Localização");
	
	public String nome;
	
	FerramentaConfiguracao(String nome){
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
}
