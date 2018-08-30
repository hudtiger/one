package test.ibm;

import java.util.Hashtable;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.msg.client.wmq.compat.base.internal.MQC;

public class testIBM {

//	IBM MQ
//	管理器：QM_GLS
//	通道：CNN_GLS
//	IP 192.168.1.6（外网IP 203.195.150.196） 端口1414 用户名：glsmq  密码：Glsibmmq_2018.tfp
//	队列名：
//	"QUEUE_RECV" 接收队列
//	"QUEUE_REPLY" 应答队列

	static String RECVQUEUE = "QUEUE_RECV";
	static String REPLYQUEUE = "QUEUE_REPLY";
	static MQQueueManager qMgr = null;
	static {
//		Hashtable properties = new Hashtable();
//		properties.put("hostname", "203.195.150.196");
//		properties.put("port", new Integer(1414));
//		properties.put("channel", "CNN_GLS");
//		properties.put("CCSID", new Integer(1381));
//		properties.put("userID","glsmq");
//		properties.put("password","Glsibmmq_2018.tfp");
//		properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES);
		
		MQEnvironment.hostname="203.195.150.196";
		MQEnvironment.port = 1414;
		MQEnvironment.channel="CNN_GLS";
		MQEnvironment.CCSID = 1381;
		MQEnvironment.userID = "glsmq";
		MQEnvironment.password="Glsibmmq_2018.tfp";
		MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES);
		try {
//			qMgr= new MQQueueManager("QM_GLS",properties);
			qMgr= new MQQueueManager("QM_GLS");
		} catch (MQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void sendMsg(String msgStr) {
		int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT | MQC.MQOO_INQUIRE;
		System.out.println("发送openOptions=="+openOptions);
		MQQueue queue = null;
		try {
			// 建立Q1通道的连接
			System.out.println("准备连接队列："+REPLYQUEUE);
			queue = qMgr.accessQueue(RECVQUEUE, openOptions, null, null, null);
			System.out.println("队列连接成功");
			MQMessage msg = new MQMessage();// 要写入队列的消息
			msg.format = MQC.MQFMT_STRING;
			msg.characterSet = 1381;
			msg.encoding = 1381;
			// msg.writeObject(msgStr); //将消息写入消息对象中
			msg.writeString(msgStr);
			MQPutMessageOptions pmo = new MQPutMessageOptions();
			msg.expiry = -1; // 设置消息用不过期
			queue.put(msg, pmo);// 将消息放入队列
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (queue != null) {
				try {
					queue.close();
				} catch (MQException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
 
	public static void receiveMsg() {
		int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT | MQC.MQOO_INQUIRE;
		MQQueue queue = null;
		System.out.println("接收openOptions=="+openOptions);
		try {
			System.out.println("准备连接队列："+RECVQUEUE);
			queue = qMgr.accessQueue(RECVQUEUE, openOptions, null, null, null);
			System.out.println("队列连接成功,该队列当前的深度为:" + queue.getCurrentDepth());
			System.out.println("===========================");
			int depth = queue.getCurrentDepth();
			// 将队列的里的消息读出来
			while (depth-- > 0) {
				MQMessage msg = new MQMessage();// 要读的队列的消息
				MQGetMessageOptions gmo = new MQGetMessageOptions();
				queue.get(msg, gmo);
				System.out.println("消息的大小为：" + msg.getDataLength());
				System.out.println("消息的内容：\n" + msg.readStringOfByteLength(msg.getDataLength()));
				System.out.println("---------------------------");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (queue != null) {
				try {
					queue.close();
				} catch (MQException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		sendMsg("Message01");
//		Thread.sleep(5000);
	//	receiveMsg();
	}

}
