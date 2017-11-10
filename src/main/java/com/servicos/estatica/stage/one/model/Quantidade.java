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
@Table(name = "quantidade")
public class Quantidade implements Serializable {

	private static final long serialVersionUID = 7911082753712642031L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_materia")
	private Materia materiaQuantidade;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_formula")
	private Formula formulaQuantidade;
	@Column(name = "peso")
	private Double peso;

	public Quantidade() {

	}

	public Quantidade(Long id, Materia materiaQuantidade, Formula formulaQuantidade, Double peso) {
		this.id = id;
		this.materiaQuantidade = materiaQuantidade;
		this.formulaQuantidade = formulaQuantidade;
		this.peso = peso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Materia getMateriaQuantidade() {
		return materiaQuantidade;
	}

	public void setMateriaQuantidade(Materia quantidadeMateria) {
		this.materiaQuantidade = quantidadeMateria;
	}

	public Formula getFormulaQuantidade() {
		return formulaQuantidade;
	}

	public void setFormulaQuantidade(Formula quantidadeFormula) {
		this.formulaQuantidade = quantidadeFormula;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "Quantidade [id=" + id + ", materiaQuantidade=" + materiaQuantidade + ", formulaQuantidade="
				+ formulaQuantidade + ", peso=" + peso + "]";
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
		Quantidade other = (Quantidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
