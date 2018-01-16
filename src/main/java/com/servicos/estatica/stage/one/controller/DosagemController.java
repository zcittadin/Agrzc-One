package com.servicos.estatica.stage.one.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.servicos.estatica.stage.one.app.ControlledScreen;
import com.servicos.estatica.stage.one.dao.FormulaDAO;
import com.servicos.estatica.stage.one.dao.SiloDAO;
import com.servicos.estatica.stage.one.model.Formula;
import com.servicos.estatica.stage.one.model.Silo;
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
	private Sphere sensorAlto1;
	@FXML
	private Sphere sensorAlto2;
	@FXML
	private Sphere sensorAlto3;
	@FXML
	private Sphere sensorAlto4;
	@FXML
	private Sphere sensorBaixo1;
	@FXML
	private Sphere sensorBaixo2;
	@FXML
	private Sphere sensorBaixo3;
	@FXML
	private Sphere sensorBaixo4;
	@FXML
	private CheckBox chkSensores;

	private static FormulaDAO formulaDAO = new FormulaDAO();
	private static SiloDAO siloDAO = new SiloDAO();

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

	private final PhongMaterial greenMaterial = new PhongMaterial();
	private final PhongMaterial grayMaterial = new PhongMaterial();

	private Formula selectedFormula;

	ScreensController myController;

	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rectForm.setFill(Color.TRANSPARENT);
		tooltipSilo1.setStyle(TOOLTIP_CSS);
		tooltipSilo2.setStyle(TOOLTIP_CSS);
		tooltipSilo3.setStyle(TOOLTIP_CSS);
		tooltipSilo4.setStyle(TOOLTIP_CSS);
		tooltipSilo5.setStyle(TOOLTIP_CSS);
		tooltipSilo6.setStyle(TOOLTIP_CSS);
		Tooltip.install(lblSilo1, tooltipSilo1);
		Tooltip.install(lblSilo2, tooltipSilo2);
		Tooltip.install(lblSilo3, tooltipSilo3);
		Tooltip.install(lblSilo4, tooltipSilo4);
		Tooltip.install(lblSilo5, tooltipSilo5);
		Tooltip.install(lblSilo6, tooltipSilo6);
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
	private void nivel() {
		if (chkSensores.isSelected()) {
			sensorAlto1.setMaterial(greenMaterial);
			sensorAlto2.setMaterial(greenMaterial);
			sensorAlto3.setMaterial(greenMaterial);
			sensorAlto4.setMaterial(greenMaterial);
			sensorBaixo1.setMaterial(greenMaterial);
			sensorBaixo2.setMaterial(greenMaterial);
			sensorBaixo3.setMaterial(greenMaterial);
			sensorBaixo4.setMaterial(greenMaterial);
		} else {
			sensorAlto1.setMaterial(grayMaterial);
			sensorAlto2.setMaterial(grayMaterial);
			sensorAlto3.setMaterial(grayMaterial);
			sensorAlto4.setMaterial(grayMaterial);
			sensorBaixo1.setMaterial(grayMaterial);
			sensorBaixo2.setMaterial(grayMaterial);
			sensorBaixo3.setMaterial(grayMaterial);
			sensorBaixo4.setMaterial(grayMaterial);
		}
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

	private void initColors() {
		grayMaterial.setDiffuseColor(Color.DARKGRAY);
		grayMaterial.setSpecularColor(Color.GREY);
		grayMaterial.setSpecularPower(0.95);
		greenMaterial.setDiffuseColor(Color.DARKGREEN);
		greenMaterial.setSpecularColor(Color.GREEN);
		greenMaterial.setSpecularPower(0.95);
	}

}
