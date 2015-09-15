package gcm.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import gcm.web.DeploysBean.DeploysPorDia;

public class DeploysPorDiaTest {

	@Test
	public void criarListaDeploysDiasPerido() {
		DeploysBean deploysBean = new DeploysBean();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date inicio = null, fim = null;
		try {
			inicio = sdf.parse("11/09/2015");
			fim = sdf.parse("10/10/2015");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<DeploysPorDia> deploysPorDia = deploysBean.criarListaDeploysDiasPeriodo(inicio, fim);
		Assert.assertTrue(deploysPorDia.size() == 30);
		
		for (DeploysPorDia d : deploysPorDia) {
			System.out.println(sdf.format(d.dia));
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void criarListaDeploysDiasPeriodoComDataInicioMaiorQueFim() {
		DeploysBean deploysBean = new DeploysBean();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date inicio = null, fim = null;
		try {
			inicio = sdf.parse("30/09/2015");
			fim = sdf.parse("01/09/2015");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		deploysBean.criarListaDeploysDiasPeriodo(inicio, fim);
	}
}
