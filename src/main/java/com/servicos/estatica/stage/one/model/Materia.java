package com.servicos.estatica.stage.one.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "materia")
public class Materia implements Serializable {

	private static final long serialVersionUID = 8744087992295917757L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "nome_materia")
	private String nomeMateria;
	@OneToMany(orphanRemoval = true, mappedBy = "materiaQuantidade", targetEntity = Quantidade.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Quantidade> quantidades;

	public Materia() {

	}

	public Materia(Long id, String nomeMateria, List<Quantidade> quantidades) {
		this.id = id;
		this.nomeMateria = nomeMateria;
		this.quantidades = quantidades;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeMateria() {
		return nomeMateria;
	}

	public void setNomeMateria(String nomeMateria) {
		this.nomeMateria = nomeMateria;
	}

	public List<Quantidade> getQuantidades() {
		return quantidades;
	}

	public void setQuantidades(List<Quantidade> quantidades) {
		this.quantidades = quantidades;
	}

	@Override
	public String toString() {
		return nomeMateria;
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
		Materia other = (Materia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
