package gcm.dominio;

public enum Configuracao {
	NOME_PROJETO("Nome do projeto"), LOCALIZACAO("Localização");
	
	public String nome;
	
	Configuracao(String nome){
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
}
