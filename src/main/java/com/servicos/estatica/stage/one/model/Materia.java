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

	private static final long serialVersionUID = -1920919957614557284L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@OneToMany(mappedBy = "formula", targetEntity = Formula.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Formula> idFormula;
	@OneToMany(mappedBy = "quantidade", targetEntity = Quantidade.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Quantidade> idQuantidade;
	@Column(name = "nome_materia")
	private String nomeMateria;

	public Materia() {

	}

	public Materia(Long id, List<Formula> idFormula, List<Quantidade> idQuantidade, String nomeMateria) {
		this.id = id;
		this.idFormula = idFormula;
		this.idQuantidade = idQuantidade;
		this.nomeMateria = nomeMateria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Formula> getIdFormula() {
		return idFormula;
	}

	public void setIdFormula(List<Formula> idFormula) {
		this.idFormula = idFormula;
	}

	public List<Quantidade> getIdQuantidade() {
		return idQuantidade;
	}

	public void setIdQuantidade(List<Quantidade> idQuantidade) {
		this.idQuantidade = idQuantidade;
	}

	public String getNomeMateria() {
		return nomeMateria;
	}

	public void setNomeMateria(String nomeMateria) {
		this.nomeMateria = nomeMateria;
	}

	@Override
	public String toString() {
		return "Materia [id=" + id + ", idFormula=" + idFormula + ", idQuantidade=" + idQuantidade + ", nomeMateria="
				+ nomeMateria + "]";
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
