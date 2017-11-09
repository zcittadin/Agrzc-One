package com.servicos.estatica.stage.one.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.servicos.estatica.stage.one.app.ControlledScreen;
import com.servicos.estatica.stage.one.dao.MateriaDAO;
import com.servicos.estatica.stage.one.model.Materia;
import com.servicos.estatica.stage.one.util.AlertUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@SuppressWarnings("rawtypes")
public class CadastrosController implements Initializable, ControlledScreen {

	@FXML
	private TableView tblMateria;
	@FXML
	private TableColumn colNomeMateria;
	@FXML
	private TableColumn colEditarMateria;
	@FXML
	private TableColumn colExcluirMateria;

	private static ObservableList<Materia> materias = FXCollections.observableArrayList();

	private static MateriaDAO materiaDAO = new MateriaDAO();
	private static Materia materia;

	ScreensController myController;

	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	private void findMaterias() {
		Task<Void> searchTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				materias = FXCollections.observableList((List<Materia>) materiaDAO.findMaterias());
				return null;
			}
		};

		searchTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {

			}
		});

		searchTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				AlertUtil.makeAlert(AlertType.ERROR, "Erro",
						"Ocorreu uma falha ao consultar as matérias-prima existentes.");
			}
		});
		new Thread(searchTask).start();
	}

	@FXML
	private void addMateria() {
		try {
			Stage stage;
			Parent root;
			stage = new Stage();
			URL url = getClass().getResource("/fxml/CadMateria.fxml");
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(url);
			fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
			root = (Parent) fxmlloader.load(url.openStream());
			stage.setScene(new Scene(root));
			((CadastroMateriaController) fxmlloader.getController()).setContext(null, tblMateria.getScene());
			stage.setTitle("Gerenciar matéria-prima");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(tblMateria.getScene().getWindow());
			stage.setResizable(Boolean.FALSE);
			stage.showAndWait();
		} catch (

		IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void editMateria() {
		try {
			Stage stage;
			Parent root;
			stage = new Stage();
			URL url = getClass().getResource("/fxml/CadMateria.fxml");
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(url);
			fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
			root = (Parent) fxmlloader.load(url.openStream());
			stage.setScene(new Scene(root));
			((CadastroMateriaController) fxmlloader.getController()).setContext(materia, tblMateria.getScene());
			stage.setTitle("Gerenciar matéria-prima");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(tblMateria.getScene().getWindow());
			stage.setResizable(Boolean.FALSE);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
