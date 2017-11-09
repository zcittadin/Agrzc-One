package com.servicos.estatica.stage.one.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.servicos.estatica.stage.one.dao.MateriaDAO;
import com.servicos.estatica.stage.one.model.Materia;
import com.servicos.estatica.stage.one.util.AlertUtil;
import com.servicos.estatica.stage.one.util.Toast;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroMateriaController implements Initializable {

	@FXML
	private TextField txtNome;

	private static MateriaDAO materiaDAO = new MateriaDAO();
	private Materia materia;
	private Scene parentScene;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void setContext(Materia materia, Scene parentScene) {
		this.materia = materia;
		this.parentScene = parentScene;
	}

	@FXML
	private void saveMateria(ActionEvent event) {
		if (txtNome.getText() == null || "".equals(txtNome.getText().trim())) {
			AlertUtil.makeAlert(AlertType.WARNING, "Atenção", "Informe um nome para o cadastro da matéria-prima.");
			txtNome.requestFocus();
			return;
		}

		Task<Void> saveTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				if (materia == null) {
					materia = new Materia(null, txtNome.getText());
					materiaDAO.saveMateria(materia);
				} else {
					materia.setNomeMateria(txtNome.getText());
					materiaDAO.updateMateria(materia);
				}
				return null;
			}
		};

		saveTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				// fetch(false);
				makeToast("Matéria-prima cadastrada com sucesso.");
				Stage stage = (Stage) txtNome.getScene().getWindow();
				stage.close();
			}
		});

		saveTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				// fetch(false);
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erro");
				alert.setHeaderText("Ocorreu uma falha ao cadastrar a matéria-prima.");
				alert.showAndWait();
				Stage stage = (Stage) txtNome.getScene().getWindow();
				stage.close();
			}
		});
		Thread t = new Thread(saveTask);
		t.start();
	}
	
	private void makeToast(String message) {
		String toastMsg = message;
		int toastMsgTime = 5000;
		int fadeInTime = 600;
		int fadeOutTime = 600;
		Stage stage = (Stage) parentScene.getWindow();
		Toast.makeToast(stage, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
	}

}
