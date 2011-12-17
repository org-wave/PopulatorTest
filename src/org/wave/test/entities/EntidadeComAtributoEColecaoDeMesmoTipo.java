package org.wave.test.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

@Entity
public class EntidadeComAtributoEColecaoDeMesmoTipo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Version
	private Integer version;

	private Boolean active;

	@OneToOne
	private EntidadeComAtributo entidadeComAtributo;

	@OneToMany(mappedBy = "entidadeComAtributoEColecaoDeMesmoTipo")
	private Collection<EntidadeComAtributo> colecao;

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

	public EntidadeComAtributo getEntidadeComAtributo() {
		return entidadeComAtributo;
	}

	public Collection<EntidadeComAtributo> getColecao() {
		return colecao;
	}

}
