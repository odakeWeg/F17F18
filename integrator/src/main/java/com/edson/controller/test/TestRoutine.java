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

import com.edson.util.ConfigurationPathUtil;
import com.edson.util.TagConfigurationUtil;

public class TestRoutine extends Thread{
    

    //@TODO: Configurar se teste completo ou parar na falha
    //@TODO: Disable and enable button to start -> Do this inside thread???
    //@TODO: Implement interruption
    public void run() {  
        //preTestConfiguration();
        try {
            Document testingXML = retrieveTestRoutine();
            initiateTestRoutine(testingXML.getDocumentElement(), true);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //afterTestConfiguration();
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
        File file = new File(ConfigurationPathUtil.testRoutinePath + "foo.xml");
        //File file = new File(testRoutinePath + SapTestDataInstanceUtil.dados.getFamily() + ":" + SapTestDataInstanceUtil.dados.getCurrent() + "A" + ".xml");
        Document document = docBuilder.parse(file);
        return document;
    }

    //@TODO: Adiciona lógica na função
    //@TODO: Add if with break in case test should stop in the middle -> Recusive functions dont let it get out
    //@TODO: Do something with displayError
    //@TODO: Limpar função
    private void initiateTestRoutine(Node node, boolean stopInError) {
        //TestingCode
        //System.out.println(node.getNodeName() + " is son of " + node.getParentNode().getNodeName());

        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            if (TagConfigurationUtil.executeTagFunction(currentNode) != "error" && stopInError) {
                if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                    initiateTestRoutine(currentNode, stopInError);
                }
            } else {
                //displayError();
            }
        }
    }

    private void displayError() {
        System.out.println("ERRO X");
    }

    private void afterTestConfiguration() {
        //@TODO: Fechar rotina de teste e GUI primeiro
        //dataBaseTestEnd();
        //inlineTestEnd();
        //sapTestEnd();
    }
}
