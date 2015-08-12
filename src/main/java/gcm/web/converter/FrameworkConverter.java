package gcm.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import gcm.dominio.Framework;
import gcm.infra.JPAUtil;

@FacesConverter(forClass=Framework.class)
public class FrameworkConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String string) {
		if (string == null || string.isEmpty()) {
			return null;
		}
		Long id = Long.valueOf(string);
		Framework framework = JPAUtil.getEntityManager().find(Framework.class, id);
		return framework;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		Framework framework = (Framework) object;
		if (framework == null || framework.getId() == null) {
			return null;
		}
		return String.valueOf(framework.getId());
	}

}
