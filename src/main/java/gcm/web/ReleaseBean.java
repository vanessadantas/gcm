package gcm.web;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import gcm.aplicacao.CrudService;
import gcm.dominio.Release;
import gcm.infra.CrudServiceImpl;

@ManagedBean
@ViewScoped
public class ReleaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Release release = new Release();
	private CrudService<Release> crudService = new CrudServiceImpl<>(Release.class); 
	
	public ReleaseBean() {
		Map<String, String> parametros = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap();
		String parametroId = parametros.get("idRelease");
		if (parametroId != null) {
			release = crudService.buscarPorId(Long.valueOf(parametroId));
		}
	}

	public Release getRelease() {
		return release;
	}

	public void setRelease(Release release) {
		this.release = release;
	}
}
