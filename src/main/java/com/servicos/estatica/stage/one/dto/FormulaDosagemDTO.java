package com.servicos.estatica.stage.one.dto;

import java.io.Serializable;
import java.util.List;

import com.servicos.estatica.stage.one.model.Quantidade;
import com.servicos.estatica.stage.one.model.Silo;

public class FormulaDosagemDTO implements Serializable {

	private static final long serialVersionUID = 3948933452659857392L;

	private Double pesoTotal;
	private List<Quantidade> quantidades;
	private List<Silo> silos;

	public FormulaDosagemDTO() {

	}

	public FormulaDosagemDTO(Double pesoTotal, List<Quantidade> quantidades, List<Silo> silos) {
		this.pesoTotal = pesoTotal;
		this.quantidades = quantidades;
		this.silos = silos;
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

	public List<Silo> getSilos() {
		return silos;
	}

	public void setSilos(List<Silo> silos) {
		this.silos = silos;
	}

	@Override
	public String toString() {
		return "FormulaDosagemDTO [pesoTotal=" + pesoTotal + ", quantidades=" + quantidades + ", silos=" + silos + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pesoTotal == null) ? 0 : pesoTotal.hashCode());
		result = prime * result + ((quantidades == null) ? 0 : quantidades.hashCode());
		result = prime * result + ((silos == null) ? 0 : silos.hashCode());
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
		FormulaDosagemDTO other = (FormulaDosagemDTO) obj;
		if (pesoTotal == null) {
			if (other.pesoTotal != null)
				return false;
		} else if (!pesoTotal.equals(other.pesoTotal))
			return false;
		if (quantidades == null) {
			if (other.quantidades != null)
				return false;
		} else if (!quantidades.equals(other.quantidades))
			return false;
		if (silos == null) {
			if (other.silos != null)
				return false;
		} else if (!silos.equals(other.silos))
			return false;
		return true;
	}

}
