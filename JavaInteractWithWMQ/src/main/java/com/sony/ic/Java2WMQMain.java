package com.sony.ic;

import java.io.IOException;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.headers.MQHeaderList;
import com.ibm.mq.headers.MQRFH2;

public class Java2WMQMain {

	public static void main(String[] args) throws Exception {
		System.out.println("Strat");
		MQEnvironment.hostname = "seldlnx305.corpusers.net.";
		MQEnvironment.port = 1423;
		MQEnvironment.channel = "CLIENTS.IC.ADMIN";
		
		System.out.println("Try connect");
		MQQueueManager queueManager = new MQQueueManager("ICQM107T");
		
		MQQueue queue = queueManager.accessQueue("Yainsun.Test",CMQC.MQOO_OUTPUT);
		
		//construct mqrfh2 header, reference: https://www.capitalware.com/rl_blog/?p=4823
		MQMessage myMessage = new MQMessage();
		
		MQRFH2 rfh2 = new MQRFH2();
		
		rfh2.setFieldValue("usr", "ContractId", "ICCS020B");
		rfh2.setFieldValue("other", "ICHeader.BrokerName", "ICBK101Y");
		
		rfh2.write(myMessage);
		
		String content = "Yainsun Han test content";
		myMessage.writeString(content);
		
		myMessage.format = CMQC.MQFMT_RF_HEADER_2;
		
		// Use the default put message options...
		MQPutMessageOptions pmo = new MQPutMessageOptions();
		 
		// put the message!
		queue.put(myMessage,pmo);
		
		queueManager.disconnect();
		
		System.out.println("End");
	}

}
