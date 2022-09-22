package com.edson.controller.test;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.edson.util.ViewConfigurationPathUtil;

import net.weg.wcomm.modbus.NegativeConfirmationException;
import net.weg.wcomm.modbus.exception.ModbusExceptionResponseException;
import net.weg.wcomm.modbus.exception.ModbusUnexpectedResponseException;

import com.edson.routine.handler.SapTestDataHandler;
import com.edson.routine.handler.TagConfigurationHandler;
import com.edson.routine.handler.TestDataTreeHandlerDeprecated;

public class TestRoutine extends Thread{
    private TagConfigurationHandler tagConfigurationHandler;
    private SapTestDataHandler sapTestDataHandler;
    //private TestDataTreeHandlerDeprecated TestDataTreeHandler;  //Dependencia desnecessária

    //@TODO: Configurar se teste completo ou parar na falha
    //@TODO: Disable and enable button to start -> Do this inside thread???
    //@TODO: Implement interruption
    //@TODO: Join try catches
    public void run() {  
        try {
            setDependencies();
        } catch (ParserConfigurationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        //preTestConfiguration();
        try {
            Document testingXML = retrieveTestRoutine();
            initiateTestRoutine(testingXML.getDocumentElement(), true);
        } catch (ParserConfigurationException | SAXException | IOException | NegativeConfirmationException | ModbusExceptionResponseException | ModbusUnexpectedResponseException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //afterTestConfiguration();

        //@TestingCode
        System.out.println("teste finalizado");
    }

    private void setDependencies() throws ParserConfigurationException {
        tagConfigurationHandler = new TagConfigurationHandler(sapTestDataHandler);
        sapTestDataHandler = new SapTestDataHandler(1234567891, "familia", 450);
        //TestDataTreeHandler = new TestDataTreeHandlerDeprecated();
    }

    private void preTestConfiguration() {
        //@TODO: Fechar rotina de teste e GUI primeiro
        //dataBaseTestInit();
        //inlineTestInit();
        //sapTestInit();
    }
    private Document retrieveTestRoutine() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        //@TestingCode
        File file = new File(ViewConfigurationPathUtil.TEST_ROUTINE_PATH + "modbus.xml");
        //File file = new File(TEST_ROUTINE_PATH + SapTestDataInstanceUtil.dados.getFamily() + ":" + SapTestDataInstanceUtil.dados.getCurrent() + "A" + ".xml");
        Document document = docBuilder.parse(file);
        return document;
    }

    //@TODO: Adiciona lógica na função
    //@TODO: Add if with break in case test should stop in the middle -> Recusive functions dont let it get out
    //@TODO: Do something with displayError
    //@TODO: Limpar função
    private void initiateTestRoutine(Node node, boolean stopInError) throws NegativeConfirmationException, ModbusExceptionResponseException, ModbusUnexpectedResponseException, InterruptedException {
        //TestingCode
        //System.out.println(node.getNodeName() + " is son of " + node.getParentNode().getNodeName());

        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            //@TestingCode
            if ((tagConfigurationHandler.executeTagFunction(currentNode) != "error" && stopInError)) {
                if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                    initiateTestRoutine(currentNode, stopInError);
                }
            } else {
                displayError();
            }
        }
    }

    private void displayError() {
        System.out.println("-------------------------------------------------- ERRO!!! --------------------------------------------------");
    }

    private void afterTestConfiguration() {
        //@TODO: Fechar rotina de teste e GUI primeiro
        //dataBaseTestEnd();
        //inlineTestEnd();
        //sapTestEnd();
    }
}
