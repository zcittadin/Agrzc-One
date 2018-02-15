package com.servicos.estatica.stage.one.listeners.input;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;

public class Input1 {

	public static int POINTS = 16;

	private static List<SimpleBooleanProperty> listeners = new ArrayList<>();

	public static List<SimpleBooleanProperty> getListeners() {
		return listeners;
	}

	public static void setListeners(List<SimpleBooleanProperty> listeners) {
		Input1.listeners = listeners;
	}
}
