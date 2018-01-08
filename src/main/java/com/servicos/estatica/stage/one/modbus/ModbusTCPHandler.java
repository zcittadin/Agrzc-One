package com.servicos.estatica.stage.one.modbus;

import com.ghgande.j2mod.modbus.Modbus;

public class ModbusTCPHandler {
	
	private static ModbusTCPService modbusService = new ModbusTCPService();
	
	private static final String IP = "192.168.1.25";
	protected static final int PORT = Modbus.DEFAULT_PORT;

}
