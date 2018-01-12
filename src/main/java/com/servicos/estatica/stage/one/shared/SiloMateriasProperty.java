package com.servicos.estatica.stage.one.shared;

import com.servicos.estatica.stage.one.model.Materia;

import javafx.beans.property.SimpleObjectProperty;

public class SiloMateriasProperty {

	private static SimpleObjectProperty<Materia> siloMateria = new SimpleObjectProperty<Materia>();
	
	@SuppressWarnings("rawtypes")
	public static SimpleObjectProperty siloMateriaProperty() {
		return siloMateria;
	}

	public static Materia getSiloMateria() {
		return siloMateria.get();
	}

	public static void setSiloMateria(Materia materia) {
		siloMateria.set(materia);
	}
}
