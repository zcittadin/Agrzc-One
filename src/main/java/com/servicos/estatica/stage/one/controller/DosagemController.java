package com.servicos.estatica.stage.one.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.servicos.estatica.stage.one.app.ControlledScreen;
import com.servicos.estatica.stage.one.dao.FormulaDAO;
import com.servicos.estatica.stage.one.model.Formula;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DosagemController implements Initializable, ControlledScreen {

	@FXML
	private Rectangle rectForm;
	@FXML
	private ComboBox<Formula> comboFormulas;

	private static FormulaDAO formulaDAO = new FormulaDAO();

	private static ObservableList<Formula> formulas = FXCollections.observableArrayList();

	private Formula selectedFormula;

	ScreensController myController;

	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rectForm.setFill(Color.TRANSPARENT);
		formulas = FXCollections.observableList((List<Formula>) formulaDAO.findFormulas());

		comboFormulas.setItems(formulas);

		comboFormulas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Formula>() {
			public void changed(ObservableValue<? extends Formula> observable, Formula oldValue, Formula newValue) {
				selectedFormula = newValue;
			}
		});

	}

}
