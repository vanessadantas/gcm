package gcm.aplicacao;

import java.util.List;
import java.util.Map;

public interface CrudService<T> {

	T salvar(T t);
	T buscarPorId(Long id);
	void excluir(T t);
	void excluir(Long id);
	List<T> pesquisarPorNamedQuery(String nomeQuery);
	List<T> pesquisarPorNamedQuery(String nomeQuery,int limite);
	List<T> pesquisarPorNamedQuery(String nomeQuery, Map<String,Object> parametros);
	List<T> pesquisarPorNamedQuery(String nomeQuery, Map<String,Object> parametros, int limite);	

}
