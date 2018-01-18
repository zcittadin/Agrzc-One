package com.servicos.estatica.stage.one.shared;

import com.servicos.estatica.stage.one.model.Formula;

import javafx.beans.property.SimpleObjectProperty;

public class HistoricoProperty {

	private static SimpleObjectProperty<Formula> selectedFormula = new SimpleObjectProperty<Formula>();

	public static SimpleObjectProperty<Formula> selectedFormulaProperty() {
		return selectedFormula;
	}

	public static Formula getCadastroFormula() {
		return selectedFormulaProperty().get();
	}

	public final void setCadastroFormula(Formula formula) {
		selectedFormulaProperty().set(formula);
	}

}
