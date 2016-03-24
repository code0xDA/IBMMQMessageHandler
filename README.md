# IBMMQMessageHandler
Java class to send message to IBM MQ queue and topic

## API

	-public void sendSingleMessageToQueue(String qMgr, String dest, String msg,
			Hashtable<Object, Object> qMgrConProperty, boolean noReadBack);

	-public void senSingleMessageToQueue(String qMgr, String dest, String msg,
			Hashtable<Object, Object> qMgrConProperty);

	-public void sendBulkMessageToQueue(String qMgr, HashMap<String, LinkedList<String>> msgBulk,
			Hashtable<Object, Object> qMgrConProperty);

	-public void sendSingleMessageToTopic(String qMgr, String topicName, String topicObject, String msg,
			Hashtable<Object, Object> qMgrConProperty, boolean noReadBack);


##Example Code

	MQMessageDefaultHandler mqMessageHandler = new MQMessageDefaultHandler();
		Hashtable<Object, Object> qMgrConProperty = new Hashtable<Object, Object>();
        
		qMgrConProperty.put("hostname", "52.34.27.202");
		qMgrConProperty.put("port", 6464);
		qMgrConProperty.put("channel", "CHANNEL");
		qMgrConProperty.put("CCSID", 1381);

		mqMessageHandler.sendSingleMessageToQueue("QMANAGER", "QUEUE", "THIS IS SAMPLE MESSAGE", qMgrConProperty, true);
