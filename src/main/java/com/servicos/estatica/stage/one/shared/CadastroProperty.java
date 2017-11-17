package com.servicos.estatica.stage.one.shared;

import javafx.beans.property.SimpleBooleanProperty;

public class CadastroProperty {

	private static SimpleBooleanProperty cadastroFormula = new SimpleBooleanProperty();
	private static Boolean formulaChanged;

	public static SimpleBooleanProperty cadastroFormulaProperty() {
		return cadastroFormula;
	}

	public static Boolean getCadastroFormula() {
		return cadastroFormula.get();
	}

	public final void setCadastroFormula(Boolean cadastroFormula) {
		cadastroFormulaProperty().set(cadastroFormula);
	}

	public static Boolean getFormulaChanged() {
		return formulaChanged;
	}

	public static void setFormulaChanged(Boolean formulaChanged) {
		CadastroProperty.formulaChanged = formulaChanged;
	}

}
