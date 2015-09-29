package gcm.web;

import gcm.aplicacao.CrudService;
import gcm.dominio.Arquivo;
import gcm.dominio.BancoDeDados;
import gcm.dominio.PontoIntegracao;
import gcm.dominio.Sistema;
import gcm.dominio.TipoIntegracao;
import gcm.dominio.WebService;
import gcm.infra.CrudServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class IntegracaoBean {

	private CrudService<Sistema> crudService = new CrudServiceImpl<>(Sistema.class);
	private Sistema sistema;
	
	private TipoIntegracao tipoIntegracao;
	private String endereco;
	private String observacao;
	
	private WebService webService;
	private boolean wsRest;
	
	private Arquivo arquivo;
	private String formato;
	
	private BancoDeDados bancoDeDados;
	private String schema;
	private String tabelas;
	
	private List<PontoIntegracao> pontosIntegracao = new ArrayList<>();
	

	//TODO Não permitir que a aba de integrações seja habilitada enquanto os sitema não estiver salvo
	//TODO Enquanto estiver trabalhando com a aba de integrações, após o salvamento, manter essa aba habilitada
	//TODO Criar os botões para editar as integrações
	//TODO Permitir excluir uma integração

		
	public IntegracaoBean() {
		Map<String, String> parametros = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap();
		String parametroId = parametros.get("id");
		if (parametroId != null) {
			sistema = crudService.buscarPorId(Long.valueOf(parametroId));
		}
	}
		
	public void salvar() {
		PontoIntegracao pi = null;
		
		if (tipoIntegracao == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Escolha o tipo de integração.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		
		if (tipoIntegracao.equals(TipoIntegracao.WEB_SERVICE)) {
			pi = new WebService();
			webService = (WebService)pi;
			webService.setRest(wsRest);
		} else if (tipoIntegracao.equals(TipoIntegracao.ARQUIVO)) {
			pi = new Arquivo();
			arquivo = (Arquivo)pi;
			arquivo.setFormato(formato);
		} else if (tipoIntegracao.equals(TipoIntegracao.BANCO_DE_DADOS)) {
			pi = new BancoDeDados();
			bancoDeDados = (BancoDeDados)pi;
			String[] arrayTabelas = tabelas.trim().split(",");
			if (arrayTabelas != null && arrayTabelas.length > 0) {
				Set<String> conjuntoTabelas = new HashSet<>(Arrays.asList(arrayTabelas));
				bancoDeDados.setTabelas(conjuntoTabelas);
			}
		}
		pi.setEndereco(endereco);
		pi.setObservacao(observacao);
		
		sistema.adicionarPontoIntegracao(pi);
		limparForm();
	}
	
	private void limparForm() {
		wsRest = false;
		formato = "";
		tabelas = "";
		endereco = "";
		observacao = "";
	}
	
	public TipoIntegracao[] getTiposIntegracao() {
		return TipoIntegracao.values();
	}

	public TipoIntegracao getTipoIntegracao() {
		return tipoIntegracao;
	}

	public void setTipoIntegracao(TipoIntegracao tipoIntegracao) {
		this.tipoIntegracao = tipoIntegracao;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public boolean isWsRest() {
		return wsRest;
	}

	public void setWsRest(boolean wsRest) {
		this.wsRest = wsRest;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getTabelas() {
		return tabelas;
	}

	public void setTabelas(String tabelas) {
		this.tabelas = tabelas;
	}

	public List<PontoIntegracao> getPontosIntegracao() {
		return pontosIntegracao;
	}
	
}
