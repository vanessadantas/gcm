package gcm.web;

import gcm.dominio.Ferramenta;
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
public class FerramentaBean {	
		
		private Ferramenta ferramenta = new Ferramenta();
		private List<Ferramenta> ferramentas;
		private String nomeferramenta;
		
		public FerramentaBean() {
			Map<String, String> parametros = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String parametroId = parametros.get("id");
			if (parametroId != null) {
				EntityManager em = JPAUtil.getEntityManager();
				ferramenta = em.find(Ferramenta.class, Long.valueOf(parametroId));
			}
		}
		
		public String salvar() {
			EntityManager em = JPAUtil.getEntityManager();
			em.merge(ferramenta);
					
			FacesMessage msg = new FacesMessage("Ferramenta cadastrada com sucesso");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true); 
			return "ferramentaLista.jsf?faces-redirect=true";
		}
		
		public String pesquisarFerramenta() {
			EntityManager em = JPAUtil.getEntityManager();
			TypedQuery<Ferramenta> query = em.createNamedQuery(Ferramenta.PESQUISAR_POR_NOME, Ferramenta.class);
			query.setParameter("nome", "%" + nomeferramenta.toUpperCase() + "%");
			ferramentas = query.getResultList();
			if (ferramentas.isEmpty()) {
				FacesMessage msg = new FacesMessage("Não foi encontrada nenhuma ferramenta");
				FacesContext.getCurrentInstance().addMessage(null, msg);			
			}
			return "sucesso";
		}
		
		public void excluir(Long idFerramenta) {
			EntityManager em = JPAUtil.getEntityManager();
			Ferramenta ferramenta = em.find(Ferramenta.class, idFerramenta);
			em.remove(ferramenta);
			pesquisarFerramenta();
		}

		public Ferramenta getFerramenta() {
			return ferramenta;
		}

		public void setFerramenta(Ferramenta ferramenta) {
			this.ferramenta = ferramenta;
		}

		public List<Ferramenta> getFerramentas() {
			return ferramentas;
		}

		public void setFerramentas(List<Ferramenta> ferramentas) {
			this.ferramentas = ferramentas;
		}

		public String getNomeferramenta() {
			return nomeferramenta;
		}

		public void setNomeferramenta(String nomeferramenta) {
			this.nomeferramenta = nomeferramenta;
		}

}
