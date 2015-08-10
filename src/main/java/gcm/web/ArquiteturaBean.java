package gcm.web;

import gcm.dominio.Arquitetura;
import gcm.dominio.Framework;
import gcm.dominio.Linguagem;
import gcm.infra.JPAUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@ManagedBean
@ViewScoped
public class ArquiteturaBean {
	//Usado para cadastro
	private Arquitetura arquitetura = new Arquitetura();
	private String linguagem;
	
	//usado para pesquisa
	private List<Arquitetura> arquiteturas;
	private String nomeArquitetura;

	public ArquiteturaBean() {
		Map<String, String> parametros = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String parametroId = parametros.get("id");
		if (parametroId != null) {
			EntityManager em = JPAUtil.getEntityManager();
			arquitetura = em.find(Arquitetura.class, Long.valueOf(parametroId));
		}
	}
	
	public String salvar() {
		EntityManager em = JPAUtil.getEntityManager();
		em.merge(arquitetura);
				
		FacesMessage msg = new FacesMessage("Arquitetura cadastrada com sucesso");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true); 
		return "arquiteturaLista.jsf?faces-redirect=true";
	}
	
	public String pesquisarArquitetura() {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Arquitetura> query = em.createNamedQuery(Arquitetura.PESQUISAR_POR_NOME, Arquitetura.class);
		query.setParameter("nome", "%" + nomeArquitetura.toUpperCase() + "%");
		arquiteturas = query.getResultList();
		if (arquiteturas.isEmpty()) {
			FacesMessage msg = new FacesMessage("Não foi encontrada a Arquitetura");
			FacesContext.getCurrentInstance().addMessage(null, msg);			
		}
		return "sucesso";
	}
	
	public void excluir(Long idArquitetura) {
		EntityManager em = JPAUtil.getEntityManager();
		Arquitetura arquitetura = em.find(Arquitetura.class, idArquitetura);
		em.remove(arquitetura);
		pesquisarArquitetura();
	}
	
	public List<Framework> getFrameworks() {
		if (linguagem != null) {
			EntityManager em = JPAUtil.getEntityManager();
			TypedQuery<Framework> query = em.createQuery("select f from Framework f where linguagem = :linguagem", Framework.class);
			query.setParameter("linguagem", Linguagem.valueOf(linguagem));
			return query.getResultList();
		}
		return Collections.emptyList();
	}

	public Arquitetura getArquitetura() {
		return arquitetura;
	}

	public void setArquitetura(Arquitetura arquitetura) {
		this.arquitetura = arquitetura;
	}

	public List<Arquitetura> getArquiteturas() {
		return arquiteturas;
	}

	public void setArquiteturas(List<Arquitetura> arquiteturas) {
		this.arquiteturas = arquiteturas;
	}

	public String getNomeArquitetura() {
		return nomeArquitetura;
	}

	public void setNomeArquitetura(String nomeArquitetura) {
		this.nomeArquitetura = nomeArquitetura;
	}

	public List<Linguagem> getLinguagens() {
		return Arrays.asList(Linguagem.values());
	}

	public String getLinguagem() {
		return linguagem;
	}
	public void setLinguagem(String linguagem) {
		this.linguagem = linguagem;
	}
}
