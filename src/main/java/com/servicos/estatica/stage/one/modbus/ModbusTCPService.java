package com.servicos.estatica.stage.one.modbus;

import java.net.InetAddress;

import com.ghgande.j2mod.modbus.io.ModbusTCPTransaction;
import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersRequest;
import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersResponse;
import com.ghgande.j2mod.modbus.msg.WriteSingleRegisterRequest;
import com.ghgande.j2mod.modbus.net.TCPMasterConnection;
import com.ghgande.j2mod.modbus.procimg.Register;
import com.ghgande.j2mod.modbus.procimg.SimpleRegister;

public class ModbusTCPService {

	protected static TCPMasterConnection con = null; // the conection
	protected static ModbusTCPTransaction trans = null; // the transaction

	protected static ReadMultipleRegistersRequest req = null; // the request
	protected static ReadMultipleRegistersResponse res = null; // the response

	protected static WriteSingleRegisterRequest writeRegister = null; // the request

	protected static ReadMultipleRegistersRequest inputReq = null;
	protected static ReadMultipleRegistersResponse inputRes = null;

	protected static InetAddress addr = null; // the slave's address
	protected static final int ref = 20; // the reference; offset where to start reading from
	protected static final int count = 1; // the number of DI's to read
	protected static final int repeat = 1; // a loop for repeating the transaction

	public void setConnectionParams(String ip, int port) {
		try {
			addr = InetAddress.getByName(ip);
			con = new TCPMasterConnection(addr);
			con.setPort(port);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Integer readSingleRegisterRequest(Integer addr) {
		try {
			con.connect();
			req = new ReadMultipleRegistersRequest(addr, 1);
			trans = new ModbusTCPTransaction(con);
			req.setUnitID(1);
			trans.setRequest(req);
			trans.execute();
			res = (ReadMultipleRegistersResponse) trans.getResponse();
			con.close();
			return res.getRegisterValue(0);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public Boolean readSlaveInput() {
		try {
			con.connect();
			trans = new ModbusTCPTransaction(con);
			inputReq = new ReadMultipleRegistersRequest(10, 1);
			inputReq.setUnitID(1);
			trans.setRequest(inputReq);
			trans.execute();
			inputRes = (ReadMultipleRegistersResponse) trans.getResponse();
			con.close();
			if (inputRes.getRegisterValue(0) == 1)
				return true;
			else
				return false;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public Boolean[] readMultiplePoints(int ref, int qtd) {
		Boolean[] results = null;
		try {
			con.connect();
			trans = new ModbusTCPTransaction(con);
			inputReq = new ReadMultipleRegistersRequest(ref, qtd);
			inputReq.setUnitID(1);
			trans.setRequest(inputReq);
			trans.execute();
			inputRes = (ReadMultipleRegistersResponse) trans.getResponse();

			Register[] regs = inputRes.getRegisters();
			if (regs.length > 0)
				results = new Boolean[regs.length];
			else
				return new Boolean[0];
			
			for (int i = 0; i < regs.length; i++) {
				Integer value = inputRes.getRegisterValue(i);
				if (value == 1)
					results[i] = true;
				else
					results[i] = false;
			}

			con.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return results;
	}

	public void writeRegisterRequest(Integer regRef, Integer value) {
		try {
			con.connect();
			trans = new ModbusTCPTransaction(con);
			req.setUnitID(1);
			writeRegister = new WriteSingleRegisterRequest(regRef, new SimpleRegister(value));
			trans.setRequest(writeRegister);
			trans.execute();
			con.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
