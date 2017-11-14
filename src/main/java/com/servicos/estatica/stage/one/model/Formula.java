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
@Table(name = "formula")
public class Formula implements Serializable {

	private static final long serialVersionUID = -6765550580537151371L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "nome_formula")
	private String nomeFormula;
	@Column(name = "peso_total")
	private Double pesoTotal;
	@OneToMany(orphanRemoval = false, mappedBy = "formulaQuantidade", targetEntity = Quantidade.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Quantidade> quantidades;

	public Formula() {

	}

	public Formula(Long id, String nomeFormula, Double pesoTotal, List<Quantidade> quantidades) {
		this.id = id;
		this.nomeFormula = nomeFormula;
		this.pesoTotal = pesoTotal;
		this.quantidades = quantidades;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeFormula() {
		return nomeFormula;
	}

	public void setNomeFormula(String nomeFormula) {
		this.nomeFormula = nomeFormula;
	}

	public Double getPesoTotal() {
		return pesoTotal;
	}

	public void setPesoTotal(Double pesoTotal) {
		this.pesoTotal = pesoTotal;
	}

	public List<Quantidade> getQuantidades() {
		return quantidades;
	}

	public void setQuantidades(List<Quantidade> quantidades) {
		this.quantidades = quantidades;
	}

	@Override
	public String toString() {
		return "Formula [id=" + id + ", nomeFormula=" + nomeFormula + ", pesoTotal=" + pesoTotal + ", quantidades="
				+ quantidades + "]";
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
		Formula other = (Formula) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
