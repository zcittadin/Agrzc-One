package com.servicos.estatica.stage.one.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.servicos.estatica.stage.one.dao.FormulaDAO;
import com.servicos.estatica.stage.one.dao.MateriaDAO;
import com.servicos.estatica.stage.one.dao.QuantidadeDAO;
import com.servicos.estatica.stage.one.model.Formula;
import com.servicos.estatica.stage.one.model.ItemFormula;
import com.servicos.estatica.stage.one.model.Materia;
import com.servicos.estatica.stage.one.model.Quantidade;
import com.servicos.estatica.stage.one.util.AlertUtil;
import com.servicos.estatica.stage.one.util.FormatterUtil;
import com.servicos.estatica.stage.one.util.Toast;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

@SuppressWarnings("rawtypes")
public class CadastroFormulaController implements Initializable {

	@FXML
	private TextField txtFormula;
	@FXML
	private TextField txtQuantidade;
	@FXML
	private ComboBox comboMateria;
	@FXML
	private TableView tblFormula;
	@FXML
	private TableColumn colMateria;
	@FXML
	private TableColumn colQuantidade;
	@FXML
	private TableColumn colEditar;
	@FXML
	private TableColumn colExcluir;
	@FXML
	private Label lblTotal;
	@FXML
	private Button btAdicionar;
	@FXML
	private ProgressIndicator progItens;

	private static Materia materia;

	private static MateriaDAO materiaDAO = new MateriaDAO();
	private static FormulaDAO formulaDAO = new FormulaDAO();
	private static QuantidadeDAO quantidadeDAO = new QuantidadeDAO();

	private static ObservableList<ItemFormula> itens = FXCollections.observableArrayList();
	private static ObservableList<Materia> materias = FXCollections.observableArrayList();

	private static String COMMA = ",";
	private static String DOT = ".";

	private Double total = new Double(0);

	private Scene parentScene;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		FormatterUtil.formatNumberField(txtQuantidade);
		materias = FXCollections.observableList((List<Materia>) materiaDAO.findMaterias());

		comboMateria.setItems(materias);
		comboMateria.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Materia>() {
			public void changed(ObservableValue<? extends Materia> observable, Materia oldValue, Materia newValue) {
				materia = newValue;
				txtQuantidade.requestFocus();
			}
		});

		prepareTable();
		tblFormula.setItems(itens);
	}

	public void setContext(Scene parentScene) {
		this.parentScene = parentScene;
	}

	@FXML
	private void addItem() {
		if (materia == null) {
			AlertUtil.makeWarning("Atenção", "Informe a matéria-prima a ser adicionada.");
			return;
		}
		if (txtQuantidade.getText() == null || "".equals(txtQuantidade.getText().trim())) {
			AlertUtil.makeWarning("Atenção", "Informe a quantidade para a matéria-prima selecionada.");
			txtQuantidade.requestFocus();
			return;
		}
		itens.add(new ItemFormula(materia.getId(), materia.getNomeMateria(),
				Double.parseDouble(FormatterUtil.adjustDecimal(txtQuantidade.getText(), COMMA, DOT))));
		txtQuantidade.clear();
		tblFormula.refresh();
		calculaTotal();
	}

	@FXML
	private void saveFormula() {
		Task<Void> saveTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				progItens.setVisible(true);
				tblFormula.setDisable(true);
				Formula formula = new Formula(null, txtFormula.getText(), Double.parseDouble(lblTotal.getText()), null);
				formulaDAO.saveFormula(formula);
				itens.forEach(it -> {
					Materia mat = materiaDAO.findById(it.getIdMateria());
					Quantidade q = new Quantidade(null, mat, formula, it.getQuantidade());
					quantidadeDAO.saveQuantidade(q);
				});
				return null;
			}
		};
		saveTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				progItens.setVisible(false);
				tblFormula.setDisable(false);
				itens.clear();
				Toast.makeToast((Stage) parentScene.getWindow(), "Formulação salva com sucesso.");
				Stage stage = (Stage) tblFormula.getScene().getWindow();
				stage.close();
			}

		});
		saveTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				progItens.setVisible(false);
				tblFormula.setDisable(false);
				AlertUtil.makeError("Erro", "Ocorreu uma falha ao registrar a nova formulação.");
				Stage stage = (Stage) tblFormula.getScene().getWindow();
				stage.close();
			}

		});
		new Thread(saveTask).start();

	}

	@FXML
	private void enableFields() {
		if (txtFormula.getText() == null || "".equals(txtFormula.getText().trim())) {
			comboMateria.setDisable(true);
			txtQuantidade.setDisable(true);
			btAdicionar.setDisable(true);
		} else {
			comboMateria.setDisable(false);
			txtQuantidade.setDisable(false);
			btAdicionar.setDisable(false);
		}
	}

	@SuppressWarnings("unchecked")
	private void prepareTable() {

		colMateria.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ItemFormula, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<ItemFormula, String> cell) {
						final ItemFormula item = cell.getValue();
						final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty<String>(
								item.getNomeMateria());
						return simpleObject;
					}
				});

		colQuantidade.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ItemFormula, Double>, ObservableValue<Double>>() {
					public ObservableValue<Double> call(CellDataFeatures<ItemFormula, Double> cell) {
						final ItemFormula item = cell.getValue();
						final SimpleObjectProperty<Double> simpleObject = new SimpleObjectProperty<Double>(
								item.getQuantidade());
						return simpleObject;
					}
				});

		Callback<TableColumn<ItemFormula, String>, TableCell<ItemFormula, String>> cellEditarFactory = new Callback<TableColumn<ItemFormula, String>, TableCell<ItemFormula, String>>() {
			@Override
			public TableCell call(final TableColumn<ItemFormula, String> param) {
				final TableCell<ItemFormula, String> cell = new TableCell<ItemFormula, String>() {

					final Button btn = new Button();

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn.setOnAction(event -> {
								// ItemFormula it = getTableView().getItems().get(getIndex());
								// editMateria(mat);
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
		colEditar.setCellFactory(cellEditarFactory);

		Callback<TableColumn<ItemFormula, String>, TableCell<ItemFormula, String>> cellExcluirFactory = new Callback<TableColumn<ItemFormula, String>, TableCell<ItemFormula, String>>() {
			@Override
			public TableCell call(final TableColumn<ItemFormula, String> param) {
				final TableCell<ItemFormula, String> cell = new TableCell<ItemFormula, String>() {

					final Button btn = new Button();

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn.setOnAction(event -> {
								ItemFormula it = getTableView().getItems().get(getIndex());
								itens.remove(it);
								tblFormula.refresh();
								calculaTotal();
								comboMateria.requestFocus();
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
		colExcluir.setCellFactory(cellExcluirFactory);

		colMateria.setStyle("-fx-alignment: CENTER;");
		colQuantidade.setStyle("-fx-alignment: CENTER;");
		colEditar.setStyle("-fx-alignment: CENTER;");
		colExcluir.setStyle("-fx-alignment: CENTER;");
	}

	private void calculaTotal() {
		itens.forEach(it -> {
			total = total + it.getQuantidade();
		});
		lblTotal.setText(total.toString());
		total = new Double(0);
	}

}
