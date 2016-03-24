package util;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 * @author 0xda
 *
 */
public interface MQMessageHandler {

	public void sendSingleMessageToQueue(String qMgr, String dest, String msg,
			Hashtable<Object, Object> qMgrConProperty, boolean noReadBack);

	public void senSingleMessageToQueue(String qMgr, String dest, String msg,
			Hashtable<Object, Object> qMgrConProperty);

	public void sendBulkMessageToQueue(String qMgr, HashMap<String, LinkedList<String>> msgBulk,
			Hashtable<Object, Object> qMgrConProperty);

	public void sendSingleMessageToTopic(String qMgr, String topicName, String topicObject, String msg,
			Hashtable<Object, Object> qMgrConProperty, boolean noReadBack);

}
