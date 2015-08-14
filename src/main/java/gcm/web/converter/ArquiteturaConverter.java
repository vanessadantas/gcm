package gcm.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import gcm.dominio.Arquitetura;
import gcm.infra.JPAUtil;

@FacesConverter(forClass=Arquitetura.class)
public class ArquiteturaConverter implements Converter { 
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String string) {
		if (string == null || string.isEmpty()) {
			return null;
		}
		Long id = Long.valueOf(string);
		Arquitetura arquitetura = JPAUtil.getEntityManager().find(Arquitetura.class, id);
		return arquitetura;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		Arquitetura arquitetura = (Arquitetura) object;
		if (arquitetura == null || arquitetura.getId() == null) {
			return null;
		}
		return String.valueOf(arquitetura.getId());
	}
}
