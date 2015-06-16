/*
\ * 文件名：JmsConsumerService.java 版权：Copyright 2014 MetersBonwe. All Rights Reserved. 描述： 修改人：Weijf 修改时间：下午12:53:18 修改内容：
 */

package mb.erp.dr.o2o.service.impl.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import mb.erp.dr.o2o.service.impl.producer.AllocTypeService;
import mb.erp.dr.o2o.service.impl.producer.JmsProduceService;
import mb.erp.dr.o2o.utils.O2oUtils;
import mb.erp.dr.soa.constant.O2OBillConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.AllocType;
import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.constant.O2OBillConstant.GdnMode;
import mb.erp.dr.soa.constant.O2OBillConstant.IDT_PROGRESS;
import mb.erp.dr.soa.constant.O2OBillConstant.BASE_EXTRA;
import mb.erp.dr.soa.constant.O2OBillConstant.NewBillType;
import mb.erp.dr.soa.constant.O2OBillConstant.POF_DocStatus;
import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.old.service.bill.CommonService;
import mb.erp.dr.soa.old.service.bill.PubO2oFlowService;
import mb.erp.dr.soa.old.service.dubbo.SoaJmsDubboService;
import mb.erp.dr.soa.old.service.proxy.IBillServiceHander;
import mb.erp.dr.soa.old.service.wareh.CgdrnService;
import mb.erp.dr.soa.old.service.wareh.WarehService;
import mb.erp.dr.soa.old.vo.AdnDtlVo;
import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.BaseBizVo;
import mb.erp.dr.soa.old.vo.BgrVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnDtlVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.old.vo.IdtVo;
import mb.erp.dr.soa.old.vo.PubO2oFlowVo;
import mb.erp.dr.soa.old.vo.ScnVo;
import mb.erp.dr.soa.old.vo.TbnVo;
import mb.erp.dr.soa.service.bill.NewBillService;
import mb.erp.dr.soa.service.bill.NewERPCommonService;
import mb.erp.dr.soa.service.bill.NewPubO2oFlowService;
import mb.erp.dr.soa.service.dubbo.NewSoaJmsDubboService;
import mb.erp.dr.soa.service.proxy.INewBillServiceHander;
import mb.erp.dr.soa.service.wareh.SfGdrnService;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.vo.DrTbnVo;
import mb.erp.dr.soa.vo.SfDgnDtlVo;
import mb.erp.dr.soa.vo.SfDgnVo;
import mb.erp.dr.soa.vo.SfGdnDtlVo;
import mb.erp.dr.soa.vo.SfGdnVo;
import mb.erp.dr.soa.vo.SfGrnDtlVo;
import mb.erp.dr.soa.vo.SfGrnVo;
import mb.erp.dr.soa.vo.SfIdtVo;
import mb.erp.dr.soa.vo.SfRvdVo;
import mb.erp.dr.soa.vo.SfSchTaskExecOosDtlVo;
import mb.erp.dr.soa.vo.SfSchTaskExecOosVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;

/**
 * 队列消费者
 * 
 * @author 余从玉
 * @version
 * @see
 * @since
 */
public class JmsConsumerService {
	private final Logger logger = LoggerFactory.getLogger(JmsConsumerService.class);
	@Resource
	private SoaJmsDubboService soaJmsDubboService;
	@Resource
	private NewSoaJmsDubboService newSoaJmsDubboService;
	@Resource
	private JmsProduceService jmsProducerService;
	@Resource
	private PubO2oFlowService pubO2oFlowService;
	@Resource
	private NewPubO2oFlowService newPubO2oFlowService;
	@Resource
    private WarehService warehService;
	@Resource
	private CgdrnService cgdrnService;
	@Resource
	private SfGdrnService sfGdrnService;
    @Resource
    private NewBillService<SfDgnVo> sfDgnService;
    @Resource
    private CreateVoService createVoService;
    @Resource
    private NewCreateVoService newCreateVoService;
    @Resource
    private AllocTypeService allocTypeService;
    @Resource
    private IBillServiceHander billServiceHander;
    @Resource
    private INewBillServiceHander iNewBillServiceHander;
    @Resource
    private NewERPCommonService newERPCommonService;
    @Resource
    private CommonService commonService;
    
    /**
     * 两方调配队列
     * @param vo
     */
    public  void onMessageForT2queue(String baseBizJson) {
    	BaseBizVo vo = null;
    	Object adnNum = JSON.parseObject(baseBizJson).get("adnNum");
    	String b2cDocCode = null; // 保存异常情况的单据流，只有现货订单和调配单这样的原始单据需要这样处理，因为vo生成过程中可能报错，这样就取不到PubB2cDocCode了
    	String vendeeId = null; // 重处理需要判断当前重处理的是哪个流程
    	boolean erpFlag = false; //判断是否执行到新ERP逻辑
    	Boolean flg = false;
    	TbnVo tbnVo = new TbnVo();
    	DrTbnVo drTbnVo = new DrTbnVo();
    	MsgVo handerMsg = new MsgVo();
    	String errorMsg = null;
    	String exceptionMsg = "";
    	try {
	    	if (adnNum != null && adnNum.toString().trim().length() > 0) {
				// 计划配货单
	    		vo = JSON.parseObject(baseBizJson, AdnVo.class);
	    		vendeeId = ((AdnVo) vo).getVendeeId();
	    		b2cDocCode = vo.getPubB2cDocCode();
				tbnVo = createVoService.genTbnByAdnVo((AdnVo) vo);
			}else{
				vo = JSON.parseObject(baseBizJson, SfSchTaskExecOosVo.class);
				b2cDocCode = ((SfSchTaskExecOosVo)vo).getB2cDocCode();
				// 根据SfSchTaskExecOosVo生成调配单
				//根据商品读取品牌
				Map<String, String> map = new HashMap<String, String>();
				map.put("prod_id", ((SfSchTaskExecOosVo)vo).getLstSfSchTaskExecOosDtls().get(0).getProdNum());
				String brandId= soaJmsDubboService.selectBrandByProdID(map);
				vendeeId = ((SfSchTaskExecOosVo) vo).getVendeeCode();
				String venderId = ((SfSchTaskExecOosVo) vo).getVenderCode();
				//生成录入中的调配单
				tbnVo = createVoService.genTbnBySfSchTaskExecOosVo(((SfSchTaskExecOosVo)vo), vendeeId, venderId, brandId);
			}
            handerMsg = billServiceHander.save(tbnVo,BillType.TBN); // 保存
            if (O2OMsgConstant.MSG_CODE.ERROR.equals(handerMsg.getCode())) {
            	errorMsg = "保存调配单出现异常"+handerMsg.getMsg();
            	throw new RuntimeException(errorMsg);
			}
            tbnVo.setTbnNum(handerMsg.getBillNum());// 代码分离
            handerMsg = billServiceHander.confirm(tbnVo,BillType.TBN); // 确认
            if (O2OMsgConstant.MSG_CODE.ERROR.equals(handerMsg.getCode())){
            	errorMsg = "确认调配单出现异常"+handerMsg.getMsg();
            	throw new RuntimeException(errorMsg);
			}
            handerMsg = billServiceHander.audit(tbnVo,BillType.TBN); // 审核
            if (O2OMsgConstant.MSG_CODE.ERROR.equals(handerMsg.getCode())) {
            	errorMsg = "审核调配单出现异常"+handerMsg.getMsg();
            	throw new RuntimeException(errorMsg);
			}
            
            GdnVo gdnVo = createVoService.genGdnByTbn(tbnVo);
            gdnVo.setSfDgnCode(vo.getSfDgnCode());
            // 判断发货方是否启用新erp
            flg = newERPCommonService.isEnableNewErp(tbnVo.getVendeeId());
            logger.warn(tbnVo.getVendeeId()+"是否启用新ERP:"+flg);
        	if (flg) {
        		erpFlag = true;
        		//新ERP生成已审批的调配单
        		drTbnVo = newCreateVoService.genDrTbnByTbn(tbnVo);
        		drTbnVo.setLastFactDispWarehCode(tbnVo.getLastFactDispWarehId()); // 最终发货仓库，用之判断规则码
        		drTbnVo = newCreateVoService.genDrTbnDtlByTbnDtl(tbnVo,drTbnVo);
            	MsgVo msg = iNewBillServiceHander.save(drTbnVo,NewBillType.TBN);
            	drTbnVo.setId(msg.getNewBillId());
            	gdnVo.setDrTbnId(msg.getNewBillId());
            	msg = iNewBillServiceHander.confirm(drTbnVo,NewBillType.TBN);
            	msg = iNewBillServiceHander.audit(drTbnVo,NewBillType.TBN);
    		}
        	erpFlag = false;
            //  根据调配单生成出库单
            gdnVo.setGdnMode(tbnVo.getGdnMode()); // 出库模式
            if (AllocType.XXXT.equals(tbnVo.getAllocType())) { // 是否两方调配 
            	gdnVo.setAllocType(AllocType.XXXT); // 出库单队列标记为两方调配
			}else { // 三方调配的上一级单据为同级的另一个组织发起的计划配货单
				gdnVo.setAllocType(AllocType.XX3T); // 标记为三方调配
				// 记录下级的计划配货单号，以及最下级的购货方
			}
            gdnVo.getExtraParams().put(BASE_EXTRA.ORIGIN_DOC_NUM, drTbnVo.getTbnNum());
            gdnVo.getExtraParams().put(BASE_EXTRA.ORIGIN_DOC_TYPE, NewBillType.TBN.name());
            gdnVo.getExtraParams().put(BASE_EXTRA.ORIGIN_UNIT_ID, drTbnVo.getVenderCode());
            jmsProducerService.gdnQueue(gdnVo); // 插入出库单处理队列
        } catch (Exception e) {
        	if (erpFlag) {
        		errorMsg = "两方调配队列异常-新ERP。";
			}else {
				errorMsg = "两方调配队列异常-老ERP。";
			}
			logger.error(e.getMessage()); 
			exceptionMsg = subStringException(e.getMessage());
            logger.warn("异常提示："+exceptionMsg);
			throw new RuntimeException(errorMsg+e.getMessage());
        }finally{
        	if (null != errorMsg) {
        		logger.error(errorMsg); 
        		pubO2oFlowService.savePof4PrepareUpdate(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class),b2cDocCode, errorMsg+exceptionMsg
        				,SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO)));
			}else {
				pubO2oFlowService.savePof4Tbn(vo,tbnVo, "已生成老ERP调配单",  POF_DocStatus.SU);
				if (flg) {
					newPubO2oFlowService.savePof4DrTbn(drTbnVo,tbnVo, "已生成新ERP调配单",POF_DocStatus.SU);
				}
			}
        }
    }
    
    /**
     * 老ERP订单队列
     * @param vo
     */
    public void onMessageForIdtQueue(String sfSchTaskExecOosJson)  {
    	SfSchTaskExecOosVo vo = JSON.parseObject(sfSchTaskExecOosJson, SfSchTaskExecOosVo.class);
    	boolean erpFlag = false; //判断是否执行到新ERP逻辑
    	Boolean flg = false;
    	AdnVo adnVo = new AdnVo();
    	MsgVo msg = new MsgVo();
    	IdtVo idtVo = new IdtVo();
    	SfIdtVo sfIdtVo = new SfIdtVo();
    	String errorMsg = null;
    	String exceptionMsg = "";
        try {
        	logger.info("发货仓库"+vo.getDispWarehCode()+"门店"+vo.getShopCode()+"满足直配流程");
        	//根据商品读取品牌
        	Map<String, String> map = new HashMap<String, String>();
        	map.put("prod_id", vo.getLstSfSchTaskExecOosDtls().get(0).getProdNum());
        	String brandId= soaJmsDubboService.selectBrandByProdID(map);
        	String vendeeId = vo.getVendeeCode();
        	String venderId = vo.getVenderCode();
        	String rcvWarehId = vo.getShopCode(); // 收货仓库
        	String dispWarehId = vo.getDispWarehCode(); // 发货仓库
        	if (SoaBillUtils.isBlank(rcvWarehId)) { // 取虚拟收货仓
        		rcvWarehId = warehService.searchVirtualWarehouse(vendeeId, brandId);
        		vo.setShopCode(rcvWarehId);
			}
        	if (SoaBillUtils.isBlank(dispWarehId)) {// 取虚拟发货仓
        		dispWarehId = warehService.searchVirtualWarehouse(venderId, brandId);
        		vo.setDispWarehCode(dispWarehId);
			}
        	//生成录入中的现货订单
        	idtVo = createVoService.genIdtVoValue(vo, vendeeId, venderId, brandId);
        	msg = billServiceHander.save(idtVo,BillType.IDT);
        	if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
        		errorMsg = "保存现货订单出现异常"+msg.getMsg();
        		throw new RuntimeException(errorMsg);
        	}
        	idtVo.setIdtNum(msg.getBillNum());// 代码分离
        	msg = billServiceHander.confirm(idtVo,BillType.IDT);
        	if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
        		errorMsg = "确认现货订单出现异常"+msg.getMsg();
        		throw new RuntimeException(errorMsg);
        	}
        	msg = billServiceHander.audit(idtVo,BillType.IDT);
        	if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
        		errorMsg = "审核现货订单出现异常"+msg.getMsg();
        		throw new RuntimeException(errorMsg);
        	}
        	
        	// 判断代理商是否启用新ERP
        	String originDocNum = "";
        	flg = newERPCommonService.isEnableNewErp(idtVo.getVendeeId());
        	logger.warn(idtVo.getVendeeId()+"是否启用新ERP:"+flg);
        	if (flg) {
        		erpFlag = true;
        		//新ERP生成已审批的现货订单
        		sfIdtVo = newCreateVoService.genSfIdtVoValue(idtVo);
        		sfIdtVo = newCreateVoService.genSfIdtDtlVoValue(sfIdtVo,idtVo);
            	msg = iNewBillServiceHander.save(sfIdtVo,NewBillType.IDT);
            	sfIdtVo.setId(msg.getNewBillId());
            	sfIdtVo.setCode(msg.getCode());
            	originDocNum = msg.getCode();
            	msg = iNewBillServiceHander.confirm(sfIdtVo,NewBillType.IDT);
            	msg = iNewBillServiceHander.audit(sfIdtVo,NewBillType.IDT);
			}
        	// 将新ERP的现货订单号写入到老ERP现货订单
	    	soaJmsDubboService.modifyIdt_newErpIdtCode(idtVo, sfIdtVo.getCode());
	    	
        	erpFlag = false;
        	//根据现货订单生成计划配货单VO
        	adnVo = createVoService.genAdnByIdt(idtVo,vo);
        	adnVo.getExtraParams().put(BASE_EXTRA.ORIGIN_DOC_NUM, originDocNum);
        	adnVo.getExtraParams().put(BASE_EXTRA.ORIGIN_DOC_TYPE, NewBillType.IDT.name());
        	adnVo.getExtraParams().put(BASE_EXTRA.ORIGIN_UNIT_ID, vendeeId);
        	jmsProducerService.adnQueue(adnVo);
        } catch (Exception e) {
        	if (erpFlag) {
        		errorMsg = "现货订单队列异常-新ERP。";
			}else {
				errorMsg = "现货订单队列异常-老ERP。";
			}
        	logger.error(e.getMessage()); 
        	exceptionMsg = subStringException(e.getMessage());
        	logger.warn("异常提示："+exceptionMsg);
        	throw new RuntimeException(errorMsg+e.getMessage());
        }finally{
        	if (null != errorMsg) {
        		logger.error(errorMsg); 
        		pubO2oFlowService.savePof4PrepareUpdate(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class) , vo.getB2cDocCode(), errorMsg+exceptionMsg
        				,SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO)));
			}else {
				pubO2oFlowService.savePof4Idt(vo,idtVo, "已生成老ERP现货订单",POF_DocStatus.SU);
				if (flg) {
					newPubO2oFlowService.savePof4SfIdt(sfIdtVo,idtVo, "已生成新ERP现货订单",POF_DocStatus.SU);
				}
			}
        }
    }
    
	/**
     * 老ERP配货队列
     * @param vo
     */
    public void onMessageForAdnQueue(String adnJson)  {
    	AdnVo vo = JSON.parseObject(adnJson, AdnVo.class);
    	boolean erpFlag = false; //判断是否执行到新ERP逻辑
    	Boolean flg = false;
    	MsgVo msg = new MsgVo();
    	SfDgnVo sfDgnVo = new SfDgnVo();
    	SfIdtVo sfIdtVo = new SfIdtVo();
    	String errorMsg = null;
    	String exceptionMsg ="";
        try {
        	String dispWarehID = vo.getWarehId(); // 发货仓库
        	String rcvWarehId = vo.getTranRcvWarehId(); // 收货仓库
        	if (SoaBillUtils.isBlank(dispWarehID)) {
				dispWarehID = warehService.searchVirtualWarehouse(vo.getVenderId(), vo.getBrandId());
				vo.setWarehId(dispWarehID);
			}
        	if (SoaBillUtils.isBlank(rcvWarehId)) {
        		rcvWarehId = warehService.searchVirtualWarehouse(vo.getVendeeId(), vo.getBrandId());
				vo.setTranRcvWarehId(rcvWarehId);
			}
            //生成计划配货单
        	msg = billServiceHander.save(vo,BillType.ADN);
        	vo.setAdnNum(msg.getBillNum());// 代码分离
        	if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
        		errorMsg = "保存老ERP配货单出现异常"+msg.getMsg();
        		throw new RuntimeException(errorMsg);
        	}
        	
    		IdtVo idtVo = new IdtVo();
    		idtVo.setVendeeId(vo.getVendeeId());
    		idtVo.setIdtNum(vo.getIdtNum());
    		if (!AllocType.XXUZ.equals(vo.getAllocType())) {
    			msg = billServiceHander.orderAG(idtVo,BillType.IDT);
    			if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
    				errorMsg = "保存老ERP配货单，现货订单配货中出现异常"+msg.getMsg();
    				throw new RuntimeException(errorMsg);
    			}
			}
    		//确认计划配货单
    		msg = billServiceHander.confirm(vo,BillType.ADN);
        	if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
        		errorMsg = "确认老ERP配货单出现异常"+msg.getMsg();
        		throw new RuntimeException(errorMsg);
        	}
        	
        	if (!AllocType.XXUZ.equals(vo.getAllocType())) {
        		msg = billServiceHander.orderAD(idtVo,BillType.IDT);
        		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
        			errorMsg = "保存老ERP配货单，现货订单已配货时出现异常"+msg.getMsg();
        			throw new RuntimeException(errorMsg);
        		}
        	}
        	
        	//配货方是否启用新ERP
        	flg = newERPCommonService.isEnableNewErp(vo.getVenderId());
        	logger.warn(vo.getVenderId()+"是否启用新ERP:"+flg);
        	String sfDgnCodeUPZ = "";
        	if (flg) {
        		erpFlag = true;
        		if (AllocType.XXUZ.equals(vo.getAllocType())) {
        			//生成上级现货订单
        			sfIdtVo = newCreateVoService.genSfIdtByAdn(vo);
        			sfIdtVo = newCreateVoService.genSfIdtDtlByAdnDtl(vo,sfIdtVo);
        			msg = iNewBillServiceHander.save(sfIdtVo,NewBillType.IDT);
        			sfIdtVo.setId(msg.getNewBillId());
        			sfIdtVo.setCode(msg.getCode());
                	msg = iNewBillServiceHander.confirm(sfIdtVo,NewBillType.IDT);
                	msg = iNewBillServiceHander.audit(sfIdtVo,NewBillType.IDT);
        			//根据新ERP现货单生成交货单
        			sfDgnVo = newSoaJmsDubboService.createSfDgnBySfIdt(sfIdtVo.getId());
        			List<SfDgnDtlVo> sfDgnDtlVos = newSoaJmsDubboService.createDgnDtlBySfIdtDtl(sfIdtVo.getId());
                	sfDgnVo.setSfDgnDtlVos(sfDgnDtlVos);
        			sfDgnVo = newCreateVoService.genSfDgnBySfIdt(sfDgnVo,sfIdtVo,vo);
        			msg = iNewBillServiceHander.save(sfDgnVo,NewBillType.DGN);
        			sfDgnVo.setId(msg.getNewBillId());
        			sfDgnVo.setCode(msg.getCode());
        			sfDgnCodeUPZ = msg.getCode();
        			sfDgnVo.setLastFactDispWarehCode(vo.getLastFactDispWarehId()); // 最终发货仓
                	msg = iNewBillServiceHander.confirm(sfDgnVo,NewBillType.DGN);
                	msg = iNewBillServiceHander.audit(sfDgnVo,NewBillType.DGN);
        		}else {
					//生成已审批的交货单
        			sfDgnVo = newCreateVoService.genSfDgnByAdn(vo);
        			sfDgnVo = newCreateVoService.genSfDgnDtlByAdnDtl(sfDgnVo,vo);
        			msg = iNewBillServiceHander.save(sfDgnVo,NewBillType.DGN);
        			sfDgnVo.setId(msg.getNewBillId());
        			sfDgnVo.setCode(msg.getCode());
        			sfDgnVo.setLastFactDispWarehCode(vo.getLastFactDispWarehId()); // 最终发货仓
                	msg = iNewBillServiceHander.confirm(sfDgnVo,NewBillType.DGN);
                	msg = iNewBillServiceHander.audit(sfDgnVo,NewBillType.DGN);
				}
			}
        	erpFlag = false;
        	String orginDocNum = O2oUtils.ToString(vo.getExtraParams().get(BASE_EXTRA.ORIGIN_DOC_NUM));
        	String orginDocType = O2oUtils.ToString(vo.getExtraParams().get(BASE_EXTRA.ORIGIN_DOC_TYPE));
        	String orginUnitId = O2oUtils.ToString(vo.getExtraParams().get(BASE_EXTRA.ORIGIN_UNIT_ID));
            if (AllocType.XX3Z.equals(vo.getAllocType())) { //转配
        		//插入上级计划配货单队列（标记为上级配货单，并记录计划配货单号）
            	AdnVo adnVo = JSON.parseObject(JSON.toJSONString(vo), AdnVo.class);
            	adnVo.setAllocType(AllocType.XXUZ);
            	adnVo.setUpDocCode(vo.getAdnNum());
            	adnVo.setVendeeId(vo.getVenderId());
            	adnVo.setVenderId(vo.getUpVendeeId());
            	adnVo.setFactVendeeId(vo.getVenderId());
            	adnVo.setProgress(O2OBillConstant.PROGRESS.PG);
            	adnVo.setWarehId(vo.getUpDispWarehId()); // 原始发货仓库
            	adnVo.setTranRcvWarehId(null); // 收货仓库设置为空
            	adnVo.setSrcDocType(BillType.ADN);
            	adnVo.setSrcDocNum(vo.getAdnNum());
            	adnVo.setSrcUnitId(vo.getVenderId());
            	adnVo.setFactVendeeId(vo.getVendeeId()); // 实际购货方
            	adnVo.setDispUnitId(vo.getUpVendeeId()); // 配发组织编码
            	adnVo.setOriginAdnNum(vo.getAdnNum());
            	adnVo.setAllocationType(O2OBillConstant.CONSTANT.ALLOCTION_Z);
            	adnVo.getExtraParams().put(BASE_EXTRA.ORIGIN_DOC_NUM_FIRST, orginDocNum);
            	adnVo.getExtraParams().put(BASE_EXTRA.ORIGIN_DOC_TYPE_FIRST, orginDocType);
            	adnVo.getExtraParams().put(BASE_EXTRA.ORIGIN_UNIT_ID_FIRST, orginUnitId);
            	adnVo.setSfDgnCode(sfDgnVo.getCode());
            	Set<String> set = new HashSet<String>();
            	for(AdnDtlVo adnDtlVo :adnVo.getAdnDtlVos()){
            		adnDtlVo.setVenderId(vo.getUpVendeeId());
            		set.add(adnDtlVo.getProdId().substring(0, 6));
            	}
            	adnVo.setProductCount((double) set.size());
        		jmsProducerService.adnQueue(adnVo);
			}else if(AllocType.XX3T.equals(vo.getAllocType())){ //三方调配
				AdnVo adnVo = JSON.parseObject(JSON.toJSONString(vo), AdnVo.class);
				//插入两方调配队列 （标记为三方调配，并记录计划配货单号）
				adnVo.setAllocType(AllocType.XX3T);
				adnVo.setUpDocCode(vo.getAdnNum());
				adnVo.setOriginAdnNum(vo.getAdnNum());
				adnVo.setSfDgnCode(sfDgnVo.getCode());
				adnVo.getExtraParams().put(BASE_EXTRA.ORIGIN_DOC_NUM_FIRST, orginDocNum);
            	adnVo.getExtraParams().put(BASE_EXTRA.ORIGIN_DOC_TYPE_FIRST, orginDocType);
            	adnVo.getExtraParams().put(BASE_EXTRA.ORIGIN_UNIT_ID_FIRST, orginUnitId);
        		jmsProducerService.t2Queue(adnVo);
			}
            
            boolean flg1 = newERPCommonService.isEnableNewErp(vo.getVendeeId());
            logger.warn(">>>>>>>>>>>>"+flg1+"  "+flg+"  "+orginDocNum);
            if (flg1 && !flg) {
            	if (!AllocType.XXUZ.equals(vo.getAllocType())) {
            		iNewBillServiceHander.orderAG(null, orginDocNum, null ,vo,NewBillType.IDT);
            		iNewBillServiceHander.orderAD(null, orginDocNum, null ,vo,NewBillType.IDT);
            	}
            }
            logger.warn(">>>>>>>>>>>>"+vo.getAllocType()+"  "+vo.getVendeeId()+"  "+vo.getVenderId()+"  "+vo.getAdnNum());
            //是否上层转配或直配
            if (AllocType.XXUZ.equals(vo.getAllocType()) || AllocType.XXXS.equals(vo.getAllocType())) {
            	//插入出库单队列 （标记为转配或直配）
            	//根据计划配货单生成出库单VO	
            	GdnVo gdnVo = createVoService.genGdnByAdn(vo);
            	gdnVo.setSfDgnCode(SoaBillUtils.isBlank(vo.getSfDgnCode()) ? sfDgnVo.getCode() : vo.getSfDgnCode());
            	gdnVo.setSfDgnCodeUPZ(sfDgnCodeUPZ);
            	jmsProducerService.gdnQueue(gdnVo);
            }
        } catch (Exception e) {
        	if (erpFlag) {
        		errorMsg = "计划配货单队列异常-新ERP。";
			}else {
				errorMsg = "计划配货单队列异常-老ERP。";
			}
        	logger.error(e.getMessage()); 
        	exceptionMsg = subStringException(e.getMessage());
        	logger.warn("异常提示："+exceptionMsg);
        	throw new RuntimeException(errorMsg+e.getMessage());
        }finally{
        	if (null != errorMsg) {
        		logger.error(errorMsg); 
        		pubO2oFlowService.savePof4PrepareUpdate(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class),vo.getPubB2cDocCode(), errorMsg+exceptionMsg
        				,SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO)));
			}else {
				pubO2oFlowService.savePof4Adn(vo, "已生成老ERP配货单",POF_DocStatus.SU);
				if (flg) {
					if (AllocType.XXUZ.equals(vo.getAllocType())) {
						newPubO2oFlowService.savePof4SfIdt(sfIdtVo,vo, "已生成新ERP上层现货订单",POF_DocStatus.SU);
					}
					newPubO2oFlowService.savePof4SfDgn(sfDgnVo,vo, "已生成新ERP交货单",POF_DocStatus.SU);
				}
			}
        }
    }
    
    /**
     * 老ERP出库队列
     * @param vo
     */
    public void onMessageForGdnQueue(String gdnJson) {
    	GdnVo vo = JSON.parseObject(gdnJson, GdnVo.class);
    	boolean erpFlag = false; //判断是否执行到新ERP逻辑
    	Boolean flg = false;
    	MsgVo handerMsg = new MsgVo();
    	SfDgnVo sfDgnVo = new SfDgnVo();
    	SfGdnVo sfGdnVo = new SfGdnVo();
    	String errorMsg = null;
    	String exceptionMsg = "";
    	String remark = "";
        try {
        	handerMsg = billServiceHander.save(vo,BillType.GDN);
        	 if (O2OMsgConstant.MSG_CODE.ERROR.equals(handerMsg.getCode())) {
        		 errorMsg = "保存老ERP出库单出现异常"+handerMsg.getMsg();
         		throw new RuntimeException(errorMsg);
 			 }
        	 vo.setGdnNum(handerMsg.getBillNum()); // 代码分离
        	// 原始单据发货中
           	 BillType srcBillType = vo.getSrcDocType();
           	 if (BillType.TBN.equals(srcBillType)) {
           		 TbnVo tbnVo = new TbnVo();
           		 tbnVo.setVenderId(vo.getSrcUnitId());
           		 tbnVo.setTbnNum(vo.getSrcDocNum());
           		billServiceHander.orderDG(tbnVo,BillType.TBN); // 原始调配单发货中
    		}else if (BillType.AAD.equals(srcBillType)) {
    			if (!AllocType.XXUZ.equals(vo.getAllocType())) {
    				IdtVo idtVo = new IdtVo();
    				idtVo.setVendeeId(vo.getRcvUnitId());
    				idtVo.setIdtNum(vo.getUpDocCode());
    				billServiceHander.orderDG(idtVo,BillType.IDT); // 原始现货订单发货中
				}
    		}
        	 handerMsg = billServiceHander.confirm(vo,BillType.GDN);
        	 if (O2OMsgConstant.MSG_CODE.ERROR.equals(handerMsg.getCode())) {
          		errorMsg = "确认老ERP出库单出现异常"+handerMsg.getMsg();
          		throw new RuntimeException(errorMsg);
  			 }
        	 
        	// 出库
        	cgdrnService.billGdn(vo, vo.getSrcDocType()); // 取源单据类型
        	AllocType allocType = vo.getAllocType();
        	// 分析配货模式
        	if (AllocType.XX3Z.equals(vo.getAllocType())) {//是否转配
        		remark = "已生成转配老ERP出库单";
        	}else if (AllocType.XXUZ.equals(vo.getAllocType())) {//是否上层转配
        		remark = "已生成上层转配老ERP出库单";
			}else if (AllocType.XX3T.equals(vo.getAllocType()) || AllocType.XXLS.equals(vo.getAllocType())) {//是否三方调配
				remark = "已生成三方调配老ERP出库单";
			}else if (AllocType.XXXS.equals(vo.getAllocType())) {//是否直配
				remark = "已生成直配出库单";
			}else if (AllocType.XXXT.equals(vo.getAllocType())) {//是否两方调配
				remark = "已生成两方调配出库单";
			}else if (AllocType.XXXZ.equals(vo.getAllocType())) {//是否转账
				remark = "已生成转账出库单";
				allocType = AllocType.XXXS;
			}else {
				errorMsg = "出库队列生成出库单时，没有匹配的配货模式";
				throw new RuntimeException(errorMsg);
			}
        	
        	GrnVo grnVo = createVoService.genGrn(vo);
        	grnVo.setAllocType(allocType); // 标记配货模式
        	
        	// 发货方是否启用新erp
        	flg = newERPCommonService.isEnableNewErp(vo.getUnitId());
        	logger.warn(vo.getUnitId()+"是否启用新ERP:"+flg);
        	if (flg) {
        		erpFlag = true;
        		grnVo.setSfDgnCode(vo.getSfDgnCode());
        		grnVo.setSfDgnCodeUPZ(vo.getSfDgnCodeUPZ());
        		if (BillType.TBN.equals(srcBillType)) {
        			sfDgnVo = newCreateVoService.genSfDgnByGdn(vo);
        			//生成已审批的交货单
        			MsgVo msg = iNewBillServiceHander.save(sfDgnVo,NewBillType.DGN);
        			sfDgnVo.setId(msg.getNewBillId());
        			sfDgnVo.setCode(msg.getCode());
        			sfDgnVo.setLastFactDispWarehCode(vo.getLastFactDispWarehId()); // 最终发货仓
                	msg = iNewBillServiceHander.confirm(sfDgnVo,NewBillType.DGN);
                	msg = iNewBillServiceHander.audit(sfDgnVo,NewBillType.DGN);
                	grnVo.setSfDgnCode(sfDgnVo.getCode());
				}else {
					//队列中存在交货单号
					if (AllocType.XXUZ.equals(vo.getAllocType())) {
						sfDgnVo = newSoaJmsDubboService.selectDgnByCode(vo.getSfDgnCodeUPZ());
					}else {
						sfDgnVo = newSoaJmsDubboService.selectDgnByCode(vo.getSfDgnCode());
					}
				}
        		
				//根据交货单生成出库单
        		sfGdnVo = newSoaJmsDubboService.createSfGdnBySfDgn(sfDgnVo);
        		List<SfGdnDtlVo> sfGdnDtlVos = newSoaJmsDubboService.createSfGdnDtlBySfDgnDtl(sfDgnVo);
        		sfGdnVo.setSfGdnDtlVos(sfGdnDtlVos);
        		sfGdnVo = newCreateVoService.genSfGdnBySfDgn(sfDgnVo,sfGdnVo,vo);
        		sfGdnVo.setOsDocCode(vo.getOsDocCode());
				//生成已确认的出库单
				MsgVo msg = iNewBillServiceHander.save(sfGdnVo,NewBillType.GDN);
				grnVo.setSfGdnCode(msg.getCode());
				sfGdnVo.setId(msg.getNewBillId());
				sfGdnVo.setCode(msg.getCode());
				msg = iNewBillServiceHander.confirm(sfGdnVo,NewBillType.GDN);
	           	if (BillType.TBN.equals(srcBillType)) {
	           		iNewBillServiceHander.orderDG(vo.getDrTbnId(), "", sfGdnVo.getId(), NewBillType.TBN); // 原始调配单发货中
	    		}else if (BillType.AAD.equals(srcBillType)) {
	    			if (!AllocType.XXUZ.equals(vo.getAllocType())) {
	    				iNewBillServiceHander.orderDG(null, sfGdnVo.getOrigDocCode(), sfGdnVo.getId(), NewBillType.IDT); // 原始现货订单发货中
	    			}
	    		}
	           	//更新交货单为发货中
	           	sfDgnService.orderDG(sfDgnVo.getId(), null, null);
	           	sfGdrnService.billGdn(sfGdnVo, sfGdnVo.getOrigDocType());
	           	//更新交货单为已发货
	           	sfDgnService.orderDD(sfDgnVo.getId(), null, null,null);
			}
        	// 将新ERP的出库单号写入到老ERP出库单
	    	soaJmsDubboService.modifyGdn_newErpGdnCode(vo, sfGdnVo.getCode());
	    	
        	erpFlag = false;
        	boolean flg1 = newERPCommonService.isEnableNewErp(vo.getRcvUnitId());
        	String orginDocNum = O2oUtils.ToString(vo.getExtraParams().get(BASE_EXTRA.ORIGIN_DOC_NUM));
        	// 三方下层调配或者下层转配 - 更新最原始的的现货单
        	if (AllocType.XX3Z.equals(vo.getAllocType()) || AllocType.XXLS.equals(vo.getAllocType())) {
        		orginDocNum = O2oUtils.ToString(vo.getExtraParams().get(BASE_EXTRA.ORIGIN_DOC_NUM_FIRST));
			}
    		if (flg1 && !flg) {
    			if (BillType.AAD.equals(srcBillType)) {
    				if (!AllocType.XXUZ.equals(vo.getAllocType())) {
    					logger.warn("&&&&&16"+orginDocNum);
    					iNewBillServiceHander.orderDG(null, orginDocNum, null ,NewBillType.IDT);
    					iNewBillServiceHander.orderDD(null, orginDocNum, null ,vo,NewBillType.IDT);
    				}
    			}
			}
        	jmsProducerService.grnQueue(grnVo);// 发送入库队列
        } catch (Exception e) {
        	if (erpFlag) {
				errorMsg = "出库单队列异常-新ERP。";
			}else {
				errorMsg = "出库单队列异常-老ERP。";
			}
        	logger.error(e.getMessage()); 
        	exceptionMsg = subStringException(e.getMessage());
        	logger.warn("异常提示："+exceptionMsg);
        	throw new RuntimeException(errorMsg+e.getMessage());
        }finally{
        	if (null != errorMsg) {
        		logger.error(errorMsg); 
        		pubO2oFlowService.savePof4PrepareUpdate(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class),vo.getPubB2cDocCode(), errorMsg+exceptionMsg
        				,SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO)));
			}else {
				pubO2oFlowService.savePof4Gdn(vo, remark,POF_DocStatus.SU);
				if (flg) {
					if (BillType.TBN.equals(vo.getSrcDocType())) {
						newPubO2oFlowService.savePof4SfDgn(sfDgnVo,vo, "已生成新ERP交货单",POF_DocStatus.SU);
					}
					newPubO2oFlowService.savePof4SfGdn(sfGdnVo,vo, "已生成新ERP出库单",POF_DocStatus.SU);
				}
			}
        }
    }
    
    /**
     * 老ERP入库队列
     * @param vo
     */
    public void onMessageForGrnQueue(String grnJson) {
    	GrnVo vo = JSON.parseObject(grnJson, GrnVo.class);
    	boolean erpFlag = false; //判断是否执行到新ERP逻辑
    	Boolean flg = false;
    	MsgVo handerMsg = new MsgVo();
    	SfRvdVo sfRvdVo = new SfRvdVo();
    	SfGrnVo sfGrnVo = new SfGrnVo();
    	String errorMsg = null;
    	String exceptionMsg = "";
    	String remark = "";
        try {
        	handerMsg = billServiceHander.save(vo,BillType.GRN);
       	 	if (O2OMsgConstant.MSG_CODE.ERROR.equals(handerMsg.getCode())) {
	       		errorMsg = "保存老ERP入库单出现异常"+handerMsg.getMsg();
	      		throw new RuntimeException(errorMsg);
			 }
       	 	vo.setGrnNum(handerMsg.getBillNum()); // 代码分离
       	 	// 原始单据收货中
	       	BillType srcBillType = vo.getSrcDocType();
	       	if (BillType.TBN.equals(srcBillType)) {
	       		 TbnVo tbnVo = new TbnVo();
	       		 tbnVo.setVenderId(vo.getSrcUnitId());
	       		 tbnVo.setTbnNum(vo.getSrcDocNum());
	       		 billServiceHander.orderRG(tbnVo,BillType.TBN); // 原始调配单收货中
			}else if (BillType.AAD.equals(srcBillType)) {
				if (!AllocType.XXUZ.equals(vo.getAllocType())) {
					IdtVo idtVo = new IdtVo();
					idtVo.setVendeeId(vo.getUnitId());
					idtVo.setIdtNum(vo.getUpDocCode());
					idtVo.setAllocType(vo.getAllocType());
					billServiceHander.orderRG(idtVo,BillType.IDT); // 原始现货订单收货中
				}
			}
	       	handerMsg = billServiceHander.confirm(vo,BillType.GRN);
	       	if (O2OMsgConstant.MSG_CODE.ERROR.equals(handerMsg.getCode())) {
	       		errorMsg = "确认老ERP入库单出现异常"+handerMsg.getMsg();
	       		throw new RuntimeException(errorMsg);
			 }
	       	// 入库
	       	cgdrnService.billGrn(vo, vo.getSrcDocType()); // 取源单据类型
	       	AllocType allocType = vo.getAllocType();
	    	boolean flag = false;
	    	// 分析配货模式
	       	if (AllocType.XX3Z.equals(vo.getAllocType())) {//是否转配
	       		remark = "已生成转账老ERP入库单";
	       	}else if (AllocType.XXUZ.equals(vo.getAllocType())) {//是否上层转配
	   			flag = true;
	   			remark = "已生成上层转账老ERP入库单";
			}else if (AllocType.XX3T.equals(vo.getAllocType()) || AllocType.XXLS.equals(vo.getAllocType())) {//是否三方调配
				if (AllocType.XX3T.equals(vo.getAllocType())) {
					flag = true;
				}
				remark = "已生成三方转账老ERP入库单";
			}else if (AllocType.XXXS.equals(vo.getAllocType())) {//是否直配
				remark = "已生成直配入库单";
			}else if (AllocType.XXXT.equals(vo.getAllocType())) {//是否两方调配
				remark = "已生成调配入库单";
			}else {
				errorMsg = "入库队列生成入库单时，没有匹配的配货模式";
	       		throw new RuntimeException(errorMsg);
			}
	       	if (flag) { // 只有上层转配、三方调配才需要进入出库队列
	       		AdnVo adnVo = new AdnVo();
	       		adnVo.setAdnNum(vo.getOriginAdnNum());
	       		adnVo.setVenderId(vo.getUnitId()); // 供货方id
	       		adnVo = soaJmsDubboService.select(adnVo);
	       		adnVo.setAdnDtlVos(soaJmsDubboService.selectAdnDtl(adnVo));
	       		adnVo.setGdnMode(GdnMode.AGOR); //出库方式
	       		adnVo.setApproved(vo.getApproved());
	       		adnVo.setDataSource(vo.getDataSource());
	       		adnVo.setPubB2cDocCode(vo.getPubB2cDocCode()); // 在下面的方法createVoService.genGdnByAdn（）里会用到
	       		adnVo.setHadLockWareh(vo.getHadLockWareh());//  出库规则用到
	       		adnVo.setExtraParams(vo.getExtraParams());
	       		for(GrnDtlVo grnDtlVo : vo.getGrnDtlVos()){
	       			for(AdnDtlVo adnDtlVo : adnVo.getAdnDtlVos()){
	       				if (adnDtlVo.getProdId().equals(grnDtlVo.getProdId())) {
	       					adnDtlVo.setLocId(grnDtlVo.getLocId());
	       					adnDtlVo.setRcptLocId(grnDtlVo.getRcptLocId());
	       					logger.warn("****货位："+grnDtlVo.getLocId() +"，"+grnDtlVo.getRcptLocId());
						}
	       			}
	       		}
	       		GdnVo gdnVo = createVoService.genGdnByAdn(adnVo);
	       		gdnVo.setSrcDocType(BillType.AAD);
	       		AllocType tempAllocType = allocType;
	       		if (AllocType.XXUZ.equals(allocType)) {
	       			tempAllocType = AllocType.XX3Z;
				}else if (AllocType.XX3T.equals(allocType)) {
					tempAllocType = AllocType.XXLS;
				}
	       		gdnVo.setAllocType(tempAllocType); // 标记配货模式
	       		gdnVo.setLastFactRcvWarehId(vo.getLastFactRcvWarehId());// 最终收货仓库
	       		gdnVo.setLastFactDispWarehId(vo.getLastFactDispWarehId());//  最终发货仓库（出库规则使用）
	       		gdnVo.setSfDgnCode(vo.getSfDgnCode());
	       		gdnVo.setSfDgnCodeUPZ(vo.getSfDgnCodeUPZ());
	       		jmsProducerService.gdnQueue(gdnVo);// 发送出库队列
			}
	       	//收货方是否启用新erp
	    	flg = newERPCommonService.isEnableNewErp(vo.getUnitId());
	    	String orginDocNum = O2oUtils.ToString(vo.getExtraParams().get(BASE_EXTRA.ORIGIN_DOC_NUM));
        	String orginDocType = O2oUtils.ToString(vo.getExtraParams().get(BASE_EXTRA.ORIGIN_DOC_TYPE));
        	String orginUnitId = O2oUtils.ToString(vo.getExtraParams().get(BASE_EXTRA.ORIGIN_UNIT_ID));
        	// 三方下层调配或者下层转配 - 更新最原始的的现货单(调配单)
        	if (AllocType.XX3Z.equals(vo.getAllocType()) || AllocType.XXLS.equals(vo.getAllocType())) {
        		logger.warn("&&&&&& 三方下层调配或者下层转配："+orginDocType+" "+orginDocNum);
        		orginDocNum = O2oUtils.ToString(vo.getExtraParams().get(BASE_EXTRA.ORIGIN_DOC_NUM_FIRST));
        		orginDocType = O2oUtils.ToString(vo.getExtraParams().get(BASE_EXTRA.ORIGIN_DOC_TYPE_FIRST));
        		orginUnitId = O2oUtils.ToString(vo.getExtraParams().get(BASE_EXTRA.ORIGIN_UNIT_ID_FIRST));
			}
	    	logger.warn(vo.getUnitId()+"是否启用新ERP:"+flg);
	    	Boolean flg1 = newERPCommonService.isEnableNewErp(vo.getDispUnitId());
	    	if (flg) {
	    		erpFlag = true;
	    		//发货方是否启用新erp
	      		logger.warn(vo.getDispUnitId()+"是否启用新ERP:"+flg1);
	    		if (flg1) {
					//根据新ERP出库单生成到货通知单
	    			SfGdnVo sfGdnVo = newSoaJmsDubboService.selectSfGdnByCode(vo.getSfGdnCode());
	    			List<SfGdnDtlVo> sfGdnDtlVos = newSoaJmsDubboService.selectGdnDtlById(sfGdnVo);
	    			sfGdnVo.setSfGdnDtlVos(sfGdnDtlVos);
	    			sfRvdVo = newCreateVoService.genSfRvdBySfGdn(sfGdnVo);
	    			sfRvdVo.setLastFactDispWarehId(vo.getLastFactDispWarehId());// 最终发货仓库（出库规则使用）
	    			sfRvdVo.setLastFactRcvWarehId(vo.getLastFactRcvWarehId());// 最终收货仓库
	        		sfRvdVo = newCreateVoService.genSfRvdBySfGdnDtl(sfGdnVo,sfRvdVo,vo);
	        		//生成已审批的到货通知单
	    			MsgVo msg = iNewBillServiceHander.save(sfRvdVo,NewBillType.RVD);
	    			sfRvdVo.setId(msg.getNewBillId());
	    			sfRvdVo.setCode(msg.getCode());
	            	msg = iNewBillServiceHander.confirm(sfRvdVo,NewBillType.RVD);
	            	msg = iNewBillServiceHander.audit(sfRvdVo,NewBillType.RVD);
	            	//获取主键ID
	            	Long id = newERPCommonService.getPrimaryIdNew("SF_GRN",1);
	            	sfRvdVo.setSfGrnId(id);
	            	newSoaJmsDubboService.insertByRVD(sfRvdVo);
	            	newSoaJmsDubboService.insertDtlByRVD(sfRvdVo);
	            	sfGrnVo = newSoaJmsDubboService.selectSfGrnById(sfRvdVo.getSfGrnId());
	            	sfGrnVo.setSfDgnCode(vo.getSfDgnCode());
	            	sfGrnVo.setAllocType(vo.getAllocType());
	            	sfGrnVo.setSfDgnCodeUPZ(vo.getSfDgnCodeUPZ());
	            	List<SfGrnDtlVo> dtlVos = newSoaJmsDubboService.selectSfGrnDtlById(sfGrnVo.getId());
	            	sfGrnVo.setDtlVos(dtlVos);
	            	logger.warn("保存新ERP入库单,id:{}",sfGrnVo.getId());
	            	sfGdrnService.billGrn(sfGrnVo, sfRvdVo.getOriginDocType());
				}else {
					//根据老ERP出库单生成到货通知单
					if (!AllocType.XXUZ.equals(vo.getAllocType())) {
						if (NewBillType.IDT.name().equals(orginDocType)) {
							//更新原始现货单为已发货
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("sf_idt_code", orginDocNum);
							map.put("doc_state", IDT_PROGRESS.DD_NEW);
							newSoaJmsDubboService.updateIdtDocState(map);
						}
					}
					GdnVo gdnVo = new GdnVo();
					gdnVo.setGdnNum(vo.getGdnNum());
					gdnVo.setUnitId(vo.getDispUnitId());
					gdnVo = soaJmsDubboService.selectGdn(gdnVo);
					gdnVo.setLastFactDispWarehId(vo.getLastFactDispWarehId());// 最终发货仓库
					gdnVo.setLastFactRcvWarehId(vo.getLastFactRcvWarehId());// 最终收货仓库
					logger.warn(">>>>>>>最终收货仓（onMessageForGrnQueue）："+vo.getLastFactRcvWarehId()+" 最终发货仓："+vo.getLastFactDispWarehId());
					gdnVo.setGdnDtlVos(soaJmsDubboService.selectGdnDtl(gdnVo));
					gdnVo.getExtraParams().put(BASE_EXTRA.ORIGIN_DOC_NUM, orginDocNum);
		            gdnVo.getExtraParams().put(BASE_EXTRA.ORIGIN_DOC_TYPE, orginDocType);
		            gdnVo.getExtraParams().put(BASE_EXTRA.ORIGIN_UNIT_ID, orginUnitId);
					sfRvdVo = newCreateVoService.genSfRvdByGdn(gdnVo);
					sfRvdVo = newCreateVoService.genSfRvdByGdnDtl(gdnVo,sfRvdVo,vo);
					sfRvdVo.setAllocType(vo.getAllocType());
					//生成已审批的到货通知单
	    			MsgVo msg = iNewBillServiceHander.save(sfRvdVo,NewBillType.RVD);
	    			sfRvdVo.setId(msg.getNewBillId());
	    			sfRvdVo.setCode(msg.getCode());
	            	msg = iNewBillServiceHander.confirm(sfRvdVo,NewBillType.RVD);
	            	msg = iNewBillServiceHander.audit(sfRvdVo,NewBillType.RVD);
	            	
	            	//获取主键ID
	            	Long id = newERPCommonService.getPrimaryIdNew("SF_GRN",1);
	            	sfRvdVo.setSfGrnId(id);
					newSoaJmsDubboService.insertByRVD(sfRvdVo);
			    	newSoaJmsDubboService.insertDtlByRVD(sfRvdVo);
			    	sfGrnVo = newSoaJmsDubboService.selectSfGrnById(sfRvdVo.getSfGrnId());
			    	sfGrnVo.setSfDgnCode(vo.getSfDgnCode());
	            	sfGrnVo.setSfDgnCodeUPZ(vo.getSfDgnCodeUPZ());
	            	sfGrnVo.setAllocType(vo.getAllocType());
	            	List<SfGrnDtlVo> dtlVos = newSoaJmsDubboService.selectSfGrnDtlById(sfGrnVo.getId());
	            	sfGrnVo.setDtlVos(dtlVos);
			    	logger.warn("保存新ERP入库单,id:{}",sfGrnVo.getId());
			    	iNewBillServiceHander.orderRG(sfRvdVo.getId(),"",null,null,NewBillType.RVD);
			    	sfGdrnService.billGrn(sfGrnVo, sfRvdVo.getOriginDocType());
				}
			}
	    	// 将新ERP的入库单号写入到老ERP入库单
	    	soaJmsDubboService.modifyGrn_newErpGrnCode(vo, sfGrnVo.getGrnNum());
       		if (!flg && flg1 && BillType.TBN.equals(vo.getSrcDocType())) { // 入库单的收货方不开启新ERP并且发货方开启新ERP，则根据老ERP入库单更新新ERP调配单
       			logger.warn("&&&&&&入库单："+orginDocNum);
       			iNewBillServiceHander.orderRG(null, orginDocNum, null , vo,NewBillType.TBN);
       			iNewBillServiceHander.orderRD(null, orginDocNum, null , vo,NewBillType.TBN);
			}
       } catch (Exception e) {
    	   if (erpFlag) {
    		   errorMsg = "入库单队列异常-新ERP。";
		   }else {
			   errorMsg = "入库单队列异常-老ERP。";
		   }
    	   logger.error(e.getMessage()); 
    	   exceptionMsg = subStringException(e.getMessage());
    	   logger.warn("异常提示："+exceptionMsg);
    	   throw new RuntimeException(errorMsg+e.getMessage());
        }finally{
        	if (null != errorMsg) {
        		logger.error(errorMsg); 
        		pubO2oFlowService.savePof4PrepareUpdate(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class)
        				,vo.getPubB2cDocCode(), errorMsg+exceptionMsg
        				,SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO)));
			}else {
				pubO2oFlowService.savePof4Grn(vo, remark,POF_DocStatus.SU);
				if (flg) {
					newPubO2oFlowService.savePof4SfRvd(sfRvdVo,vo, "已生成新ERP到货通知单",POF_DocStatus.SU);
					newPubO2oFlowService.savePof4SfGrn(sfGrnVo,vo, "已生成新ERP入库单",POF_DocStatus.SU);
				}
			}
        }
    }
    
    /**
     * 退货申请单队列
     * @param sfSchTaskExecOosJson
     */
    public void onMessageForBgrQueue(String sfSchTaskExecOosJson){
    	SfSchTaskExecOosVo vo = JSON.parseObject(sfSchTaskExecOosJson, SfSchTaskExecOosVo.class);
    	BgrVo bgrVo = null;
    	MsgVo msg = null;
    	String errorMsg = null;
    	GdnVo gdnVo = null;
    	String exceptionMsg = "";
    	try {
    		logger.info("直营门店退货流程,退货仓库:"+vo.getDispWarehCode()+"接收仓库："+vo.getShopCode());
    		//生成录入中的现货订单
    		bgrVo = createVoService.genBgrVoValue(vo);
        	msg = billServiceHander.save(bgrVo,BillType.SBG);
        	if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
        		errorMsg = "保存退货申请单出现异常"+msg.getMsg();
        		throw new RuntimeException(errorMsg);
        	}
        	bgrVo.setBgrNum(msg.getBillNum());// 代码分离
        	msg = billServiceHander.confirm(bgrVo,BillType.SBG);
        	if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
        		errorMsg = "确认退货申请单出现异常"+msg.getMsg();
        		throw new RuntimeException(errorMsg);
        	}
        	msg = billServiceHander.audit(bgrVo,BillType.SBG);
        	if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
        		errorMsg = "审核退货申请单出现异常"+msg.getMsg();
        		throw new RuntimeException(errorMsg);
        	}
        	msg = billServiceHander.orderFI(bgrVo,BillType.SBG);
        	if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
        		errorMsg = "退货申请单过账中出现异常"+msg.getMsg();
        		throw new RuntimeException(errorMsg);
        	}
        	bgrVo.getExtraParams().put(BASE_EXTRA.ORIGIN_DOC_NUM, bgrVo.getBgrNum());
        	//根据退货申请单生成退货出库单vo
        	gdnVo = createVoService.genGdnByBgr(bgrVo,vo);
        	
        	jmsProducerService.thGdnQueue(gdnVo);
    	} catch (Exception e) {
           logger.error(e.getMessage()); 
     	   exceptionMsg = subStringException(e.getMessage());
     	   logger.warn("异常提示："+exceptionMsg);
     	   throw new RuntimeException(errorMsg+e.getMessage());
    	} finally {
    		//记录单据流
    		if (!SoaBillUtils.isBlank(exceptionMsg)) {
        		pubO2oFlowService.savePof4PrepareUpdate(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class) , vo.getB2cDocCode(), errorMsg+exceptionMsg
        				,SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO)));
			}else {
				pubO2oFlowService.savePof4Bgr(bgrVo, "已生成老ERP退货申请单",POF_DocStatus.SU);
			}
    		
    	}
    	
    }
    
    /**
     * 退货出库队列
     * @param gdnJson
     */
    public void onMessageForThgdnQueue(String gdnJson) {
       GdnVo gdnVo = JSON.parseObject(gdnJson, GdnVo.class);
   	   String errorMsg = null;
	   String exceptionMsg = "";
	   ScnVo scnVo = null;
	   MsgVo msg = null;
	   try{
		   //保存
		   msg = billServiceHander.save(gdnVo, BillType.GDN);
		   if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
      		  errorMsg = "保存退货出库单出现异常"+msg.getMsg();
       		  throw new RuntimeException(errorMsg);
		   }
		   gdnVo.setGdnNum(msg.getBillNum());
		   //确认
		   msg = billServiceHander.confirm(gdnVo,BillType.GDN);
      	   if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
        	  errorMsg = "确认退货出库单出现异常"+msg.getMsg();
        	  throw new RuntimeException(errorMsg);
		   }
      	   // 出库
      	   cgdrnService.billGdn(gdnVo, gdnVo.getSrcDocType()); // 取源单据类型
      	   //出库单过账中设置
      	   msg = billServiceHander.orderFI(gdnVo,BillType.GDN);
      	   if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
      		   errorMsg = "退货出库单过账中出现异常"+msg.getMsg();
      		   throw new RuntimeException(errorMsg);
      	   }
      	   gdnVo.getExtraParams().put(BASE_EXTRA.TH_GDN_NUM, gdnVo.getGdnNum());
      	   scnVo = createVoService.genScnByGdn(gdnVo);
    	
      	   jmsProducerService.scnQueue(scnVo);
		   
	   	 } catch (Exception e) {
	   		 logger.error(e.getMessage()); 
	   		 exceptionMsg = subStringException(e.getMessage());
	   		 logger.warn("异常提示："+exceptionMsg);
	   		 throw new RuntimeException(errorMsg+e.getMessage());
	   	 } finally {
	   		if (!SoaBillUtils.isBlank(exceptionMsg)) {
        		pubO2oFlowService.savePof4PrepareUpdate(SoaBillUtils.toObject(gdnVo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class),gdnVo.getPubB2cDocCode(), errorMsg+exceptionMsg
        				,SoaBillUtils.toObject(gdnVo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO)));
			}else {
				pubO2oFlowService.savePof4Gdn(gdnVo, "已生成老ERP出库单",POF_DocStatus.SU);
			}
	     }
    }
    
    /**
     * 退货单队列
     * @param scnJson
     */
    public void onMessageForScnQueue(String scnJson) {
       ScnVo scnVo = JSON.parseObject(scnJson, ScnVo.class);
       String errorMsg = null;
 	   String exceptionMsg = "";
 	   GrnVo grnVo = null;
 	   MsgVo msg = null;
 	   try {
      	   msg = billServiceHander.save(scnVo,BillType.SSC);
      	   if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
      		   errorMsg = "保存退货单出现异常"+msg.getMsg();
      		   throw new RuntimeException(errorMsg);
      	   }
      	   scnVo.setScnNum(msg.getBillNum());
      	   msg = billServiceHander.confirm(scnVo,BillType.SSC);
      	   if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
      		   errorMsg = "确认退货单出现异常"+msg.getMsg();
      		   throw new RuntimeException(errorMsg);
      	   }
      	   
      	   msg = billServiceHander.audit(scnVo,BillType.SSC);
      	   if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
      		   errorMsg = "审核退货单出现异常"+msg.getMsg();
      		   throw new RuntimeException(errorMsg);
      	   }
      	   
      	   msg = billServiceHander.orderFI(scnVo,BillType.SSC);
      	   if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
      		   errorMsg = "退货申请单过账中出现异常"+msg.getMsg();
      		   throw new RuntimeException(errorMsg);
      	   }
      	
      	   //根据退货申请单生成退货出库单vo
      	   grnVo = createVoService.genGrnByScn(scnVo);
      	
      	   jmsProducerService.thGrnQueue(grnVo);
		
 	   } catch (Exception e) {
 		   logger.error(e.getMessage()); 
    	   exceptionMsg = subStringException(e.getMessage());
    	   logger.warn("异常提示："+exceptionMsg);
    	   throw new RuntimeException(errorMsg+e.getMessage());
 	   } finally {
 		   if (!SoaBillUtils.isBlank(exceptionMsg)) {
      	      pubO2oFlowService.savePof4PrepareUpdate(SoaBillUtils.toObject(scnVo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class),scnVo.getPubB2cDocCode(), errorMsg+exceptionMsg
      				,SoaBillUtils.toObject(scnVo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO)));
		   }else {
			  pubO2oFlowService.savePof4Scn(scnVo, "已生成老ERP退货单",POF_DocStatus.SU);
		   }
 		   
 	   }
    }
    
    /**
     * 退货入库单队列
     * @param grnJson
     */
    public void onMessageForThgrnQueue(String grnJson) {
    	GrnVo grnVo = JSON.parseObject(grnJson, GrnVo.class);
    	MsgVo msgVo = new MsgVo();
    	String errorMsg = null;
    	String exceptionMsg = "";
    	try {
    		msgVo = billServiceHander.save(grnVo, BillType.GRN);
     	    if (O2OMsgConstant.MSG_CODE.ERROR.equals(msgVo.getCode())) {
	       		errorMsg = "保存退货入库单出现异常"+msgVo.getMsg();
	      		throw new RuntimeException(errorMsg);
			}
     	    grnVo.setGrnNum(msgVo.getBillNum());
     	    
     	    msgVo = billServiceHander.confirm(grnVo,BillType.GRN);
	        if (O2OMsgConstant.MSG_CODE.ERROR.equals(msgVo.getCode())) {
	       		errorMsg = "确认退货入库单出现异常"+msgVo.getMsg();
	       		throw new RuntimeException(errorMsg);
		    }
	        
	        msgVo = billServiceHander.orderFI(grnVo,BillType.GRN);
	        if (O2OMsgConstant.MSG_CODE.ERROR.equals(msgVo.getCode())) {
	       		errorMsg = "退货入库单过账中出现异常"+msgVo.getMsg();
	       		throw new RuntimeException(errorMsg);
		    }
	        // 入库
	        cgdrnService.billGrn(grnVo, grnVo.getSrcDocType()); // 取源单据类型
	        //退货申请单进度设为已发货 
	        bgrToOrderDD(grnVo);
	        //退货出库单进度设为已发货 
	        gdnToOrderDD(grnVo);
	        //退货单进度设为已收货 
	        scnToOrderRD(grnVo);
    	} catch (Exception e) {
    	    logger.error(e.getMessage()); 
      	    exceptionMsg = subStringException(e.getMessage());
      	    logger.warn("异常提示："+exceptionMsg);
      	    throw new RuntimeException(errorMsg+e.getMessage());
    	} finally {
    		if (null != errorMsg) {
        		logger.error(errorMsg); 
        		pubO2oFlowService.savePof4PrepareUpdate(SoaBillUtils.toObject(grnVo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class),grnVo.getPubB2cDocCode(), errorMsg+exceptionMsg
        				,SoaBillUtils.toObject(grnVo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO)));
			} else {
				pubO2oFlowService.savePof4Grn(grnVo, "已生成老ERP入库单",POF_DocStatus.SU);
			}
    		
    	}
    }
    
	/**
	 * 退货申请单进度设为已发货
	 * @param bgrVo
	 */
    private void bgrToOrderDD(GrnVo grnVo) {
		BgrVo bgrVo = new BgrVo();
		bgrVo.setVendeeId(grnVo.getSrcUnitId());
		bgrVo.setBgrNum((String) grnVo.getExtraParams().get(BASE_EXTRA.ORIGIN_DOC_NUM));
		billServiceHander.orderDD(bgrVo, BillType.SBG);
    }

    /**
     * 退货出库单进度设为已发货 
     * @param grnVo
     */
    private void gdnToOrderDD(GrnVo grnVo) {
    	GdnVo gdnVo = new GdnVo();
    	gdnVo.setUnitId(grnVo.getSrcUnitId());
    	gdnVo.setGdnNum((String)grnVo.getExtraParams().get(BASE_EXTRA.TH_GDN_NUM));
		billServiceHander.orderDD(gdnVo, BillType.GDN);
	}
    
    /**
     * 退货单进度设为已收货 
     * @param grnVo
     */
    private void scnToOrderRD(GrnVo grnVo) {
		ScnVo scnVo = new ScnVo();
		scnVo.setVendeeId(grnVo.getSrcUnitId());
		scnVo.setScnNum(grnVo.getSrcDocNum());
		billServiceHander.orderRD(scnVo, BillType.SSC);
	}
    
	/**
	 * 队列预处理
	 * 
	 * @param vo
	 */
	public void onMessage(String sfSchTaskExecOosJson) {
		SfSchTaskExecOosVo vo = JSON.parseObject(sfSchTaskExecOosJson,
				SfSchTaskExecOosVo.class);
		String remark = "预处理队列，处理成功。";
		AllocType AT = null;
		Integer SEQ = 1; // 流程编号
		String dispWarehId = vo.getDispWarehCode();
		String rcvWarehId = vo.getShopCode();
		vo.setLastFactDispWarehId(dispWarehId);// 最终发货仓库（出库规则使用）
		vo.setLastFactRcvWarehId(rcvWarehId); // 最终收货仓库
		String vender = "";
		String vendee = "";
		PubO2oFlowVo pFlowVo = null;
		boolean flg = true;
		try {
			Map<String, Object> result = allocTypeService.getAllocType(vo);
			vender = (String) result.get("lastVender"); // 预处理单据流里用到
			vendee = (String) result.get("lastvendee");// 预处理单据流里用到
			flg = commonService.isOldFranchiseeOrDirect(vendee);
			if (!flg) {
			 throw new RuntimeException("请设置"+vendee+"账户组为加盟或直营！");
			}
			pFlowVo = commonService.getO2oSeqIdAndBatchNo();
			vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_BATCH_NO, pFlowVo.getBatchNo());
			AT = (AllocType) result.get("alloc");
			//根据品牌组 拆单
			List<SfSchTaskExecOosVo> list = separateBill(vo);
			if(!SoaBillUtils.isListBlank(list)){
				for(SfSchTaskExecOosVo oosVo : list) {
					logger.warn("配货模式 : " + AT);
					if (AllocType.XXXS.equals(AT) || AllocType.XTFO.equals(AT)) { // ---------------------------------直配
						oosVo.setAllocType(AllocType.XXXS); // 标记为直配
						oosVo.setVendeeCode((String) result.get("vendee"));
						oosVo.setVenderCode((String) result.get("vender"));
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.idtQueue(oosVo);
					} else if (AllocType.XXTH.equals(AT)) { // -------------------------2方调配
						oosVo.setAllocType(AllocType.XXTH);// 标记为直营退货
						oosVo.setVendeeCode((String) result.get("vendee"));
						oosVo.setVenderCode((String) result.get("vender"));
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.bgrQueue(oosVo);
					} else if (AllocType.XXXT.equals(AT)) { // -------------------------2方调配
						oosVo.setAllocType(AllocType.XXXT);// 标记为调配
						oosVo.setVendeeCode((String) result.get("vendee"));
						oosVo.setVenderCode((String) result.get("vender"));
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.t2Queue(oosVo);
					} else if (AllocType.XX3Z.equals(AT)) { // -------------------------转配
						oosVo.setAllocType(AllocType.XX3Z);// 标记为转配
						oosVo.setVendeeCode((String) result.get("vendee"));
						oosVo.setVenderCode((String) result.get("vender"));
						oosVo.setDispWarehCode(null); // 发货仓库设置为null
						oosVo.setUpVendeeId((String) result.get("upVendee"));
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.idtQueue(oosVo);
					} else if (AllocType.XX3T.equals(AT)) { // -------------------------三方调配
						oosVo.setAllocType(AllocType.XX3T); // 标记为三方调配
						oosVo.setVendeeCode((String) result.get("vendee"));
						oosVo.setVenderCode((String) result.get("vender"));
						oosVo.setDispWarehCode(null); // 发货仓库设置为null
						oosVo.setUpVendeeId((String) result.get("upVendee"));
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.idtQueue(oosVo);
					} else if (AllocType.XXTT.equals(AT)) { // -------------------------调配+调配
						oosVo.setAllocType(AllocType.XXXT); // 标记为调配
						oosVo.setVendeeCode((String) result.get("vendee"));
						oosVo.setVenderCode((String) result.get("vender"));
						oosVo.setShopCode(null);
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.t2Queue(oosVo);
						oosVo.setAllocType(AllocType.XXXT); // 标记为调配
						oosVo.setVendeeCode((String) result.get("vendee1"));
						oosVo.setVenderCode((String) result.get("vender1"));
						oosVo.setShopCode(oosVo.getUpRcvWarehId()); // 原始收货仓
						oosVo.setDispWarehCode(null);
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.t2Queue(oosVo);
					} else if (AllocType.XS3Z.equals(AT)) { // -------------------------直配+转配
						oosVo.setAllocType(AllocType.XXXS); // 标记为直配
						oosVo.setVendeeCode((String) result.get("vendee"));
						oosVo.setVenderCode((String) result.get("vender"));
						oosVo.setDispWarehCode(null); // 发货仓库设置为null
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.idtQueue(oosVo);
						oosVo.setAllocType(AllocType.XX3Z);// 标记为转配
						oosVo.setVendeeCode((String) result.get("vendee1"));
						oosVo.setVenderCode((String) result.get("vender1"));
						oosVo.setUpVendeeId((String) result.get("upVendee1"));
						oosVo.setShopCode(null); // 收货仓库设置为null
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.idtQueue(oosVo);
					} else if (AllocType.XT3Z.equals(AT)) { // -------------------------调配+转配
						oosVo.setAllocType(AllocType.XXXT); // 标记为调配
						oosVo.setVendeeCode((String) result.get("vendee"));
						oosVo.setVenderCode((String) result.get("vender"));
						oosVo.setShopCode(null); // 收货仓库设置为null
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.t2Queue(oosVo);
						oosVo.setAllocType(AllocType.XX3Z);// 标记为转配
						oosVo.setVendeeCode((String) result.get("vendee1"));
						oosVo.setVenderCode((String) result.get("vender1"));
						oosVo.setUpVendeeId((String) result.get("upVendee1"));
						oosVo.setShopCode(oosVo.getUpRcvWarehId()); // 收货仓库设置回来
						oosVo.setDispWarehCode(null); // 发货仓库设置为null
						oosVo.setUpDispWarehId(null); // 原始发货仓不在转配业务
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.idtQueue(oosVo);
					} else if (AllocType.X3TZ.equals(AT)) { // -------------------------三方调配+转配
						oosVo.setAllocType(AllocType.XX3T);// 标记为三方调配
						oosVo.setVendeeCode((String) result.get("vendee"));
						oosVo.setVenderCode((String) result.get("vender"));
						oosVo.setUpVendeeId((String) result.get("upVendee"));
						oosVo.setDispWarehCode(null); // 发货仓库设置为null
						oosVo.setShopCode(null); // 收货仓库设置为null
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.idtQueue(oosVo);
						oosVo.setAllocType(AllocType.XX3Z);// 标记为转配
						oosVo.setVendeeCode((String) result.get("vendee1"));
						oosVo.setVenderCode((String) result.get("vender1"));
						oosVo.setUpVendeeId((String) result.get("upVendee1"));
						oosVo.setShopCode(oosVo.getUpRcvWarehId()); // 收货仓库设置回来
						oosVo.setUpDispWarehId(null); // 原始发货仓不在转配业务
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.idtQueue(oosVo);
					} else if (AllocType.XT3T.equals(AT)) { // -------------------------调配+三方调配
						oosVo.setAllocType(AllocType.XXXT); // 标记为调配
						oosVo.setVendeeCode((String) result.get("vendee"));
						oosVo.setVenderCode((String) result.get("vender"));
						oosVo.setShopCode(null);
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.t2Queue(oosVo);
						oosVo.setAllocType(AllocType.XX3T);// 标记为三方调配
						oosVo.setVendeeCode((String) result.get("vendee1"));
						oosVo.setVenderCode((String) result.get("vender1"));
						oosVo.setUpVendeeId((String) result.get("upVendee1"));
						oosVo.setShopCode(oosVo.getUpRcvWarehId()); // 收货仓库设置回来
						oosVo.setUpDispWarehId(null); // 原始发货仓不在三方调配业务
						oosVo.setDispWarehCode(null);// 发货仓库设置为null
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.idtQueue(oosVo);
					} else if (AllocType.T3TZ.equals(AT)) { // -------------------------调配+三方调配+转配
						oosVo.setAllocType(AllocType.XXXT); // 标记为调配
						oosVo.setVendeeCode((String) result.get("vendee"));
						oosVo.setVenderCode((String) result.get("vender"));
						oosVo.setShopCode(null);
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.t2Queue(oosVo);
						oosVo.setAllocType(AllocType.XX3T);// 标记为三方调配
						oosVo.setVendeeCode((String) result.get("vendee1"));
						oosVo.setVenderCode((String) result.get("vender1"));
						oosVo.setUpVendeeId((String) result.get("upVendee1"));
						oosVo.setUpDispWarehId(null); // 原始发货仓不在三方调配业务
						oosVo.setDispWarehCode(null); // 发货仓库设置为null
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.idtQueue(oosVo);
						oosVo.setAllocType(AllocType.XX3Z);// 标记为转配
						oosVo.setVendeeCode((String) result.get("vendee2"));
						oosVo.setVenderCode((String) result.get("vender2"));
						oosVo.setUpVendeeId((String) result.get("upVendee2"));
						oosVo.setShopCode(oosVo.getUpRcvWarehId()); // 收货仓库设置回来
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.idtQueue(oosVo);
					} else if (AllocType.XTTT.equals(AT)) { // -------------------------调配+调配+调配
						oosVo.setAllocType(AllocType.XXXT); // 标记为调配
						oosVo.setVendeeCode((String) result.get("vendee"));
						oosVo.setVenderCode((String) result.get("vender"));
						oosVo.setShopCode(null);
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.t2Queue(oosVo);
						oosVo.setAllocType(AllocType.XXXT); // 标记为调配
						oosVo.setVendeeCode((String) result.get("vendee1"));
						oosVo.setVenderCode((String) result.get("vender1"));
						oosVo.setDispWarehCode(null);
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.t2Queue(oosVo);
						oosVo.setAllocType(AllocType.XXXT); // 标记为调配
						oosVo.setVendeeCode((String) result.get("vendee2"));
						oosVo.setVenderCode((String) result.get("vender2"));
						oosVo.setShopCode(oosVo.getUpRcvWarehId()); // 原始收货仓
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.t2Queue(oosVo);
					} else if (AllocType.TT3T.equals(AT)) { // -------------------------调配+调配+三方调配
						oosVo.setAllocType(AllocType.XXXT); // 标记为调配
						oosVo.setVendeeCode((String) result.get("vendee"));
						oosVo.setVenderCode((String) result.get("vender"));
						oosVo.setShopCode(null);
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.t2Queue(oosVo);
						oosVo.setAllocType(AllocType.XXXT); // 标记为调配
						oosVo.setVendeeCode((String) result.get("vendee1"));
						oosVo.setVenderCode((String) result.get("vender1"));
						oosVo.setDispWarehCode(null); // 发货仓库设置为null
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.t2Queue(oosVo);
						oosVo.setAllocType(AllocType.XX3T);// 标记为三方调配
						oosVo.setVendeeCode((String) result.get("vendee2"));
						oosVo.setVenderCode((String) result.get("vender2"));
						oosVo.setUpVendeeId((String) result.get("upVendee2"));
						oosVo.setShopCode(oosVo.getUpRcvWarehId()); // 收货仓库设置回来
						oosVo.setUpDispWarehId(null); // 原始发货仓不在三方调配业务
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.idtQueue(oosVo);
					} else if (AllocType.XTTZ.equals(AT)) { // -------------------------调配+调配+转配
						oosVo.setAllocType(AllocType.XXXT); // 标记为调配
						oosVo.setVendeeCode((String) result.get("vendee"));
						oosVo.setVenderCode((String) result.get("vender"));
						oosVo.setShopCode(null);
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.t2Queue(oosVo);
						oosVo.setAllocType(AllocType.XXXT); // 标记为调配
						oosVo.setVendeeCode((String) result.get("vendee1"));
						oosVo.setVenderCode((String) result.get("vender1"));
						oosVo.setDispWarehCode(null); // 发货仓库设置为null
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.t2Queue(oosVo);
						oosVo.setAllocType(AllocType.XX3Z);// 标记为转配
						oosVo.setVendeeCode((String) result.get("vendee2"));
						oosVo.setVenderCode((String) result.get("vender2"));
						oosVo.setUpVendeeId((String) result.get("upVendee2"));
						oosVo.setShopCode(oosVo.getUpRcvWarehId()); // 收货仓库设置回来
						oosVo.setUpDispWarehId(null); // 原始发货仓不在转配业务
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.idtQueue(oosVo);
					} else if (AllocType.TT3TS.equals(AT)) { // -------------------------调配+调配+三方调配+直配
						oosVo.setAllocType(AllocType.XXXS); // 标记为直配
						oosVo.setVendeeCode((String) result.get("vendee3"));
						oosVo.setVenderCode((String) result.get("vender3"));
						oosVo.setDispWarehCode(null);
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.idtQueue(oosVo);
						oosVo.setAllocType(AllocType.XXXT); // 标记为调配
						oosVo.setVendeeCode((String) result.get("vendee"));
						oosVo.setVenderCode((String) result.get("vender"));
						oosVo.setDispWarehCode(oosVo.getUpDispWarehId()); // 发货仓库设置回来
						oosVo.setShopCode(null); // 收货仓库设置为null
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.t2Queue(oosVo);
						oosVo.setAllocType(AllocType.XXXT); // 标记为调配
						oosVo.setVendeeCode((String) result.get("vendee1"));
						oosVo.setVenderCode((String) result.get("vender1"));
						oosVo.setDispWarehCode(null);
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.t2Queue(oosVo);
						oosVo.setAllocType(AllocType.XX3T);// 标记为三方调配
						oosVo.setVendeeCode((String) result.get("vendee2"));
						oosVo.setVenderCode((String) result.get("vender2"));
						oosVo.setUpVendeeId((String) result.get("upVendee2"));
						oosVo.setUpDispWarehId(null); // 原始发货仓不在三方调配业务
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.idtQueue(oosVo);
					} else if (AllocType.TT3TZ.equals(AT)) { // -------------------------调配+调配+三方调配+转配
						oosVo.setAllocType(AllocType.XXXT); // 标记为调配
						oosVo.setVendeeCode((String) result.get("vendee"));
						oosVo.setVenderCode((String) result.get("vender"));
						oosVo.setShopCode(null);
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.t2Queue(oosVo);
						oosVo.setAllocType(AllocType.XXXT); // 标记为调配
						oosVo.setVendeeCode((String) result.get("vendee1"));
						oosVo.setVenderCode((String) result.get("vender1"));
						oosVo.setDispWarehCode(null); // 发货仓库设置为null
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.t2Queue(oosVo);
						oosVo.setAllocType(AllocType.XX3T);// 标记为三方调配
						oosVo.setVendeeCode((String) result.get("vendee2"));
						oosVo.setVenderCode((String) result.get("vender2"));
						oosVo.setUpVendeeId((String) result.get("upVendee2"));
						oosVo.setUpDispWarehId(null); // 原始发货仓不在三方调配业务
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.idtQueue(oosVo);
						oosVo.setAllocType(AllocType.XX3Z);// 标记为转配
						oosVo.setVendeeCode((String) result.get("vendee3"));
						oosVo.setVenderCode((String) result.get("vender3"));
						oosVo.setUpVendeeId((String) result.get("upVendee3"));
						oosVo.setShopCode(oosVo.getUpRcvWarehId()); // 收货仓库设置回来
						oosVo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
						jmsProducerService.idtQueue(oosVo);
					} else if (AllocType.XXXO.equals(AT)) {
						remark = "预处理队列，暂时不支持此业务模式";
					}
				}
			}else {
				logger.warn("配货模式 : " + AT);
				if (AllocType.XXXS.equals(AT) || AllocType.XTFO.equals(AT)) { // ---------------------------------直配
					vo.setAllocType(AllocType.XXXS); // 标记为直配
					vo.setVendeeCode((String) result.get("vendee"));
					vo.setVenderCode((String) result.get("vender"));
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.idtQueue(vo);
				} else if (AllocType.XXTH.equals(AT)) { // -------------------------2方调配
					vo.setAllocType(AllocType.XXTH);// 标记为直营退货
					vo.setVendeeCode((String) result.get("vendee"));
					vo.setVenderCode((String) result.get("vender"));
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.bgrQueue(vo);
				} else if (AllocType.XXXT.equals(AT)) { // -------------------------2方调配
					vo.setAllocType(AllocType.XXXT);// 标记为调配
					vo.setVendeeCode((String) result.get("vendee"));
					vo.setVenderCode((String) result.get("vender"));
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.t2Queue(vo);
				} else if (AllocType.XX3Z.equals(AT)) { // -------------------------转配
					vo.setAllocType(AllocType.XX3Z);// 标记为转配
					vo.setVendeeCode((String) result.get("vendee"));
					vo.setVenderCode((String) result.get("vender"));
					vo.setDispWarehCode(null); // 发货仓库设置为null
					vo.setUpVendeeId((String) result.get("upVendee"));
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.idtQueue(vo);
				} else if (AllocType.XX3T.equals(AT)) { // -------------------------三方调配
					vo.setAllocType(AllocType.XX3T); // 标记为三方调配
					vo.setVendeeCode((String) result.get("vendee"));
					vo.setVenderCode((String) result.get("vender"));
					vo.setDispWarehCode(null); // 发货仓库设置为null
					vo.setUpVendeeId((String) result.get("upVendee"));
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.idtQueue(vo);
				} else if (AllocType.XXTT.equals(AT)) { // -------------------------调配+调配
					vo.setAllocType(AllocType.XXXT); // 标记为调配
					vo.setVendeeCode((String) result.get("vendee"));
					vo.setVenderCode((String) result.get("vender"));
					vo.setShopCode(null);
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.t2Queue(vo);
					vo.setAllocType(AllocType.XXXT); // 标记为调配
					vo.setVendeeCode((String) result.get("vendee1"));
					vo.setVenderCode((String) result.get("vender1"));
					vo.setShopCode(vo.getUpRcvWarehId()); // 原始收货仓
					vo.setDispWarehCode(null);
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.t2Queue(vo);
				} else if (AllocType.XS3Z.equals(AT)) { // -------------------------直配+转配
					vo.setAllocType(AllocType.XXXS); // 标记为直配
					vo.setVendeeCode((String) result.get("vendee"));
					vo.setVenderCode((String) result.get("vender"));
					vo.setDispWarehCode(null); // 发货仓库设置为null
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.idtQueue(vo);
					vo.setAllocType(AllocType.XX3Z);// 标记为转配
					vo.setVendeeCode((String) result.get("vendee1"));
					vo.setVenderCode((String) result.get("vender1"));
					vo.setUpVendeeId((String) result.get("upVendee1"));
					vo.setShopCode(null); // 收货仓库设置为null
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.idtQueue(vo);
				} else if (AllocType.XT3Z.equals(AT)) { // -------------------------调配+转配
					vo.setAllocType(AllocType.XXXT); // 标记为调配
					vo.setVendeeCode((String) result.get("vendee"));
					vo.setVenderCode((String) result.get("vender"));
					vo.setShopCode(null); // 收货仓库设置为null
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.t2Queue(vo);
					vo.setAllocType(AllocType.XX3Z);// 标记为转配
					vo.setVendeeCode((String) result.get("vendee1"));
					vo.setVenderCode((String) result.get("vender1"));
					vo.setUpVendeeId((String) result.get("upVendee1"));
					vo.setShopCode(vo.getUpRcvWarehId()); // 收货仓库设置回来
					vo.setDispWarehCode(null); // 发货仓库设置为null
					vo.setUpDispWarehId(null); // 原始发货仓不在转配业务
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.idtQueue(vo);
				} else if (AllocType.X3TZ.equals(AT)) { // -------------------------三方调配+转配
					vo.setAllocType(AllocType.XX3T);// 标记为三方调配
					vo.setVendeeCode((String) result.get("vendee"));
					vo.setVenderCode((String) result.get("vender"));
					vo.setUpVendeeId((String) result.get("upVendee"));
					vo.setDispWarehCode(null); // 发货仓库设置为null
					vo.setShopCode(null); // 收货仓库设置为null
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.idtQueue(vo);
					vo.setAllocType(AllocType.XX3Z);// 标记为转配
					vo.setVendeeCode((String) result.get("vendee1"));
					vo.setVenderCode((String) result.get("vender1"));
					vo.setUpVendeeId((String) result.get("upVendee1"));
					vo.setShopCode(vo.getUpRcvWarehId()); // 收货仓库设置回来
					vo.setUpDispWarehId(null); // 原始发货仓不在转配业务
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.idtQueue(vo);
				} else if (AllocType.XT3T.equals(AT)) { // -------------------------调配+三方调配
					vo.setAllocType(AllocType.XXXT); // 标记为调配
					vo.setVendeeCode((String) result.get("vendee"));
					vo.setVenderCode((String) result.get("vender"));
					vo.setShopCode(null);
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.t2Queue(vo);
					vo.setAllocType(AllocType.XX3T);// 标记为三方调配
					vo.setVendeeCode((String) result.get("vendee1"));
					vo.setVenderCode((String) result.get("vender1"));
					vo.setUpVendeeId((String) result.get("upVendee1"));
					vo.setShopCode(vo.getUpRcvWarehId()); // 收货仓库设置回来
					vo.setUpDispWarehId(null); // 原始发货仓不在三方调配业务
					vo.setDispWarehCode(null);// 发货仓库设置为null
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.idtQueue(vo);
				} else if (AllocType.T3TZ.equals(AT)) { // -------------------------调配+三方调配+转配
					vo.setAllocType(AllocType.XXXT); // 标记为调配
					vo.setVendeeCode((String) result.get("vendee"));
					vo.setVenderCode((String) result.get("vender"));
					vo.setShopCode(null);
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.t2Queue(vo);
					vo.setAllocType(AllocType.XX3T);// 标记为三方调配
					vo.setVendeeCode((String) result.get("vendee1"));
					vo.setVenderCode((String) result.get("vender1"));
					vo.setUpVendeeId((String) result.get("upVendee1"));
					vo.setUpDispWarehId(null); // 原始发货仓不在三方调配业务
					vo.setDispWarehCode(null); // 发货仓库设置为null
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.idtQueue(vo);
					vo.setAllocType(AllocType.XX3Z);// 标记为转配
					vo.setVendeeCode((String) result.get("vendee2"));
					vo.setVenderCode((String) result.get("vender2"));
					vo.setUpVendeeId((String) result.get("upVendee2"));
					vo.setShopCode(vo.getUpRcvWarehId()); // 收货仓库设置回来
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.idtQueue(vo);
				} else if (AllocType.XTTT.equals(AT)) { // -------------------------调配+调配+调配
					vo.setAllocType(AllocType.XXXT); // 标记为调配
					vo.setVendeeCode((String) result.get("vendee"));
					vo.setVenderCode((String) result.get("vender"));
					vo.setShopCode(null);
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.t2Queue(vo);
					vo.setAllocType(AllocType.XXXT); // 标记为调配
					vo.setVendeeCode((String) result.get("vendee1"));
					vo.setVenderCode((String) result.get("vender1"));
					vo.setDispWarehCode(null);
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.t2Queue(vo);
					vo.setAllocType(AllocType.XXXT); // 标记为调配
					vo.setVendeeCode((String) result.get("vendee2"));
					vo.setVenderCode((String) result.get("vender2"));
					vo.setShopCode(vo.getUpRcvWarehId()); // 原始收货仓
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.t2Queue(vo);
				} else if (AllocType.TT3T.equals(AT)) { // -------------------------调配+调配+三方调配
					vo.setAllocType(AllocType.XXXT); // 标记为调配
					vo.setVendeeCode((String) result.get("vendee"));
					vo.setVenderCode((String) result.get("vender"));
					vo.setShopCode(null);
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.t2Queue(vo);
					vo.setAllocType(AllocType.XXXT); // 标记为调配
					vo.setVendeeCode((String) result.get("vendee1"));
					vo.setVenderCode((String) result.get("vender1"));
					vo.setDispWarehCode(null); // 发货仓库设置为null
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.t2Queue(vo);
					vo.setAllocType(AllocType.XX3T);// 标记为三方调配
					vo.setVendeeCode((String) result.get("vendee2"));
					vo.setVenderCode((String) result.get("vender2"));
					vo.setUpVendeeId((String) result.get("upVendee2"));
					vo.setShopCode(vo.getUpRcvWarehId()); // 收货仓库设置回来
					vo.setUpDispWarehId(null); // 原始发货仓不在三方调配业务
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.idtQueue(vo);
				} else if (AllocType.XTTZ.equals(AT)) { // -------------------------调配+调配+转配
					vo.setAllocType(AllocType.XXXT); // 标记为调配
					vo.setVendeeCode((String) result.get("vendee"));
					vo.setVenderCode((String) result.get("vender"));
					vo.setShopCode(null);
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.t2Queue(vo);
					vo.setAllocType(AllocType.XXXT); // 标记为调配
					vo.setVendeeCode((String) result.get("vendee1"));
					vo.setVenderCode((String) result.get("vender1"));
					vo.setDispWarehCode(null); // 发货仓库设置为null
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.t2Queue(vo);
					vo.setAllocType(AllocType.XX3Z);// 标记为转配
					vo.setVendeeCode((String) result.get("vendee2"));
					vo.setVenderCode((String) result.get("vender2"));
					vo.setUpVendeeId((String) result.get("upVendee2"));
					vo.setShopCode(vo.getUpRcvWarehId()); // 收货仓库设置回来
					vo.setUpDispWarehId(null); // 原始发货仓不在转配业务
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.idtQueue(vo);
				} else if (AllocType.TT3TS.equals(AT)) { // -------------------------调配+调配+三方调配+直配
					vo.setAllocType(AllocType.XXXS); // 标记为直配
					vo.setVendeeCode((String) result.get("vendee3"));
					vo.setVenderCode((String) result.get("vender3"));
					vo.setDispWarehCode(null);
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.idtQueue(vo);
					vo.setAllocType(AllocType.XXXT); // 标记为调配
					vo.setVendeeCode((String) result.get("vendee"));
					vo.setVenderCode((String) result.get("vender"));
					vo.setDispWarehCode(vo.getUpDispWarehId()); // 发货仓库设置回来
					vo.setShopCode(null); // 收货仓库设置为null
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.t2Queue(vo);
					vo.setAllocType(AllocType.XXXT); // 标记为调配
					vo.setVendeeCode((String) result.get("vendee1"));
					vo.setVenderCode((String) result.get("vender1"));
					vo.setDispWarehCode(null);
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.t2Queue(vo);
					vo.setAllocType(AllocType.XX3T);// 标记为三方调配
					vo.setVendeeCode((String) result.get("vendee2"));
					vo.setVenderCode((String) result.get("vender2"));
					vo.setUpVendeeId((String) result.get("upVendee2"));
					vo.setUpDispWarehId(null); // 原始发货仓不在三方调配业务
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.idtQueue(vo);
				} else if (AllocType.TT3TZ.equals(AT)) { // -------------------------调配+调配+三方调配+转配
					vo.setAllocType(AllocType.XXXT); // 标记为调配
					vo.setVendeeCode((String) result.get("vendee"));
					vo.setVenderCode((String) result.get("vender"));
					vo.setShopCode(null);
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.t2Queue(vo);
					vo.setAllocType(AllocType.XXXT); // 标记为调配
					vo.setVendeeCode((String) result.get("vendee1"));
					vo.setVenderCode((String) result.get("vender1"));
					vo.setDispWarehCode(null); // 发货仓库设置为null
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.t2Queue(vo);
					vo.setAllocType(AllocType.XX3T);// 标记为三方调配
					vo.setVendeeCode((String) result.get("vendee2"));
					vo.setVenderCode((String) result.get("vender2"));
					vo.setUpVendeeId((String) result.get("upVendee2"));
					vo.setUpDispWarehId(null); // 原始发货仓不在三方调配业务
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.idtQueue(vo);
					vo.setAllocType(AllocType.XX3Z);// 标记为转配
					vo.setVendeeCode((String) result.get("vendee3"));
					vo.setVenderCode((String) result.get("vender3"));
					vo.setUpVendeeId((String) result.get("upVendee3"));
					vo.setShopCode(vo.getUpRcvWarehId()); // 收货仓库设置回来
					vo.getExtraParams().put(BASE_EXTRA.DATA_FLOW_SEQID, SEQ++);
					jmsProducerService.idtQueue(vo);
				} else if (AllocType.XXXO.equals(AT)) {
					remark = "预处理队列，暂时不支持此业务模式";
				}
			}
			
		} catch (Exception e) {
			logger.error("预处理队列异常 , " + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			if (flg) { // 不设置用户组，则不生成单据流
				vo.setDispWarehCode(dispWarehId);
				vo.setShopCode(rcvWarehId);
				vo.setVendeeCode(vendee);
				vo.setVenderCode(vender);
				pubO2oFlowService.savePof4Prepare(vo, pFlowVo,remark, AT,POF_DocStatus.SU);
			} else {
				logger.error("如果不设置" + vendee + "账户组为加盟或直营，则不生成单据流 . ");
			}
		}
	}
	
	/**
	 * 根据品牌拆单
	 * @param vo
	 * @return
	 * List<SfSchTaskExecOosVo>
	 */
	private List<SfSchTaskExecOosVo> separateBill(SfSchTaskExecOosVo vo) {
		List<SfSchTaskExecOosVo> list = new ArrayList<SfSchTaskExecOosVo>();
		List<SfSchTaskExecOosDtlVo> lstSfSchTaskExecOosDtls = vo
				.getLstSfSchTaskExecOosDtls();
		if (SoaBillUtils.isListBlank(lstSfSchTaskExecOosDtls)) {
			return list;
		}
		if (lstSfSchTaskExecOosDtls.size() == 1) {
			return list;
		}
		Map<String, List<SfSchTaskExecOosDtlVo>> map = new HashMap<String, List<SfSchTaskExecOosDtlVo>>();
		Map<String, String> prodMap = new HashMap<String, String>();
		List<SfSchTaskExecOosDtlVo> dtlVoList = null;
		for (SfSchTaskExecOosDtlVo dtlVo : lstSfSchTaskExecOosDtls) {
			prodMap.put("prod_id", dtlVo.getProdNum());
			String brandId = soaJmsDubboService.selectBrandByProdID(prodMap);
			if (map.containsKey(brandId)) {
				map.get(brandId).add(dtlVo);
			} else {
				dtlVoList = new ArrayList<SfSchTaskExecOosDtlVo>();
				dtlVoList.add(dtlVo);
				map.put(brandId, dtlVoList);
			}
		}
		if (map.size() > 0) {
			for (String key : map.keySet()) {
				SfSchTaskExecOosVo temp = new SfSchTaskExecOosVo();
				BeanUtils.copyProperties(vo, temp);
				temp.setLstSfSchTaskExecOosDtls(map.get(key));
				list.add(temp);
			}
		}
		return list;
	}
    
    /**
     * 原始数据队列
     * @param vo
     */
    public void onMessageForInitialDataQueue(String sfSchTaskExecOosJson) {
        try {
        	logger.warn(sfSchTaskExecOosJson);
        	jmsProducerService.prepareHandler(O2oUtils.parseSfSchTaskExecOosVo(sfSchTaskExecOosJson));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public String subStringException(String exception){
    	String e = "";
    	if (null == exception) {
    		return "空异常";
		}
		if (exception.startsWith("java")) {
			e = exception.replaceAll("^java.*Exception:", "");
		}else {
			e = exception.replaceAll("^Exception.*Exception:", "");
		}
		return e;
    }
    
}
    