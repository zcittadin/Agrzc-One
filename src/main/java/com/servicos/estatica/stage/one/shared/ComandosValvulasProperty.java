package com.servicos.estatica.stage.one.shared;

import javafx.beans.property.SimpleBooleanProperty;

public class ComandosValvulasProperty {

	private static SimpleBooleanProperty silos12Property = new SimpleBooleanProperty();
	private static SimpleBooleanProperty silos34Property = new SimpleBooleanProperty();
	private static SimpleBooleanProperty saidaBalanca1Property = new SimpleBooleanProperty();
	private static SimpleBooleanProperty mistura11Property = new SimpleBooleanProperty();
	private static SimpleBooleanProperty mistura12Property = new SimpleBooleanProperty();
	private static SimpleBooleanProperty transportePneumaticoProperty = new SimpleBooleanProperty();
	private static SimpleBooleanProperty saidaBalanca2Property = new SimpleBooleanProperty();
	private static SimpleBooleanProperty mistura2Property = new SimpleBooleanProperty();

	public static SimpleBooleanProperty silos12Property() {
		return silos12Property;
	}

	public static SimpleBooleanProperty silos34Property() {
		return silos34Property;
	}

	public static SimpleBooleanProperty saidaBalanca1Property() {
		return saidaBalanca1Property;
	}

	public static SimpleBooleanProperty mistura11Property() {
		return mistura11Property;
	}

	public static SimpleBooleanProperty mistura12Property() {
		return mistura12Property;
	}

	public static SimpleBooleanProperty transportePneumaticoProperty() {
		return transportePneumaticoProperty;
	}

	public static SimpleBooleanProperty saidaBalanca2Property() {
		return saidaBalanca2Property;
	}

	public static SimpleBooleanProperty mistura2Property() {
		return mistura2Property;
	}

}
