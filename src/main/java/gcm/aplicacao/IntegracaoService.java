package gcm.aplicacao;

import gcm.dominio.Ambiente;
import gcm.dominio.Release;
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

	private EntityManager em = JPAUtil.getEntityManager();
	private Sistema sistema;
	private Ambiente ambiente;
	private Release release;
	private String mensagens = "";
	
	@GET
	@Path("/registrarRelease/{siglaSistema}/{versao}")
	public Response registrarRelease(@PathParam("siglaSistema") String siglaSistema, 
								@PathParam("versao")String versao) {
		if ( !carregarSistema(siglaSistema)) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Erro - " + mensagens).build();			
		}
		Release release = new Release();
		release.setDataCriacao(new Date());
		release.setNumero(versao);
		sistema.adicionarRelease(release);
		em.merge(sistema);
		return Response.status(Response.Status.CREATED).entity("Ok").build();
	}
	
	@GET
	@Path("/registrarDeploy/{siglaSistema}/{versao}/{ambiente}")
	public Response registrarDeploy(@PathParam("siglaSistema") String siglaSistema, 
								@PathParam("versao")String versao, 
								@PathParam("ambiente") String ambiente) {
		if ( !carregarSistema(siglaSistema) | !carregarAmbiente(ambiente) | !carregarRelease(versao)) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Erro - " + mensagens).build();			
		}
		
		if (this.ambiente.equals(Ambiente.PRODUCAO)) {
			release.adicionarDeployProducao();
		} else if (this.ambiente.equals(Ambiente.HOMOLOGACAO)) {
			release.adicionarDeployHomologacao();
		} else if (this.ambiente.equals(Ambiente.TESTE)) {
			release.adicionarDeployTeste();
		}
		
		sistema.adicionarRelease(release);		
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
	
	private boolean carregarRelease(String versao) {
		if (sistema != null) {
			for (Release release : sistema.getReleases()) {
				if (release.getNumero().equalsIgnoreCase(versao)) {
					this.release = release;
					return true;
				}
			}
		}
		mensagens = mensagens + "Nenhuma release encontrada com o número informado. ";
		return false;
	}
	
}
