package org.wave.test.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Version;

@javax.persistence.Entity
public class EntidadeManyToMany implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Version
	private Integer version;

	private Boolean active;

	private String stringField;

	@ManyToMany
	@JoinTable(
			name = "TabelaManyToMany",
			joinColumns = @JoinColumn(name = "this_id"),
			inverseJoinColumns = @JoinColumn(name = "colecao_id")
	)
	private Collection<EntidadeManyToManyInverse> colecao;
	
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

	public String getStringField() {
		return stringField;
	}

	public Collection<EntidadeManyToManyInverse> getColecao() {
		return colecao;
	}

}
