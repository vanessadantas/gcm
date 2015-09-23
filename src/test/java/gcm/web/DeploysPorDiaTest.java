package gcm.web;

import gcm.web.DeploysBean.DeploysPorDia;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class DeploysPorDiaTest {

	@Test
	public void criarListaDeploysDiasPerido() {
		DeploysBean deploysBean = new DeploysBean();
		deploysBean.setInicioPeriodo("11/09/2015");
		deploysBean.setFimPeriodo("10/10/2015");
		deploysBean.isDatasValidas();
		
		List<DeploysPorDia> deploysPorDia = deploysBean.criarListaDeploysDias();
		Assert.assertTrue(deploysPorDia.size() == 30);
		
		for (DeploysPorDia d : deploysPorDia) {
			System.out.println(d.dia);
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void criarListaDeploysDiasPeriodoComDataInicioMaiorQueFim() {
		DeploysBean deploysBean = new DeploysBean();
		deploysBean.setInicioPeriodo("30/09/2015");
		deploysBean.setFimPeriodo("01/09/2015");
		deploysBean.isDatasValidas();
		deploysBean.criarListaDeploysDias();
	}
	
	@Test
	public void pesquisarDeploys() {
		DeploysBean deploysBean = new DeploysBean();
		deploysBean.setInicioPeriodo("01/09/2015");
		deploysBean.setFimPeriodo("30/09/2015");
		deploysBean.isDatasValidas();
		
		deploysBean.pesquisarDeploys();
		deploysBean.criarListaDeploysDias();
		deploysBean.carregarListaDeploysDias();
	}
}
