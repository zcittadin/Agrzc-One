package com.servicos.estatica.stage.one.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.servicos.estatica.stage.one.dao.MateriaDAO;
import com.servicos.estatica.stage.one.model.Materia;
import com.servicos.estatica.stage.one.util.FormatterUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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

	private static MateriaDAO materiaDAO = new MateriaDAO();

	private static ObservableList<Materia> materias = FXCollections.observableArrayList();

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		FormatterUtil.formatNumberField(txtQuantidade);

		materias = FXCollections.observableList((List<Materia>) materiaDAO.findMaterias());
		comboMateria.setItems(materias);

	}

}
