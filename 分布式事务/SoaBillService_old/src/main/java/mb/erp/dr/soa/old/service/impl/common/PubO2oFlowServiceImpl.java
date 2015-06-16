package mb.erp.dr.soa.old.service.impl.common;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.AllocType;
import mb.erp.dr.soa.constant.O2OBillConstant.BASE_EXTRA;
import mb.erp.dr.soa.constant.O2OBillConstant.POF_BIZTYPE;
import mb.erp.dr.soa.constant.O2OBillConstant.POF_DocStatus;
import mb.erp.dr.soa.constant.O2OBillConstant.PubO2OFlowType;
import mb.erp.dr.soa.constant.O2OBillConstant.SRC_DOC_TYPE;
import mb.erp.dr.soa.old.dao.PubO2oFlowMapper;
import mb.erp.dr.soa.old.service.bill.PubO2oFlowService;
import mb.erp.dr.soa.old.service.dubbo.SoaJmsDubboService;
import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.BaseBizVo;
import mb.erp.dr.soa.old.vo.BgrVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.old.vo.IdtDtlVo;
import mb.erp.dr.soa.old.vo.IdtVo;
import mb.erp.dr.soa.old.vo.PubO2oFlowVo;
import mb.erp.dr.soa.old.vo.ScnVo;
import mb.erp.dr.soa.old.vo.TbnVo;
import mb.erp.dr.soa.service.bill.NewERPCommonService;
import mb.erp.dr.soa.service.dubbo.NewSoaJmsDubboService;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.vo.SfGdnVo;
import mb.erp.dr.soa.vo.SfIdtVo;
import mb.erp.dr.soa.vo.SfSchTaskExecOosDtlVo;
import mb.erp.dr.soa.vo.SfSchTaskExecOosVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 老ERP单据流服务
 * @author     余从玉
 * @version    1.0, 2014-12-22
 * @see         PubO2oFlowServiceImpl
 * @since       全流通改造
 */
@Service
public class PubO2oFlowServiceImpl implements PubO2oFlowService {
	
	private final Logger logger = LoggerFactory.getLogger(PubO2oFlowServiceImpl.class);

	@Resource
	private PubO2oFlowMapper pubO2oFlowMapper;
	@Resource
	private NewERPCommonService newERPCommonService;
	@Resource
	private NewSoaJmsDubboService newSoaJmsDubboService;
	@Resource
	private SoaJmsDubboService soaJmsDubboService;
	
	
	/**
     * 结果信息表写入接口(预处理)
     * @param vo SfSchTaskExecOosVo
     * @param sfIdtVo 新erp现货订单
     * @param remark
     * @param allocType
     * @param docStatus
     */
    public void savePof4Prepare(SfSchTaskExecOosVo vo,PubO2oFlowVo pFlowVo ,String remark,AllocType allocType,POF_DocStatus docStatus){
    	PubO2oFlowVo pof = new PubO2oFlowVo();
    	pof.setRemark(remark);
    	pof.setBussinessMode(allocType);
    	pof.setDocStatus(docStatus);
    	pof.setDataTypeNo(O2OBillConstant.POF_DataTypeNo.B2C_IDT);
    	
    	String bizType = POF_BIZTYPE.O2O.name();
    	if ("1".equals(vo.getIsOos())) {
    		bizType = POF_BIZTYPE.OOS.name();
		}
    	pof.setBizType(bizType);
    	
    	Long qty =0l;
    	if (vo.getLstSfSchTaskExecOosDtls() != null && vo.getLstSfSchTaskExecOosDtls().size() > 0) {
    		for (SfSchTaskExecOosDtlVo dtlVo : vo.getLstSfSchTaskExecOosDtls()) {
    			qty += dtlVo.getQty();
    		}
		}
    	Double amount=0d;
    	if (SoaBillUtils.isNotBlank(vo.getSrcDocType()) 
    			&& vo.getSrcDocType().equals(SRC_DOC_TYPE.IS_TH)) {
//    		if (SoaBillUtils.isBlank(vo.getB2cDocCode())) {
//				throw new RuntimeException("退货单单号不能为空:"+JSON.toJSONString(vo));
//			}
//    		ScnVo scnVo = soaJmsDubboService.getScnVoByTDCode(vo.getB2cDocCode());
//    		if (scnVo == null) {
//    			throw new RuntimeException("此退货单不存在，单号:"+vo.getB2cDocCode());
//			}
//    		amount = scnVo.getCrVal();
    	}else{
    		SfIdtVo sfIdtVo = newSoaJmsDubboService.getSfIdtByCode(vo.getB2cDocCode());
    		amount = sfIdtVo.getOrderVal(); // b2c现货订单 金额
    	}
    	pof.setAmount(amount);
    	pof.setQty(qty);
    	pof.setDataNo(vo.getB2cDocCode());
    	pof.setDataType(PubO2OFlowType.B2C_NIDT.name());
    	pof.setBatchNo(pFlowVo.getBatchNo());
    	pof.setO2oSeqid(pFlowVo.getO2oSeqid());
    	pof.setShopid(vo.getShopCode());
    	pof.setUnitId(vo.getVendeeCode());
    	pof.setVendeeid(vo.getVenderCode());
    	pof.setWarehid(vo.getDispWarehCode());
    	pubO2oFlowMapper.save(pof);
    	
    	if (SoaBillUtils.isNotBlank(vo.getSrcDocType()) 
    			&& vo.getSrcDocType().equals(SRC_DOC_TYPE.IS_TH)) {
    		return;
    	}
    	//插入B2C老单据流
    	String doctype = vo.getSrcDocType();
    	PubO2oFlowVo oldPof = new PubO2oFlowVo();
    	Long seqId = pubO2oFlowMapper.getO2oSeqId();
    	oldPof.setBizType(bizType);
    	oldPof.setO2oSeqid(seqId);
		oldPof.setQty(qty);
		oldPof.setAmount(amount);
    	oldPof.setUnitId(vo.getVendeeCode());
		oldPof.setDataNo(vo.getSrcDocCode());
		oldPof.setInnerNo(vo.getB2cDocCode());
		oldPof.setBatchNo(pFlowVo.getBatchNo());
		oldPof.setShopid(vo.getShopCode());
		oldPof.setWarehid(vo.getDispWarehCode());
		oldPof.setVendeeid(vo.getVenderCode());
		oldPof.setRemark(vo.getRemark());
    	if ("0".equals(doctype)){//出库单
    		oldPof.setDataType(PubO2OFlowType.B2C_NGDN.name());
    		newERPCommonService.upSfGdnExFlag(vo.getSrcDocCode());
    		SfGdnVo sfGdnVo = newSoaJmsDubboService.selectSfGdnByCode(vo.getSrcDocCode());
    		oldPof.setQty(sfGdnVo.getTtlQty().longValue());
    		oldPof.setAmount(sfGdnVo.getTtlVal());
        }else if ("1".equals(doctype)){//发货单
    		oldPof.setDataType(PubO2OFlowType.B2C_NDVN.name());
    		newERPCommonService.upSfDegExFlag(vo.getSrcDocCode());
        }
    	pubO2oFlowMapper.save(oldPof);
    }
    
    /**
     * 错误结果信息表更新接口(预处理)
     * @param remark
     * @param b2cCode
     */
    public void savePof4PrepareUpdate(Integer pubFlowSeq , String b2cCode , String remark,String batchNo){
    	Map<String, String> param = new HashMap<String, String>();
    	param.put("b2cDocCode", b2cCode);
    	param.put("batchNo", batchNo);
    	PubO2oFlowVo pVo = pubO2oFlowMapper.getO2oInfo(param);
    	String[] remarks = pVo.getRemark().split(":::");
    	Boolean hasHandledFlag = false;
    	String pubFlowSeqString = pubFlowSeq.toString();
    	for (String s : remarks) {
    		String temp = s.split("::")[0];
    		if (pubFlowSeqString.equals(temp)) {
    			hasHandledFlag = true;
    			break;
    		}
    	}
    	if (!hasHandledFlag) {
    		PubO2oFlowVo pof = new PubO2oFlowVo();
    		logger.error("/////////////error/////////// b2cCode:"+b2cCode+" , batchNo:"+batchNo+" , pubFlowSeq:"+pubFlowSeqString+" , remark:"+remark);
    		pof.setDataNo(b2cCode);
    		pof.setBatchNo(batchNo);
    		pof.setRemark(":::"+pubFlowSeqString+"::"+remark);
    		pubO2oFlowMapper.updatePrepare(pof);
		}
    }
    
    /**
     * 队列重处理成功后，将单据流备注信息修改为正常状态
     * @param b2cCode
     */
    private void modify2Correct(String b2cCode,String batchNo,String remak,Integer pubFlowSeq){
    	String[] remarks = remak.split(":::");
    	String result = "";
    	String pubFlowSeqString = pubFlowSeq.toString();
    	for (String s : remarks) {
    		String temp = s.split("::")[0];
    		if (pubFlowSeqString.equals(temp)
    				|| SoaBillUtils.isBlank(temp)) {
    			continue;
    		}
    		result += ":::"+s;
    	}
    	POF_DocStatus status = null;
    	if (SoaBillUtils.isBlank(result)) {
    		status = POF_DocStatus.SU;
			result = "预处理队列 , 处理成功 .";
		}
    	PubO2oFlowVo pof = new PubO2oFlowVo();
    	logger.error("////////////correct//////////// b2cCode:"+b2cCode+" , batchNo:"+batchNo+" , pubFlowSeq:"+pubFlowSeqString+" , result:"+result+" , remak:"+remak);
    	pof.setDataNo(b2cCode);
    	pof.setBatchNo(batchNo);
    	pof.setRemark(result);
    	pof.setDocStatus(status);
    	pubO2oFlowMapper.updateCorrect(pof);
    }
    
    /**
     * 结果信息表写入接口(IDT)
     * @param vo IdtVo
     * @param remark
     * @param allocType
     * @param docStatus
     */
    public void savePof4Idt(SfSchTaskExecOosVo svo,IdtVo vo,String remark,POF_DocStatus docStatus){
    	PubO2oFlowVo pof = new PubO2oFlowVo();
    	pof.setRemark(remark);
    	pof.setBussinessMode(SoaBillUtils.isBlank(vo.getAllocType().name()) ? svo.getAllocType() : vo.getAllocType());
    	pof.setDocStatus(docStatus);
    	pof.setDataTypeNo(O2OBillConstant.POF_DataTypeNo.OLD_ERP_IDT);
    	String b2cDocCode = vo.getPubB2cDocCode();
    	pof.setAmount(vo.getOrderVal());
    	String bizType = POF_BIZTYPE.O2O.name();
    	if ("1".equals(vo.getIsOos())) {
    		bizType = POF_BIZTYPE.OOS.name();
		}
    	pof.setBizType(bizType);
    	if (vo.getIdtDtlVos() != null && vo.getIdtDtlVos().size() > 0) {
    		Long qty = 0l;
    		for (IdtDtlVo dtlVo : vo.getIdtDtlVos()) {
    			qty += dtlVo.getOrderQty().longValue();
    		}
    		pof.setQty(qty);
		}
    	pof.setDataNo(vo.getIdtNum());
    	pof.setDataType(PubO2OFlowType.B2B_OIDT.name());
    	Long seqId = pubO2oFlowMapper.getO2oSeqId();
    	pof.setO2oSeqid(seqId);
    	pof.setBatchNo(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO)));
    	pof.setShopid(vo.getRcvWarehId());
    	pof.setInnerNo(b2cDocCode);
    	pof.setUnitId(vo.getVendeeId());
    	pof.setVendeeid(vo.getVenderId());
    	pof.setDataFlowSeqid(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class));
    	pubO2oFlowMapper.save(pof);
    }
    
    /**
     * 结果信息表写入接口(TBN)
     * @param vo SfSchTaskExecOosVo
     * @param remark
     * @param docStatus
     */
    public void savePof4Tbn(BaseBizVo bvo,TbnVo vo,String remark,POF_DocStatus docStatus){
    	PubO2oFlowVo pof = new PubO2oFlowVo();
    	pof.setRemark(remark);
    	pof.setBussinessMode(SoaBillUtils.isBlank(vo.getAllocType().name()) ? bvo.getAllocType() : vo.getAllocType());
    	pof.setDocStatus(docStatus);
    	pof.setDataTypeNo(O2OBillConstant.POF_DataTypeNo.OLD_ERP_TBN);
    	String b2cDocCode = vo.getPubB2cDocCode();
    	pof.setAmount(vo.getCrVal());
    	pof.setQty(vo.getCrQty().longValue());
    	String bizType = POF_BIZTYPE.O2O.name();
    	if ("1".equals(vo.getIsOos())) {
    		bizType = POF_BIZTYPE.OOS.name();
		}
    	pof.setBizType(bizType);
    	pof.setDataNo(vo.getTbnNum());
    	pof.setDataType(PubO2OFlowType.B2B_OTBN.name());
    	Long seqId = pubO2oFlowMapper.getO2oSeqId();
    	pof.setBatchNo(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO)));
    	pof.setO2oSeqid(seqId);
    	pof.setInnerNo(b2cDocCode);
    	pof.setUnitId(vo.getVenderId());
    	pof.setVendeeid(vo.getVendeeId());
    	pof.setWarehid(vo.getDispWarehId());
    	pof.setShopid(vo.getRcvWarehId());
    	pof.setDataFlowSeqid(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class));
    	pubO2oFlowMapper.save(pof);
    }
    
    /**
     * 结果信息表写入接口(ADN)
     * @param vo AdnVo
     * @param remark
     * @param docStatus
     */
    public void savePof4Adn(AdnVo vo,String remark,POF_DocStatus docStatus){
    	PubO2oFlowVo pof = new PubO2oFlowVo();
    	pof.setRemark(remark);
    	pof.setBussinessMode(vo.getAllocType());
    	pof.setDocStatus(docStatus);
    	pof.setDataTypeNo(O2OBillConstant.POF_DataTypeNo.OLD_ERP_ADN);
    	String b2cDocCode = vo.getPubB2cDocCode();
    	pof.setAmount(vo.getAdmVal());
    	pof.setQty(vo.getAdmQty().longValue());
    	String bizType = POF_BIZTYPE.O2O.name();
    	pof.setBizType(bizType);
    	pof.setDataNo(vo.getAdnNum());
    	pof.setDataType(PubO2OFlowType.B2B_OADN.name());
    	Long seqId = pubO2oFlowMapper.getO2oSeqId();
    	pof.setO2oSeqid(seqId);
    	pof.setBatchNo(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO)));
    	pof.setInnerNo(b2cDocCode);
    	pof.setShopid(vo.getTranRcvWarehId());
    	pof.setUnitId(vo.getVenderId());
    	pof.setVendeeid(vo.getVendeeId());
    	pof.setWarehid(vo.getWarehId());
    	pof.setDataFlowSeqid(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class));
    	pubO2oFlowMapper.save(pof);
    }
    
    /**
     * 结果信息表写入接口(GDN)
     * @param vo SfSchTaskExecOosVo
     * @param remark
     * @param docStatus
     */
    public void savePof4Gdn(GdnVo vo,String remark,POF_DocStatus docStatus){
    	PubO2oFlowVo pof = new PubO2oFlowVo();
    	pof.setRemark(remark);
    	pof.setBussinessMode(vo.getAllocType());
    	pof.setDocStatus(docStatus);
    	pof.setDataTypeNo(O2OBillConstant.POF_DataTypeNo.OLD_ERP_GDN);
    	String b2cDocCode = vo.getPubB2cDocCode();
    	pof.setAmount(vo.getTtlVal());
    	pof.setQty(vo.getTtlQty().longValue());
    	String bizType = POF_BIZTYPE.O2O.name();
    	pof.setBizType(bizType);
    	pof.setDataNo(vo.getGdnNum());
    	pof.setDataType(PubO2OFlowType.B2B_OGDN.name());
    	Long seqId = pubO2oFlowMapper.getO2oSeqId();
    	pof.setO2oSeqid(seqId);
    	pof.setBatchNo(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO)));
    	pof.setInnerNo(b2cDocCode);
    	pof.setShopid(vo.getRcvWarehId());
    	pof.setUnitId(vo.getUnitId());
    	pof.setVendeeid(vo.getRcvUnitId());
    	pof.setWarehid(vo.getWarehId());
    	pof.setDataFlowSeqid(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class));
    	pubO2oFlowMapper.save(pof);
    }
    
    /**
     * 结果信息表写入接口(GRN)
     * @param vo SfSchTaskExecOosVo
     * @param remark
     * @param docStatus
     */
    public void savePof4Grn(GrnVo vo,String remark,POF_DocStatus docStatus){
    	PubO2oFlowVo pof = new PubO2oFlowVo();
    	pof.setRemark(remark);
    	pof.setBussinessMode(vo.getAllocType());
    	pof.setDocStatus(docStatus);
    	pof.setDataTypeNo(O2OBillConstant.POF_DataTypeNo.OLD_ERP_GRN);
    	String b2cDocCode = vo.getPubB2cDocCode();
    	pof.setAmount(vo.getTtlVal());
    	pof.setQty(vo.getTtlQty().longValue());
    	String bizType = POF_BIZTYPE.O2O.name();
    	pof.setBizType(bizType);
    	pof.setDataNo(vo.getGrnNum());
    	pof.setDataType(PubO2OFlowType.B2B_OGRN.name());
    	Long seqId = pubO2oFlowMapper.getO2oSeqId();
    	pof.setO2oSeqid(seqId);
    	String batchNo = SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO));
    	Map<String, String> param = new HashMap<String, String>();
    	param.put("b2cDocCode", b2cDocCode);
    	param.put("batchNo", batchNo);
    	PubO2oFlowVo pVo = pubO2oFlowMapper.getO2oInfo(param);
    	pof.setBatchNo(batchNo);
    	pof.setInnerNo(b2cDocCode);
    	pof.setShopid(vo.getWarehId());
    	pof.setUnitId(vo.getUnitId());
    	pof.setVendeeid(vo.getDispUnitId());
    	pof.setWarehid(vo.getDelivWarehId());
    	pof.setDataFlowSeqid(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class));
    	pubO2oFlowMapper.save(pof);
    	if (POF_DocStatus.RE.equals(pVo.getDocStatus())) {
    		modify2Correct(b2cDocCode,batchNo,pVo.getRemark(),SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class));
		}
    }
    /**
     * 结果信息表写入接口(BGR)
     * @param vo BgrVo
     * @param remark
     * @param docStatus
     */
    public void savePof4Bgr(BgrVo vo, String remark, POF_DocStatus docStatus) {
    	PubO2oFlowVo pof = new PubO2oFlowVo();
    	pof.setRemark(remark);
    	pof.setBussinessMode(vo.getAllocType());
    	pof.setDocStatus(docStatus);
    	pof.setDataTypeNo(O2OBillConstant.POF_DataTypeNo.OLD_ERP_BGR);
    	String b2cDocCode = vo.getPubB2cDocCode();
    	pof.setAmount(vo.getCrVal());
    	pof.setQty(vo.getCrQty().longValue());
    	String bizType = POF_BIZTYPE.O2O.name();
    	pof.setBizType(bizType);
    	pof.setDataNo(vo.getBgrNum());
    	pof.setDataType(PubO2OFlowType.B2B_OBGR.name());
    	Long seqId = pubO2oFlowMapper.getO2oSeqId();
    	pof.setO2oSeqid(seqId);
    	String batchNo = SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO));
    	Map<String, String> param = new HashMap<String, String>();
    	param.put("b2cDocCode", b2cDocCode);
    	param.put("batchNo", batchNo);
    	pof.setBatchNo(batchNo);
    	pof.setInnerNo(b2cDocCode);
    	pof.setShopid(vo.getRcvWarehId());
    	pof.setUnitId(vo.getVendeeId());
    	pof.setVendeeid(vo.getVenderId());
    	pof.setWarehid(vo.getDispWarehId());
    	pof.setDataFlowSeqid(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class));
    	pubO2oFlowMapper.save(pof);
    }
    
    /**
     * 结果信息表写入接口(BGR)
     * @param vo ScnVo
     * @param remark
     * @param docStatus
     */
    public void savePof4Scn(ScnVo vo, String remark, POF_DocStatus docStatus) {
    	PubO2oFlowVo pof = new PubO2oFlowVo();
    	pof.setRemark(remark);
    	pof.setBussinessMode(vo.getAllocType());
    	pof.setDocStatus(docStatus);
    	pof.setDataTypeNo(O2OBillConstant.POF_DataTypeNo.OLD_ERP_SCN);
    	String b2cDocCode = vo.getPubB2cDocCode();
    	pof.setAmount(vo.getCrVal());
    	pof.setQty(vo.getCrQty().longValue());
    	String bizType = POF_BIZTYPE.O2O.name();
    	pof.setBizType(bizType);
    	pof.setDataNo(vo.getScnNum());
    	pof.setDataType(PubO2OFlowType.B2B_OSCN.name());
    	Long seqId = pubO2oFlowMapper.getO2oSeqId();
    	pof.setO2oSeqid(seqId);
    	String batchNo = SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO));
    	Map<String, String> param = new HashMap<String, String>();
    	param.put("b2cDocCode", b2cDocCode);
    	param.put("batchNo", batchNo);
    	pof.setBatchNo(batchNo);
    	pof.setInnerNo(b2cDocCode);
    	pof.setShopid(vo.getRcvWarehId());
    	pof.setUnitId(vo.getVendeeId());
    	pof.setVendeeid(vo.getVenderId());
    	pof.setWarehid(vo.getDispWarehId());
    	pof.setDataFlowSeqid(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class));
    	pubO2oFlowMapper.save(pof);
    }
    
    public void deleteSql(PubO2oFlowVo pof){
    	pubO2oFlowMapper.deleteSql(pof);
    }

}
