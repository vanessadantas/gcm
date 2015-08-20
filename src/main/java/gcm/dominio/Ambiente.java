package gcm.dominio;

public enum Ambiente {
	
DESENVOLVIMENTO("Desenvolvimento"), TESTE("Teste"), HOMOLOGOCAO("Homologação"), PRODUCAO("Produção");
	
	public String nome;
	
	Ambiente(String nome){
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
}
