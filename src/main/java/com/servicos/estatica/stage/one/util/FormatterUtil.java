package com.servicos.estatica.stage.one.util;

import java.text.DecimalFormat;
import java.text.ParsePosition;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class FormatterUtil {

	private static DecimalFormat decimalFormat = new DecimalFormat("#.0");

	public static void formatNumberField(TextField txtField) {
		txtField.setTextFormatter(new TextFormatter<>(c -> {
			if (c.getControlNewText().isEmpty()) {
				return c;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = decimalFormat.parse(c.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
				return null;
			} else {
				return c;
			}
		}));
	}

	public static String adjustDecimal(String value, String origin, String destiny) {
		if (value == null || value.trim().equals("")) {
			return "0";
		}
		if (value.contains(origin)) {
			return value.replace(origin, destiny);
		}
		return value;
	}
}
