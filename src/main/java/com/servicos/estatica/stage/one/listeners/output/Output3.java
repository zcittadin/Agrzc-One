package com.servicos.estatica.stage.one.listeners.output;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;

public class Output3 {

	public static int points = 16;

	private static List<SimpleBooleanProperty> listeners = new ArrayList<>();

	public static List<SimpleBooleanProperty> getListeners() {
		return listeners;
	}

	public static void setListeners(List<SimpleBooleanProperty> listeners) {
		Output3.listeners = listeners;
	}
}
