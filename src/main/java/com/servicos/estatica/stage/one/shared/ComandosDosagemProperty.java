package com.servicos.estatica.stage.one.shared;

import javafx.beans.property.SimpleBooleanProperty;

public class ComandosDosagemProperty {

	private static SimpleBooleanProperty elevadorProperty = new SimpleBooleanProperty();

	public static SimpleBooleanProperty elevadorProperty() {
		return elevadorProperty;
	}

	public static Boolean getOnOffElevador() {
		return elevadorProperty.get();
	}

	public static void setOnOffElevador(Boolean comando) {
		elevadorProperty().set(comando);
	}

}
