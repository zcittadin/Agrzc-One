package com.servicos.estatica.stage.one.shared;

import javafx.beans.property.SimpleObjectProperty;

public class ProcessamentoProperty {
	
	private static SimpleObjectProperty<Integer> writeBalanca2Property = new SimpleObjectProperty<>();

	public static SimpleObjectProperty<Integer> writeBalanca2Property() {
		return writeBalanca2Property;
	}

	public static Integer getWriteBalanca2Property() {
		return writeBalanca2Property().get();
	}

	public static void setWriteBalanca2Property(Integer value) {
		writeBalanca2Property().set(value);
	}

}
