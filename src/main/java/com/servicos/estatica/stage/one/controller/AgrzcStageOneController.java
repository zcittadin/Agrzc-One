package com.servicos.estatica.stage.one.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.ghgande.j2mod.modbus.Modbus;
import com.servicos.estatica.stage.one.dto.FormulaDosagemDTO;
import com.servicos.estatica.stage.one.listeners.input.Input0;
import com.servicos.estatica.stage.one.listeners.input.Input1;
import com.servicos.estatica.stage.one.listeners.input.Input2;
import com.servicos.estatica.stage.one.listeners.output.Output0;
import com.servicos.estatica.stage.one.listeners.output.Output3;
import com.servicos.estatica.stage.one.listeners.output.Output4;
import com.servicos.estatica.stage.one.modbus.ModbusTCPService;
import com.servicos.estatica.stage.one.model.Formula;
import com.servicos.estatica.stage.one.shared.CadastroProperty;
import com.servicos.estatica.stage.one.shared.ComandosDosagemProperty;
import com.servicos.estatica.stage.one.shared.DosagemProperty;
import com.servicos.estatica.stage.one.shared.HistoricoProperty;
import com.servicos.estatica.stage.one.shared.ProcessamentoProperty;
import com.servicos.estatica.stage.one.shared.StatusLabelProperty;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
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

	private static FormulaDosagemDTO formula;
	private int dosagemIndex = 0;

	private static final int REG_PESO_MATERIA = 202;
	private static final int REG_PESO_TOTAL_CARGA = 224;
	private static final int REG_SILO = 230;
	private static final int REG_QUANTIDADE_MATERIA = 200;
	private static final int REG_FLAG_FINALIZADO = 220;
	private static final int REG_LEITURA_BALANCA_1 = 212;
	private static final int REG_LEITURA_BALANCA_2 = 232;
	private static final int REG_ESCRITA_BALANCA_2 = 234;
	private static final int REG_COMANDO_ELEVADOR = 240;
	private static final int REG_ROSCA_SUP_HORARIO = 242;
	private static final int REG_ROSCA_SUP_ANTI_HORARIO = 244;
	private static final int REG_COMANDO_ROSCA_1 = 246;
	private static final int REG_COMANDO_ROSCA_2 = 248;

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
		System.out.println("Conectado ao IP " + IP);
		initModbusScan();
		labelTransition.play();
	}

	private void initModbusScan() {

		Integer[] valorEscritaBalanca2 = modbusService.readMultipleRegisterRequest(REG_ESCRITA_BALANCA_2, 1);
		processamentoController.setValorPesagem(valorEscritaBalanca2[0].toString());

		scanIO = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				readMultiplePoints(slot);
				slot++;
				if (slot == 9)
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

		HistoricoProperty.selectedFormulaProperty().addListener(new ChangeListener<Formula>() {
			@Override
			public void changed(ObservableValue<? extends Formula> observable, Formula oldValue, Formula newValue) {
				cadastrosController.saveHistorico(newValue);
			}
		});

		ComandosDosagemProperty.elevadorProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue == true)
					modbusService.writeRegisterRequest(REG_COMANDO_ELEVADOR, 1);
				else
					modbusService.writeRegisterRequest(REG_COMANDO_ELEVADOR, 0);
			}
		});

		ComandosDosagemProperty.roscaSuperiorHorarioProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue == true)
					modbusService.writeRegisterRequest(REG_ROSCA_SUP_HORARIO, 1);
				else
					modbusService.writeRegisterRequest(REG_ROSCA_SUP_HORARIO, 0);
			}
		});

		ComandosDosagemProperty.roscaSuperiorAntiHorarioProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue == true)
					modbusService.writeRegisterRequest(REG_ROSCA_SUP_ANTI_HORARIO, 1);
				else
					modbusService.writeRegisterRequest(REG_ROSCA_SUP_ANTI_HORARIO, 0);
			}
		});

		ComandosDosagemProperty.rosca1Property().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue == true)
					modbusService.writeRegisterRequest(REG_COMANDO_ROSCA_1, 1);
				else
					modbusService.writeRegisterRequest(REG_COMANDO_ROSCA_1, 0);
			}
		});

		ComandosDosagemProperty.rosca2Property().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue == true)
					modbusService.writeRegisterRequest(REG_COMANDO_ROSCA_2, 1);
				else
					modbusService.writeRegisterRequest(REG_COMANDO_ROSCA_2, 0);
			}
		});

		DosagemProperty.cancelaDosagemProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				System.out.println("CANCELADO");
				modbusService.writeRegisterRequest(REG_PESO_MATERIA, 0);
				modbusService.writeRegisterRequest(REG_PESO_TOTAL_CARGA, 0);
				modbusService.writeRegisterRequest(REG_SILO, 0);
				modbusService.writeRegisterRequest(REG_QUANTIDADE_MATERIA, 0);
				dosagemController.updateSiloCarga("00");
				dosagemIndex = 0;
			}
		});

		DosagemProperty.startFormulaProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				formula = DosagemProperty.getSelectedFormula();
				if (dosagemIndex == 0) {
					/*
					 * System.out
					 * .println(formula.getQuantidades().get(dosagemIndex).getMateriaQuantidade().
					 * getNomeMateria() + ": " + formula.getSilos().get(dosagemIndex).getSilo() +
					 * " - " + formula.getQuantidades().get(dosagemIndex).getPeso());
					 */
					// Peso da materia-prima
					modbusService.writeRegisterRequest(REG_PESO_MATERIA,
							formula.getQuantidades().get(dosagemIndex).getPeso().intValue());
					// Total da carga
					modbusService.writeRegisterRequest(REG_PESO_TOTAL_CARGA, formula.getPesoTotal().intValue());
					// Silo
					modbusService.writeRegisterRequest(REG_SILO,
							formula.getSilos().get(dosagemIndex).getId().intValue());
					// Qtde de materias
					modbusService.writeRegisterRequest(REG_QUANTIDADE_MATERIA, formula.getQuantidades().size());
					dosagemIndex++;
				}
			}
		});

		ProcessamentoProperty.writeBalanca2Property().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				modbusService.writeRegisterRequest(REG_ESCRITA_BALANCA_2, newValue);
				processamentoController.setValorPesagem(newValue.toString());
			}
		});

		StatusLabelProperty.statusLabelProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				lblInfo.setText(newValue);
			}
		});

		// INPUT CANAL 0
		for (int i = 0; i < Input0.POINTS; i++) {
			Input0.getListeners().add(new SimpleBooleanProperty());
		}
		Input0.getListeners().forEach(listener -> {
			listener.addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					dosagemController.updateIOPoints(0, Input0.getListeners().indexOf(listener), newValue);
					processamentoController.updateIOPoints(0, Input0.getListeners().indexOf(listener), newValue);
				}
			});
		});

		// INPUT CANAL 1
		for (int i = 0; i < Input1.points; i++) {
			Input1.getListeners().add(new SimpleBooleanProperty());
		}
		Input1.getListeners().forEach(listener -> {
			listener.addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					dosagemController.updateIOPoints(1, Input1.getListeners().indexOf(listener), newValue);
				}
			});
		});

		// INPUT CANAL 2
		for (int i = 0; i < Input2.points; i++) {
			Input2.getListeners().add(new SimpleBooleanProperty());
		}
		Input2.getListeners().forEach(listener -> {
			listener.addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					dosagemController.updateIOPoints(2, Input2.getListeners().indexOf(listener), newValue);
				}
			});
		});

		// OUTPUT CANAL 0
		for (int i = 0; i < Output0.points; i++) {
			Output0.getListeners().add(new SimpleBooleanProperty());
		}
		Output0.getListeners().forEach(listener -> {
			listener.addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					// dosagemController.updateIOPoints(Output0.getListeners().indexOf(listener),
					// newValue);
				}
			});
		});

		// OUTPUT CANAL 3
		for (int i = 0; i < Output3.points; i++) {
			Output3.getListeners().add(new SimpleBooleanProperty());
		}
		Output3.getListeners().forEach(listener -> {
			listener.addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					// dosagemController.updateIOPoints(Output3.getListeners().indexOf(listener),
					// newValue);
				}
			});
		});

		// OUTPUT CANAL 4
		for (int i = 0; i < Output4.points; i++) {
			Output4.getListeners().add(new SimpleBooleanProperty());
		}
		Output4.getListeners().forEach(listener -> {
			listener.addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					// dosagemController.updateIOPoints(Output4.getListeners().indexOf(listener),
					// newValue);
				}
			});
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
			for (int i = 0; i < points.length; i++) {
				Input0.getListeners().get(i).setValue(points[i]);
			}
			break;
		case 1:
			points = modbusService.readMultiplePoints(24, 16);
			for (int i = 0; i < points.length; i++) {
				Input1.getListeners().get(i).setValue(points[i]);
			}
			break;
		case 2:
			points = modbusService.readMultiplePoints(40, 16);
			for (int i = 0; i < points.length; i++) {
				Input2.getListeners().get(i).setValue(points[i]);
			}
			break;
		case 3:
			points = modbusService.readMultiplePoints(100, 16);
			for (int i = 0; i < points.length; i++) {
				Output0.getListeners().get(i).setValue(points[i]);
			}
			break;
		case 4:
			points = modbusService.readMultiplePoints(116, 16);
			for (int i = 0; i < points.length; i++) {
				Output3.getListeners().get(i).setValue(points[i]);
			}
			break;
		case 5:
			points = modbusService.readMultiplePoints(132, 16);
			for (int i = 0; i < points.length; i++) {
				Output4.getListeners().get(i).setValue(points[i]);
			}
			break;
		case 6:
			Integer[] valuesBalanca1 = modbusService.readMultipleRegisterRequest(REG_LEITURA_BALANCA_1, 2);
			if (valuesBalanca1.length > 0) {
				dosagemController.updateBalanca1(valuesBalanca1[0]);
			}
			break;
		case 7:
			Integer[] valuesBalanca2 = modbusService.readMultipleRegisterRequest(REG_LEITURA_BALANCA_2, 2);
			if (valuesBalanca2.length > 0) {
				processamentoController.updateBalanca2(valuesBalanca2[0]);
			}
			break;
		case 8:
			//dosagemController.updateSiloCarga(formula.getSilos().get(dosagemIndex).getId().toString());
			Integer[] finalize = modbusService.readMultipleRegisterRequest(REG_FLAG_FINALIZADO, 1);
			if (finalize[0] > 0 && dosagemIndex < formula.getQuantidades().size()) {
				modbusService.writeRegisterRequest(REG_PESO_MATERIA,
						formula.getQuantidades().get(dosagemIndex).getPeso().intValue());
				modbusService.writeRegisterRequest(REG_SILO, formula.getSilos().get(dosagemIndex).getId().intValue());
				modbusService.writeRegisterRequest(REG_FLAG_FINALIZADO, 0);
				dosagemIndex++;
				return;
			}
			if (finalize[0] > 0 && dosagemIndex == formula.getQuantidades().size()) {
				StatusLabelProperty.setStatusLabel("Dosagem finalizada");
				System.out.println("FIM!");
				modbusService.writeRegisterRequest(REG_FLAG_FINALIZADO, 0);
				dosagemController.updateSiloCarga("00");
				dosagemIndex = 0;
			}
			break;
		}
	}

}
