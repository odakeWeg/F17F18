package com.edson.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.edson.App;
import com.edson.model.TestName;
import com.edson.model.TestTag;
import com.edson.util.ViewConfigurationPathUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class SettingsController implements Initializable{

    //@TODO: Criar controllers separados, lógica muito hardCoded
    @FXML
    private TableView<TestName> testNameTable;
    @FXML
    private TableColumn<TestName, String> nameCol;
    @FXML
    private TableColumn<TestName, Integer> stepCol;

    @FXML
    private TableView<TestTag> tagTable;
    @FXML
    private TableColumn<TestTag, String> commandCol;
    @FXML
    private TableColumn<TestTag, String> attribute1Col;
    @FXML
    private TableColumn<TestTag, String> attribute2Col;
    @FXML
    private TableColumn<TestTag, String> attribute3Col;
    @FXML
    private TableColumn<TestTag, String> attribute4Col;
    @FXML
    private TableColumn<TestTag, String> attribute5Col;
    @FXML
    private TableColumn<TestTag, String> attribute6Col;
    @FXML
    private TableColumn<TestTag, Integer> tagStepCol;

    @FXML 
    private TextField nameText;
    @FXML
    private Pane namePane;
    @FXML
    private Pane testPane;

    @FXML
    private MenuButton tagChoiceMenu;
    @FXML 
    private TextField attr1Text;
    @FXML 
    private TextField attr2Text;
    @FXML 
    private TextField attr3Text;
    @FXML 
    private TextField attr4Text;
    @FXML 
    private TextField attr5Text;
    @FXML 
    private TextField attr6Text;



    private Integer id;
    private Integer inTestStep;

    private HashMap<Integer, ObservableList<TestTag>> testProcedureMap = new HashMap<Integer, ObservableList<TestTag>>();

    private int getAutoId() {
        if (id == null) {
            id = 0;
        } else {
            id += 1;
        }
        return id;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        stepCol.setCellValueFactory(new PropertyValueFactory<>("step"));

        commandCol.setCellValueFactory(new PropertyValueFactory<>("command"));
        attribute1Col.setCellValueFactory(new PropertyValueFactory<>("attribute1"));
        attribute2Col.setCellValueFactory(new PropertyValueFactory<>("attribute2"));
        attribute3Col.setCellValueFactory(new PropertyValueFactory<>("attribute3"));
        attribute4Col.setCellValueFactory(new PropertyValueFactory<>("attribute4"));
        attribute5Col.setCellValueFactory(new PropertyValueFactory<>("attribute5"));
        attribute6Col.setCellValueFactory(new PropertyValueFactory<>("attribute6"));
        tagStepCol.setCellValueFactory(new PropertyValueFactory<>("tagStep"));
        //testNameTable.setItems(nameList());
    }

    private ObservableList<TestName> nameList() {
        return FXCollections.observableArrayList(
            new TestName("name", getAutoId()),
            new TestName("name", getAutoId()),
            new TestName("name", getAutoId()),
            new TestName("name", getAutoId())
        );
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot(ViewConfigurationPathUtil.VIEW_PATH + "primary");
    }

    @FXML
    private void adicionar() {
        if(namePane.isVisible()) {
            testNameTable.getItems().add(new TestName(nameText.getText(), getAutoId()));
            testProcedureMap.put(id, FXCollections.observableArrayList());
        } else {
            tagTable.getItems().add(new TestTag(tagChoiceMenu.getText(), attr1Text.getText(), attr2Text.getText(), attr3Text.getText(), attr4Text.getText(), attr5Text.getText(), attr6Text.getText(), getAutoId()));
        }
        resetTextFields();
    }

    @FXML
    private void entrar() {
        //Make this pane invisible
        //Enter in this test
        //List of list for each test -> hashmap
        getTestProcedureFromStep(testNameTable.getSelectionModel().getSelectedItems().get(0).getStep());

        //System.out.println(tagChoice());
    }

    private void getTestProcedureFromStep(int step) {
        inTestStep = step;
        ObservableList<TestTag> testProcedure = testProcedureMap.get(step);
        if(testProcedure == null) {
            //testProcedure = FXCollections.observableArrayList();
        } else {
            tagTable.setItems(testProcedureMap.get(step));
        }

        namePane.setVisible(false);
        testPane.setVisible(true);
    }

    @FXML
    private void editar() {
        if(namePane.isVisible()) {
            testNameTable.getSelectionModel().getSelectedItems().get(0).setNome(nameText.getText());
        } else {
            //@TODO: Verificar se viável
        }
    }

    @FXML
    private void remover() {
        if(namePane.isVisible()) {
            testNameTable.getItems().removeAll(testNameTable.getSelectionModel().getSelectedItems());
        } else {
            tagTable.getItems().removeAll(tagTable.getSelectionModel().getSelectedItems());
        }
    }

    @FXML
    private void back() {
        if(namePane.isVisible()) {
            //@TODO: página inicial
        } else {
            namePane.setVisible(true);
            testPane.setVisible(false);

            System.out.println(testNameTable.getItems().get(0).getName());
        }
        inTestStep = null;
    }

    @FXML
    private void tagChoice() {
        //return testNameTable.getSelectionModel().getSelectedItems().get(0).getName();
    }

    @FXML
    private void readTag() {
        tagChoiceMenu.setText("read");;
    }

    @FXML
    private void writeTag() {
        tagChoiceMenu.setText("write");;
    }

    @FXML
    private void compareTag() {
        tagChoiceMenu.setText("compare");;
    }

    @FXML
    private void verifyTag() {
        tagChoiceMenu.setText("verify");;
    }

    @FXML
    private void communicationTag() {
        tagChoiceMenu.setText("communication");;
    }



    private void resetTextFields() {
        nameText.setText("");

        attr1Text.setText("");
        attr2Text.setText("");
        attr3Text.setText("");
        attr4Text.setText("");
        attr5Text.setText("");
        attr6Text.setText("");
    }
}