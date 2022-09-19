package com.edson.routine.handler;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
 
//@TestingCode
public class TestDataTreeHandlerDeprecated {
 
    public static final String xmlFilePath = "src/main/resources/com/edson/TestRoutine/test.xml";
 
    public static void test() {
 
        try {
 
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
 
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
 
            Document document = documentBuilder.newDocument();
 
            // root element
            Element root = document.createElement("company");
            document.appendChild(root);
 
            // employee element
            Element employee = document.createElement("employee");
 
            root.appendChild(employee);
 
            // set an attribute to staff element
            Attr attr = document.createAttribute("id");
            attr.setValue("10");
            employee.setAttributeNode(attr);
 
            //you can also use staff.setAttribute("id", "1") for this
 
            // firstname element
            Element firstName = document.createElement("firstname");
            firstName.appendChild(document.createTextNode("James"));
            employee.appendChild(firstName);
 
            // lastname element
            Element lastname = document.createElement("lastname");
            lastname.appendChild(document.createTextNode("Harley"));
            employee.appendChild(lastname);
 
            // email element
            Element email = document.createElement("email");
            email.appendChild(document.createTextNode("james@example.org"));
            employee.appendChild(email);
 
            // department elements
            Element department = document.createElement("department");
            department.appendChild(document.createTextNode("Human Resources"));
            employee.appendChild(department);



            System.out.println(prettyPrintByTransformer(document, 2, true));
 
            /* 
            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));
 
            // If you use
            // StreamResult result = new StreamResult(System.out);
            // the output will be pushed to the standard output ...
            // You can use that for debugging 
 
            transformer.transform(domSource, streamResult);
            */
            System.out.println("Done creating XML File");
 
        } catch (ParserConfigurationException /*| TransformerException*/ ex) {
            ex.printStackTrace();
        }
    }

    public static String prettyPrintByTransformer(/*String*/ Document xmlString, int indent, boolean ignoreDeclaration) {
        System.out.println("antes do try");
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

            System.out.println("dentro do print");
            return out.toString();
        } catch (Exception e) {
            System.out.println("dentro do printException");
            throw new RuntimeException("Error occurs when pretty-printing xml:\n" + xmlString, e);
        }
    }
}

//@TODO: Refactor
    /*
    public void appendItemDeprecated(String tagName, String nodeName, String action, String data) {
        Element root, nodeElement, leafElement;
        Element t;

        
        root = document.createElement("root");
        document.appendChild(root);

        nodeElement = document.createElement(nodeName);
        root.appendChild(nodeElement);

        Element car = document.createElement("Car");
        nodeElement.appendChild(car);

        Element n = document.createElement("node");
        nodeElement.appendChild(n);

        Element as = document.createElement("node");
        nodeElement.appendChild(as);

        leafElement = document.createElement(action);
        nodeElement.appendChild(leafElement);

        Element a = document.createElement("a");
        nodeElement.appendChild(a);
        
        
        System.out.println(document.getDocumentElement().getChildNodes().item(0));
        System.out.println(document.getDocumentElement().getChildNodes().item(1));
        System.out.println(document.getDocumentElement().getChildNodes().item(2));
        System.out.println(document.getDocumentElement().getChildNodes().item(3));
        
        System.out.println(document.getDocumentElement().getNodeName().equals("root"));
        System.out.println(document.getDocumentElement().equals("root"));

        System.out.println(document.getDocumentElement().getChildNodes().item(0).getChildNodes().toString());
        
        printData();
    }
    */

    //@TODO: Refactor
    /*
    public void recursiveAppendItem(Node node, String[] tagPath, String data) {
        Element element;
        String tagName= tagPath[0];
        if(tagPath.length > 1) {
            tagPath = removeFirstElement(tagPath);

            NodeList nodeList = node.getChildNodes();
            if(nodeList.getLength() < 1) {
                element = document.createElement(tagName);
                node.appendChild(element);
                Node nodeChield = node.getChildNodes().item(nodeList.getLength()-1);
                recursiveAppendItem(nodeChield, tagPath, data);
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
                    recursiveAppendItem(currentNode, tagPath, data);
                } else {
                    element = document.createElement(tagName);
                    node.appendChild(element);
                    Node nodeChield = node.getChildNodes().item(nodeList.getLength()-1);
                    recursiveAppendItem(nodeChield, tagPath, data);
                }
            }
        } else {
            NodeList nodeList = node.getChildNodes();
            if(nodeList.getLength() < 1) {
                element = document.createElement(tagName);
                node.appendChild(element);
                appendAttribute(element, data);
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
                    element = document.createElement(tagName);
                    node.appendChild(element);
                    appendAttribute(element, data);
                } else {
                    element = document.createElement(tagName);
                    node.appendChild(element);
                    appendAttribute(element, data);
                }
            }
        }
    }
    */

    //@TODO: Refactor
    /* 
    private void appendAttribute(Node node, Element element, String data) {
        Attr attr;
        String attribute = "";
        NamedNodeMap nodeAttributes = node.getAttributes();
        for (int i = 0; i < nodeAttributes.getLength(); ++i) {
            Node attrNode = nodeAttributes.item(i);
            if (attrNode.getNodeName() == "data") {
                attribute = attrNode.getNodeValue();
            }
        }
        attr = document.createAttribute("data");
        attr.setValue(data);
        element.setAttributeNode(attr);
    }
    */

    //@TODO: Refactor
    /* 
    public void recursiveAppendItemDeprecated(Node node, String[] tagPath, String data) {
        Attr attr;
        Element element;
        String tagName, attrValue;
        if(tagPath.length >= 1) {
            tagName = tagPath[0];
            tagPath = removeFirstElement(tagPath);

            NodeList nodeList = node.getChildNodes();
            if(nodeList.getLength() < 1) {
                element = document.createElement(tagName);
                node.appendChild(element);
                Node nodeChield = node.getChildNodes().item(0);
                if(tagPath.length == 0) {
                    attr = document.createAttribute("data");
                    attrValue = attr.getValue();
                    if(attrValue=="") {
                        attr.setValue(attrValue + data);
                        element.setAttributeNode(attr);
                    } else {
                        attr.setValue(attrValue+ "," + data);
                        element.setAttributeNode(attr);
                    }
                    return;
                }
                recursiveAppendItem(nodeChield, tagPath, data);
            } else {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node currentNode = nodeList.item(i);
                    if(currentNode.getNodeName().equals(tagName)) {
                        if(tagPath.length == 0) {
                            element = document.createElement(tagName);
                            attr = document.createAttribute("data");
                            attrValue = attr.getValue();
                            if(attrValue=="") {
                                attr.setValue(attrValue + data);
                                element.setAttributeNode(attr);
                            } else {
                                attr.setValue(attrValue+ "," + data);
                                element.setAttributeNode(attr);
                            }
                            return;
                        }
                        recursiveAppendItem(currentNode, tagPath, data);
                    } else {
                        if(nodeList.getLength() == (i+1)) {
                            element = document.createElement(tagName);
                            currentNode.appendChild(element);
                            if(tagPath.length == 0) {
                                attr = document.createAttribute("data");
                                attrValue = attr.getValue();
                                if(attrValue=="") {
                                    attr.setValue(attrValue + data);
                                    element.setAttributeNode(attr);
                                } else {
                                    attr.setValue(attrValue+ "," + data);
                                    element.setAttributeNode(attr);
                                }
                                return;
                            } else {
                                Node currentNodeChield = currentNode.getChildNodes().item(0);
                                recursiveAppendItem(currentNodeChield, tagPath, data);
                            }
                        }
                    }
                }
            }
        }
    }
    */


    /*
    String[] tagPath1 = {"dir1", "dir2", "dir3", "dir44"};
        String[] tagPath2 = {"a1", "a2", "a3", "dir44"};
        String[] tagPath3 = {"a1", "dir2", "dir3", "dir44"};
        String[] tagPath4 = {"dir3", "dir2", "dir23", "dir5"};
        String[] tagPath5 = {"dir1", "dir12", "dir3", "dir4"};
        String[] tagPath6 = {"dir11"};
        TestDataTreeHandler TestDataTreeHandler = new TestDataTreeHandler();
        TestDataTreeHandler.appendItem(tagPath1, "asd1,as");
        TestDataTreeHandler.appendItem(tagPath1, "asd1,as,14,14,2");
        //TestDataTreeHandler.printData();
        TestDataTreeHandler.appendItem(tagPath2, "1,2,3");
        //TestDataTreeHandler.printData();
        TestDataTreeHandler.appendItem(tagPath3, "1,2,4,3");
        //TestDataTreeHandler.printData();
        TestDataTreeHandler.appendItem(tagPath4, "dado,r1");
        TestDataTreeHandler.appendItem(tagPath5, "dado,1");
        TestDataTreeHandler.appendItem(tagPath6, "dado");
        TestDataTreeHandler.printData();
        //TestDataTreeHandler.appendItemDeprecated("tagName", "nodeName", "action", "data");
        //TestDataTreeHandler.appendItemDeprecated("tagName","node","action","data");
    */