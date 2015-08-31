package gcm.dominio;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Deploy {
@Id
@GeneratedValue
private Long id;
private Ambiente ambiente;
private String versao;
private Date dataDeploy;


@Override
public String toString() {
	return ambiente + ", " + versao + ", " + dataDeploy;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((ambiente == null) ? 0 : ambiente.hashCode());
	result = prime * result + ((dataDeploy == null) ? 0 : dataDeploy.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((versao == null) ? 0 : versao.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Deploy other = (Deploy) obj;
	if (ambiente != other.ambiente)
		return false;
	if (dataDeploy == null) {
		if (other.dataDeploy != null)
			return false;
	} else if (!dataDeploy.equals(other.dataDeploy))
		return false;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
	if (versao == null) {
		if (other.versao != null)
			return false;
	} else if (!versao.equals(other.versao))
		return false;
	return true;
}

public Ambiente getAmbiente() {
	return ambiente;
}
public void setAmbiente(Ambiente ambiente) {
	this.ambiente = ambiente;
}
public String getVersao() {
	return versao;
}
public void setVersao(String versao) {
	this.versao = versao;
}
public Date getDataDeploy() {
	return dataDeploy;
}
public void setDataDeploy(Date dataDeploy) {
	this.dataDeploy = dataDeploy;
}
}
