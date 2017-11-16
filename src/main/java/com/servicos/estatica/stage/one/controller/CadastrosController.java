package com.servicos.estatica.stage.one.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.servicos.estatica.stage.one.app.ControlledScreen;
import com.servicos.estatica.stage.one.dao.FormulaDAO;
import com.servicos.estatica.stage.one.dao.MateriaDAO;
import com.servicos.estatica.stage.one.dao.QuantidadeDAO;
import com.servicos.estatica.stage.one.model.Formula;
import com.servicos.estatica.stage.one.model.Materia;
import com.servicos.estatica.stage.one.model.Quantidade;
import com.servicos.estatica.stage.one.util.AlertUtil;
import com.servicos.estatica.stage.one.util.Toast;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

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
	@FXML
	private ProgressIndicator progMaterias;
	@FXML
	private TableView tblFormula;
	@FXML
	private TableColumn colNomeFormula;
	@FXML
	private TableColumn colPesoFormula;
	@FXML
	private TableColumn colEditarFormula;
	@FXML
	private TableColumn colExcluirFormula;
	@FXML
	private ProgressIndicator progFormulas;
	@FXML
	private Button btAddMateria;
	@FXML
	private Button btAddFormula;

	private static ObservableList<Materia> materias = FXCollections.observableArrayList();
	private static ObservableList<Formula> formulas = FXCollections.observableArrayList();

	private static Double pesoToRecalc;

	private static MateriaDAO materiaDAO = new MateriaDAO();
	private static FormulaDAO formulaDAO = new FormulaDAO();
	private static QuantidadeDAO quantidadeDAO = new QuantidadeDAO();

	ScreensController myController;

	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		prepareTables();
		findMaterias();
		findFormulas();
	}

	private void findMaterias() {
		Task<Void> searchTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				progMaterias.setVisible(true);
				tblMateria.setDisable(true);
				btAddMateria.setDisable(true);
				btAddFormula.setDisable(true);
				materias = FXCollections.observableList((List<Materia>) materiaDAO.findMaterias());
				return null;
			}
		};

		searchTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@SuppressWarnings("unchecked")
			@Override
			public void handle(WorkerStateEvent arg0) {
				progMaterias.setVisible(false);
				tblMateria.setDisable(false);
				btAddMateria.setDisable(false);
				btAddFormula.setDisable(false);
				tblMateria.setItems(materias);
			}
		});
		searchTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				progMaterias.setVisible(false);
				tblMateria.setDisable(false);
				btAddMateria.setDisable(false);
				btAddFormula.setDisable(false);
				AlertUtil.makeError("Erro", "Ocorreu uma falha ao consultar as matérias-prima existentes.");
			}
		});
		new Thread(searchTask).start();
	}

	private void findFormulas() {
		Task<Void> searchTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				progFormulas.setVisible(true);
				tblFormula.setDisable(true);
				formulas = FXCollections.observableList((List<Formula>) formulaDAO.findFormulas());
				return null;
			}
		};

		searchTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@SuppressWarnings("unchecked")
			@Override
			public void handle(WorkerStateEvent arg0) {
				progFormulas.setVisible(false);
				tblFormula.setDisable(false);
				tblFormula.setItems(formulas);
				tblFormula.refresh();
			}
		});
		searchTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				progFormulas.setVisible(false);
				tblFormula.setDisable(false);
				AlertUtil.makeError("Erro", "Ocorreu uma falha ao consultar as formulações existentes.");
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
			findMaterias();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void editMateria(Materia mat) {
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
			((CadastroMateriaController) fxmlloader.getController()).setContext(mat, tblMateria.getScene());
			stage.setTitle("Gerenciar matéria-prima");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(tblMateria.getScene().getWindow());
			stage.setResizable(Boolean.FALSE);
			stage.showAndWait();
			findMaterias();
			tblMateria.refresh();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void addFormula() {
		try {
			Stage stage;
			Parent root;
			stage = new Stage();
			URL url = getClass().getResource("/fxml/CadFormulas.fxml");
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(url);
			fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
			root = (Parent) fxmlloader.load(url.openStream());
			stage.setScene(new Scene(root));
			((CadastroFormulaController) fxmlloader.getController()).setContext(null, tblMateria.getScene());
			stage.setTitle("Gerenciar formulação");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(tblMateria.getScene().getWindow());
			stage.setResizable(Boolean.FALSE);
			stage.showAndWait();
			findFormulas();
			tblFormula.refresh();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void editFormula(Formula f) {
		try {
			Stage stage;
			Parent root;
			stage = new Stage();
			URL url = getClass().getResource("/fxml/CadFormulas.fxml");
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(url);
			fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
			root = (Parent) fxmlloader.load(url.openStream());
			stage.setScene(new Scene(root));
			((CadastroFormulaController) fxmlloader.getController()).setContext(f, tblFormula.getScene());
			stage.setTitle("Gerenciar formulação");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(tblFormula.getScene().getWindow());
			stage.setResizable(Boolean.FALSE);
			stage.showAndWait();
			findFormulas();
			tblFormula.refresh();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void prepareTables() {

		colNomeMateria.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Materia, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Materia, String> cell) {
						final Materia mat = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								mat.getNomeMateria());
						return simpleObject;
					}
				});

		Callback<TableColumn<Materia, String>, TableCell<Materia, String>> cellEditarFactory = new Callback<TableColumn<Materia, String>, TableCell<Materia, String>>() {
			@Override
			public TableCell call(final TableColumn<Materia, String> param) {
				final TableCell<Materia, String> cell = new TableCell<Materia, String>() {

					final Button btn = new Button();

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn.setOnAction(event -> {
								Materia mat = getTableView().getItems().get(getIndex());
								editMateria(mat);
							});
							btn.setStyle("-fx-graphic: url('/icons/Modify.png');");
							btn.setCursor(Cursor.HAND);
							setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}
		};
		colEditarMateria.setCellFactory(cellEditarFactory);

		Callback<TableColumn<Materia, String>, TableCell<Materia, String>> cellExcluirFactory = new Callback<TableColumn<Materia, String>, TableCell<Materia, String>>() {
			@Override
			public TableCell call(final TableColumn<Materia, String> param) {
				final TableCell<Materia, String> cell = new TableCell<Materia, String>() {

					final Button btn = new Button();

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn.setOnAction(event -> {
								Optional<ButtonType> result = AlertUtil.makeConfirm("Confirmar exclusão",
										"Podem haver formulações que estejam utilizando esta matéria-prima. Tem certeza que deseja excluir?.");
								if (result.get() == ButtonType.OK) {
									Materia mat = getTableView().getItems().get(getIndex());
									Task<Void> exclusionTask = new Task<Void>() {
										@Override
										protected Void call() throws Exception {
											materiaDAO.removeMateria(mat);
											materias.remove(mat);
											tblMateria.refresh();
											return null;
										}
									};
									exclusionTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
										@Override
										public void handle(WorkerStateEvent arg0) {
											Toast.makeToast((Stage) tblMateria.getScene().getWindow(),
													"Matéria-prima removida com sucesso.");
											materias.remove(mat);
											tblMateria.refresh();
											calculaTotais();
											findFormulas();
										}
									});
									exclusionTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
										@Override
										public void handle(WorkerStateEvent arg0) {
											AlertUtil.makeError("Erro",
													"Ocorreu uma falha ao tentar remover a matéria-prima selecionada.");
											tblMateria.setItems(materias);
											tblMateria.refresh();
										}
									});
									new Thread(exclusionTask).start();
								}
							});
							btn.setStyle("-fx-graphic: url('/icons/Trash.png');");
							btn.setCursor(Cursor.HAND);
							setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}
		};
		colExcluirMateria.setCellFactory(cellExcluirFactory);

		colNomeMateria.setStyle("-fx-alignment: CENTER;");
		colEditarMateria.setStyle("-fx-alignment: CENTER;");
		colExcluirMateria.setStyle("-fx-alignment: CENTER;");

		colNomeFormula.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Formula, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Formula, String> cell) {
						final Formula formula = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								formula.getNomeFormula());
						return simpleObject;
					}
				});

		colPesoFormula.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Formula, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<Formula, Double> cell) {
						final Formula formula = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(
								formula.getPesoTotal());
						return simpleObject;
					}
				});

		Callback<TableColumn<Formula, String>, TableCell<Formula, String>> cellEditarFormulaFactory = new Callback<TableColumn<Formula, String>, TableCell<Formula, String>>() {
			@Override
			public TableCell call(final TableColumn<Formula, String> param) {
				final TableCell<Formula, String> cell = new TableCell<Formula, String>() {

					final Button btn = new Button();

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn.setOnAction(event -> {
								Formula f = getTableView().getItems().get(getIndex());
								editFormula(f);
							});
							btn.setStyle("-fx-graphic: url('/icons/Modify.png');");
							btn.setCursor(Cursor.HAND);
							setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}
		};
		colEditarFormula.setCellFactory(cellEditarFormulaFactory);

		Callback<TableColumn<Formula, String>, TableCell<Formula, String>> cellExcluirFormulaFactory = new Callback<TableColumn<Formula, String>, TableCell<Formula, String>>() {
			@Override
			public TableCell call(final TableColumn<Formula, String> param) {
				final TableCell<Formula, String> cell = new TableCell<Formula, String>() {

					final Button btn = new Button();

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn.setOnAction(event -> {
								Optional<ButtonType> result = AlertUtil.makeConfirm("Confirmar exclusão",
										"Tem certeza que deseja excluir esta formulação?.");
								if (result.get() == ButtonType.OK) {
									Formula f = getTableView().getItems().get(getIndex());
									Task<Void> exclusionTask = new Task<Void>() {
										@Override
										protected Void call() throws Exception {
											formulaDAO.removeFormula(f);
											return null;
										}
									};
									exclusionTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
										@Override
										public void handle(WorkerStateEvent arg0) {
											Toast.makeToast((Stage) tblMateria.getScene().getWindow(),
													"Formulação removida com sucesso.");
											formulas.remove(f);
											tblFormula.refresh();
										}
									});
									exclusionTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
										@Override
										public void handle(WorkerStateEvent arg0) {
											AlertUtil.makeError("Erro",
													"Ocorreu uma falha ao tentar remover a formulação selecionada.");
											tblFormula.setItems(materias);
											tblFormula.refresh();
										}
									});
									new Thread(exclusionTask).start();
								}
							});
							btn.setStyle("-fx-graphic: url('/icons/Trash.png');");
							btn.setCursor(Cursor.HAND);
							setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}
		};
		colExcluirFormula.setCellFactory(cellExcluirFormulaFactory);

		colNomeFormula.setStyle("-fx-alignment: CENTER;");
		colPesoFormula.setStyle("-fx-alignment: CENTER;");
		colEditarFormula.setStyle("-fx-alignment: CENTER;");
		colExcluirFormula.setStyle("-fx-alignment: CENTER;");
	}

	private void calculaTotais() {
		List<Formula> formulas = formulaDAO.findFormulas();
		formulas.forEach(frm -> {
			List<Quantidade> quantidades = quantidadeDAO.findByFormula(frm);
			pesoToRecalc = new Double(0);
			quantidades.forEach(qtd -> {
				pesoToRecalc += qtd.getPeso();
			});
			frm.setPesoTotal(pesoToRecalc);
			formulaDAO.updateFormula(frm);
		});
	}

}
