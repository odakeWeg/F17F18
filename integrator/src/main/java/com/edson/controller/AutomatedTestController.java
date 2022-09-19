package com.edson.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import com.edson.App;
import com.edson.controller.test.TestRoutine;
import com.edson.util.ViewConfigurationPathUtil;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

//@TODO: Create another class for xml manipulation, maybe util class
public class AutomatedTestController {

    @FXML
    private void switchToMainScreen() throws IOException {
        App.setRoot(ViewConfigurationPathUtil.VIEW_PATH + "mainScreen");
    }
    @FXML
    private void startTest() throws IOException, ParserConfigurationException, SAXException, InterruptedException, ExecutionException {
        //@TODO: Usar serviço ao invés de fazer tudo no controller
        //@TODO: Move serialReading inssede the thread
        //serialReading();
        
        TestRoutine testThread = new TestRoutine();  
        testThread.start();
    }

    private void serialReading() throws IOException {
        //@TODO: Desabilitar interação com tela anterior ao abrir tela nova
        Scene scene = new Scene(App.loadFXML(ViewConfigurationPathUtil.VIEW_PATH + "serialReading"));

        Stage window = new Stage();
        window.setScene(scene);

        window.showAndWait();
    }
}

//@TestingCode
/*      
        // do something with the current node instead of System.out
        //Dentro do InitiateRoutine
        
        try {
            System.out.println(node.getNodeName() + " is son of " + node.getParentNode().getNodeName());
        } catch (Exception e) {
            System.out.println("no parent");
        }
        
        try { 
            for (int i = 0; i < node.getAttributes().getLength(); ++i)
            {
                Node attr = node.getAttributes().item(i);
                System.out.println(attr.getNodeName() + " = \"" + attr.getNodeValue() + "\"");
            }
            
            System.out.println(node.getAttributes().getNamedItem("registers").getNodeValue());
        } catch (Exception e) {
            System.out.println("no attribute");
        }
        
        try {
            System.out.println(node.getTextContent());
        } catch (Exception e) {
            System.out.println("no value");
        }
 */

 /*
    System.out.println("thread is running...");  
        for (int t = 0; t < 10; t++) {

            System.out.println(t);
            for(int i = 0; i < 1000000000; i++) {
                for(int c = 0; c < 1000000000; c++) {
                }
            }
        }
  */