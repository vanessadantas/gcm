package gcm.dominio;

public enum Linguagem {

	JAVA("Java"), PHP("PHP"), POWERBUILDER("Power Builder");
	
	public String nome;
	
	Linguagem(String nome){
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
}
