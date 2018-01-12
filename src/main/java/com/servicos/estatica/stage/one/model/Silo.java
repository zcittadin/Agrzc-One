package com.servicos.estatica.stage.one.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "silos")
public class Silo implements Serializable {

	private static final long serialVersionUID = -3490308927478202127L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "id")
	private Long id;
	@Column(name = "silo")
	private String silo;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_materia")
	private Materia materia;

	public Silo() {

	}

	public Silo(Long id, String silo, Materia materia) {
		this.id = id;
		this.silo = silo;
		this.materia = materia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSilo() {
		return silo;
	}

	public void setSilo(String silo) {
		this.silo = silo;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	@Override
	public String toString() {
		return "Silo [id=" + id + ", silo=" + silo + ", materia=" + materia + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Silo other = (Silo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
