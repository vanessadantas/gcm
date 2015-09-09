package gcm.web;

import gcm.aplicacao.CrudService;
import gcm.dominio.Ferramenta;
import gcm.infra.CrudServiceImpl;
import gcm.infra.GcmPersistenceException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class FerramentaBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private CrudService<Ferramenta> crudService = new CrudServiceImpl<Ferramenta>(Ferramenta.class);
	private Ferramenta ferramenta = new Ferramenta();
	private List<Ferramenta> ferramentas;
	private String nomeferramenta;
		
		public FerramentaBean() {
			Map<String, String> parametros = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String parametroId = parametros.get("id");
			if (parametroId != null) {
 				ferramenta = crudService.buscarPorId(Long.valueOf(parametroId));
			}
		}
		
		public String salvar() {
			crudService.salvar(ferramenta);
					
			FacesMessage msg = new FacesMessage("Ferramenta cadastrada com sucesso");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true); 
			return "ferramentaLista.jsf?faces-redirect=true";
		}
		
		public String pesquisarFerramenta() {
			Map<String, Object> parametros = new HashMap<>();			
			parametros.put("nome", "%" + nomeferramenta.toUpperCase() + "%");
			ferramentas = crudService.pesquisarPorNamedQuery(Ferramenta.PESQUISAR_POR_NOME, parametros);
			
			if (ferramentas.isEmpty()) {
				FacesMessage msg = new FacesMessage("Não foi encontrada nenhuma ferramenta");
				FacesContext.getCurrentInstance().addMessage(null, msg);			
			}
			return "sucesso";
		}
		
		public String excluir(Long idFerramenta) {
			try {
				crudService.excluir(idFerramenta);
				FacesMessage msg = new FacesMessage("Ferramenta excluída com sucesso");
				FacesContext.getCurrentInstance().addMessage(null, msg);			
				pesquisarFerramenta();
			} catch(GcmPersistenceException e) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Não é possível excluir a ferramenta pois existem sistemas que dependem dela.", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);			
			}
			return "sucesso";
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
