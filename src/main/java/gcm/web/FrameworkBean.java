package gcm.web;

import gcm.dominio.Framework;
import gcm.dominio.Linguagem;
import gcm.infra.JPAUtil;

import java.util.Arrays;
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
public class FrameworkBean {
	//Usado para cadastro
	private Framework framework = new Framework();
	
	//usado para pesquisa
	private List<Framework> frameworks;
	private String nomeFramework;

	public FrameworkBean() {
		Map<String, String> parametros = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String parametroId = parametros.get("id");
		if (parametroId != null) {
			EntityManager em = JPAUtil.getEntityManager();
			framework = em.find(Framework.class, Long.valueOf(parametroId));
		}
	}
	
	public String salvar() {
		EntityManager em = JPAUtil.getEntityManager();
		em.merge(framework);
				
		FacesMessage msg = new FacesMessage("Framework cadastrado com sucesso");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true); 
		return "frameworkLista.jsf?faces-redirect=true";
	}
	
	public String pesquisarFramework() {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Framework> query = em.createNamedQuery(Framework.PESQUISAR_POR_NOME, Framework.class);
		query.setParameter("nome", "%" + nomeFramework.toUpperCase() + "%");
		frameworks = query.getResultList();
		if (frameworks.isEmpty()) {
			FacesMessage msg = new FacesMessage("Não foi encontrado framework");
			FacesContext.getCurrentInstance().addMessage(null, msg);			
		}
		return "sucesso";
	}
	
	public void excluir(Long idFramework) {
		EntityManager em = JPAUtil.getEntityManager();
		Framework framework = em.find(Framework.class, idFramework);
		em.remove(framework);
		pesquisarFramework();
	}

	public Framework getFramework() {
		return framework;
	}

	public void setFramework(Framework framework) {
		this.framework = framework;
	}

	public List<Framework> getFrameworks() {
		return frameworks;
	}

	public void setFrameworks(List<Framework> frameworks) {
		this.frameworks = frameworks;
	}

	public String getNomeFramework() {
		return nomeFramework;
	}

	public void setNomeFramework(String nomeFramework) {
		this.nomeFramework = nomeFramework;
	}

	public List<Linguagem> getLinguagens() {
		return Arrays.asList(Linguagem.values());
	}

}
