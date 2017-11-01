package com.servicos.estatica.stage.one.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

public class AgrzcStageOneController implements Initializable {

	public static String screenDosagemID = "DOSAGEM";
	public static String screenDosagemFile = "/com/servicos/estatica/stage/one/app/Dosagem.fxml";
	public static String screenMistura1ID = "MISTURA1";
	public static String screenMistura1File = "/com/servicos/estatica/stage/one/app/Mistura1.fxml";
	public static String screenMoagemID = "MOAGEM";
	public static String screenMoagemFile = "/com/servicos/estatica/stage/one/app/Moagem.fxml";
	public static String screenMistura2ID = "MISTURA2";
	public static String screenMistura2File = "/com/servicos/estatica/stage/one/app/Mistura2.fxml";

	@FXML
	private Pane centralPane;

	ScreensController mainContainer = new ScreensController();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		mainContainer.loadScreen(screenDosagemID, screenDosagemFile);
		mainContainer.loadScreen(screenMistura1ID, screenMistura1File);
		mainContainer.loadScreen(screenMoagemID, screenMoagemFile);
		mainContainer.loadScreen(screenMistura2ID, screenMistura2File);
		// CurrentScreenProperty.setScreen(screenInicioID);

		mainContainer.setScreen(screenDosagemID);
		centralPane.getChildren().addAll(mainContainer);
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

}
