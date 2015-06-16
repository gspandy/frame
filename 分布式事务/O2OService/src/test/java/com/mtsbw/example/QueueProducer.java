package com.mtsbw.example;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class QueueProducer {
	// 原始队列 XT3T
	static String[] INITDATE = {"MB.ERP.DR.O2O.INITDATE","{\"lstSfSchTaskExecOosDtls\":[{\"ID\":0,\"EXEC_OOS_ID\":0,\"PROD_NUM\":\"21100099156\",\"QTY\":6,\"LOC_ID\":\"\",\"RCPT_LOC_ID\":\"\",\"PRICE\":349.0,\"DISC_RATE\":87.4687,\"BRAND_ID\":null,\"EntityState\":0,\"Selected\":false}],\"ID\":0,\"SF_SCH_TASK_EXEC_ID\":0,\"DISP_WAREH_CODE\":\"A10045S001\",\"SHOP_CODE\":\"A54330S001\",\"IS_NEED_LOCK_WAREH\":\"1\",\"IS_ATUO_GDN\":\"1\",\"IS_ATUO_GRN\":\"1\",\"FILL_RATE\":0.0,\"IS_OLD_ERP\":\"1\",\"RCV_USER\":\"覃冰淋\",\"RCV_ADDRESS\":\"光谷大道1号\",\"RCV_PHONENO\":\"13800010001\",\"DATA_SOURCE\":\"02\",\"VENDER_CODE\":null,\"VENDEE_CODE\":null,\"IDT_CODE\":null,\"ORDER_VAL\":0.0,\"HAD_LOCK_WAREH\":\"0\",\"IS_OOS\":\"0\",\"SRC_DOC_TYPE\":0,\"SRC_UNIT_CODE\":\"HQ01\",\"SRC_DOC_CODE\":\"5115185\",\"IS_ONLINE\":\"0\",\"B2C_DOC_CODE\":\"03106358\",\"OS_DOC_CODE\":\"975\",\"RCV_RATIO\":10,\"DOC_SOURCE\":\"03\",\"EntityState\":0,\"Selected\":false}"};
	// XXXS
//	static String[] INITDATE = {"MB.ERP.DR.O2O.INITDATE","{\"lstSfSchTaskExecOosDtls\":[{\"ID\":0,\"EXEC_OOS_ID\":0,\"PROD_NUM\":\"51650001148\",\"QTY\":2,\"LOC_ID\":\"\",\"RCPT_LOC_ID\":\"\",\"PRICE\":349.0,\"DISC_RATE\":87.4687,\"BRAND_ID\":null,\"EntityState\":0,\"Selected\":false}],\"ID\":0,\"SF_SCH_TASK_EXEC_ID\":0,\"DISP_WAREH_CODE\":\"A54320S001\",\"SHOP_CODE\":\"HQ01S116\",\"IS_NEED_LOCK_WAREH\":\"1\",\"IS_ATUO_GDN\":\"1\",\"IS_ATUO_GRN\":\"1\",\"FILL_RATE\":0.0,\"IS_OLD_ERP\":\"1\",\"RCV_USER\":\"覃冰淋\",\"RCV_ADDRESS\":\"光谷大道1号\",\"RCV_PHONENO\":\"13800010001\",\"DATA_SOURCE\":\"02\",\"VENDER_CODE\":null,\"VENDEE_CODE\":null,\"IDT_CODE\":null,\"ORDER_VAL\":0.0,\"HAD_LOCK_WAREH\":\"0\",\"IS_OOS\":\"0\",\"SRC_DOC_TYPE\":0,\"SRC_UNIT_CODE\":\"HQ01\",\"SRC_DOC_CODE\":\"5100512\",\"IS_ONLINE\":\"0\",\"B2C_DOC_CODE\":\"03106235\",\"OS_DOC_CODE\":\"888\",\"RCV_RATIO\":10,\"DOC_SOURCE\":\"03\",\"EntityState\":0,\"Selected\":false}"};
	// XTTT
//	static String[] INITDATE = {"MB.ERP.DR.O2O.INITDATE","{\"lstSfSchTaskExecOosDtls\":[{\"ID\":0,\"EXEC_OOS_ID\":0,\"PROD_NUM\":\"27629293130\",\"QTY\":1,\"LOC_ID\":\"\",\"RCPT_LOC_ID\":\"\",\"PRICE\":349.0,\"DISC_RATE\":87.4687,\"BRAND_ID\":null,\"EntityState\":0,\"Selected\":false}],\"ID\":0,\"SF_SCH_TASK_EXEC_ID\":0,\"DISP_WAREH_CODE\":\"A10045S001\",\"SHOP_CODE\":\"HQ01S116\",\"IS_NEED_LOCK_WAREH\":\"1\",\"IS_ATUO_GDN\":\"1\",\"IS_ATUO_GRN\":\"1\",\"FILL_RATE\":0.0,\"IS_OLD_ERP\":\"1\",\"RCV_USER\":\"覃冰淋\",\"RCV_ADDRESS\":\"光谷大道1号\",\"RCV_PHONENO\":\"13800010001\",\"DATA_SOURCE\":\"02\",\"VENDER_CODE\":null,\"VENDEE_CODE\":null,\"IDT_CODE\":null,\"ORDER_VAL\":0.0,\"HAD_LOCK_WAREH\":\"1\",\"IS_OOS\":\"0\",\"SRC_DOC_TYPE\":1,\"SRC_UNIT_CODE\":\"HQ01\",\"SRC_DOC_CODE\":\"5089249\",\"IS_ONLINE\":\"0\",\"B2C_DOC_CODE\":\"03106131\",\"OS_DOC_CODE\":\"275\",\"RCV_RATIO\":31,\"DOC_SOURCE\":\"03\",\"EntityState\":0,\"Selected\":false}"};
	
//	static String[] INITDATE = {"MB.ERP.DR.O2O.INITDATE","{\"lstSfSchTaskExecOosDtls\":[{\"ID\":0,\"EXEC_OOS_ID\":0,\"PROD_NUM\":\"21100099156\",\"QTY\":5,\"LOC_ID\":\"\",\"RCPT_LOC_ID\":\"\",\"PRICE\":349.0,\"DISC_RATE\":87.4687,\"BRAND_ID\":null,\"EntityState\":0,\"Selected\":false}],\"ID\":0,\"SF_SCH_TASK_EXEC_ID\":0,\"DISP_WAREH_CODE\":\"A54320S001\",\"SHOP_CODE\":\"A00045S001\",\"IS_NEED_LOCK_WAREH\":\"1\",\"IS_ATUO_GDN\":\"1\",\"IS_ATUO_GRN\":\"1\",\"FILL_RATE\":0.0,\"IS_OLD_ERP\":\"1\",\"RCV_USER\":\"覃冰淋\",\"RCV_ADDRESS\":\"光谷大道1号\",\"RCV_PHONENO\":\"13800010001\",\"DATA_SOURCE\":\"02\",\"VENDER_CODE\":null,\"VENDEE_CODE\":null,\"IDT_CODE\":null,\"ORDER_VAL\":0.0,\"HAD_LOCK_WAREH\":\"1\",\"IS_OOS\":\"0\",\"SRC_DOC_TYPE\":1,\"SRC_UNIT_CODE\":\"HQ01\",\"SRC_DOC_CODE\":\"5089292\",\"IS_ONLINE\":\"0\",\"B2C_DOC_CODE\":\"03106201\",\"OS_DOC_CODE\":\"287\",\"RCV_RATIO\":30,\"DOC_SOURCE\":\"03\",\"EntityState\":0,\"Selected\":false}"};
//	static String[] INITDATE = {"MB.ERP.DR.O2O.INITDATE","{\"lstSfSchTaskExecOosDtls\":[{\"ID\":0,\"EXEC_OOS_ID\":0,\"PROD_NUM\":\"10101347030\",\"QTY\":3,\"LOC_ID\":\"\",\"RCPT_LOC_ID\":\"\",\"PRICE\":349.0,\"DISC_RATE\":87.4687,\"BRAND_ID\":null,\"EntityState\":0,\"Selected\":false}],\"ID\":0,\"SF_SCH_TASK_EXEC_ID\":0,\"DISP_WAREH_CODE\":\"HQ01W021\",\"SHOP_CODE\":\"A03461S001\",\"IS_NEED_LOCK_WAREH\":\"1\",\"IS_ATUO_GDN\":\"1\",\"IS_ATUO_GRN\":\"1\",\"FILL_RATE\":0.0,\"IS_OLD_ERP\":\"1\",\"RCV_USER\":\"覃冰淋\",\"RCV_ADDRESS\":\"光谷大道1号\",\"RCV_PHONENO\":\"13800010001\",\"DATA_SOURCE\":\"02\",\"VENDER_CODE\":null,\"VENDEE_CODE\":null,\"IDT_CODE\":null,\"ORDER_VAL\":0.0,\"HAD_LOCK_WAREH\":\"0\",\"IS_OOS\":\"0\",\"SRC_DOC_TYPE\":1,\"SRC_UNIT_CODE\":\"HQ01\",\"SRC_DOC_CODE\":\"5089906\",\"IS_ONLINE\":\"0\",\"B2C_DOC_CODE\":\"03106202\",\"OS_DOC_CODE\":\"575\",\"RCV_RATIO\":30,\"DOC_SOURCE\":\"03\",\"EntityState\":0,\"Selected\":false}"};
	// XX3S 转配
//	static String[] PREPARE = {"MB.ERP.DR.O2O.PREPARE","{\"b2cDocCode\":\"03106057\",\"dataSource\":\"02\",\"dispWarehCode\":\"HQ01W424\",\"lstSfSchTaskExecOosDtls\":[{\"discRate\":100,\"execOosId\":0,\"id\":1915542,\"locId\":\"\",\"price\":119,\"prodNum\":\"21122290152\",\"qty\":1,\"rcptLocId\":\"A002\"}],\"fillRate\":0,\"hadLockWareh\":\"1\",\"id\":1916095,\"isAtuoGdn\":\"1\",\"isAtuoGrn\":\"1\",\"isNeedLockWareh\":\"1\",\"isOldErp\":\"1\",\"isOnline\":\"0\",\"isOos\":\"1\",\"orderVal\":0,\"osDocCode\":\"120427558577\",\"rcvAddress\":\"芷江西路\",\"rcvPhoneno\":\"13690909090\",\"rcvUser\":\"Ｃｈａｎｇ\",\"rulesGroup\":[],\"sfSchTaskExecId\":1916095,\"shopCode\":\"A00030S001\",\"srcDocCode\":\"5047099\",\"srcDocType\":\"NG\",\"rcvRatio\":31,\"srcUnitCode\":\"HQ01\"}"};
	// 调配
//	static String[] PREPARE = {"MB.ERP.DR.O2O.PREPARE","{\"b2cDocCode\":\"03106320\",\"dataSource\":\"02\",\"dispWarehCode\":\"A00030S001\",\"lstSfSchTaskExecOosDtls\":[{\"discRate\":100,\"execOosId\":0,\"id\":1915782,\"locId\":\"A001\",\"price\":119,\"prodNum\":\"21100099156\",\"qty\":1}],\"fillRate\":0,\"hadLockWareh\":\"1\",\"id\":1916367,\"isAtuoGdn\":\"1\",\"isAtuoGrn\":\"1\",\"isNeedLockWareh\":\"1\",\"isOldErp\":\"1\",\"isOnline\":\"0\",\"isOos\":\"1\",\"orderVal\":0,\"osDocCode\":\"120427566767\",\"rcvAddress\":\"ＧＹ１８\",\"rcvPhoneno\":\"13676765432\",\"rcvUser\":\"gy\",\"rulesGroup\":[],\"sfSchTaskExecId\":1916367,\"shopCode\":\"HQ06W001\",\"srcDocCode\":\"000000944\",\"srcDocType\":\"FG\",\"rcvRatio\":31,\"srcUnitCode\":\"A01339\"}"};
	public static void main(String[] args) {
		String[] MY_DATA = INITDATE;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD,
				"tcp://10.100.20.158:61616");
//				"tcp://localhost:61616");
		Connection connection = null;
		try {
			// 构造从工厂得到连接对象 
			connection = connectionFactory.createConnection();// 启动 
			connection.start();// 获取操作连接
			Session session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(new ActiveMQQueue(MY_DATA[0]));
			producer.setDeliveryMode(DeliveryMode.PERSISTENT); // 发送持久消息
			ObjectMessage message = session.createObjectMessage();
			message.setObject(MY_DATA[1]);
			producer.send(message);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				connection.close();
			} catch (JMSException e) {}
		}
	}
	
//	public static void main(String[] args) {
//		BaseBizVo vo = new BaseBizVo();
//		vo.setCurrency("RMB");
//		vo.setBizType("ADN");
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("A", "11111");
//		map.put("B", 22222);
//		map.put("C", "11111");
//		vo.setExtraParams(map);
////		vo.getExtraParams().put("D", 4444.1);
////		vo.getExtraParams().put("E", NewBillType.TBN);
//		
//		String ss = JSON.toJSONString(vo);
//		System.out.println(ss);
//		BaseBizVo bb = JSON.parseObject(ss,BaseBizVo.class);
//		System.out.println(bb);
//		BigDecimal ii = SoaBillUtils.toObject(bb.getExtraParams().get("D"),BigDecimal.class);
//		System.out.println(ii);
//		NewBillType tbn = SoaBillUtils.toObject(bb.getExtraParams().get("E"),NewBillType.class);
//		System.out.println(tbn);
//	}
}
