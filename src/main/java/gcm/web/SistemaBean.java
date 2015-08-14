package gcm.web;

import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import gcm.dominio.Arquitetura;
import gcm.dominio.Framework;
import gcm.dominio.Linguagem;
import gcm.dominio.Sistema;
import gcm.infra.JPAUtil;

@ManagedBean
@ViewScoped
public class SistemaBean {

	// Usado para cadastro
	private Sistema sistema = new Sistema();

	// Usado para pesquisa
	private List<Arquitetura> arquiteturas;
	private String nomeArquitetura;

	public Arquitetura arquitetura = new Arquitetura();
	
	public void pesquisarArquitetura() {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Arquitetura> query = em.createQuery("select a from Arquitetura a", Arquitetura.class);
		arquiteturas = query.getResultList();
	}
	

	public String salvar() {
		EntityManager em = JPAUtil.getEntityManager();
		em.merge(sistema);

		FacesMessage msg = new FacesMessage("Sistema cadastrado com sucesso");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		return "sistemaLista.jsf?faces-redirect=true";
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public List<Arquitetura> getArquiteturas() {
		return arquiteturas;
	}

	public void setArquiteturas(List<Arquitetura> arquiteturas) {
		this.arquiteturas = arquiteturas;
	}

	public List<Linguagem> getLinguagens() {
		return Arrays.asList(Linguagem.values());
	}


	public String getNomeArquitetura() {
		return nomeArquitetura;
	}


	public void setNomeArquitetura(String nomeArquitetura) {
		this.nomeArquitetura = nomeArquitetura;
	}
}
