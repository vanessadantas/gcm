package gcm.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import gcm.dominio.Arquitetura;
import gcm.dominio.Framework;
import gcm.dominio.Linguagem;
import gcm.dominio.Responsavel;
import gcm.dominio.Sistema;
import gcm.infra.JPAUtil;

@ManagedBean
@ViewScoped
public class SistemaBean {
	private List<Arquitetura> arquiteturas;
	private List<Sistema> sistemas;
	private String nomeArquitetura;
	private String nomeSistema = "";
	private String linguagemSelecionada = "";
	private List<Responsavel> responsaveis;
	private Responsavel responsavelSelecionado = new Responsavel();
	private List<Responsavel> responsaveisSelecionados = new ArrayList<>();

	private Sistema sistema = new Sistema();
	public Arquitetura arquitetura = new Arquitetura();
	public Responsavel responsavel = new Responsavel();
	
	// preencher os valos da combobox de arquitetura
	public void pesquisarArquitetura() {
		if (linguagemSelecionada != "") {
			EntityManager em = JPAUtil.getEntityManager();
			TypedQuery<Arquitetura> query = em.createNamedQuery(Arquitetura.PESQUISAR_POR_LINGUAGEM, Arquitetura.class);
			query.setParameter("linguagem", Linguagem.valueOf(linguagemSelecionada));
			arquiteturas = query.getResultList();
		}
	}
	// preencher os valos da combobox de responsável

	public void listarResponsaveis() {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Responsavel> query = em.createNamedQuery(Responsavel.LISTAR_TODOS, Responsavel.class);
		responsaveis = query.getResultList();
	}
	// selecionar responsável da combobox

	public void adicionarResponsavel(){
		responsaveisSelecionados.add(responsavelSelecionado);
		System.out.println(responsaveisSelecionados);
	}
	// remover responsável da lista

	public void removerResponsavel(Responsavel responsavel) {
		responsaveisSelecionados.remove(responsavel);
	}

	// Usado para cadastro
	public String salvar() {
		EntityManager em = JPAUtil.getEntityManager();
		sistema.getResponsaveis().clear();
		sistema.getResponsaveis().addAll(responsaveisSelecionados);
		em.merge(sistema);
		FacesMessage msg = new FacesMessage("Sistema cadastrado com sucesso");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		return "sistemaLista.jsf?faces-redirect=true";
	}

	// Usado para pesquisar
	public String pesquisarSistema() {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Sistema> query = em.createNamedQuery(Sistema.PESQUISAR_POR_NOME, Sistema.class);
		query.setParameter("nome", "%" + nomeSistema.toUpperCase() + "%");
		sistemas = query.getResultList();
		if (sistemas.isEmpty()) {
			FacesMessage msg = new FacesMessage("Não foi encontrado sistema");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "sucesso";
	}

	// Usado para alterar
	public SistemaBean() {
		// Verifica se foi passada um parametro via querystring com o nome de
		// "id"
		// Em caso positivo, trata-se de uma alteração do framework
		Map<String, String> parametros = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap();
		String parametroId = parametros.get("id");
		if (parametroId != null) {
			EntityManager em = JPAUtil.getEntityManager();
			sistema = em.find(Sistema.class, Long.valueOf(parametroId));
			try {
				linguagemSelecionada = sistema.getArquitetura().getFrameworks().iterator().next().getLinguagem().toString();
			} catch (NullPointerException npe) {
				System.err.println("Sistema cadastrado sem arquitetura. [id = " + sistema.getId() + ", " + sistema.getNome() + "]");
			}
			responsaveisSelecionados.addAll(sistema.getResponsaveis());
			pesquisarArquitetura();
		}
			listarResponsaveis();
		
	}

	// método de exclusão
	public void excluir(Long idSistema) {
		EntityManager em = JPAUtil.getEntityManager();
		Sistema sistema = em.find(Sistema.class, idSistema);
		em.remove(sistema);
		pesquisarSistema();
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

	public List<Sistema> getSistemas() {
		return sistemas;
	}

	public void setSistemas(List<Sistema> sistemas) {
		this.sistemas = sistemas;
	}

	public String getNomeSistema() {
		return nomeSistema;
	}

	public void setNomeSistema(String nomeSistema) {
		this.nomeSistema = nomeSistema;
	}

	public String getLinguagemSelecionada() {
		return linguagemSelecionada;
	}

	public void setLinguagemSelecionada(String linguagemSelecionada) {
		this.linguagemSelecionada = linguagemSelecionada;
	}

	public List<Responsavel> getResponsaveis() {
		return responsaveis;
	}

	public void setResponsaveis(List<Responsavel> responsaveis) {
		this.responsaveis = responsaveis;
	}

	public Responsavel getResponsavelSelecionado() {
		return responsavelSelecionado;
	}

	public void setResponsavelSelecionado(Responsavel responsavelSelecionado) {
		this.responsavelSelecionado = responsavelSelecionado;
	}

	public List<Responsavel> getResponsaveisSelecionados() {
		return responsaveisSelecionados;
	}

	public void setResponsaveisSelecionados(List<Responsavel> responsaveisSelecionados) {
		this.responsaveisSelecionados = responsaveisSelecionados;
	}

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

}
