package gcm.aplicacao;

import gcm.dominio.Sistema;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/integracao")
public class IntegracaoFacade {

	//EntityManager em = JPAUtil.getEntityManager();

	@GET
	@Path("/registrarDeploy/{nomeSistema}/{versao}/{ambiente}")
	public Response registrarDeploy(@PathParam("nomeSistema") String nomeSistema, 
								@PathParam("versao")String versao, 
								@PathParam("ambiente") String ambiente) {
		return Response.status(200).entity("<h1>Chamou o ws</h1>").build();
//		Sistema sistema = buscarSistema(nomeSistema);
//		
//		Deploy deploy = new Deploy();
//		deploy.setAmbiente(Ambiente.valueOf(ambiente));
//		deploy.setVersao(versao);
//		deploy.setDataDeploy(new Date());
//		sistema.adicionarDeploy(deploy);
//		
//		em.merge(sistema);
	}
	
	private Sistema buscarSistema(String nomeSistema) {
//		Query query = em.createNamedQuery(Sistema.PESQUISAR_POR_NOME_EXATO, Sistema.class);
//		query.setParameter("nome", nomeSistema);
//		return (Sistema) query.getSingleResult();
		return null;
	}
	
}
