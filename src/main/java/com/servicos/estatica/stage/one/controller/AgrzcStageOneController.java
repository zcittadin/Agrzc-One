package com.servicos.estatica.stage.one.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.ghgande.j2mod.modbus.Modbus;
import com.servicos.estatica.stage.one.modbus.ModbusTCPService;
import com.servicos.estatica.stage.one.shared.CadastroProperty;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
	public static String screenDosagemFile = "/com/servicos/estatica/stage/one/app/Dosagem.fxml";
	public static String screenProcessamentoID = "PROCESSAMENTO";
	public static String screenProcessamentoFile = "/com/servicos/estatica/stage/one/app/Processamento.fxml";
	public static String screenCadastrosID = "CADASTROS";
	public static String screenCadastrosFile = "/com/servicos/estatica/stage/one/app/Cadastros.fxml";
	public static String screenCadastroFormulasID = "CADASTROS_FORMULAS";
	public static String screenCadastroFormulasFile = "/com/servicos/estatica/stage/one/app/CadFormulas.fxml";

	private CadastrosController cadastrosController = new CadastrosController();
	private DosagemController dosagemController = new DosagemController();
	private ProcessamentoController processamentoController = new ProcessamentoController();

	private static ModbusTCPService modbusService = new ModbusTCPService();
	private static Timeline scanIO;
	private static final String IP = "192.168.1.25";
	protected static final int PORT = Modbus.DEFAULT_PORT;
	private static int slot = 0;

	@FXML
	private Pane centralPane;
	@FXML
	private ImageView imgCliente;
	@FXML
	private ImageView imgEstatica;
	@FXML
	private Label lblInfo;

	private static FadeTransition labelTransition;

	private static String TOOLTIP_CSS = "-fx-font-size: 12pt; -fx-font-weight: bold; -fx-font-style: normal; -fx-background-color: green;";
	private Tooltip tooltipCliente = new Tooltip("Informações sobre o cliente");
	private Tooltip tooltipEstatica = new Tooltip("Informações sobre o fabricante");

	ScreensController mainContainer = new ScreensController();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		mainContainer.loadScreenAndController(screenDosagemID, screenDosagemFile, dosagemController);
		mainContainer.loadScreenAndController(screenProcessamentoID, screenProcessamentoFile, processamentoController);
		mainContainer.loadScreenAndController(screenCadastrosID, screenCadastrosFile, cadastrosController);
		mainContainer.setScreen(screenDosagemID);
		centralPane.getChildren().addAll(mainContainer);

		tooltipCliente.setStyle(TOOLTIP_CSS);
		tooltipEstatica.setStyle(TOOLTIP_CSS);
		Tooltip.install(imgCliente, tooltipCliente);
		Tooltip.install(imgEstatica, tooltipEstatica);
		initListeners();
		configAnimations();
		modbusService.setConnectionParams(IP, PORT);
		System.out.println("Conectado ao " + IP);
		initModbusScan();
		labelTransition.play();
	}

	private void initModbusScan() {
		scanIO = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				readMultiplePoints(slot);
				slot++;
				if (slot == 6)
					slot = 0;
			}
		}));
		scanIO.setCycleCount(Timeline.INDEFINITE);
		scanIO.play();
	}

	@FXML
	private void openDosagem() {
		mainContainer.setScreen(screenDosagemID);
	}

	@FXML
	private void openProcessamento() {
		mainContainer.setScreen(screenProcessamentoID);
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
		root = FXMLLoader.load(getClass().getResource("/com/servicos/estatica/stage/one/app/ClienteInfo.fxml"));
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
		root = FXMLLoader.load(getClass().getResource("/com/servicos/estatica/stage/one/app/EstaticaInfo.fxml"));
		stage.setScene(new Scene(root));
		stage.setTitle("Informações sobre o fabricante");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(imgCliente.getScene().getWindow());
		stage.setResizable(Boolean.FALSE);
		stage.showAndWait();
	}

	private void initListeners() {
		CadastroProperty.setFormulaChanged(false);
		CadastroProperty.cadastroFormulaProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				dosagemController.populateComboFormulas();
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

	private void readMultiplePoints(int slot) {
		Boolean[] points = new Boolean[0];
		switch (slot) {
		case 0:
			points = modbusService.readMultiplePoints(0, 24);

			System.out.println("Slot 0:");
			for (int i = 0; i < points.length; i++) {
				System.out.println("I0." + i + ": " + points[i]);
			}

			break;
		case 1:
			points = modbusService.readMultiplePoints(24, 16);
			
			break;
		case 2:
			points = modbusService.readMultiplePoints(40, 16);

			break;
		case 3:
			points = modbusService.readMultiplePoints(100, 16);
			
			System.out.println("Slot 3:");
			for (int i = 0; i < points.length; i++) {
				System.out.println("Q0." + i + ": " + points[i]);
			}

			break;
		case 4:
			points = modbusService.readMultiplePoints(116, 16);

			break;
		case 5:
			points = modbusService.readMultiplePoints(132, 16);

			break;
		}
	}

}
