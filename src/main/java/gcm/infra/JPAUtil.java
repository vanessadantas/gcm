package gcm.infra;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.Session;

public class JPAUtil {

	private static final String PERSISTENCE_UNIT_NAME = "gcm-pu";
	private static ThreadLocal<EntityManager> em = new ThreadLocal<EntityManager>();
	private static EntityManagerFactory emf;

	private JPAUtil() {
	}

	public static boolean isEntityManagerOpen(){
		return JPAUtil.em.get() != null && JPAUtil.em.get().isOpen();
	}
	
	public static EntityManager getEntityManager() {
		if (JPAUtil.emf == null) {
			JPAUtil.emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		}
		EntityManager em = JPAUtil.em.get();
		if (em == null || !em.isOpen()) {
			em = JPAUtil.emf.createEntityManager();
			JPAUtil.em.set(em);
		}
		return em;
	}
	
	public static void evictCache(EntityManager em, String region){
		((Session)em.getDelegate()).getSessionFactory().getCache().evictQueryRegion(region);
	}

	public static void closeEntityManager() {
		EntityManager em = JPAUtil.em.get();
		if (em != null) {
			EntityTransaction tx = em.getTransaction();
			if (tx.isActive()) { 
				tx.commit();
			}
			em.close();
			JPAUtil.em.set(null);
		}
	}
	
	public static void closeEntityManagerFactory(){
		closeEntityManager();
		if (JPAUtil.emf != null) {
			JPAUtil.emf.close();
		}
	}
}
