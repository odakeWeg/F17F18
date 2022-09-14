package com.edson.controller;

import java.io.IOException;

import com.edson.App;
import com.edson.dto.TestData;
import com.edson.util.ConfigurationPathUtil;

import javafx.fxml.FXML;

public class MainScreenController {

    @FXML
    private void switchToSettings() throws IOException {
        //@TestingCode
        TestData.test();
        //App.setRoot(ConfigurationPathUtil.viewPath + "settings");
    }
    @FXML
    private void switchToTest() throws IOException {
        App.setRoot(ConfigurationPathUtil.viewPath + "automatedTest");
    }
}