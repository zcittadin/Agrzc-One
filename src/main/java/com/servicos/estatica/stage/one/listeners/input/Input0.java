package com.servicos.estatica.stage.one.listeners.input;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;

public class Input0 {

	public static int points = 24;

	private static List<SimpleBooleanProperty> listeners = new ArrayList<>();

	/*public Input0() {
		for (int i = 0; i < this.points; i++) {
			this.listeners.add(new SimpleBooleanProperty());
		}
	}*/

	public static List<SimpleBooleanProperty> getListeners() {
		return listeners;
	}

	public static void setListeners(List<SimpleBooleanProperty> listeners) {
		Input0.listeners = listeners;
	}

}
