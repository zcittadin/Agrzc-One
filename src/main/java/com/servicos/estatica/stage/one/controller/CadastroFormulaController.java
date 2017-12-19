package com.servicos.estatica.stage.one.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.servicos.estatica.stage.one.dao.FormulaDAO;
import com.servicos.estatica.stage.one.dao.MateriaDAO;
import com.servicos.estatica.stage.one.dao.QuantidadeDAO;
import com.servicos.estatica.stage.one.model.Formula;
import com.servicos.estatica.stage.one.model.ItemFormula;
import com.servicos.estatica.stage.one.model.Materia;
import com.servicos.estatica.stage.one.model.Quantidade;
import com.servicos.estatica.stage.one.shared.CadastroProperty;
import com.servicos.estatica.stage.one.util.AlertUtil;
import com.servicos.estatica.stage.one.util.FormatterUtil;
import com.servicos.estatica.stage.one.util.Toast;

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
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;

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
	private static List<ItemFormula> itensToRemove = new ArrayList<>();
	private static List<ItemFormula> itensCopy = new ArrayList<>();

	private static String COMMA = ",";
	private static String DOT = ".";

	private Double total = new Double(0);
	
	private Formula formula;

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

	public void setContext(Formula formula, Scene parentScene) {
		this.parentScene = parentScene;
		this.formula = formula;
		itens.clear();
		itensCopy.clear();
		itensToRemove.clear();
		if (formula != null) {
			tblFormula.setEditable(true);
			populateFields();
		} else {
			tblFormula.setEditable(false);
		}
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
		itens.add(new ItemFormula(null, materia.getId(), materia.getNomeMateria(),
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
				if (formula != null) {
					formula.setNomeFormula(txtFormula.getText());
					formula.setPesoTotal(Double.parseDouble(lblTotal.getText()));
					formula.setQuantidades(null);
					formulaDAO.updateFormula(formula);
				} else {
					formula = new Formula(null, txtFormula.getText(), Double.parseDouble(lblTotal.getText()), null);
					formulaDAO.saveFormula(formula);
				}
				itens.forEach(it -> {
					if (it.getIdQuantidade() == null) {
						Materia mat = materiaDAO.findByName(it.getNomeMateria());
						quantidadeDAO.saveQuantidade(new Quantidade(null, mat, formula, it.getQuantidade()));
					} else {
						itensCopy.forEach(itCopy -> {
							if ((it.getIdQuantidade() == itCopy.getIdQuantidade())
									&& (!it.getNomeMateria().equals(itCopy.getNomeMateria())
											|| (it.getQuantidade() != itCopy.getQuantidade()))) {
								Materia mat = materiaDAO.findByName(it.getNomeMateria());
								it.setIdMateria(mat.getId());
								Quantidade q = quantidadeDAO.findById(it.getIdQuantidade());
								if (q != null) {
									q.setMateriaQuantidade(mat);
									q.setPeso(it.getQuantidade());
									quantidadeDAO.updateQuantidade(q);
								}
							}
						});
					}
				});
				if (!itensToRemove.isEmpty()) {
					itensToRemove.forEach(it -> {
						Materia m = null;
						m = materiaDAO.findById(it.getIdMateria());
						Quantidade q = quantidadeDAO.findByMateriaAndFormula(m, formula);
						if (q != null)
							quantidadeDAO.removeQuantidade(q);
					});
				}
				return null;
			}
		};
		saveTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent arg0) {
				progItens.setVisible(false);
				tblFormula.setDisable(false);
				itens.clear();
				itensCopy.clear();
				itensToRemove.clear();
				CadastroProperty.cadastroFormulaProperty().set((!CadastroProperty.getFormulaChanged()));
				CadastroProperty.setFormulaChanged(!CadastroProperty.getFormulaChanged());
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

	@FXML
	private void cancel() {
		Stage stage = (Stage) tblFormula.getScene().getWindow();
		stage.close();
	}

	@SuppressWarnings("unchecked")
	private void populateFields() {
		txtFormula.setText(formula.getNomeFormula());
		lblTotal.setText(formula.getPesoTotal().toString());
		comboMateria.setDisable(false);
		txtQuantidade.setDisable(false);
		btAdicionar.setDisable(false);
		List<Quantidade> quantidades = quantidadeDAO.findByFormula(formula);
		quantidades.forEach(q -> {
			itens.add(new ItemFormula(q.getId(), q.getMateriaQuantidade().getId(),
					q.getMateriaQuantidade().getNomeMateria(), q.getPeso()));
			itensCopy.add(new ItemFormula(q.getId(), q.getMateriaQuantidade().getId(),
					q.getMateriaQuantidade().getNomeMateria(), q.getPeso()));
		});
		tblFormula.setItems(itens);
	}

	@SuppressWarnings("unchecked")
	private void prepareTable() {
		ObservableList<String> nomesMateria = FXCollections.observableArrayList();
		materias.forEach(str -> {
			nomesMateria.add(str.getNomeMateria());
		});
		colMateria.setCellValueFactory(new PropertyValueFactory<ItemFormula, String>("nomeMateria"));
		colMateria.setCellFactory(ComboBoxTableCell.forTableColumn(nomesMateria));
		colMateria.setOnEditCommit(new EventHandler<CellEditEvent<ItemFormula, String>>() {
			@Override
			public void handle(CellEditEvent<ItemFormula, String> t) {
				ItemFormula item = (ItemFormula) t.getTableView().getItems().get(t.getTablePosition().getRow());
				item.setNomeMateria(t.getNewValue());
			}
		});

		colQuantidade.setCellValueFactory(new PropertyValueFactory<ItemFormula, Double>("quantidade"));
		colQuantidade.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		colQuantidade.setOnEditCommit(new EventHandler<CellEditEvent<ItemFormula, Double>>() {
			@Override
			public void handle(CellEditEvent<ItemFormula, Double> t) {
				((ItemFormula) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setQuantidade(t.getNewValue());
				calculaTotal();
			}
		});

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
								itensToRemove.add(it);
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
