package gcm.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;

import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;

import gcm.aplicacao.CrudService;
import gcm.dominio.Deploy;
import gcm.dominio.Sistema;
import gcm.infra.CrudServiceImpl;

@ManagedBean
public class DeploysBean {
	
	private List<Deploy> deploys = new ArrayList<>();
	private List<DeploysPorDia> listaDeploysPorDia; 
	private String inicio;
	private String fim;
	
	public void iniciar() {
		pesquisarDeploys();
		//criarListaDeploysDiasPeriodo(inicio, fim);
	}
	
	public void pesquisarDeploys() {
		CrudService<Sistema> cs = new CrudServiceImpl<Sistema>(Sistema.class);
		Map<String, Object> parametros = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		 
		try {
			Date inicio = sdf.parse("01/09/2015");
			Date fim = sdf.parse("30/09/2015");
			parametros.put("inicio", inicio);
			parametros.put("fim", fim);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Sistema> sistemas = cs.pesquisarPorNamedQuery(Sistema.PESQUISAR_DEPLOYS_POR_PERIODO, parametros);
		
		for (Sistema sistema : sistemas){
			deploys.addAll(sistema.getDeploysOrdenadosPorData());
		}
	}
	
	public List<DeploysPorDia> criarListaDeploysDiasPeriodo(Date inicio, Date fim) {
		if (inicio.after(fim)) {
			throw new IllegalArgumentException("Data de início superior ao fim");
		}
		DateTime i = new DateTime(inicio);
		DateTime f = new DateTime(fim);
		f = f.plusDays(1);
		
		listaDeploysPorDia = new ArrayList<>();
		for (DateTime d = i; d. isBefore(f); d=d.plusDays(1)) {
			DeploysPorDia dpd = new DeploysPorDia();
			dpd.dia = d;
			listaDeploysPorDia.add(dpd);
		}
		return listaDeploysPorDia;
	}
	
	private void carregarListaDeploysDiasPeriodo() {
//		for (Deploy deploy : deploys) {
//			DateTime dataDeploy = new DateTime(deploy.getDataDeploy());
//			while (dataDeploy.getDayOfMonth() <= )
//		}
	}

	public class DeploysPorDia {
		DateTime dia;
		List<Deploy> deploysProducao = new ArrayList<>();
		List<Deploy> deploysHomologacao = new ArrayList<>();
		List<Deploy> deploysTeste = new ArrayList<>();
	}
	
	
	public List<Deploy> getDeploys() {
		return deploys;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFim() {
		return fim;
	}

	public void setFim(String fim) {
		this.fim = fim;
	}
}
