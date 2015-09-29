package gcm.web;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import gcm.aplicacao.CrudService;
import gcm.dominio.Release;
import gcm.dominio.SituacaoHomologacao;
import gcm.dominio.SituacaoTeste;
import gcm.infra.CrudServiceImpl;

@ManagedBean
@ViewScoped
public class ReleaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Release release = new Release();
	private CrudService<Release> crudService = new CrudServiceImpl<>(Release.class); 
	private String dataSituacaoTeste;
	private String  horaSituacaoTeste;
	private String dataSituacaoHomologacao;
	private String horaSituacaoHomologacao;
	private SituacaoTeste situacaoTeste;
	private SituacaoHomologacao situacaoHomologacao;
	
	public ReleaseBean() {
		Map<String, String> parametros = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap();
		String parametroId = parametros.get("idRelease");
		if (parametroId != null) {
			release = crudService.buscarPorId(Long.valueOf(parametroId));
		}
		
		Date agora = new Date();
		SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");
		dataSituacaoTeste = sdfData.format(agora);
		horaSituacaoTeste = sdfHora.format(agora);
		
		dataSituacaoHomologacao = sdfData.format(agora);
		horaSituacaoHomologacao = sdfHora.format(agora);
	}
	
	public void adicionarSituacaoTeste () {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date dataSituacao = null;
		
		try {
			dataSituacao = sdf.parse(dataSituacaoTeste + " " + horaSituacaoTeste);
		} catch (ParseException e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,"Erro na data ou hora da situação de teste", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		release.adicionarSituacaoTeste(situacaoTeste, dataSituacao);
	}
	public SituacaoTeste[] getSituacoesTeste (){
		return SituacaoTeste.values();
	}
	
	public SituacaoHomologacao[] getSituacoesHomologacao (){
		return SituacaoHomologacao.values();
	}
	
	public Release getRelease() {
		return release;
	}

	public void setRelease(Release release) {
		this.release = release;
	}

	public String getDataSituacaoTeste() {
		return dataSituacaoTeste;
	}

	public void setDataSituacaoTeste(String dataSituacaoTeste) {
		this.dataSituacaoTeste = dataSituacaoTeste;
	}

	public String getHoraSituacaoTeste() {
		return horaSituacaoTeste;
	}

	public void setHoraSituacaoTeste(String horaSituacaoTeste) {
		this.horaSituacaoTeste = horaSituacaoTeste;
	}

	public String getDataSituacaoHomologacao() {
		return dataSituacaoHomologacao;
	}

	public void setDataSituacaoHomologacao(String dataSituacaoHomologacao) {
		this.dataSituacaoHomologacao = dataSituacaoHomologacao;
	}

	public String getHoraSituacaoHomologacao() {
		return horaSituacaoHomologacao;
	}

	public void setHoraSituacaoHomologacao(String horaSituacaoHomologacao) {
		this.horaSituacaoHomologacao = horaSituacaoHomologacao;
	}

	public SituacaoTeste getSituacaoTeste() {
		return situacaoTeste;
	}

	public void setSituacaoTeste(SituacaoTeste situacaoTeste) {
		this.situacaoTeste = situacaoTeste;
	}

	public SituacaoHomologacao getSituacaoHomologacao() {
		return situacaoHomologacao;
	}

	public void setSituacaoHomologacao(SituacaoHomologacao situacaoHomologacao) {
		this.situacaoHomologacao = situacaoHomologacao;
	}
	
}
