package gcm.dominio;

import org.junit.Assert;
import org.junit.Test;

public class ReleaseTest {
	
	@Test (expected=GcmException.class)
	public void definirReleaseTestadaSemDeployEmAmbienteDeTeste() {
		Release release = new Release();
		release.setTestada(true);
	}
	
	@Test
	public void definirReleaseTestadaComDeployEmAmbienteDeTeste() {
		Release release = new Release();
		release.adicionarDeployTeste();
		release.setTestada(true);
		Assert.assertTrue(release.isTestada());
	}

	@Test (expected=GcmException.class)
	public void definirReleaseHomologadaSemDeployEmAmbienteDeHomologacao() {
		Release release = new Release();
		release.setHomologada(true);
	}
	
	@Test
	public void definirReleaseHomologadaComDeployEmAmbienteDeHomologacao() {
		Release release = new Release();
		release.adicionarDeployHomologacao();
		release.setHomologada(true);
		Assert.assertTrue(release.isHomologada());
	}
	
	@Test
	public void verificarSituacaoReleaseSemDeployEmProducao() {
		Release release = new Release();
		Assert.assertEquals(SituacaoRelease.SEM_DEPLOY_PRODUCAO, release.getSituacao());
	}
	
	@Test
	public void verificarSituacaoReleaseTestadaHomologada() {
		Release release = new Release();
		release.adicionarDeployTeste();
		release.adicionarDeployHomologacao();
		release.adicionarDeployProducao();
		
		release.setTestada(true);
		release.setHomologada(true);
		
		Assert.assertEquals(SituacaoRelease.PRODUCAO_TESTADA_HOMOLOGADA, release.getSituacao());
	}
	
	@Test
	public void verificarSituacaoReleaseTestada() {
		Release release = new Release();
		release.adicionarDeployTeste();
		release.adicionarDeployProducao();
		
		release.setTestada(true);
		
		Assert.assertEquals(SituacaoRelease.PRODUCAO_TESTADA, release.getSituacao());
	}
	
	@Test
	public void verificarSituacaoReleaseHomologada() {
		Release release = new Release();
		release.adicionarDeployHomologacao();;
		release.adicionarDeployProducao();
		
		release.setHomologada(true);
		
		Assert.assertEquals(SituacaoRelease.PRODUCAO_HOMOLOGADA, release.getSituacao());
	}

	@Test
	public void verificarSituacaoReleaseDeployEmTesteHomolocao() {
		Release release = new Release();
		release.adicionarDeployTeste();
		release.adicionarDeployHomologacao();
		release.adicionarDeployProducao();
		
		Assert.assertEquals(SituacaoRelease.PRODUCAO_DEPLOYS_HOMOLOGACAO_TESTE, release.getSituacao());
	}
	
	@Test
	public void verificarSituacaoReleaseDeployEmTeste() {
		Release release = new Release();
		release.adicionarDeployTeste();
		release.adicionarDeployProducao();
		
		Assert.assertEquals(SituacaoRelease.PRODUCAO_DEPLOY_TESTE, release.getSituacao());
	}

	@Test
	public void verificarSituacaoReleaseDeployEmHomologacao() {
		Release release = new Release();
		release.adicionarDeployHomologacao();
		release.adicionarDeployProducao();
		
		Assert.assertEquals(SituacaoRelease.PRODUCAO_DEPLOY_HOMOLOGACAO, release.getSituacao());
	}
	
	@Test
	public void verificarSituacaoReleaseDeploySomenteEmProducao() {
		Release release = new Release();
		release.adicionarDeployProducao();
		
		Assert.assertEquals(SituacaoRelease.PRODUCAO_SEM_DEPLOY_HOMOLOGACAO_TESTE, release.getSituacao());
	}
	
}
