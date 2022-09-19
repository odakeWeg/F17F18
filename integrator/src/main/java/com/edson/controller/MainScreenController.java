package com.edson.controller;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import com.edson.App;
import com.edson.routine.handler.TestDataTreeHandler;
import com.edson.util.ViewConfigurationPathUtil;

import javafx.fxml.FXML;

public class MainScreenController {

    @FXML
    private void switchToSettings() throws IOException, ParserConfigurationException {
        //@TestingCode
        //App.setRoot(ConfigurationPathUtil.VIEW_PATH + "settings");

        String[] tagPath2 = {"a1", "a2", "a3", "dir44"};
        String[] tagPath3 = {"a1", "dir2", "dir3", "dir44"};
        String[] tagPath4 = {"dir3", "dir2", "dir23", "dir5"};
        String[] tagPath5 = {"dir1", "dir12", "dir3", "dir4"};
        String[] tagPath6 = {"dir11"};

        String[] attr = {"data", "ow", "a", "t"};
        String[] data = {"1,5,2,6,5", ",51,12", "sf","asd2s0"};
        TestDataTreeHandler TestDataTreeHandler = new TestDataTreeHandler();
        TestDataTreeHandler.setTestName("first");
        TestDataTreeHandler.appendItem(tagPath2, attr, data);
        TestDataTreeHandler.setTestName("sec");
        TestDataTreeHandler.appendItem(tagPath2, attr, data);
        TestDataTreeHandler.printData();
    }
    @FXML
    private void switchToTest() throws IOException {
        App.setRoot(ViewConfigurationPathUtil.VIEW_PATH + "automatedTest");
    }
}