package com.servicos.estatica.stage.one.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.servicos.estatica.stage.one.shared.ProcessamentoProperty;
import com.servicos.estatica.stage.one.util.FormatterUtil;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Balanca2EscritaController implements Initializable {

	@FXML
	private TextField txtValor;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		FormatterUtil.formatNumberField(txtValor);
	}

	@FXML
	private void saveValorPesagem() {
		ProcessamentoProperty.setWriteBalanca2Property(Integer.parseInt(txtValor.getText()));
		Stage stage = (Stage) txtValor.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void cancel() {
		Stage stage = (Stage) txtValor.getScene().getWindow();
		stage.close();
	}

}
