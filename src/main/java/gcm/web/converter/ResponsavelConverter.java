package gcm.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import gcm.dominio.Responsavel;
import gcm.infra.JPAUtil;

@FacesConverter(forClass=Responsavel.class)
public class ResponsavelConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String string) {
		if (string == null || string.isEmpty()) {
			return null;
		}
		Long id = Long.valueOf(string);
		Responsavel responsavel = JPAUtil.getEntityManager().find(Responsavel.class, id);
		return responsavel;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		Responsavel responsavel = (Responsavel) object;
		if (responsavel == null || responsavel.getId() == null) {
			return null;
		}
		return String.valueOf(responsavel.getId());
	}

}
