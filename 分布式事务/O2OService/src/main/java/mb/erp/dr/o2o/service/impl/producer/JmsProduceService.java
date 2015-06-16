package mb.erp.dr.o2o.service.impl.producer;

import javax.annotation.Resource;

import mb.erp.dr.o2o.service.impl.mq.JmsSend;
import mb.erp.dr.soa.constant.O2OBillConstant.AllocType;
import mb.erp.dr.soa.constant.O2OBillConstant.GdnMode;
import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.BaseBizVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.old.vo.ScnVo;
import mb.erp.dr.soa.service.dubbo.ActiveMqDubboService;
import mb.erp.dr.soa.vo.SfSchTaskExecOosVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * activemq生产者服务
 * 
 * @author     余从玉
 * @version    1.0, 2014-11-20
 * @see         JmsProducerService
 * @since       全流通改造
 */
public class JmsProduceService {

	private final Logger logger = LoggerFactory.getLogger(JmsProduceService.class);
	@Resource
	private ActiveMqDubboService activeMqDubboService;
	@Resource
	private JmsSend jmsSend;
	
	public void initialData(String json)   {
		jmsSend.sendToInitialDataQueue(json);
	}
	/**
	 * 预处理接口( B2C发货消息写入队列接口)
	 */
	public Integer prepareHandler(SfSchTaskExecOosVo vo)   {
		Integer result = 0;
		try {
//			result = activeMqDubboService.saveSfSchTaskExecOos(vo);
			jmsSend.send(JSON.toJSONString(vo));
		} catch (Exception e) {
			String msg = "预处理接口:JmsProduceServiceImpl.prepareHandler 出现异常，";
			logger.error(msg+e.getMessage());
			throw new RuntimeException(msg+e.getMessage());
		}
		return result;
	}

	/**
	 * 预处理订单写入两方调配队列
	 */
	public void t2Queue(BaseBizVo vo)  {
		vo.setGdnMode(GdnMode.AGRT); // 出库方式
		jmsSend.sendToT2queue(JSON.toJSONString(vo));
	}
	
	/**
	 * 写入订单队列
	 */
	public void idtQueue(SfSchTaskExecOosVo vo)  {
		if (vo.getVendeeCode().equals(vo.getVenderCode())) {// 收货方和发货方一致，出库方式就为SHOR
			vo.setGdnMode(GdnMode.SHOR); // 出库方式
		}else if (AllocType.XXXS.equals(vo.getAllocType())//调拨
				|| AllocType.XX3Z.equals(vo.getAllocType())//转配
				|| AllocType.XX3T.equals(vo.getAllocType())) { // 三方调配
			vo.setGdnMode(GdnMode.AGOR); // 出库方式
		}
		jmsSend.sendToIdtQueue(JSON.toJSONString(vo));
	}
	
	/**
	 * 写入配货队列
	 */
	public void adnQueue(AdnVo vo)  {
		jmsSend.sendToAdnQueue(JSON.toJSONString(vo));
	}
	
	/**
	 * 写入出库队列
	 */
	public void gdnQueue(GdnVo vo)  {
		jmsSend.sendToGdnQueue(JSON.toJSONString(vo));
	}
	
	/**
	 * 写入入库队列
	 */
	public void grnQueue(GrnVo vo)  {
		jmsSend.sendToGrnQueue(JSON.toJSONString(vo));
	}
	
	/**
	 * 写入退货申请单队列
	 */
	public void bgrQueue(SfSchTaskExecOosVo vo) {
		jmsSend.sendToBgrQueue(JSON.toJSONString(vo));
	}
	
	/**
	 * 写入退货出库队列
	 */
	public void thGdnQueue(GdnVo vo) {
		jmsSend.sendToThgdnQueue(JSON.toJSONString(vo));
	}
	
	/**
	 * 写入退货单队列
	 */
	public void scnQueue(ScnVo vo) {
		jmsSend.sendToScnQueue(JSON.toJSONString(vo));
	}
	
	/**
	 * 写入退货入库队列 
	 */
	public void thGrnQueue(GrnVo vo) {
		jmsSend.sendToThgrnQueue(JSON.toJSONString(vo));
	}
}
