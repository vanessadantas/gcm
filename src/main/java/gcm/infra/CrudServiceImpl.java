package gcm.infra;

import gcm.aplicacao.CrudService;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class CrudServiceImpl<T> implements CrudService<T> {
	final Class<T> tipoClasse;

	public CrudServiceImpl(Class<T> tipoClasse) {
		this.tipoClasse = tipoClasse;
	}
	
	@Override
	public T salvar(T t) {
		EntityManager em = JPAUtil.getEntityManager();
		em.merge(t);
		em.flush();
		return t;
	}

	@Override
	public T buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		return (T) em.find(tipoClasse, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void excluir(T t) {
		EntityManager em = JPAUtil.getEntityManager();
		T ref = (T) em.getReference(t.getClass(), t);
		em.remove(ref);
	}
	
	@Override
	public void excluir(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		T t = em.find(tipoClasse, id);
		em.remove(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> pesquisarPorNamedQuery(String nomeQuery) {
		EntityManager em = JPAUtil.getEntityManager();
		return em.createNamedQuery(nomeQuery).getResultList();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<T> pesquisarPorNamedQuery(String nomeQuery, int limite) {
		EntityManager em = JPAUtil.getEntityManager();
		return em.createNamedQuery(nomeQuery).setMaxResults(limite).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> pesquisarPorNamedQuery(String nomeQuery, Map<String, Object> parametros) {
		EntityManager em = JPAUtil.getEntityManager();
		Query query = em.createNamedQuery(nomeQuery);
		
		Set<Entry<String, Object>> rawParameters = parametros.entrySet();
		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> pesquisarPorNamedQuery(String nomeQuery, Map<String, Object> parametros, int limite) {
		EntityManager em = JPAUtil.getEntityManager();
		Query query = em.createNamedQuery(nomeQuery);
		
		Set<Entry<String, Object>> rawParameters = parametros.entrySet();
		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		
		return query.setMaxResults(limite).getResultList();
	}

}
