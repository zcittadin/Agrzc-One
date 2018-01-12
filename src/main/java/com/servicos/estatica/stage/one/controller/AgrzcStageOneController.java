package com.servicos.estatica.stage.one.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.servicos.estatica.stage.one.model.Materia;
import com.servicos.estatica.stage.one.shared.CadastroProperty;
import com.servicos.estatica.stage.one.shared.SiloMateriasProperty;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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
	public static String screenCadastroFormulasID = "CADASTROS_FORMULAS";
	public static String screenCadastroFormulasFile = "/fxml/CadFormulas.fxml";

	private CadastrosController cadastrosController = new CadastrosController();
	private DosagemController dosagemController = new DosagemController();
	private Mistura1Controller mistura1Controller = new Mistura1Controller();
	private Mistura2Controller mistura2Controller = new Mistura2Controller();
	private MoagemController moagemController = new MoagemController();

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

		mainContainer.loadScreenAndController(screenDosagemID, screenDosagemFile, dosagemController);
		mainContainer.loadScreenAndController(screenMistura1ID, screenMistura1File, mistura1Controller);
		mainContainer.loadScreenAndController(screenMoagemID, screenMoagemFile, moagemController);
		mainContainer.loadScreenAndController(screenMistura2ID, screenMistura2File, mistura2Controller);
		mainContainer.loadScreenAndController(screenCadastrosID, screenCadastrosFile, cadastrosController);
		mainContainer.setScreen(screenDosagemID);
		centralPane.getChildren().addAll(mainContainer);

		tooltipCliente.setStyle(TOOLTIP_CSS);
		tooltipEstatica.setStyle(TOOLTIP_CSS);
		Tooltip.install(imgCliente, tooltipCliente);
		Tooltip.install(imgEstatica, tooltipEstatica);
		initListeners();
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
	private void exit() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmar encerramento");
		alert.setHeaderText("Deseja realmente sair do sistema?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			Platform.exit();
		}
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

	@SuppressWarnings("unchecked")
	private void initListeners() {
		CadastroProperty.setFormulaChanged(false);
		CadastroProperty.cadastroFormulaProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				dosagemController.populateComboFormulas();
			}
		});
		SiloMateriasProperty.siloMateriaProperty().addListener(new ChangeListener<Materia>() {
			@Override
			public void changed(ObservableValue<? extends Materia> observable, Materia oldValue, Materia newValue) {
				dosagemController.verifySilosStatus();
			}
		});
	}

	private void configAnimations() {
		labelTransition = new FadeTransition(Duration.millis(900), lblInfo);
		labelTransition.setFromValue(0.0);
		labelTransition.setToValue(1.0);
		labelTransition.setCycleCount(Timeline.INDEFINITE);
		labelTransition.setAutoReverse(Boolean.TRUE);
	}

}
