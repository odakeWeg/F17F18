package com.edson.routine.handler;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;

import com.edson.communication.ComunicacaoSerial;

import com.edson.routine.handler.TestDataTreeHandler;

import net.weg.wcomm.modbus.NegativeConfirmationException;
import net.weg.wcomm.modbus.Register;
import net.weg.wcomm.modbus.exception.ModbusExceptionResponseException;
import net.weg.wcomm.modbus.exception.ModbusUnexpectedResponseException;
import net.weg.wcomm.modbus.wrapper.WriteMultipleRegistersRequestWrapper;


public class TagConfigurationHandler {
    //@TODO: Data mapping
    //@TODO: Como mostrar os dados de falha ? ToString() : dentroDessaClasse : naClasseDeTeste 
    //@TODO: Maybe set a default constant for the tags attributes
    //@TODO: Adicionar metodos auxiliares para diminuir a quantidade de parametros -> método para pegar a comunicação, usada em diversos métodos
    //@TODO: Implement communication errors, timeouts and etc 
    //@TODO: Cada tag deveria ter uma classe, e como construtor da classe, pode ser passado o node. -> dessa forma, não seria necessário todos esses parâmetros no método
    private LinkedHashMap<Integer, String> resultSequence = new LinkedHashMap<>();

    private TestDataTreeHandler testDataTreeHandler;

    

    public TagConfigurationHandler() throws ParserConfigurationException {
        testDataTreeHandler = new TestDataTreeHandler();
    }

    public void reset(){
        resultSequence = new LinkedHashMap<>();
    }

    public String executeTagFunction(Node node) throws NegativeConfirmationException, ModbusExceptionResponseException, ModbusUnexpectedResponseException, InterruptedException {
        String currentTestResult;

        switch (node.getNodeName()) {
            case "communication":
                currentTestResult = startConnectionCommand(getAttributeString("name" , node), getAttributeString("portName" , node), getAttributeInt("baudRate" , node), getAttributeInt("dataBits" , node), getAttributeInt("stopBits" , node), getAttributeString("parity" , node), getAttributeInt("timeout" , node), getAttributeString("protocol" , node));
                resultSequence.put(getAttributeInt("step", node), currentTestResult);
                break;
            case "test":
                currentTestResult = setTestNameCommand(getAttributeString("name" , node));
                resultSequence.put(getAttributeInt("step", node), currentTestResult);
                break;
            case "write":
                currentTestResult = writeCommand(getAttributeString("communicationName" , node), getAttributeInt("address" , node), getAttributeArray("registers" , node), getAttributeArray("value" , node), getAttributeInt("timeOut" , node), getAttributeInt("waitBefore" , node), getAttributeInt("waitAfter" , node));
                resultSequence.put(getAttributeInt("step", node), currentTestResult);
                break;
            case "read":
                currentTestResult = readCommand(getAttributeString("communicationName" , node), getAttributeInt("address" , node), getAttributeArray("registers" , node), getAttributeInt("timeOut" , node), getAttributeInt("waitBefore" , node), getAttributeInt("waitAfter" , node));
                resultSequence.put(getAttributeInt("step", node), currentTestResult);
                break;
            case "compare":
                currentTestResult = compareCommand(getAttributeInt("referenceStep" , node), getAttributeInt("targetStep" , node), getAttributeInt("referenceScale" , node), getAttributeInt("measureScale" , node), getAttributeArray("tolerancyPercentage" , node));
                resultSequence.put(getAttributeInt("step", node), currentTestResult);
                break;
            case "verify":
                currentTestResult = verifyCommand(getAttributeInt("targetStep" , node), getAttributeInt("measureScale" , node), getAttributeInt("value" , node), getAttributeArray("tolerance" , node));
                resultSequence.put(getAttributeInt("step", node), currentTestResult);
            case "pass":
            case "root":
            case "#text":
                //@TODO: Verificar se pode ser removido
                currentTestResult = "approved";
                return currentTestResult;
            default:
            currentTestResult = "non identified Tag";
                return currentTestResult;
                //@TODO: Avisar que tag deve ser implementada (nenhuma tag com esse nome encontrada)
        }
        
        resultSequence.put(getAttributeInt("step", node), currentTestResult);

        return currentTestResult;
    }

    //@TODO: Toda tag tem uma etapa de teste, utilizada para trace error
    //@TODO: Printar na tela uma mensagem customizada para cada falha
    //@TODO: Use BigData instead
    //@TestingCode
    private String compareCommand(int referenceStep, int targetStep, int referenceScale, int measureScale, int[] tolerancyPercentage) {
        System.out.println("compareCommand");
        String[] endTag = {"compare"};
        String[] attributeName = {"data"};
        String[] tagPath;
        String[] readString = new String[1];
        //testDataTreeHandler.printData();
        
        float targetData = testDataTreeHandler.getStepData(targetStep);
        float referenceData = testDataTreeHandler.getStepData(referenceStep);

        float error = Math.abs(targetData - referenceData)/targetData;
        boolean isInTolerance = error < tolerancyPercentage[0];

        if(!isInTolerance) {
            return "ERROR";
        }

        readString[0] = Float.toString(targetData); 
        tagPath = concatenateStrings("compareImprob", endTag);
        testDataTreeHandler.appendItem(tagPath, attributeName, readString);

        testDataTreeHandler.printData();

        return "approved";
    }

    private String verifyCommand(int targetStep, int measureScale, int value, int[] tolerance) {
        System.out.println("compareCommand");
        String[] endTag = {"compare"};
        String[] attributeName = {"data"};
        String[] tagPath;
        String[] readString = new String[1];
        //testDataTreeHandler.printData();
        
        float targetData = testDataTreeHandler.getStepData(targetStep);

        float error = Math.abs(targetData - value);
        boolean isInTolerance = error < tolerance[0];

        if(!isInTolerance) {
            return "ERROR";
        }

        readString[0] = Float.toString(targetData); 
        tagPath = endTag; //concatenateStrings("compareImprob", endTag);
        testDataTreeHandler.appendItem(tagPath, attributeName, readString);

        return "approved";
    }


    //@TODO: Define the parameters that will be passed to compare
    /* 
    private String compareCommand(String reference, String measure, int[] referenceRegisters, int[] measureRegisters, int referenceScale, int measureScale, int[] tolerancyPercentage) {
        System.out.println("compareCommand");
        testDataTreeHandler.printData();
        if (referenceRegisters.length != measureRegisters.length || measureRegisters.length != tolerancyPercentage.length) {
            return "string";
        }
        for (int i = 0; i < referenceRegisters.length; i++) {}
        return "approved";
    }
    */


    //@TODO: Implementar corretamente na classe de comunicação
    //@TODO: Jogar append para o handler ou para a classe que futuramente será criada por tag
    private String readCommand(String communicationName, int address, int[] registers, int timeOut, int waitBefore, int waitAfter) throws NegativeConfirmationException, ModbusExceptionResponseException, ModbusUnexpectedResponseException, InterruptedException {
        System.out.println("readingStuff");
        String[] endTag = {"read"};
        String[] attributeName = {"data"};
        String[] tagPath;
        String[] readString = new String[registers.length];

        Register[] readings = new Register[registers.length];
        ComunicacaoSerial readComm = testDataTreeHandler.getCommunication(communicationName);
        readComm.getSerialModbusCommunication().setUnitID((short) address);
        for (int i = 0; i < registers.length; i++) {
            readings[i] = readComm.getSerialModbusCommunication().readHoldingRegisters((short) registers[i], (short) 1)[0];
            readString[i] = Integer.toString(readings[i].intValue());
        }

        tagPath = endTag; //concatenateStrings(communicationName, endTag);
        testDataTreeHandler.appendItem(tagPath, attributeName, readString);

        return "approved";
    }

    //@TODO: Mudar implementação, forloop desnecessário
    private String writeCommand(String communicationName, int address, int[] registers, int[] value, int timeOut, int waitBefore, int waitAfter) throws NegativeConfirmationException, ModbusExceptionResponseException, ModbusUnexpectedResponseException {
        System.out.println("writingStuff");
        String[] endTag = {"write"};
        String[] attributeName = {"data"};
        String[] tagPath;
        String[] writeString = new String[registers.length];
        
        ComunicacaoSerial writeComm = testDataTreeHandler.getCommunication(communicationName);
        for (int i = 0; i < registers.length; i++) {
            short[] valueArray = {(short) value[i]};
            writeComm.getSerialModbusCommunication().writeMultipleRegisters((short) registers[i], valueArray);

            writeString[i] = Integer.toString(value[i]);
        }

        tagPath = endTag; //concatenateStrings(communicationName, endTag);
        testDataTreeHandler.appendItem(tagPath, attributeName, writeString);

        return "approved";
    }

    private String setTestNameCommand(String name) {
        //@TestingCode
        testDataTreeHandler.printData();
        testDataTreeHandler.setTestName(name);
        return "pass";
    }
    //@ERROR: comunicacaoSerial.add adiciona infinicamente
    //@TODO: Verificar o melhor jeito de tratar o exception -> dentro dessa função ou dentro da classe
    private String startConnectionCommand(String name, String portName, int baudRate, int dataBits, int stopBits, String parity, int timeout, String protocol) throws NegativeConfirmationException, ModbusExceptionResponseException, ModbusUnexpectedResponseException {
        System.out.println("startConnectionCommand");
        switch (protocol) {
            case "modbus":
                String[] data = {protocol, name};
                ComunicacaoSerial comunicacaoSerial = new ComunicacaoSerial(portName, baudRate, dataBits, stopBits, parity, timeout);
                testDataTreeHandler.appendCommunication(comunicacaoSerial, data);
                break;
            default:
                return "Error";
        }

        return "approved";
    }

    //TypeManipulation

    private int getAttributeInt(String attributeName, Node node) {
        return Integer.parseInt(node.getAttributes().getNamedItem(attributeName).getNodeValue());
    }

    private int[] getAttributeArray(String attributeName, Node node) {
        String[] value = node.getAttributes().getNamedItem(attributeName).getNodeValue().split(",");
        int[] output = new int[value.length];
        for (int i = 0; i < value.length; i++) {
            output[i] = Integer.parseInt(value[i]);
        }

        return output;
    }

    private String getAttributeString(String attributeName, Node node) {
        return node.getAttributes().getNamedItem(attributeName).getNodeValue();
    }

    private String[] concatenateStrings(String[] one, String[] two) {
        String[] stringConcatenated = Stream.concat(Arrays.stream(one), Arrays.stream(two)).toArray(String[]::new);
        return stringConcatenated;
    }

    private String[] concatenateStrings(String one, String two) {
        String[] oneBuffer = {one};
        String[] twoBuffer = {two};
        String[] stringConcatenated = Stream.concat(Arrays.stream(oneBuffer), Arrays.stream(twoBuffer)).toArray(String[]::new);
        return stringConcatenated;
    }

    private String[] concatenateStrings(String[] one, String two) {
        String[] twoBuffer = {two};
        String[] stringConcatenated = Stream.concat(Arrays.stream(one), Arrays.stream(twoBuffer)).toArray(String[]::new);
        return stringConcatenated;
    }

    private String[] concatenateStrings(String one, String[] two) {
        String[] oneBuffer = {one};
        String[] stringConcatenated = Stream.concat(Arrays.stream(oneBuffer), Arrays.stream(two)).toArray(String[]::new);
        return stringConcatenated;
    }

    //Setters and Getters
    
}
