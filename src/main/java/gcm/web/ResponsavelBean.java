package gcm.web;

import gcm.dominio.Responsavel;
import gcm.infra.JPAUtil;

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
public class ResponsavelBean {
	
	private Responsavel responsavel = new Responsavel();
	private List<Responsavel> responsaveis;
	private String nomeResponsavel;
	
	public ResponsavelBean() {
		Map<String, String> parametros = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String parametroId = parametros.get("id");
		if (parametroId != null) {
			EntityManager em = JPAUtil.getEntityManager();
			responsavel = em.find(Responsavel.class, Long.valueOf(parametroId));
		}
	}
	
	public String salvar() {
		EntityManager em = JPAUtil.getEntityManager();
		em.merge(responsavel);
				
		FacesMessage msg = new FacesMessage("Responsável cadastrado com sucesso");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true); 
		return "responsavelLista.jsf?faces-redirect=true";
	}
	
	public String pesquisarResponsavel() {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Responsavel> query = em.createNamedQuery(Responsavel.PESQUISAR_POR_NOME, Responsavel.class);
		query.setParameter("nome", "%" + nomeResponsavel.toUpperCase() + "%");
		responsaveis = query.getResultList();
		if (responsaveis.isEmpty()) {
			FacesMessage msg = new FacesMessage("Não foi encontrado responsável");
			FacesContext.getCurrentInstance().addMessage(null, msg);			
		}
		return "sucesso";
	}
	
	public void excluir(Long idResponsavel) {
		EntityManager em = JPAUtil.getEntityManager();
		Responsavel responsavel = em.find(Responsavel.class, idResponsavel);
		em.remove(responsavel);
		pesquisarResponsavel();
	}
	
	public String getNomeResponsavel() {
		return nomeResponsavel;
	}
	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}
	public List<Responsavel> getResponsaveis() {
		return responsaveis;
	}
	public void setResponsaveis(List<Responsavel> responsaveis) {
		this.responsaveis = responsaveis;
	}
	public Responsavel getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}
}
