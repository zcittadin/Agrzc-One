package com.servicos.estatica.stage.one.shared;

import javafx.beans.property.SimpleStringProperty;

public class StatusLabelProperty {

	private static SimpleStringProperty statusLabel = new SimpleStringProperty();

	public static SimpleStringProperty statusLabelProperty() {
		return statusLabel;
	}

	public static String getStatusLabel() {
		return statusLabel.get();
	}

	public static void setStatusLabel(String statusMsg) {
		statusLabelProperty().set(statusMsg);
	}
}
