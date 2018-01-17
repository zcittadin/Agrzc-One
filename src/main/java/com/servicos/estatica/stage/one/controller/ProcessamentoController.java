package com.servicos.estatica.stage.one.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.servicos.estatica.stage.one.app.ControlledScreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.Line;

public class ProcessamentoController implements Initializable, ControlledScreen {

	@FXML
	private Label lblBalanca;
	@FXML
	private Line divLine;

	private Tooltip tooltipBalanca = new Tooltip("Peso atual");

	private static String TOOLTIP_CSS = "-fx-font-size: 12pt; -fx-font-weight: bold; -fx-font-style: normal; -fx-background-color: green;";

	ScreensController myController;

	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		divLine.getStrokeDashArray().addAll(2d, 5d);
		tooltipBalanca.setStyle(TOOLTIP_CSS);
		Tooltip.install(lblBalanca, tooltipBalanca);

	}

}
