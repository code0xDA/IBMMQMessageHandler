package util;

import java.util.Hashtable;

public class TestMQMessageDefaultHandler {

	public static void main(String args[]) {

		MQMessageDefaultHandler mqMessageHandler = new MQMessageDefaultHandler();
		Hashtable<Object, Object> qMgrConProperty = new Hashtable<Object, Object>();
        
		qMgrConProperty.put("hostname", "52.34.27.202");
		qMgrConProperty.put("port", 6464);
		qMgrConProperty.put("channel", "CHANNEL");
		qMgrConProperty.put("CCSID", 1381);

		mqMessageHandler.sendSingleMessageToQueue("QMANAGER", "QUEUE", "THIS IS SAMPLE MESSAGE", qMgrConProperty, true);
 
	}

}
