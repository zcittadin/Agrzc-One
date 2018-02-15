package com.servicos.estatica.stage.one.listeners.output;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;

public class Output0 {

	public static int POINTS = 16;

	private static List<SimpleBooleanProperty> listeners = new ArrayList<>();

	public static List<SimpleBooleanProperty> getListeners() {
		return listeners;
	}

	public static void setListeners(List<SimpleBooleanProperty> listeners) {
		Output0.listeners = listeners;
	}
}
