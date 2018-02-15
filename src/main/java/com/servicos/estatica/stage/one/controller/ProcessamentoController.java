package com.servicos.estatica.stage.one.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.servicos.estatica.stage.one.app.ControlledScreen;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Line;
import javafx.scene.shape.Sphere;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProcessamentoController implements Initializable, ControlledScreen {

	@FXML
	private Label lblBalanca;
	@FXML
	private Label lblBalancaEscrita;
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
	private Tooltip tooltipBalancaEscrita = new Tooltip("Clique para programar o peso do aditivo");

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
		tooltipBalancaEscrita.setStyle(TOOLTIP_CSS);
		Tooltip.install(lblBalanca, tooltipBalanca);
		Tooltip.install(lblBalancaEscrita, tooltipBalancaEscrita);
	}

	@FXML
	private void openValorPesagem() {
		try {
			Stage stage;
			Parent root;
			stage = new Stage();
			URL url = getClass().getResource("/com/servicos/estatica/stage/one/app/Balanca2Escrita.fxml");
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(url);
			fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
			root = (Parent) fxmlloader.load(url.openStream());
			stage.setScene(new Scene(root));
			// ((Balanca2EscritaController) fxmlloader.getController()).setContext(null,
			// lblBalancaEscrita.getScene());
			stage.setTitle("Gerenciar matéria-prima");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(lblBalancaEscrita.getScene().getWindow());
			stage.setResizable(Boolean.FALSE);
			stage.showAndWait();
			// findMaterias();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setValorPesagem(String valor) {
		if (valor.length() < 2)
			valor = "0".concat(valor);
		if (valor.length() < 3)
			valor = "0".concat(valor);
		if (valor.length() < 4)
			valor = "0".concat(valor);
		lblBalancaEscrita.setText(valor);
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
		// if (channel == 1)
		// sph = input_1.get(point);
		sph = input_0.get(point);
		if (sph == null)
			return;
		if (b)
			sph.setMaterial(greenMaterial);
		else
			sph.setMaterial(grayMaterial);
	}

	public void updateBalanca2(Integer value) {
		String strValue = value.toString();
		if (strValue.length() < 2)
			strValue = "0".concat(strValue);
		if (strValue.length() < 3)
			strValue = "0".concat(strValue);
		if (strValue.length() < 4)
			strValue = "0".concat(strValue);
		lblBalanca.setText(strValue);
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
