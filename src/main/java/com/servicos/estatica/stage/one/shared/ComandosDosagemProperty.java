package com.servicos.estatica.stage.one.shared;

import javafx.beans.property.SimpleBooleanProperty;

public class ComandosDosagemProperty {

	private static SimpleBooleanProperty elevadorProperty = new SimpleBooleanProperty();
	private static SimpleBooleanProperty roscaSuperiorHorarioProperty = new SimpleBooleanProperty();
	private static SimpleBooleanProperty roscaSuperiorAntiHorarioProperty = new SimpleBooleanProperty();
	private static SimpleBooleanProperty rosca1Property = new SimpleBooleanProperty();
	private static SimpleBooleanProperty rosca2Property = new SimpleBooleanProperty();

	public static SimpleBooleanProperty elevadorProperty() {
		return elevadorProperty;
	}

	public static Boolean getOnOffElevador() {
		return elevadorProperty.get();
	}

	public static void setOnOffElevador(Boolean comando) {
		elevadorProperty().set(comando);
	}

	public static SimpleBooleanProperty roscaSuperiorHorarioProperty() {
		return roscaSuperiorHorarioProperty;
	}

	public static Boolean getRoscaSuperiorHorario() {
		return roscaSuperiorHorarioProperty().get();
	}

	public static void setRoscaSuperiorHorario(Boolean comando) {
		roscaSuperiorHorarioProperty().set(comando);
	}

	public static SimpleBooleanProperty roscaSuperiorAntiHorarioProperty() {
		return roscaSuperiorAntiHorarioProperty;
	}

	public static Boolean getRoscaSuperiorAntiHorario() {
		return roscaSuperiorAntiHorarioProperty().get();
	}

	public static void setRoscaSuperiorAntiHorario(Boolean comando) {
		roscaSuperiorAntiHorarioProperty().set(comando);
	}

	public static SimpleBooleanProperty rosca1Property() {
		return rosca1Property;
	}

	public static Boolean getRosca1() {
		return rosca1Property().get();
	}

	public static void setRosca1(Boolean comando) {
		rosca1Property().set(comando);
	}

	public static SimpleBooleanProperty rosca2Property() {
		return rosca2Property;
	}

	public static Boolean getRosca2() {
		return rosca2Property().get();
	}

	public static void setRosca2(Boolean comando) {
		rosca2Property().set(comando);
	}

}
