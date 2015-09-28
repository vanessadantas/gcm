package gcm.web;

import gcm.dominio.Arquivo;
import gcm.dominio.BancoDeDados;
import gcm.dominio.PontoIntegracao;
import gcm.dominio.TipoIntegracao;
import gcm.dominio.WebService;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class IntegracaoBean {
	
	private TipoIntegracao tipoIntegracao;
	private String endereco;
	private String observacao;
	
	private WebService webService;
	private boolean wsRest;
	
	private Arquivo arquivo;
	private String formato;
	
	private BancoDeDados bancoDeDados;
	
	
	public void salvar() {
		PontoIntegracao pi;
		if (tipoIntegracao.equals(TipoIntegracao.WEB_SERVICE)) {
			pi = new WebService();
			WebService ws = (WebService)pi;
			ws.setRest(wsRest);
		} else if (tipoIntegracao.equals(TipoIntegracao.ARQUIVO)) {
			pi = new Arquivo();
			Arquivo arq = (Arquivo)pi;
			arq.setFormato(formato);
		} else if (tipoIntegracao.equals(TipoIntegracao.BANCO_DE_DADOS)) {
			pi = new BancoDeDados();
			BancoDeDados bd = (BancoDeDados)pi;
			//bd.setTabelas(tabelas);
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Escolha o tipo de integração.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		pi.setEndereco(endereco);
		pi.setObservacao(observacao);
	}
	
	public TipoIntegracao[] getTiposIntegracao() {
		return TipoIntegracao.values();
	}

	public TipoIntegracao getTipoIntegracao() {
		return tipoIntegracao;
	}

	public void setTipoIntegracao(TipoIntegracao tipoIntegracao) {
		this.tipoIntegracao = tipoIntegracao;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public boolean isWsRest() {
		return wsRest;
	}

	public void setWsRest(boolean wsRest) {
		this.wsRest = wsRest;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}
	
}
