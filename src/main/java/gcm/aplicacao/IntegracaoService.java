package gcm.aplicacao;

import gcm.dominio.Ambiente;
import gcm.dominio.Deploy;
import gcm.dominio.Sistema;
import gcm.infra.JPAUtil;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/integracao")
public class IntegracaoService {

	EntityManager em = JPAUtil.getEntityManager();
	Sistema sistema;
	Ambiente ambiente;
	String mensagens = "";
	
	@GET
	@Path("/registrarDeploy/{siglaSistema}/{versao}/{ambiente}")
	public Response registrarDeploy(@PathParam("siglaSistema") String siglaSistema, 
								@PathParam("versao")String versao, 
								@PathParam("ambiente") String ambiente) {

		if ( !carregarSistema(siglaSistema) || !carregarAmbiente(ambiente)) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Erro - " + mensagens).build();			
		}
		
		Deploy deploy = new Deploy();
		deploy.setAmbiente(this.ambiente);
		deploy.setVersao(versao);
		deploy.setDataDeploy(new Date());
		sistema.adicionarDeploy(deploy);
		
		em.merge(sistema);
		return Response.status(Response.Status.CREATED).entity("Ok").build();
	}

	private boolean carregarSistema(String siglaSistema) {
		TypedQuery<Sistema> query = em.createNamedQuery(Sistema.PESQUISAR_POR_SIGLA_EXATA, Sistema.class);
		query.setParameter("sigla", siglaSistema.toUpperCase());
		List<Sistema> sistemas = query.getResultList();
		
		if (sistemas.size() > 0) {
			this.sistema = sistemas.get(0);
			return true;
		} else {
			mensagens = mensagens + "Nenhum sistema encontrado com a sigla informada. ";
			return false;
		}
	}
	
	private boolean carregarAmbiente(String ambiente) {
		try {
			this.ambiente = Ambiente.valueOf(ambiente);
			return true;
		} catch (IllegalArgumentException e) {
			mensagens = mensagens + "O ambiente informado não é válido. ";
			return false;
		}
	}
	
}
