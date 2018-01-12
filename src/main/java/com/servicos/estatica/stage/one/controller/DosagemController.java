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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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

	private static FormulaDAO formulaDAO = new FormulaDAO();
	private static SiloDAO siloDAO = new SiloDAO();

	private static List<Silo> silos = new ArrayList<Silo>();
	private static ObservableList<Formula> formulas = FXCollections.observableArrayList();

	private static String TOOLTIP_MSG_VAZIO = "O silo está vazio.";
	private static String TOOLTIP_MSG_1;
	private static String TOOLTIP_MSG_2;
	private static String TOOLTIP_MSG_3;
	private static String TOOLTIP_MSG_4;
	private static String TOOLTIP_CSS = "-fx-font-size: 8pt; -fx-font-weight: bold; -fx-font-style: normal; ";

	private Tooltip tooltipSilo1 = new Tooltip(TOOLTIP_MSG_VAZIO);
	private Tooltip tooltipSilo2 = new Tooltip(TOOLTIP_MSG_VAZIO);
	private Tooltip tooltipSilo3 = new Tooltip(TOOLTIP_MSG_VAZIO);
	private Tooltip tooltipSilo4 = new Tooltip(TOOLTIP_MSG_VAZIO);

	private Formula selectedFormula;

	ScreensController myController;

	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rectForm.setFill(Color.TRANSPARENT);
		Tooltip.install(lblSilo1, tooltipSilo1);
		Tooltip.install(lblSilo2, tooltipSilo2);
		Tooltip.install(lblSilo3, tooltipSilo3);
		Tooltip.install(lblSilo4, tooltipSilo4);
		populateComboFormulas();
		verifySilosStatus();
	}

	@FXML
	private void editMateria(Event ev) {
		Silo silo = null;
		if (ev.toString().contains("lblSilo1"))
			silo = siloDAO.findBySilo("Silo 1");
		if (ev.toString().contains("lblSilo2"))
			silo = siloDAO.findBySilo("Silo 2");
		if (ev.toString().contains("lblSilo3"))
			silo = siloDAO.findBySilo("Silo 3");
		if (ev.toString().contains("lblSilo4"))
			silo = siloDAO.findBySilo("Silo 4");
		try {
			Stage stage;
			Parent root;
			stage = new Stage();
			URL url = getClass().getResource("/fxml/SilosMateria.fxml");
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(url);
			fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
			root = (Parent) fxmlloader.load(url.openStream());
			stage.setScene(new Scene(root));
			((SilosMateriaController) fxmlloader.getController()).setContext(silo);
			stage.setTitle("Gerenciar matéria-prima");
			stage.initModality(Modality.APPLICATION_MODAL);
			// stage.initOwner(tblMateria.getScene().getWindow());
			stage.setResizable(Boolean.FALSE);
			stage.showAndWait();
			// findMaterias();
		} catch (IOException e) {
			e.printStackTrace();
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
				silos.forEach(s -> {
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

}
