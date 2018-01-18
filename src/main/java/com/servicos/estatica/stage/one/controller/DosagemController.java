package com.servicos.estatica.stage.one.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.servicos.estatica.stage.one.app.ControlledScreen;
import com.servicos.estatica.stage.one.dao.FormulaDAO;
import com.servicos.estatica.stage.one.dao.SiloDAO;
import com.servicos.estatica.stage.one.model.Formula;
import com.servicos.estatica.stage.one.model.Silo;
import com.servicos.estatica.stage.one.shared.HistoricoProperty;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
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

	private static FormulaDAO formulaDAO = new FormulaDAO();
	private static SiloDAO siloDAO = new SiloDAO();

	private static List<Silo> silos = new ArrayList<Silo>();
	private static ObservableList<Formula> formulas = FXCollections.observableArrayList();

	private static String TOOLTIP_MSG_VAZIO = "O silo está vazio.";
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
			stage.setTitle("Gerenciar matéria-prima");
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
			AlertUtil.makeWarning("Atenção", "Selecione uma formulação para iniciar a dosagem.");
			return;
		}
	}

	@FXML
	private void startDescarga() {
		if (selectedFormula == null) {
			AlertUtil.makeWarning("Atenção", "Não há nenhuma carga na balança.");
			return;
		}
		HistoricoProperty.selectedFormulaProperty().set(selectedFormula);
		// Historico h = new Historico(null, new Date(), selectedFormula.getPesoTotal(),
		// selectedFormula);
		// historicoDAO.saveHistorico(h);
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

	public void updateBalanca(Integer value) {
		String strValue = value.toString();
		if (strValue.length() < 2)
			strValue = "0".concat(strValue);
		if (strValue.length() < 3)
			strValue = "0".concat(strValue);
		if (strValue.length() < 4)
			strValue = "0".concat(strValue);
		lblBalanca.setText(strValue);
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

				AlertUtil.makeError("Erro", "Ocorreu uma falha ao consultar as a situação dos silos.");
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
