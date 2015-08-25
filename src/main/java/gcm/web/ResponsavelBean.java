package gcm.web;

import gcm.aplicacao.CrudService;
import gcm.dominio.Responsavel;
import gcm.infra.CrudServiceImpl;

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
public class ResponsavelBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private CrudService<Responsavel> crudService = new CrudServiceImpl<Responsavel>(Responsavel.class);
	
	//usado para cadastro
	private Responsavel responsavel = new Responsavel();
	
	//usado para pesquisa
	private List<Responsavel> responsaveis;
	private String nomeResponsavel;
	
	public ResponsavelBean() {
		Map<String, String> parametros = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String parametroId = parametros.get("id");
		if (parametroId != null) {
			responsavel = crudService.buscarPorId(Long.valueOf(parametroId));
		}
	}
	
	public String salvar() {
		crudService.salvar(responsavel);
				
		FacesMessage msg = new FacesMessage("Responsável cadastrado com sucesso");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true); 
		return "responsavelLista.jsf?faces-redirect=true";
	}
	
	public String pesquisarResponsavel() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("nome", "%" + nomeResponsavel.toUpperCase() + "%");
		responsaveis = crudService.pesquisarPorNamedQuery(Responsavel.PESQUISAR_POR_NOME, parametros);
		
		if (responsaveis.isEmpty()) {
			FacesMessage msg = new FacesMessage("Não foi encontrado responsável");
			FacesContext.getCurrentInstance().addMessage(null, msg);			
		}
		return "sucesso";
	}
	
	public void excluir(Long idResponsavel) {
		crudService.excluir(idResponsavel);
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
