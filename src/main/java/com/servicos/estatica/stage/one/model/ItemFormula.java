package com.servicos.estatica.stage.one.model;

public class ItemFormula {

	private Long idMateria;
	private String nomeMateria;
	private Double quantidade;

	public ItemFormula() {

	}

	public ItemFormula(Long idMateria, String nomeMateria, Double quantidade) {
		this.idMateria = idMateria;
		this.nomeMateria = nomeMateria;
		this.quantidade = quantidade;
	}

	public Long getIdMateria() {
		return idMateria;
	}

	public void setIdMateria(Long idMateria) {
		this.idMateria = idMateria;
	}

	public String getNomeMateria() {
		return nomeMateria;
	}

	public void setNomeMateria(String nomeMateria) {
		this.nomeMateria = nomeMateria;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

}
