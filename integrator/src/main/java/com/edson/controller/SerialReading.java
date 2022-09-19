package com.edson.controller;

import java.io.IOException;

import com.edson.App;
//import com.edson.util.SapTestDataInstanceUtil;

import javafx.fxml.FXML;
import javafx.stage.Stage;

//@TODO: Mudar os imports e funções comentadas de Util para o Handler
public class SerialReading {
    @FXML 
    private javafx.scene.control.Button closeButton;
    @FXML 
    private javafx.scene.control.TextField serialTextField;

    @FXML
    private void getSerial() throws IOException {
        //@TODO: Colocar validação
        //SapTestDataInstanceUtil.dados.setSerial(Long.parseLong(serialTextField.getText()));

        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void closeWindow() throws IOException {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
