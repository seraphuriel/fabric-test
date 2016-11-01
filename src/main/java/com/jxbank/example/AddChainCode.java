package com.jxbank.example;

import org.hyperledger.fabric.sdk.shim.ChaincodeBase;
import org.hyperledger.fabric.sdk.shim.ChaincodeStub;


/**
 * Created by minxianglin on 2016/11/1.
 */
public class AddChainCode extends ChaincodeBase {

	public static void main(String[] args) {
		new AddChainCode().start(args);
	}

	@Override
	public String run(ChaincodeStub stub, String function, String[] args) {

		if (args == null || args.length == 0) {
			throw new RuntimeException("invoke error");
		}
		switch (function) {
			case "add":
				addFun(stub, args);
				break;
			case "flush":
				flushFun(stub, args);
				break;
			default:
				throw new RuntimeException("invoke error");
		}
		return null;
	}

	private void flushFun(ChaincodeStub stub, String[] args) {
		if (args.length != 1) {
			throw new RuntimeException("invoke args wrong");
		}
		stub.putState(args[0], "0");
	}

	private void addFun(ChaincodeStub stub, String[] args) {
		if (args.length != 2) {
			throw new RuntimeException("invoke args wrong");
		}
		if (stub.getState(args[0]) == null) {
			stub.putState(args[0], args[1]);
		} else {
			stub.putState(args[0], String.valueOf(Integer.parseInt(stub.getState(args[0]))) + Integer.parseInt
					(args[1]));
		}
	}

	@Override
	public String query(ChaincodeStub stub, String function, String[] args) {
		if (args == null || args.length != 1) {
			throw new RuntimeException("invoke args wrong");
		}
		return stub.getState(args[0]);
	}

	@Override
	public String getChaincodeID() {
		return "addFunction";
	}
}
