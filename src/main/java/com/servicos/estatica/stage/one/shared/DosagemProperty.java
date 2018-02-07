package com.servicos.estatica.stage.one.shared;

import com.servicos.estatica.stage.one.dto.FormulaDosagemDTO;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class DosagemProperty {

	private static SimpleObjectProperty<FormulaDosagemDTO> selectedFormula = new SimpleObjectProperty<FormulaDosagemDTO>();

	private static SimpleBooleanProperty startFormula = new SimpleBooleanProperty();
	private static Boolean start;

	private static SimpleBooleanProperty cancelaDosagem = new SimpleBooleanProperty();
	private static Boolean cancela;

	public static SimpleObjectProperty<FormulaDosagemDTO> selectedFormulaProperty() {
		return selectedFormula;
	}

	public static FormulaDosagemDTO getSelectedFormula() {
		return selectedFormulaProperty().get();
	}

	public static void setSelectedFormula(FormulaDosagemDTO formula) {
		selectedFormulaProperty().set(formula);
	}

	public static SimpleBooleanProperty startFormulaProperty() {
		return startFormula;
	}

	public static Boolean getStartFormula() {
		return startFormula.get();
	}

	public static void setStartFormula(Boolean start) {
		startFormulaProperty().set(start);
	}

	public static Boolean getFormulaChanged() {
		return start;
	}

	public static void setFormulaStart(Boolean formulaStart) {
		DosagemProperty.start = formulaStart;
	}

	public static SimpleBooleanProperty cancelaDosagemProperty() {
		return cancelaDosagem;
	}
	
	public static Boolean getCancelaDosagem() {
		return cancelaDosagem.get();
	}
	
	public static void setCancelaDosagem(Boolean cancela) {
		cancelaDosagemProperty().set(cancela);
	}
	
	public static Boolean getCancelaDosagemChanged() {
		return cancela;
	}
	
	public static void setCancelaProperty(Boolean cancela) {
		DosagemProperty.cancela = cancela;
	}

}
