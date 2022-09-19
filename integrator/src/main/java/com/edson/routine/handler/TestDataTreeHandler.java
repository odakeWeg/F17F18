package com.edson.routine.handler;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.edson.communication.ComunicacaoSerial;
import com.edson.util.NamedNodeMapIterableUtil;

public class TestDataTreeHandler {
    private DocumentBuilderFactory documentFactory;
    private DocumentBuilder documentBuilder;
    private Document document;

    //@TODO: Make it private again
    public List<ComunicacaoSerial> comunicacaoSerial;

    private String[] testName = {""};
    private int id = 0;

    public TestDataTreeHandler() throws ParserConfigurationException {
        documentFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = documentFactory.newDocumentBuilder();
        document = documentBuilder.newDocument();

        comunicacaoSerial = new ArrayList<ComunicacaoSerial>();
    }

    public void appendItem(String[] tagPath, String[] attributeName, String[] data) {
        Element root;
        String[] tagPathFull = Stream.concat(Arrays.stream(testName), Arrays.stream(tagPath)).toArray(String[]::new);

        if(document.getDocumentElement() == null){
            root = document.createElement("root");
            document.appendChild(root);
        }
        recursiveAppendItem(document.getDocumentElement(), tagPathFull, attributeName, data);
    }

    //@TODO: Fragment into subfunctions for reading purpose
    private void recursiveAppendItem(Node node, String[] tagPath, String[] attributeName, String[] data) {
        Element element;
        String tagName= tagPath[0];
        if(tagPath.length > 1) {
            tagPath = removeFirstElement(tagPath);

            NodeList nodeList = node.getChildNodes();
            if(nodeList.getLength() < 1) {
                element = document.createElement(tagName);
                node.appendChild(element);
                setId(element);
                Node nodeChield = node.getChildNodes().item(nodeList.getLength()-1);
                recursiveAppendItem(nodeChield, tagPath, attributeName, data);
            } else {
                Node currentNode = null;
                boolean doesTagExist = false;
                for (int i = 0; i < nodeList.getLength(); i++) {
                    currentNode = nodeList.item(i);
                    if(currentNode.getNodeName().equals(tagName)) {
                        doesTagExist = true;
                        break;
                    }
                }
                if(doesTagExist) {
                    recursiveAppendItem(currentNode, tagPath, attributeName, data);
                } else {
                    element = document.createElement(tagName);
                    node.appendChild(element);
                    setId(element);
                    Node nodeChield = node.getChildNodes().item(nodeList.getLength()-1);
                    recursiveAppendItem(nodeChield, tagPath, attributeName,  data);
                }
            }
        } else {
            element = document.createElement(tagName);
            node.appendChild(element);
            setId(element);
            appendAttribute(element, attributeName, data);
            return;
        }
    }

    private void appendAttribute(Element element, String[] attributeName, String[] data) {
        Attr[] attr = new Attr[data.length];

        for (int i = 0; i < data.length; i++) {
            attr[i] = document.createAttribute(attributeName[i]);
            attr[i].setValue(data[i]);
            element.setAttributeNode(attr[i]);
        }
    }

    public void appendCommunication(ComunicacaoSerial instancia, String[] data) {
        String[] tag = {"communication"};
        String[] attrName = {"protocol", "name", "index"};
        String[] index = {"0"};
        String[] dataBuffer = Stream.concat(Arrays.stream(data), Arrays.stream(index)).toArray(String[]::new);

        if(!comunicacaoSerial.isEmpty()) {
            index[0] = Integer.toString(comunicacaoSerial.size());
        }
        
        appendItem(tag, attrName, dataBuffer);

        comunicacaoSerial.add(instancia);
    }

    public ComunicacaoSerial getCommunication(String name) {
        int index = getCommunicationIndexByName(name);
        return comunicacaoSerial.get(index);
    }

    private int getCommunicationIndexByName(String name) {
        NodeList commTags = document.getElementsByTagName("communication");
        Node currentNode;
        for (int i = 0; i < commTags.getLength(); i++) {
            currentNode = commTags.item(i);
            NamedNodeMap nodeAttrList = currentNode.getAttributes();
            for (Node node : NamedNodeMapIterableUtil.of(nodeAttrList)) {
                if (node.getNodeValue().equals(name)) {
                    for (Node nodeIndex : NamedNodeMapIterableUtil.of(nodeAttrList)) {
                        if (nodeIndex.getNodeName().equals("index")) {
                            return Integer.parseInt(nodeIndex.getNodeValue());
                        }
                    }
                }
            }
        }
        return -1;
    }

    public void setId(Element element) {
        Attr attr;
        attr = document.createAttribute("id");
        attr.setValue(Integer.toString(id));
        element.setAttributeNode(attr);
        element.setIdAttribute("id", true);

        id += 1;
    }


    public void setTestName(String testName) {
        String[] testNameBuffer = {testName};
        this.testName = testNameBuffer;
    }

    public float getStepData(int step) {
        printData();
        Element element = document.getElementById(Integer.toString(step));
        float data = Float.parseFloat(element.getAttribute("data"));
        return data;
    }


    //@TODO: Erase Section

    

    public void printData() {
        System.out.println(prettyPrintByTransformer(document, 2, true));
    }

    public String prettyPrintByTransformer(/*String*/ Document xmlString, int indent, boolean ignoreDeclaration) {
        
        try {
            //InputSource src = new InputSource(new StringReader(xmlString));
            //Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src);
            Document document = xmlString;
    
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, ignoreDeclaration ? "yes" : "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    
            Writer out = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(out));

            
            return out.toString();
        } catch (Exception e) {
            System.out.println("dentro do printException");
            throw new RuntimeException("Error occurs when pretty-printing xml:\n" + xmlString, e);
        }
    }

    public String[] removeFirstElement(String[] arr) {
        String newArr[] = new String[arr.length - 1];
        for (int i = 1; i < arr.length; i++) {
            newArr[i-1] = arr[i];
        }
        return newArr;
    }
}




/* 
private void recursiveAppendItem(Node node, String[] tagPath, String[] attributeName, String[] data) {
        Element element;
        String tagName= tagPath[0];
        if(tagPath.length > 1) {
            tagPath = removeFirstElement(tagPath);

            NodeList nodeList = node.getChildNodes();
            if(nodeList.getLength() < 1) {
                element = document.createElement(tagName);
                node.appendChild(element);
                Node nodeChield = node.getChildNodes().item(nodeList.getLength()-1);
                recursiveAppendItem(nodeChield, tagPath, attributeName, data);
            } else {
                Node currentNode = null;
                boolean doesTagExist = false;
                for (int i = 0; i < nodeList.getLength(); i++) {
                    currentNode = nodeList.item(i);
                    if(currentNode.getNodeName().equals(tagName)) {
                        doesTagExist = true;
                        break;
                    }
                }
                if(doesTagExist) {
                    recursiveAppendItem(currentNode, tagPath, attributeName, data);
                } else {
                    element = document.createElement(tagName);
                    node.appendChild(element);
                    Node nodeChield = node.getChildNodes().item(nodeList.getLength()-1);
                    recursiveAppendItem(nodeChield, tagPath, attributeName,  data);
                }
            }
        } else {
            element = document.createElement(tagName);
            node.appendChild(element);
            appendAttribute(element, attributeName, data);
            return;
        }
    }
*/