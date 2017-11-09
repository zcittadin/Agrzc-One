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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroMateriaController implements Initializable {

	@FXML
	private TextField txtNome;

	private static MateriaDAO materiaDAO = new MateriaDAO();
	private Materia materia;
	private Scene parentScene;

	private static String toastMsg;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void setContext(Materia materia, Scene parentScene) {
		this.materia = materia;
		this.parentScene = parentScene;
		if (materia != null)
			txtNome.setText(materia.getNomeMateria());
	}

	@FXML
	private void saveMateria(ActionEvent event) {
		if (txtNome.getText() == null || "".equals(txtNome.getText().trim())) {
			AlertUtil.makeWarning("Atenção", "Informe um nome para o cadastro da matéria-prima.");
			txtNome.requestFocus();
			return;
		}

		Task<Void> saveTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				if (materia == null) {
					materia = new Materia(null, txtNome.getText());
					materiaDAO.saveMateria(materia);
					toastMsg = "Cadastro efetuado com sucesso";
				} else {
					materia.setNomeMateria(txtNome.getText());
					materiaDAO.updateMateria(materia);
					toastMsg = "Matéria-prima atualizada com sucesso";
				}
				return null;
			}
		};

		saveTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				// fetch(false);
				Toast.makeToast((Stage) parentScene.getWindow(), toastMsg);
				Stage stage = (Stage) txtNome.getScene().getWindow();
				stage.close();
			}
		});

		saveTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				// fetch(false);
				AlertUtil.makeError("Erro", "Ocorreu uma falha ao cadastrar a matéria-prima.");
				Stage stage = (Stage) txtNome.getScene().getWindow();
				stage.close();
			}
		});
		Thread t = new Thread(saveTask);
		t.start();
	}

	@FXML
	private void cancel() {
		Stage stage = (Stage) txtNome.getScene().getWindow();
		stage.close();
	}

}
