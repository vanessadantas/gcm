package gcm.web;

import gcm.aplicacao.CrudService;
import gcm.dominio.Arquitetura;
import gcm.dominio.Framework;
import gcm.dominio.Linguagem;
import gcm.infra.CrudServiceImpl;
import gcm.infra.GcmPersistenceException;

import java.io.Serializable;
import java.util.ArrayList;
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
public class ArquiteturaBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//Usado para cadastro
	private Arquitetura arquitetura = new Arquitetura();
	private List<Framework> frameworks = new ArrayList<>();
	private List<String> idsFrameworksSelecionados = new ArrayList<>();
	
	//usado para pesquisa
	private CrudService<Arquitetura> crudService = new CrudServiceImpl<>(Arquitetura.class);
	private CrudService<Framework> crudServiceFramework = new CrudServiceImpl<>(Framework.class);
	private List<Arquitetura> arquiteturas;
	private String nomeArquitetura;
	private String linguagem, ultimaLinguagemPesquisada;

	public ArquiteturaBean() {
		Map<String, String> parametros = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String parametroId = parametros.get("id");
		if (parametroId != null) {
			arquitetura = crudService.buscarPorId(Long.valueOf(parametroId));
			
			for (Framework fw : arquitetura.getFrameworks()) {
				idsFrameworksSelecionados.add(fw.getId().toString());
				linguagem = fw.getLinguagem().name();
			}
		}
	}
	
	public String salvar() {
		//TODO usar o frameworkConverter - Solução de contorno para o erro "Valor não é válido"
		arquitetura.getFrameworks().clear();
		
		for (String id : idsFrameworksSelecionados) {
			Framework framework = crudServiceFramework.buscarPorId(Long.valueOf(id));
			arquitetura.getFrameworks().add(framework);
		}
		crudService.salvar(arquitetura);
		FacesMessage msg = new FacesMessage("Arquitetura cadastrada com sucesso");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true); 
		return "arquiteturaLista.jsf?faces-redirect=true";
	}
	
	public String pesquisarArquitetura() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("nome", "%" + nomeArquitetura.toUpperCase() + "%");
		arquiteturas = crudService.pesquisarPorNamedQuery(Arquitetura.PESQUISAR_POR_NOME, parametros);
	
		if (arquiteturas.isEmpty()) {
			FacesMessage msg = new FacesMessage("Não foi encontrada a Arquitetura");
			FacesContext.getCurrentInstance().addMessage(null, msg);			
		}
		return "sucesso";
	}
	
	private void pesquisarFrameworks() {
		if (linguagem != null && ! linguagem.equals(ultimaLinguagemPesquisada)) {
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("linguagem", Linguagem.valueOf(linguagem));
			frameworks = crudServiceFramework.pesquisarPorNamedQuery(Framework.PESQUISAR_POR_LINGUAGEM, parametros);
			ultimaLinguagemPesquisada = linguagem;
		}
	}
	
	public String excluir(Long idArquitetura) {
		try {
			crudService.excluir(idArquitetura);
			FacesMessage msg = new FacesMessage("Arquitetura excluída com sucesso");
			FacesContext.getCurrentInstance().addMessage(null, msg);			
			pesquisarArquitetura();
		} catch(GcmPersistenceException e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Não é possível excluir a arquitetura pois existem sistemas que dependem dela.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);			
		}
		return "sucesso";
	}
	
	public List<Framework> getFrameworks() {
		pesquisarFrameworks();
		return frameworks;
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
	public List<String> getIdsFrameworksSelecionados() {
		return idsFrameworksSelecionados;
	}
	public void setIdsFrameworksSelecionados(List<String> idsFrameworksSelecionados) {
		this.idsFrameworksSelecionados = idsFrameworksSelecionados;
	}
}
