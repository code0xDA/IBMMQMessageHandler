package util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.MQTopic;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.MQC;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;

/**
 * @0xda
 *
 */
public class MQMessageDefaultHandler implements MQMessageHandler {

	@SuppressWarnings("deprecation")
	private static int MQ_DESTINATION_OPEN_OPTION = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT;

	private static int MQ_TOPIC_OPEN_AS_PUBLICATION = CMQC.MQTOPIC_OPEN_AS_PUBLICATION;

	/**
	 * This method is used to send message to MQ queue with noReadBack option
	 * configured.
	 * 
	 * @param noReadBack
	 *            - if true this disables the reading back the options MQPMO
	 *            field. This avoids the overhead of converting it.
	 */
	public void sendSingleMessageToQueue(String qMgr, String dest, String msg,
			Hashtable<Object, Object> qMgrConProperty, boolean noReadBack) {
		MQQueueManager manager = this.getQueueManager(qMgr, qMgrConProperty);
		if (manager != null && manager.isConnected()) {
			MQQueue queue = this.getMQQueue(manager, dest);
			MQMessage message = new MQMessage();
			if (queue != null && message != null) {
				try {
					message.writeString(msg);
					queue.put(message, getMQPutMessageOptions(noReadBack));
					queue.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (MQException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						manager.disconnect();
					} catch (MQException e) {
						e.printStackTrace();
					}
				}
			}

		} else {

			// TODO: add logs

		}

	}

	/**
	 * Method used to send message to MQ queue without options set
	 */
	public void senSingleMessageToQueue(String qMgr, String dest, String msg,
			Hashtable<Object, Object> qMgrConProperty) {

		MQQueueManager manager = this.getQueueManager(qMgr, qMgrConProperty);
		if (manager != null && manager.isConnected()) {
			MQQueue queue = this.getMQQueue(manager, dest);
			MQMessage message = new MQMessage();
			if (queue != null && message != null) {
				try {
					message.writeString(msg);

					queue.put(message);
					queue.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (MQException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();

				} finally {
					try {
						manager.disconnect();
					} catch (MQException e) {
						e.printStackTrace();
					}
				}
			}

		} else {

			// TODO

		}
	}

	/**
	 * @param qMgr
	 * @param msgBulk
	 * @param qMgrConProperty
	 */
	@SuppressWarnings("unchecked")
	public void sendBulkMessageToQueue(String qMgr, HashMap<String, LinkedList<String>> msgBulk,
			Hashtable<Object, Object> qMgrConProperty) {
		MQQueueManager manager = this.getQueueManager(qMgr, qMgrConProperty);
		if (manager != null && manager.isConnected()) {

			try {

				Iterator<Entry<String, LinkedList<String>>> it = msgBulk.entrySet().iterator();
				while (it.hasNext()) {
					@SuppressWarnings("rawtypes")
					Map.Entry pair = (Map.Entry) it.next();
					String queueName = (String) pair.getKey();
					MQQueue queue = this.getMQQueue(manager, queueName);
					LinkedList<String> msgList = (LinkedList<String>) pair.getValue();

					ListIterator<String> listIterator = msgList.listIterator();
					while (listIterator.hasNext()) {
						MQMessage message = new MQMessage();

						message.writeString(listIterator.next());

						queue.put(message);
					}

					queue.close();

					it.remove(); // avoids a ConcurrentModificationException
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (MQException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					manager.disconnect();
				} catch (MQException e) {
					e.printStackTrace();
				}
			}

		} else {

			// TODO: add logs

		}

	}

	public void sendSingleMessageToTopic(String qMgr, String topicName, String topicObject, String msg,
			Hashtable<Object, Object> qMgrConProperty, boolean noReadBack) {

		MQQueueManager manager = this.getQueueManager(qMgr, qMgrConProperty);
		if (manager != null && manager.isConnected()) {
			MQTopic topic = this.getMQTopic(manager, topicName, topicObject);
			MQMessage message = new MQMessage();
			if (topic != null && message != null) {
				try {
					message.writeString(msg);
					topic.put(message, getMQPutMessageOptions(noReadBack));
					topic.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (MQException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						manager.disconnect();
					} catch (MQException e) {
						e.printStackTrace();
					}
				}
			}

		} else {

			// TODO: add logs

		}

	}

	private MQQueueManager getQueueManager(String qMgr, Hashtable<Object, Object> qMgrConProperty) {

		MQQueueManager queueManager = null;
		try {
			queueManager = new MQQueueManager(qMgr, qMgrConProperty);
		} catch (MQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return queueManager;
	}

	private MQPutMessageOptions getMQPutMessageOptions(boolean noReadBack) {

		return new MQPutMessageOptions(noReadBack);

	}

	private MQQueue getMQQueue(MQQueueManager queueManager, String queueName) {

		MQQueue queue = null;

		if (queueManager != null) {

			try {
				queue = queueManager.accessQueue(queueName, MQ_DESTINATION_OPEN_OPTION);
			} catch (MQException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return queue;

	}

	private MQTopic getMQTopic(MQQueueManager queueManager, String topicName, String topicObject) {

		MQTopic topic = null;

		if (queueManager != null) {

			try {
				topic = queueManager.accessTopic(topicName, topicObject, MQ_TOPIC_OPEN_AS_PUBLICATION,
						MQ_DESTINATION_OPEN_OPTION);
			} catch (MQException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return topic;

	}

}
