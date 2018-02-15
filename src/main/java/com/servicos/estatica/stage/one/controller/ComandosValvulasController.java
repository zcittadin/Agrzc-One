package com.servicos.estatica.stage.one.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.servicos.estatica.stage.one.shared.ComandosValvulasProperty;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ComandosValvulasController implements Initializable {

	@FXML
	private ImageView switchSilos12;
	@FXML
	private ImageView switchSilos34;
	@FXML
	private ImageView switchSaidaBalanca1;
	@FXML
	private ImageView switchMistura11;
	@FXML
	private ImageView switchMistura12;
	@FXML
	private ImageView switchTransportePneumatico;
	@FXML
	private ImageView switchSaidaBalanca2;
	@FXML
	private ImageView switchMistura2;

	private static String SWITCH_ON_PATH = "com/servicos/estatica/stage/one/img/switch_on.png";
	private static String SWITCH_OFF_PATH = "com/servicos/estatica/stage/one/img/switch_off.png";

	private Boolean comandoSilos12 = false;
	private Boolean comandoSilos34 = false;
	private Boolean comandoSaidaBalanca1 = false;
	private Boolean comandoMistura11 = false;
	private Boolean comandoMistura12 = false;
	private Boolean comandoTransportePneumatico = false;
	private Boolean comandoSaidaBalanca2 = false;
	private Boolean comandoMistura2 = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		switchSilos12.setImage(new Image(SWITCH_OFF_PATH));
		switchSilos34.setImage(new Image(SWITCH_OFF_PATH));
		switchSaidaBalanca1.setImage(new Image(SWITCH_OFF_PATH));
		switchMistura11.setImage(new Image(SWITCH_OFF_PATH));
		switchMistura12.setImage(new Image(SWITCH_OFF_PATH));
		switchTransportePneumatico.setImage(new Image(SWITCH_OFF_PATH));
		switchSaidaBalanca2.setImage(new Image(SWITCH_OFF_PATH));
		switchMistura2.setImage(new Image(SWITCH_OFF_PATH));
	}

	@FXML
	private void toggleSilos12() {
		if (comandoSilos12 == false) {
			comandoSilos12 = true;
			switchSilos12.setImage(new Image(SWITCH_ON_PATH));
		} else {
			comandoSilos12 = false;
			switchSilos12.setImage(new Image(SWITCH_OFF_PATH));
		}
		ComandosValvulasProperty.silos12Property().set(comandoSilos12);
	}

	@FXML
	private void toggleSilos34() {
		if (comandoSilos34 == false) {
			comandoSilos34 = true;
			switchSilos34.setImage(new Image(SWITCH_ON_PATH));
		} else {
			comandoSilos34 = false;
			switchSilos34.setImage(new Image(SWITCH_OFF_PATH));
		}
		ComandosValvulasProperty.silos34Property().set(comandoSilos34);
	}

	@FXML
	private void toggleSaidaBalanca1() {
		if (comandoSaidaBalanca1 == false) {
			comandoSaidaBalanca1 = true;
			switchSaidaBalanca1.setImage(new Image(SWITCH_ON_PATH));
		} else {
			comandoSaidaBalanca1 = false;
			switchSaidaBalanca1.setImage(new Image(SWITCH_OFF_PATH));
		}
		ComandosValvulasProperty.saidaBalanca1Property().set(comandoSaidaBalanca1);
	}

	@FXML
	private void toggleMistura11() {
		if (comandoMistura11 == false) {
			comandoMistura11 = true;
			switchMistura11.setImage(new Image(SWITCH_ON_PATH));
		} else {
			comandoMistura11 = false;
			switchMistura11.setImage(new Image(SWITCH_OFF_PATH));
		}
		ComandosValvulasProperty.mistura11Property().set(comandoMistura11);
	}

	@FXML
	private void toggleMistura12() {
		if (comandoMistura12 == false) {
			comandoMistura12 = true;
			switchMistura12.setImage(new Image(SWITCH_ON_PATH));
		} else {
			comandoMistura12 = false;
			switchMistura12.setImage(new Image(SWITCH_OFF_PATH));
		}
		ComandosValvulasProperty.mistura12Property().set(comandoMistura12);
	}

	@FXML
	private void toggleTransportePneumatico() {
		if (comandoTransportePneumatico == false) {
			comandoTransportePneumatico = true;
			switchTransportePneumatico.setImage(new Image(SWITCH_ON_PATH));
		} else {
			comandoTransportePneumatico = false;
			switchTransportePneumatico.setImage(new Image(SWITCH_OFF_PATH));
		}
		ComandosValvulasProperty.transportePneumaticoProperty().set(comandoTransportePneumatico);
	}

	@FXML
	private void toggleSaidaBalanca2() {
		if (comandoSaidaBalanca2 == false) {
			comandoSaidaBalanca2 = true;
			switchSaidaBalanca2.setImage(new Image(SWITCH_ON_PATH));
		} else {
			comandoSaidaBalanca2 = false;
			switchSaidaBalanca2.setImage(new Image(SWITCH_OFF_PATH));
		}
		ComandosValvulasProperty.saidaBalanca2Property().set(comandoSaidaBalanca2);
	}

	@FXML
	private void toggleMistura2() {
		if (comandoMistura2 == false) {
			comandoMistura2 = true;
			switchMistura2.setImage(new Image(SWITCH_ON_PATH));
		} else {
			comandoMistura2 = false;
			switchMistura2.setImage(new Image(SWITCH_OFF_PATH));
		}
		ComandosValvulasProperty.mistura2Property().set(comandoMistura2);
	}

}
