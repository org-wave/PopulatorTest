package org.wave.test.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
public class EntidadeComAtributo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Version
	private Integer version;

	private Boolean active;

	@ManyToOne
	private EntidadeComAtributosFixos entidadeComAtributosFixos;
	
	@ManyToOne
	private EntidadeComAtributoEColecaoDeMesmoTipo entidadeComAtributoEColecaoDeMesmoTipo;

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public Long getId() {
		return id;
	}

	public Integer getVersion() {
		return version;
	}

	public Boolean getActive() {
		return active;
	}

	public EntidadeComAtributosFixos getEntidadeComAtributosFixos() {
		return entidadeComAtributosFixos;
	}
	
	public EntidadeComAtributoEColecaoDeMesmoTipo getEntidadeComAtributoEColecaoDeMesmoTipo() {
		return entidadeComAtributoEColecaoDeMesmoTipo;
	}

}
