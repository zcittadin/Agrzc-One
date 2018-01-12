package com.servicos.estatica.stage.one.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.servicos.estatica.stage.one.dao.MateriaDAO;
import com.servicos.estatica.stage.one.dao.SiloDAO;
import com.servicos.estatica.stage.one.model.Materia;
import com.servicos.estatica.stage.one.model.Silo;
import com.servicos.estatica.stage.one.util.AlertUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class SilosMateriaController implements Initializable {

	@FXML
	ComboBox<Materia> comboMateria;
	@FXML
	CheckBox chkVazio;

	private static ObservableList<Materia> materias = FXCollections.observableArrayList();

	private Silo silo;
	private Materia selectedMateria;

	private static SiloDAO siloDAO = new SiloDAO();
	private static MateriaDAO materiaDAO = new MateriaDAO();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		populateComboMaterias();
	}

	public void setContext(Silo silo) {
		this.silo = silo;
		if (silo.getMateria() != null) {
			comboMateria.setValue(silo.getMateria());
			chkVazio.setSelected(false);
		} else {
			comboMateria.setDisable(true);
			chkVazio.setSelected(true);
		}
	}

	public void populateComboMaterias() {
		materias = FXCollections.observableList((List<Materia>) materiaDAO.findMaterias());
		comboMateria.setItems(materias);
		comboMateria.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Materia>() {
			public void changed(ObservableValue<? extends Materia> observable, Materia oldValue, Materia newValue) {
				selectedMateria = newValue;
			}
		});
	}

	@FXML
	private void toggleVazio() {
		if (chkVazio.isSelected()) {
			comboMateria.setDisable(true);
			selectedMateria = null;
		} else {
			comboMateria.setDisable(false);
			selectedMateria = comboMateria.getValue();
		}
	}

	@FXML
	private void cancel() {
		Stage stage = (Stage) comboMateria.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void saveSilo() {
		if (!chkVazio.isSelected() && selectedMateria == null) {
			AlertUtil.makeWarning("Atenção", "Selecione uma matéria-prima ou marque o silo como vazio.");
			return;
		}
		silo.setMateria(selectedMateria);
		Task<Void> saveTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				siloDAO.updateSilo(silo);
				return null;
			}
		};

		saveTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				Stage stage = (Stage) comboMateria.getScene().getWindow();
				stage.close();
			}
		});
		saveTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				AlertUtil.makeError("Erro", "Ocorreu uma falha ao salvar a matéria-prima do silo.");
			}
		});
		new Thread(saveTask).start();

	}

}
