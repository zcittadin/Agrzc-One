package com.servicos.estatica.stage.one.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.servicos.estatica.stage.one.app.ControlledScreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Line;
import javafx.scene.shape.Sphere;

public class ProcessamentoController implements Initializable, ControlledScreen {

	@FXML
	private Label lblBalanca;
	@FXML
	private Line divLine;
	@FXML
	private Sphere I0_12;
	@FXML
	private Sphere I0_14;
	@FXML
	private Sphere I0_15;
	@FXML
	private Sphere I0_16;
	@FXML
	private Sphere I0_17;
	@FXML
	private Sphere I0_18;
	@FXML
	private Sphere I0_19;
	@FXML
	private Sphere I0_22;
	@FXML
	private Sphere I0_23;

	private final PhongMaterial greenMaterial = new PhongMaterial();
	private final PhongMaterial grayMaterial = new PhongMaterial();

	private static Map<Integer, Sphere> input_0 = new HashMap<>();

	private Tooltip tooltipBalanca = new Tooltip("Peso atual");

	private static String TOOLTIP_CSS = "-fx-font-size: 12pt; -fx-font-weight: bold; -fx-font-style: normal; -fx-background-color: green;";

	ScreensController myController;

	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initIOPoints();
		initColors();
		divLine.getStrokeDashArray().addAll(2d, 5d);
		tooltipBalanca.setStyle(TOOLTIP_CSS);
		Tooltip.install(lblBalanca, tooltipBalanca);
	}

	private void initIOPoints() {
		input_0.put(12, I0_12);
		input_0.put(14, I0_14);
		input_0.put(15, I0_15);
		input_0.put(16, I0_16);
		input_0.put(17, I0_17);
		input_0.put(18, I0_18);
		input_0.put(19, I0_19);
		input_0.put(22, I0_22);
		input_0.put(23, I0_23);
	}

	public void updateIOPoints(int channel, int point, Boolean b) {
		Sphere sph = null;
		if (channel == 0)
			sph = input_0.get(point);
		//if (channel == 1)
			//sph = input_1.get(point);
		sph = input_0.get(point);
		if (sph == null)
			return;
		if (b)
			sph.setMaterial(greenMaterial);
		else
			sph.setMaterial(grayMaterial);
	}
	
	private void initColors() {
		grayMaterial.setDiffuseColor(Color.DARKGRAY);
		grayMaterial.setSpecularColor(Color.GREY);
		grayMaterial.setSpecularPower(0.95);
		greenMaterial.setDiffuseColor(Color.DARKGREEN);
		greenMaterial.setSpecularColor(Color.GREEN);
		greenMaterial.setSpecularPower(0.95);
	}

}
