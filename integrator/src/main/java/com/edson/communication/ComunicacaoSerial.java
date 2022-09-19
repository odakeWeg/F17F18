package com.edson.communication;

import net.weg.wcomm.modbus.Register;
import net.weg.wcomm.modbus.tcp.client.ModbusTCPHelper;
import net.weg.wcomm.modbus.tcp.client.ModbusTCPMaster;
import net.weg.wcomm.serial.SComm;

//@TODO: Implementar métodos de verificações -> atualmente essa classe é inútil
//@TODO: Implement Specific methods -> may require to fragment TagConfigurationHandler into multiple classes
public class ComunicacaoSerial extends BaseCommunication {
	private SComm serialSettings;
	private ModbusTCPHelper serialModbusCommunication;

	public ModbusTCPHelper getSerialModbusCommunication() {
		return serialModbusCommunication;
	}

	//@TODO: Change address connection string from 1 to 0
	//@TODO: Check if using the constructor to configure is good practice
	public ComunicacaoSerial(String portName, int baudRate, int dataBits, int stopBits, String parity, int timeout) {
		//@TODO: Use XML or Util class to identify dinamically the hostAddress and the port of Wcomm
		//@TODO: Verify if closePor() is necessary 
		if (this.serialSettings != null) {
			serialSettings.closePort();
		}
		try {
			ModbusTCPMaster master = new ModbusTCPMaster.Builder().hostAddress("localhost").port(34502).build();
			serialModbusCommunication = new ModbusTCPHelper(master);
			serialModbusCommunication.connect();
			//@TestingCode
			//serialModbusCommunication.subscribeClient("Serial/COM4/Modbus-RTU/@1#57600#8#1#EVEN#0#50#1000#40");
			serialModbusCommunication.subscribeClient("Serial/"+ portName + "/Modbus-RTU/@1#" + baudRate + "#" + dataBits + "#" + stopBits + "#" + parity + "#0#50#" + timeout + "#40");
		} catch (Exception e) {
			//@TODO: Implement something if necessary (probably is)
			e.printStackTrace();
		}
	}

	/* 
	//@TODO: Change address connection string from 1 to 0
	public void connect(String portName, int baudRate, int dataBits, int stopBits, String parity, int timeout) {
		if (this.serialSettings != null) {
			serialSettings.closePort();
		}
		try {
			ModbusTCPMaster master = new ModbusTCPMaster.Builder().hostAddress("localhost").port(34502).build();
			serialModbusCommunication = new ModbusTCPHelper(master);
			serialModbusCommunication.connect();
			serialModbusCommunication.subscribeClient("Serial/"+ portName + "/Modbus-RTU/@1#" + baudRate + "#" + dataBits + "#" + stopBits + "#" + parity + "#0#50#" + timeout + "#40");
		} catch (Exception e) {
			//@TODO: Implement something if necessary (probably is)
			e.printStackTrace();
		}
	}
	*/
}
