package gcm.web;

import gcm.aplicacao.CrudService;
import gcm.dominio.Ambiente;
import gcm.dominio.Deploy;
import gcm.dominio.Sistema;
import gcm.infra.CrudServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

@ManagedBean
public class DeploysBean {
	
	private List<Deploy> deploys = new ArrayList<>();
	private List<DeploysPorDia> listaDeploysPorDia; 
	private String inicioPeriodo, fimPeriodo;
	private DateTime inicio, fim;
	
//	public void iniciar() {
//		iniciarDatas();
//		pesquisarDeploys();
//		criarListaDeploysDias();
//		carregarListaDeploysDias();
//	}
	
	public void pesquisarDeploys() {
		CrudService<Sistema> cs = new CrudServiceImpl<Sistema>(Sistema.class);
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("inicio", inicio.toDate());
		parametros.put("fim", fim.toDate());
		List<Sistema> sistemas = cs.pesquisarPorNamedQuery(Sistema.PESQUISAR_DEPLOYS_POR_PERIODO, parametros);
		
		for (Sistema sistema : sistemas){
			deploys.addAll(sistema.getDeploysOrdenadosPorData());
		}
	}
	
	public List<DeploysPorDia> criarListaDeploysDias() {
		fim = fim.plusDays(1);
		
		listaDeploysPorDia = new ArrayList<>();
		for (DateTime d = inicio; d. isBefore(fim); d=d.plusDays(1)) {
			DeploysPorDia dpd = new DeploysPorDia();
			dpd.dia = d;
			listaDeploysPorDia.add(dpd);
		}
		return listaDeploysPorDia;
	}
	
	public void carregarListaDeploysDias() {
		for (DeploysPorDia deployPorDia : listaDeploysPorDia) {
			for (Deploy deploy : deploys) {
				DateTime dataDeploy = new DateTime(deploy.getDataDeploy());
				if (DateTimeComparator.getDateOnlyInstance().compare(dataDeploy, deployPorDia.dia) == 0) {
					if (deploy.getAmbiente().equals(Ambiente.PRODUCAO)){
						deployPorDia.deploysProducao.add(deploy);
					}
					if (deploy.getAmbiente().equals(Ambiente.HOMOLOGACAO)){
						deployPorDia.deploysHomologacao.add(deploy);
					}
					if (deploy.getAmbiente().equals(Ambiente.TESTE)){
						deployPorDia.deploysTeste.add(deploy);
					}
				}
				continue;
			}
		}
	}
	
	public class DeploysPorDia {
		DateTime dia;
		List<Deploy> deploysProducao = new ArrayList<>();
		List<Deploy> deploysHomologacao = new ArrayList<>();
		List<Deploy> deploysTeste = new ArrayList<>();
		
		public Date getDia() {return dia.toDate(); }
		public List<Deploy> getDeploysProducao() { return deploysProducao; }
		public List<Deploy> getDeploysHomologacao() { return deploysHomologacao; }
		public List<Deploy> getDeploysTeste() { return deploysTeste; }
	}
	
	
	public void iniciarDatas() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date inicio = sdf.parse(inicioPeriodo);
			Date fim = sdf.parse(fimPeriodo);
			
			this.inicio = new DateTime(inicio);
			this.fim = new DateTime(fim);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (inicio.isAfter(fim)) {
			throw new IllegalArgumentException("Data de início superior ao fim");
		}
	}
	
	public String getInicioPeriodo() {
		return inicioPeriodo;
	}

	public void setInicioPeriodo(String inicioPeriodo) {
		this.inicioPeriodo = inicioPeriodo;
	}

	public String getFimPeriodo() {
		return fimPeriodo;
	}

	public void setFimPeriodo(String fimPeriodo) {
		this.fimPeriodo = fimPeriodo;
	}

	public List<DeploysPorDia> getListaDeploysPorDia() {
		inicioPeriodo = "01/09/2015";
		fimPeriodo = "30/09/2015";
		iniciarDatas();
		pesquisarDeploys();
		criarListaDeploysDias();
		carregarListaDeploysDias();
		return listaDeploysPorDia;
	}
}
