package com.servicos.estatica.stage.one.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AgrzcStageOneController implements Initializable {

	public static String screenDosagemID = "DOSAGEM";
	public static String screenDosagemFile = "/fxml/Dosagem.fxml";
	public static String screenMistura1ID = "MISTURA1";
	public static String screenMistura1File = "/fxml/Mistura1.fxml";
	public static String screenMoagemID = "MOAGEM";
	public static String screenMoagemFile = "/fxml/Moagem.fxml";
	public static String screenMistura2ID = "MISTURA2";
	public static String screenMistura2File = "/fxml/Mistura2.fxml";
	public static String screenCadastrosID = "CADASTROS";
	public static String screenCadastrosFile = "/fxml/Cadastros.fxml";

	@FXML
	private Pane centralPane;
	@FXML
	private ImageView imgCliente;
	@FXML
	private ImageView imgEstatica;
	@FXML
	private Label lblInfo;
	
	private static FadeTransition labelTransition;

	private static String TOOLTIP_CSS = "-fx-font-size: 8pt; -fx-font-weight: bold; -fx-font-style: normal; ";
	private Tooltip tooltipCliente = new Tooltip("Informações sobre o cliente");
	private Tooltip tooltipEstatica = new Tooltip("Informações sobre o fabricante");

	ScreensController mainContainer = new ScreensController();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		mainContainer.loadScreen(screenDosagemID, screenDosagemFile);
		mainContainer.loadScreen(screenMistura1ID, screenMistura1File);
		mainContainer.loadScreen(screenMoagemID, screenMoagemFile);
		mainContainer.loadScreen(screenMistura2ID, screenMistura2File);
		mainContainer.loadScreen(screenCadastrosID, screenCadastrosFile);

		mainContainer.setScreen(screenCadastrosID);
		centralPane.getChildren().addAll(mainContainer);

		tooltipCliente.setStyle(TOOLTIP_CSS);
		tooltipEstatica.setStyle(TOOLTIP_CSS);
		Tooltip.install(imgCliente, tooltipCliente);
		Tooltip.install(imgEstatica, tooltipEstatica);
		configAnimations();
		labelTransition.play();
	}

	@FXML
	private void openDosagem() {
		mainContainer.setScreen(screenDosagemID);
	}

	@FXML
	private void openMistura1() {
		mainContainer.setScreen(screenMistura1ID);
	}

	@FXML
	private void openMoagem() {
		mainContainer.setScreen(screenMoagemID);
	}

	@FXML
	private void openMistura2() {
		mainContainer.setScreen(screenMistura2ID);
	}

	@FXML
	private void openCadastros() {
		mainContainer.setScreen(screenCadastrosID);
	}

	@FXML
	private void handleImgClienteAction() throws IOException {
		Stage stage;
		Parent root;
		stage = new Stage();
		root = FXMLLoader.load(getClass().getResource("/fxml/ClienteInfo.fxml"));
		stage.setScene(new Scene(root));
		stage.setTitle("Informações sobre o cliente");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(imgCliente.getScene().getWindow());
		stage.setResizable(Boolean.FALSE);
		stage.showAndWait();
	}

	@FXML
	private void handleImgEstaticaAction() throws IOException {
		Stage stage;
		Parent root;
		stage = new Stage();
		root = FXMLLoader.load(getClass().getResource("/fxml/EstaticaInfo.fxml"));
		stage.setScene(new Scene(root));
		stage.setTitle("Informações sobre o fabricante");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(imgCliente.getScene().getWindow());
		stage.setResizable(Boolean.FALSE);
		stage.showAndWait();
	}
	
	private void configAnimations() {
		labelTransition = new FadeTransition(Duration.millis(900), lblInfo);
		labelTransition.setFromValue(0.0);
		labelTransition.setToValue(1.0);
		labelTransition.setCycleCount(Timeline.INDEFINITE);
		labelTransition.setAutoReverse(Boolean.TRUE);
	}

}
