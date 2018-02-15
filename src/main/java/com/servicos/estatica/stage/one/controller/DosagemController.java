package com.servicos.estatica.stage.one.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import com.servicos.estatica.stage.one.app.ControlledScreen;
import com.servicos.estatica.stage.one.dao.FormulaDAO;
import com.servicos.estatica.stage.one.dao.HistoricoDAO;
import com.servicos.estatica.stage.one.dao.SiloDAO;
import com.servicos.estatica.stage.one.dto.FormulaDosagemDTO;
import com.servicos.estatica.stage.one.model.Formula;
import com.servicos.estatica.stage.one.model.Historico;
import com.servicos.estatica.stage.one.model.Silo;
import com.servicos.estatica.stage.one.shared.ComandosDosagemProperty;
import com.servicos.estatica.stage.one.shared.DosagemProperty;
import com.servicos.estatica.stage.one.shared.HistoricoProperty;
import com.servicos.estatica.stage.one.shared.StatusLabelProperty;
import com.servicos.estatica.stage.one.util.AlertUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DosagemController implements Initializable, ControlledScreen {

	@FXML
	private Rectangle rectForm;
	@FXML
	private Rectangle rectForm1;
	@FXML
	private ComboBox<Formula> comboFormulas;
	@FXML
	private Label lblSilo1;
	@FXML
	private Label lblSilo2;
	@FXML
	private Label lblSilo3;
	@FXML
	private Label lblSilo4;
	@FXML
	private Label lblSilo5;
	@FXML
	private Label lblSilo6;
	@FXML
	private Label lblSilo7;
	@FXML
	private Label lblSilo8;
	@FXML
	private Label lblSilo9;
	@FXML
	private Label lblSilo10;
	@FXML
	private Label lblSiloCarga;
	@FXML
	private Label lblBalanca;
	@FXML
	private Sphere I0_00;
	@FXML
	private Sphere I0_01;
	@FXML
	private Sphere I0_02;
	@FXML
	private Sphere I0_03;
	@FXML
	private Sphere I0_04;
	@FXML
	private Sphere I0_05;
	@FXML
	private Sphere I0_06;
	@FXML
	private Sphere I0_07;
	@FXML
	private Sphere I0_09;
	@FXML
	private Sphere I0_11;
	@FXML
	private Sphere I1_04;
	@FXML
	private Sphere I1_05;
	@FXML
	private Sphere I1_06;
	@FXML
	private Sphere I1_07;
	@FXML
	private CheckBox chkSensores;
	@FXML
	private ImageView switchElevador;
	@FXML
	private ImageView switchRosca1;
	@FXML
	private ImageView switchRosca2;
	@FXML
	private ImageView imgDirRoscaRight;
	@FXML
	private ImageView imgDirRoscaLeft;

	private static FormulaDAO formulaDAO = new FormulaDAO();
	private static SiloDAO siloDAO = new SiloDAO();
	private static HistoricoDAO historicoDAO = new HistoricoDAO();

	private static List<Silo> silos = new ArrayList<Silo>();
	private static ObservableList<Formula> formulas = FXCollections.observableArrayList();

	private static String TOOLTIP_MSG_VAZIO = "O silo est� vazio.";
	private static String TOOLTIP_CSS = "-fx-font-size: 12pt; -fx-font-weight: bold; -fx-font-style: normal; -fx-background-color: green;";

	private Tooltip tooltipSilo1 = new Tooltip(TOOLTIP_MSG_VAZIO);
	private Tooltip tooltipSilo2 = new Tooltip(TOOLTIP_MSG_VAZIO);
	private Tooltip tooltipSilo3 = new Tooltip(TOOLTIP_MSG_VAZIO);
	private Tooltip tooltipSilo4 = new Tooltip(TOOLTIP_MSG_VAZIO);
	private Tooltip tooltipSilo5 = new Tooltip(TOOLTIP_MSG_VAZIO);
	private Tooltip tooltipSilo6 = new Tooltip(TOOLTIP_MSG_VAZIO);
	private Tooltip tooltipSilo7 = new Tooltip(TOOLTIP_MSG_VAZIO);
	private Tooltip tooltipSilo8 = new Tooltip(TOOLTIP_MSG_VAZIO);
	private Tooltip tooltipSilo9 = new Tooltip(TOOLTIP_MSG_VAZIO);
	private Tooltip tooltipSilo10 = new Tooltip(TOOLTIP_MSG_VAZIO);
	private Tooltip tooltipBalanca = new Tooltip("Peso atual (Kg)");

	private final PhongMaterial greenMaterial = new PhongMaterial();
	private final PhongMaterial grayMaterial = new PhongMaterial();

	private static Map<Integer, Sphere> input_0 = new HashMap<>();
	private static Map<Integer, Sphere> input_1 = new HashMap<>();

	private Boolean startDosagemFlag = false;
	private Boolean cancelaDosagemFlag = false;
	private Boolean comandoElevador = false;
	private Boolean comandoRosca1 = false;
	private Boolean comandoRosca2 = false;
	private Boolean roscaSuperiorHorario = false;
	private Boolean roscaSuperiorAntiHorario = false;
	private Boolean siloVazio = false;

	private Formula selectedFormula;

	ScreensController myController;

	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initIOPoints();
		initComponents();
		populateComboFormulas();
		verifySilosStatus();
		initColors();
	}

	@FXML
	private void editMateria(Event event) {
		Silo silo = null;
		if (event.toString().contains("lblSilo1"))
			silo = siloDAO.findBySilo("Silo 1");
		if (event.toString().contains("lblSilo2"))
			silo = siloDAO.findBySilo("Silo 2");
		if (event.toString().contains("lblSilo3"))
			silo = siloDAO.findBySilo("Silo 3");
		if (event.toString().contains("lblSilo4"))
			silo = siloDAO.findBySilo("Silo 4");
		if (event.toString().contains("lblSilo5"))
			silo = siloDAO.findBySilo("Silo 5");
		if (event.toString().contains("lblSilo6"))
			silo = siloDAO.findBySilo("Silo 6");
		if (event.toString().contains("lblSilo7"))
			silo = siloDAO.findBySilo("Silo 7");
		if (event.toString().contains("lblSilo8"))
			silo = siloDAO.findBySilo("Silo 8");
		if (event.toString().contains("lblSilo9"))
			silo = siloDAO.findBySilo("Silo 9");
		if (event.toString().contains("lblSilo10"))
			silo = siloDAO.findBySilo("Silo 10");
		try {
			Stage stage;
			Parent root;
			stage = new Stage();
			URL url = getClass().getResource("/com/servicos/estatica/stage/one/app/SilosMateria.fxml");
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(url);
			fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
			root = (Parent) fxmlloader.load(url.openStream());
			stage.setScene(new Scene(root));
			((SilosMateriaController) fxmlloader.getController()).setContext(silo);
			stage.setTitle("Gerenciar mat�ria-prima");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(comboFormulas.getScene().getWindow());
			stage.setResizable(Boolean.FALSE);
			stage.showAndWait();
			verifySilosStatus();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void startDosagem() {
		if (selectedFormula == null) {
			AlertUtil.makeWarning("Aten��o", "Selecione uma formula��o para iniciar a dosagem.");
			return;
		}
		siloVazio = false;
		List<Silo> silos = new ArrayList<>();
		Task<Void> searchSiloTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				selectedFormula.getQuantidades().forEach(qtd -> {
					Silo silo = siloDAO.findByMateria(qtd.getMateriaQuantidade());
					if (silo == null) {
						siloVazio = true;
					} else {
						silos.add(silo);
					}
				});
				return null;
			}
		};
		searchSiloTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				if (siloVazio == false) {
					FormulaDosagemDTO dto = new FormulaDosagemDTO(selectedFormula.getPesoTotal(),
							selectedFormula.getQuantidades(), silos);
					DosagemProperty.setSelectedFormula(dto);
					DosagemProperty.setStartFormula(!startDosagemFlag);
					startDosagemFlag = !startDosagemFlag;
					StatusLabelProperty.setStatusLabel("Em ciclo de dosagem");
				} else {
					AlertUtil.makeWarning("Aten��o",
							"Uma ou mais mat�rias-primas que fazem parte da formula��o selecionada n�o consta em nenhum silo.\n"
									+ "Favor revisar a formula��o ou indicar o silo que cont�m a(s) mat�ria(s)-prima.");
				}
			}
		});
		new Thread(searchSiloTask).start();
	}

	@FXML
	private void cancelarDosagem() {
		Optional<ButtonType> result = AlertUtil.makeConfirm("Confirmar cancelamento",
				"Tem certeza que deseja cancelar o ciclo de dosagem em andamento?");
		if (result.get() == ButtonType.OK) {
			DosagemProperty.setCancelaDosagem(!cancelaDosagemFlag);
			cancelaDosagemFlag = !cancelaDosagemFlag;
			StatusLabelProperty.setStatusLabel("Sistema em espera");
			updateSiloCarga("00");
		}
	}

	@FXML
	private void startDescarga() {
		if (selectedFormula == null) {
			AlertUtil.makeWarning("Aten��o", "N�o h� nenhuma carga na balan�a.");
			return;
		}
		HistoricoProperty.selectedFormulaProperty().set(selectedFormula);
		StatusLabelProperty.setStatusLabel("Em processo de descarga");
		Historico h = new Historico(null, new Date(), selectedFormula.getPesoTotal(), selectedFormula);
		historicoDAO.saveHistorico(h);
	}

	@FXML
	private void toggleElevador() {
		if (comandoElevador == false) {
			comandoElevador = true;
			switchElevador.setImage(new Image("com/servicos/estatica/stage/one/img/switch_on.png"));
		} else {
			comandoElevador = false;
			switchElevador.setImage(new Image("com/servicos/estatica/stage/one/img/switch_off.png"));
		}
		ComandosDosagemProperty.setOnOffElevador(comandoElevador);
	}

	@FXML
	private void toggleRosca1() {
		if (comandoRosca1 == false) {
			comandoRosca1 = true;
			switchRosca1.setImage(new Image("com/servicos/estatica/stage/one/img/switch_on.png"));
		} else {
			comandoRosca1 = false;
			switchRosca1.setImage(new Image("com/servicos/estatica/stage/one/img/switch_off.png"));
		}
		ComandosDosagemProperty.setRosca1(comandoRosca1);
	}

	@FXML
	private void toggleRosca2() {
		if (comandoRosca2 == false) {
			comandoRosca2 = true;
			switchRosca2.setImage(new Image("com/servicos/estatica/stage/one/img/switch_on.png"));
		} else {
			comandoRosca2 = false;
			switchRosca2.setImage(new Image("com/servicos/estatica/stage/one/img/switch_off.png"));
		}
		ComandosDosagemProperty.setRosca2(comandoRosca2);
	}

	@FXML
	private void roscaSuperiorHorario() {
		if (roscaSuperiorHorario == false) {
			roscaSuperiorAntiHorario = false;
			roscaSuperiorHorario = true;
			imgDirRoscaLeft.setVisible(true);
			imgDirRoscaRight.setVisible(false);
		}
		ComandosDosagemProperty.setRoscaSuperiorHorario(true);
		ComandosDosagemProperty.setRoscaSuperiorAntiHorario(false);
	}

	@FXML
	private void roscaSuperiorAntiHorario() {
		if (roscaSuperiorAntiHorario == false) {
			roscaSuperiorHorario = false;
			roscaSuperiorAntiHorario = true;
			imgDirRoscaRight.setVisible(true);
			imgDirRoscaLeft.setVisible(false);
		}
		ComandosDosagemProperty.setRoscaSuperiorAntiHorario(true);
		ComandosDosagemProperty.setRoscaSuperiorHorario(false);
	}

	@FXML
	private void stopRoscaSuperior() {
		roscaSuperiorAntiHorario = false;
		roscaSuperiorHorario = false;
		imgDirRoscaRight.setVisible(false);
		imgDirRoscaLeft.setVisible(false);
		ComandosDosagemProperty.setRoscaSuperiorHorario(false);
		ComandosDosagemProperty.setRoscaSuperiorAntiHorario(false);
	}

	public void updateIOPoints(int channel, int point, Boolean b) {
		Sphere sph = null;
		if (channel == 0)
			sph = input_0.get(point);
		if (channel == 1)
			sph = input_1.get(point);
		if (sph == null)
			return;
		if (b)
			sph.setMaterial(greenMaterial);
		else
			sph.setMaterial(grayMaterial);
	}

	public void updateBalanca1(Integer value) {
		String strValue = value.toString();
		if (strValue.length() < 2)
			strValue = "0".concat(strValue);
		if (strValue.length() < 3)
			strValue = "0".concat(strValue);
		if (strValue.length() < 4)
			strValue = "0".concat(strValue);
		lblBalanca.setText(strValue);
	}

	public void updateSiloCarga(String numeroSilo) {
		if (numeroSilo.length() < 2)
			numeroSilo = "0".concat(numeroSilo);
		lblSiloCarga.setText(numeroSilo);
	}

	public void populateComboFormulas() {
		formulas = FXCollections.observableList((List<Formula>) formulaDAO.findFormulas());
		comboFormulas.setItems(formulas);
		comboFormulas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Formula>() {
			public void changed(ObservableValue<? extends Formula> observable, Formula oldValue, Formula newValue) {
				selectedFormula = newValue;
			}
		});
	}

	public void verifySilosStatus() {
		Task<Void> searchTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				silos = siloDAO.getSilosStatus();
				return null;
			}
		};

		searchTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				silos.stream().forEach(s -> {
					switch (s.getSilo()) {
					case "Silo 1":
						if (s.getMateria() != null) {
							lblSilo1.setText(s.getMateria().getNomeMateria());
							tooltipSilo1.setText(s.getMateria().getNomeMateria());
						} else {
							lblSilo1.setText("Vazio");
							tooltipSilo1.setText(TOOLTIP_MSG_VAZIO);
						}
						break;
					case "Silo 2":
						if (s.getMateria() != null) {
							lblSilo2.setText(s.getMateria().getNomeMateria());
							tooltipSilo2.setText(s.getMateria().getNomeMateria());
						} else {
							lblSilo2.setText("Vazio");
							tooltipSilo2.setText(TOOLTIP_MSG_VAZIO);
						}
						break;
					case "Silo 3":
						if (s.getMateria() != null) {
							lblSilo3.setText(s.getMateria().getNomeMateria());
							tooltipSilo3.setText(s.getMateria().getNomeMateria());
						} else {
							lblSilo3.setText("Vazio");
							tooltipSilo3.setText(TOOLTIP_MSG_VAZIO);
						}
						break;
					case "Silo 4":
						if (s.getMateria() != null) {
							lblSilo4.setText(s.getMateria().getNomeMateria());
							tooltipSilo4.setText(s.getMateria().getNomeMateria());
						} else {
							lblSilo4.setText("Vazio");
							tooltipSilo4.setText(TOOLTIP_MSG_VAZIO);
						}
						break;
					case "Silo 5":
						if (s.getMateria() != null) {
							lblSilo5.setText(s.getMateria().getNomeMateria());
							tooltipSilo5.setText(s.getMateria().getNomeMateria());
						} else {
							lblSilo5.setText("Vazio");
							tooltipSilo5.setText(TOOLTIP_MSG_VAZIO);
						}
						break;
					case "Silo 6":
						if (s.getMateria() != null) {
							lblSilo6.setText(s.getMateria().getNomeMateria());
							tooltipSilo6.setText(s.getMateria().getNomeMateria());
						} else {
							lblSilo6.setText("Vazio");
							tooltipSilo6.setText(TOOLTIP_MSG_VAZIO);
						}
						break;
					case "Silo 7":
						if (s.getMateria() != null) {
							lblSilo7.setText(s.getMateria().getNomeMateria());
							tooltipSilo7.setText(s.getMateria().getNomeMateria());
						} else {
							lblSilo7.setText("Vazio");
							tooltipSilo7.setText(TOOLTIP_MSG_VAZIO);
						}
						break;
					case "Silo 8":
						if (s.getMateria() != null) {
							lblSilo8.setText(s.getMateria().getNomeMateria());
							tooltipSilo8.setText(s.getMateria().getNomeMateria());
						} else {
							lblSilo8.setText("Vazio");
							tooltipSilo8.setText(TOOLTIP_MSG_VAZIO);
						}
						break;
					case "Silo 9":
						if (s.getMateria() != null) {
							lblSilo9.setText(s.getMateria().getNomeMateria());
							tooltipSilo9.setText(s.getMateria().getNomeMateria());
						} else {
							lblSilo9.setText("Vazio");
							tooltipSilo9.setText(TOOLTIP_MSG_VAZIO);
						}
						break;
					case "Silo 10":
						if (s.getMateria() != null) {
							lblSilo10.setText(s.getMateria().getNomeMateria());
							tooltipSilo10.setText(s.getMateria().getNomeMateria());
						} else {
							lblSilo10.setText("Vazio");
							tooltipSilo10.setText(TOOLTIP_MSG_VAZIO);
						}
						break;
					}
				});
			}
		});
		searchTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				AlertUtil.makeError("Erro", "Ocorreu uma falha ao consultar as a situa��o dos silos.");
			}
		});
		new Thread(searchTask).start();
	}

	private void initIOPoints() {
		input_0.put(0, I0_00);
		input_0.put(1, I0_01);
		input_0.put(2, I0_02);
		input_0.put(3, I0_03);
		input_0.put(4, I0_04);
		input_0.put(5, I0_05);
		input_0.put(6, I0_06);
		input_0.put(7, I0_07);
		input_0.put(8, null);
		input_0.put(9, I0_09);
		input_0.put(10, null);
		input_0.put(11, I0_11);

		input_1.put(4, I1_04);
		input_1.put(5, I1_05);
		input_1.put(6, I1_06);
		input_1.put(7, I1_07);

	}

	private void initComponents() {
		rectForm.setFill(Color.TRANSPARENT);
		rectForm1.setFill(Color.TRANSPARENT);
		tooltipSilo1.setStyle(TOOLTIP_CSS);
		tooltipSilo2.setStyle(TOOLTIP_CSS);
		tooltipSilo3.setStyle(TOOLTIP_CSS);
		tooltipSilo4.setStyle(TOOLTIP_CSS);
		tooltipSilo5.setStyle(TOOLTIP_CSS);
		tooltipSilo6.setStyle(TOOLTIP_CSS);
		tooltipSilo7.setStyle(TOOLTIP_CSS);
		tooltipSilo8.setStyle(TOOLTIP_CSS);
		tooltipSilo9.setStyle(TOOLTIP_CSS);
		tooltipSilo10.setStyle(TOOLTIP_CSS);
		tooltipBalanca.setStyle(TOOLTIP_CSS);
		Tooltip.install(lblSilo1, tooltipSilo1);
		Tooltip.install(lblSilo2, tooltipSilo2);
		Tooltip.install(lblSilo3, tooltipSilo3);
		Tooltip.install(lblSilo4, tooltipSilo4);
		Tooltip.install(lblSilo5, tooltipSilo5);
		Tooltip.install(lblSilo6, tooltipSilo6);
		Tooltip.install(lblSilo7, tooltipSilo7);
		Tooltip.install(lblSilo8, tooltipSilo8);
		Tooltip.install(lblSilo9, tooltipSilo9);
		Tooltip.install(lblSilo10, tooltipSilo10);
		Tooltip.install(lblBalanca, tooltipBalanca);
		switchElevador.setImage(new Image("com/servicos/estatica/stage/one/img/switch_off.png"));
		switchRosca1.setImage(new Image("com/servicos/estatica/stage/one/img/switch_off.png"));
		switchRosca2.setImage(new Image("com/servicos/estatica/stage/one/img/switch_off.png"));
		imgDirRoscaLeft.setVisible(false);
		imgDirRoscaRight.setVisible(false);
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
