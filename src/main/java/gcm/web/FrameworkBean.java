package gcm.web;

import gcm.aplicacao.CrudService;
import gcm.dominio.Framework;
import gcm.dominio.Linguagem;
import gcm.infra.CrudServiceImpl;
import gcm.infra.GcmPersistenceException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class FrameworkBean implements Serializable {
	//Cria uma instancia de Framework que será usado para apresentar os dados do formulario.
	//No caso de um cadastro de novo framework, essa instancia será carregada a partir de 
	//um formulário vazio
	private static final long serialVersionUID = 1L;
	
	private CrudService<Framework> crudService = new CrudServiceImpl<Framework>(Framework.class);
	private Framework framework = new Framework();
	private List<Framework> frameworks;
	private String nomeFramework;

	public FrameworkBean() {
		//Verifica se foi passada um parametro via querystring com o nome de "id"
		//Em caso positivo, trata-se de uma alteração do framework
		Map<String, String> parametros = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String parametroId = parametros.get("id");
		if (parametroId != null) {
			framework = crudService.buscarPorId(Long.valueOf(parametroId));
		}
	}
	
	public String salvar() {
		crudService.salvar(framework);
				
		FacesMessage msg = new FacesMessage("Framework cadastrado com sucesso");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true); 
		return "frameworkLista.jsf?faces-redirect=true";
	}
	
	public String pesquisarFramework() {
		Map<String, Object> parametros = new HashMap<>();	
		parametros.put("nome", "%" + nomeFramework.toUpperCase() + "%");
		frameworks = crudService.pesquisarPorNamedQuery(Framework.PESQUISAR_POR_NOME, parametros);
		
		if (frameworks.isEmpty()) {
			FacesMessage msg = new FacesMessage("Não foi encontrado framework");
			FacesContext.getCurrentInstance().addMessage(null, msg);			
		}
		return "sucesso";
	}
	
	public String excluir(Long idFramework) {
		try {
			crudService.excluir(idFramework);
			FacesMessage msg = new FacesMessage("Framework excluído com sucesso");
			FacesContext.getCurrentInstance().addMessage(null, msg);			
			pesquisarFramework();
		} catch(GcmPersistenceException e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Não é possível excluir o framework pois existem arquiteturas que dependem dele.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);			
		}
		return "sucesso";
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
