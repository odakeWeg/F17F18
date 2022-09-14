package com.edson.util;

//@TODO: Não Mudança para o handler
/* 
import java.util.LinkedHashMap;

import org.w3c.dom.Node;

import com.edson.communication.ComunicacaoSerial;

public class TagConfigurationUtil {
    //@TODO: Insert RIGHT attributes and implement functions
    //@TODO: Data mapping
    //@TODO: Como mostrar os dados de falha ? ToString() : dentroDessaClasse : naClasseDeTeste 
    private static LinkedHashMap<Integer, String> resultSequence = new LinkedHashMap<>();

    private static ComunicacaoSerial gambiarra;

    public static void reset(){
        resultSequence = new LinkedHashMap<>();
    }

    public static String executeTagFunction(Node node) {
        String currentTestResult;

        switch (node.getNodeName()) {
            case "communication":
                currentTestResult = startConnectionCommand(getAttributeString("portName" , node), getAttributeInt("baudRate" , node), getAttributeInt("dataBits" , node), getAttributeInt("stopBits" , node), getAttributeString("parity" , node), getAttributeInt("timeout" , node), getAttributeString("protocol" , node));
                resultSequence.put(getAttributeInt("step", node), currentTestResult);
                break;
            case "test":
                currentTestResult = setTestNameCommand(getAttributeString("name" , node));
                resultSequence.put(getAttributeInt("step", node), currentTestResult);
                break;
            case "write":
                currentTestResult = writeCommand(getAttributeInt("address" , node), getAttributeArray("registers" , node), getAttributeInt("timeOut" , node), getAttributeInt("waitBefore" , node), getAttributeInt("waitAfter" , node));
                resultSequence.put(getAttributeInt("step", node), currentTestResult);
                break;
            case "read":
                currentTestResult = readCommand(getAttributeInt("address" , node), getAttributeArray("registers" , node), getAttributeInt("timeOut" , node), getAttributeInt("waitBefore" , node), getAttributeInt("waitAfter" , node));
                resultSequence.put(getAttributeInt("step", node), currentTestResult);
                break;
            case "compare":
                currentTestResult = compareCommand(getAttributeString("reference" , node), getAttributeString("measure" , node), getAttributeArray("referenceRegisters" , node), getAttributeArray("measureRegisters" , node), getAttributeInt("referenceScale" , node), getAttributeInt("measureScale" , node), getAttributeArray("tolerancyPercentage" , node));
                resultSequence.put(getAttributeInt("step", node), currentTestResult);
                break;
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

    //TODO: toda tag tem uma etapa de teste, utilizada para trace error
    //TODO: Printar na tela uma mensagem customizada para cada falha
    //@TestingCode
    private static String compareCommand(String reference, String measure, int[] referenceRegisters, int[] measureRegisters, int referenceScale, int measureScale, int[] tolerancyPercentage) {
        System.out.println("compareCommand");
        if (referenceRegisters.length != measureRegisters.length || measureRegisters.length != tolerancyPercentage.length) {
            return "string";
        }
        for (int i = 0; i < referenceRegisters.length; i++) {}
        return "approved";
    }

    private static String readCommand(int address, int[] registers, int timeOut, int waitBefore, int waitAfter) {
        System.out.println("readCommand");
        
        try {
            //Register[] leitura = gambiarra.getSerialModbusCommunication().readHoldingRegisters((short) 0, (short) 3);
            //System.out.println("valores:" + leitura[0] + " - " + leitura[1] + " - " + leitura[2]);
            //System.out.println("valores:" + leitura[0].intValue() + " - " + leitura[1].intValue() + " - " + leitura[2].intValue());
        } catch (Exception e) {
            System.out.println("readCommand exception");
            e.printStackTrace();
        }
        

        return "approved";
    }

    private static String writeCommand(int address, int[] registers, int timeOut, int waitBefore, int waitAfter) {
        System.out.println("writeCommand");
        
        try {
            //gambiarra.getSerialModbusCommunication().writeSingleRegister((short) 27, (short) 2);
        } catch (Exception e) {
            System.out.println("writeCommand exception");
            e.printStackTrace();
        }

        return "approved";
    }

    private static String setTestNameCommand(String name) {
        System.out.println("setTestNameCommand");
        if(name.equals("parametrizacao")) {
            System.out.println("In: " + name);
            //gambiarra.getSerialModbusCommunication().disconnect();
        } else {
            System.out.println("Out: " + name);
        }
        
        return "approved";
    }
    //@ERROR: comunicacaoSerial.add adiciona infinicamente
    private static String startConnectionCommand(String portName, int baudRate, int dataBits, int stopBits, String parity, int timeout, String protocol) {
        switch (protocol) {
            case "modbus":
                //@TODO: Add created instance in a List or TreeThing
                try {
                    System.out.println("startConnectionCommand");
                    ComunicacaoSerial comunicacaoSerial = new ComunicacaoSerial(portName, baudRate, dataBits, stopBits, parity, timeout);
                } catch (Exception e) {
                    System.out.println("sendEx");
                }
                //@TestingCode
                //gambiarra = comunicacaoSerial;
                break;
            default:
        }
        return "approved";
    }

    //TypeManipulation

    private static int getAttributeInt(String attributeName, Node node) {
        return Integer.parseInt(node.getAttributes().getNamedItem(attributeName).getNodeValue());
    }

    private static int[] getAttributeArray(String attributeName, Node node) {
        String[] value = node.getAttributes().getNamedItem(attributeName).getNodeValue().split(",");
        int[] output = new int[value.length];
        for (int i = 0; i < value.length; i++) {
            output[i] = Integer.parseInt(value[i]);
        }

        return output;
    }

    private static String getAttributeString(String attributeName, Node node) {
        return node.getAttributes().getNamedItem(attributeName).getNodeValue();
    }

    //Setters and Getters
    public static LinkedHashMap<Integer, String> getresultSequence() {
        return resultSequence;
    }

    public static void setresultSequence(LinkedHashMap<Integer, String> resultSequence) {
        TagConfigurationUtil.resultSequence = resultSequence;
    }
}
*/