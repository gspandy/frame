package mb.erp.dr.soa.old.service.impl.common;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.BASE_EXTRA;
import mb.erp.dr.soa.constant.O2OBillConstant.POF_BIZTYPE;
import mb.erp.dr.soa.constant.O2OBillConstant.POF_DocStatus;
import mb.erp.dr.soa.constant.O2OBillConstant.PubO2OFlowType;
import mb.erp.dr.soa.old.dao.PubO2oFlowMapper;
import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.old.vo.IdtDtlVo;
import mb.erp.dr.soa.old.vo.IdtVo;
import mb.erp.dr.soa.old.vo.PubO2oFlowVo;
import mb.erp.dr.soa.old.vo.TbnVo;
import mb.erp.dr.soa.service.bill.NewPubO2oFlowService;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.vo.DrTbnVo;
import mb.erp.dr.soa.vo.SfDgnVo;
import mb.erp.dr.soa.vo.SfGdnVo;
import mb.erp.dr.soa.vo.SfGrnVo;
import mb.erp.dr.soa.vo.SfIdtVo;
import mb.erp.dr.soa.vo.SfRvdVo;

import org.springframework.stereotype.Service;

/**
 * 新ERP单据流服务
 * 包含接口：
 * @author     余从玉
 * @version    1.0, 2014-12-22
 * @see         NewPubO2oFlowServiceImpl
 * @since       全流通改造
 */
@Service
public class NewPubO2oFlowServiceImpl implements NewPubO2oFlowService {

	@Resource
	private PubO2oFlowMapper pubO2oFlowMapper;
	
    /**
     * 结果信息表写入接口(IDT)
     * @param vo SfIdtVo
     * @param remark
     * @param allocType
     * @param docStatus
     */
    public void savePof4SfIdt(SfIdtVo sfIdtVo,IdtVo vo,String remark,POF_DocStatus docStatus){
    	PubO2oFlowVo pof = new PubO2oFlowVo();
    	pof.setRemark(remark);
    	pof.setBussinessMode(vo.getAllocType());
    	pof.setDocStatus(docStatus);
    	pof.setDataTypeNo(O2OBillConstant.POF_DataTypeNo.NEW_ERP_IDT);
    	
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
    	pof.setDataNo(sfIdtVo.getCode());
    	pof.setDataType(PubO2OFlowType.B2B_NIDT.name());
    	Long seqId = pubO2oFlowMapper.getO2oSeqId();
    	pof.setO2oSeqid(seqId);
    	pof.setBatchNo(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO)));
    	pof.setShopid(vo.getRcvWarehId());
    	pof.setInnerNo(vo.getPubB2cDocCode());
    	pof.setUnitId(vo.getVendeeId());
    	pof.setVendeeid(vo.getVenderId());
    	pof.setDataFlowSeqid(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class));
    	pubO2oFlowMapper.save(pof);
    }
    
    /**
     * 结果信息表写入接口(IDT)
     * @param vo SfIdtVo
     * @param remark
     * @param allocType
     * @param docStatus
     */
    public void savePof4SfIdt(SfIdtVo sfIdtVo,AdnVo vo,String remark,POF_DocStatus docStatus){
    	PubO2oFlowVo pof = new PubO2oFlowVo();
    	pof.setRemark(remark);
    	pof.setBussinessMode(vo.getAllocType());
    	pof.setDocStatus(docStatus);
    	pof.setDataTypeNo(O2OBillConstant.POF_DataTypeNo.NEW_ERP_IDT);
    	
    	pof.setAmount(vo.getAdmVal());
    	pof.setQty(vo.getAdmQty().longValue());
    	String bizType = POF_BIZTYPE.O2O.name();
    	pof.setBizType(bizType);
    	pof.setDataNo(sfIdtVo.getCode());
    	pof.setDataType(PubO2OFlowType.B2B_NIDT.name());
    	Long seqId = pubO2oFlowMapper.getO2oSeqId();
    	pof.setO2oSeqid(seqId);
    	pof.setBatchNo(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO)));
    	pof.setInnerNo(vo.getPubB2cDocCode());
    	pof.setShopid(vo.getTranRcvWarehId());
    	pof.setUnitId(vo.getVendeeId());
    	pof.setVendeeid(vo.getVenderId());
    	pof.setDataFlowSeqid(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class));
    	pubO2oFlowMapper.save(pof);
    }
    
    /**
     * 结果信息表写入接口(TBN)
     * @param vo DrTbnVo
     * @param remark
     * @param docStatus
     */
    public void savePof4DrTbn(DrTbnVo drTbnVo,TbnVo vo,String remark,POF_DocStatus docStatus){
    	PubO2oFlowVo pof = new PubO2oFlowVo();
    	pof.setRemark(remark);
    	pof.setBussinessMode(vo.getAllocType());
    	pof.setDocStatus(docStatus);
    	pof.setDataTypeNo(O2OBillConstant.POF_DataTypeNo.NEW_ERP_TBN);
    	
    	pof.setAmount(vo.getCrVal());
    	pof.setQty(vo.getCrQty().longValue());
    	String bizType = POF_BIZTYPE.O2O.name();
    	if ("1".equals(vo.getIsOos())) {
    		bizType = POF_BIZTYPE.OOS.name();
		}
    	pof.setBizType(bizType);
    	pof.setDataNo(drTbnVo.getTbnNum());
    	pof.setDataType(PubO2OFlowType.B2B_NTBN.name());
    	Long seqId = pubO2oFlowMapper.getO2oSeqId();
    	pof.setBatchNo(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO)));
    	pof.setO2oSeqid(seqId);
    	pof.setInnerNo(vo.getPubB2cDocCode());
    	pof.setUnitId(vo.getVenderId());
    	pof.setVendeeid(vo.getVendeeId());
    	pof.setWarehid(vo.getDispWarehId());
    	pof.setShopid(vo.getRcvWarehId());
    	pof.setDataFlowSeqid(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class));
    	pubO2oFlowMapper.save(pof);
    }
    
    /**
     * 结果信息表写入接口(DGN)
     * @param vo SfDgnVo
     * @param remark
     * @param docStatus
     */
    public void savePof4SfDgn(SfDgnVo sfDgnVo,AdnVo vo,String remark,POF_DocStatus docStatus){
    	PubO2oFlowVo pof = new PubO2oFlowVo();
    	pof.setRemark(remark);
    	pof.setBussinessMode(vo.getAllocType());
    	pof.setDocStatus(docStatus);
    	pof.setDataTypeNo(O2OBillConstant.POF_DataTypeNo.NEW_ERP_DGN);
    	
    	pof.setAmount(vo.getAdmVal());
    	pof.setQty(vo.getAdmQty().longValue());
    	String bizType = POF_BIZTYPE.O2O.name();
    	pof.setBizType(bizType);
    	pof.setDataNo(sfDgnVo.getCode());
    	pof.setDataType(PubO2OFlowType.B2B_NDGN.name());
    	Long seqId = pubO2oFlowMapper.getO2oSeqId();
    	pof.setO2oSeqid(seqId);
    	pof.setBatchNo(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO)));
    	pof.setInnerNo(vo.getPubB2cDocCode());
    	pof.setShopid(vo.getTranRcvWarehId());
    	pof.setUnitId(vo.getVenderId());
    	pof.setVendeeid(vo.getVendeeId());
    	pof.setWarehid(vo.getWarehId());
    	pof.setDataFlowSeqid(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class));
    	pubO2oFlowMapper.save(pof);
    }
    
    /**
     * 结果信息表写入接口(DGN)
     * @param vo SfDgnVo
     * @param remark
     * @param docStatus
     */
    public void savePof4SfDgn(SfDgnVo sfDgnVo,GdnVo vo,String remark,POF_DocStatus docStatus){
    	PubO2oFlowVo pof = new PubO2oFlowVo();
    	pof.setRemark(remark);
    	pof.setBussinessMode(vo.getAllocType());
    	pof.setDocStatus(docStatus);
    	pof.setDataTypeNo(O2OBillConstant.POF_DataTypeNo.NEW_ERP_DGN);
    	
    	pof.setAmount(vo.getTtlVal());
    	pof.setQty(vo.getTtlQty().longValue());
    	String bizType = POF_BIZTYPE.O2O.name();
    	pof.setBizType(bizType);
    	pof.setDataNo(sfDgnVo.getCode());
    	pof.setDataType(PubO2OFlowType.B2B_NDGN.name());
    	Long seqId = pubO2oFlowMapper.getO2oSeqId();
    	pof.setO2oSeqid(seqId);
    	pof.setBatchNo(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO)));
    	pof.setInnerNo(vo.getPubB2cDocCode());
    	pof.setShopid(vo.getRcvWarehId());
    	pof.setUnitId(vo.getUnitId());
    	pof.setVendeeid(vo.getRcvUnitId());
    	pof.setWarehid(vo.getWarehId());
    	pof.setDataFlowSeqid(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class));
    	pubO2oFlowMapper.save(pof);
    }
    
    /**
     * 结果信息表写入接口(GDN)
     * @param vo SfGdnVo
     * @param remark
     * @param docStatus
     */
    public void savePof4SfGdn(SfGdnVo sfGdnVo,GdnVo vo,String remark,POF_DocStatus docStatus){
    	PubO2oFlowVo pof = new PubO2oFlowVo();
    	pof.setRemark(remark);
    	pof.setBussinessMode(vo.getAllocType());
    	pof.setDocStatus(docStatus);
    	pof.setDataTypeNo(O2OBillConstant.POF_DataTypeNo.NEW_ERP_GDN);
    	
    	pof.setAmount(vo.getTtlVal());
    	pof.setQty(vo.getTtlQty().longValue());
    	String bizType = POF_BIZTYPE.O2O.name();
    	pof.setBizType(bizType);
    	pof.setDataNo(sfGdnVo.getCode());
    	pof.setDataType(PubO2OFlowType.B2B_NGDN.name());
    	Long seqId = pubO2oFlowMapper.getO2oSeqId();
    	pof.setO2oSeqid(seqId);
    	pof.setBatchNo(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO)));
    	pof.setInnerNo(vo.getPubB2cDocCode());
    	pof.setShopid(vo.getRcvWarehId());
    	pof.setUnitId(vo.getUnitId());
    	pof.setVendeeid(vo.getRcvUnitId());
    	pof.setWarehid(vo.getWarehId());
    	pof.setDataFlowSeqid(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class));
    	pubO2oFlowMapper.save(pof);
    }
    
    /**
     * 结果信息表写入接口(RVD)
     * @param vo SfGdnVo
     * @param remark
     * @param docStatus
     */
    public void savePof4SfRvd(SfRvdVo sfRvdVo,GrnVo vo,String remark,POF_DocStatus docStatus){
    	PubO2oFlowVo pof = new PubO2oFlowVo();
    	pof.setRemark(remark);
    	pof.setBussinessMode(vo.getAllocType());
    	pof.setDocStatus(docStatus);
    	pof.setDataTypeNo(O2OBillConstant.POF_DataTypeNo.NEW_ERP_RVD);
    	
    	pof.setAmount(vo.getTtlVal());
    	pof.setQty(vo.getTtlQty().longValue());
    	String bizType = POF_BIZTYPE.O2O.name();
    	pof.setBizType(bizType);
    	pof.setDataNo(sfRvdVo.getCode());
    	pof.setDataType(PubO2OFlowType.B2B_NRVD.name());
    	Long seqId = pubO2oFlowMapper.getO2oSeqId();
    	pof.setO2oSeqid(seqId);
    	pof.setBatchNo(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO)));
    	pof.setInnerNo(vo.getPubB2cDocCode());
    	pof.setShopid(vo.getWarehId());
    	pof.setUnitId(vo.getUnitId());
    	pof.setVendeeid(vo.getDispUnitId());
    	pof.setWarehid(vo.getDelivWarehId());
    	pof.setDataFlowSeqid(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class));
    	pubO2oFlowMapper.save(pof);
    }
    
    /**
     * 结果信息表写入接口(GRN)
     * @param vo SfGrnVo
     * @param remark
     * @param docStatus
     */
    public void savePof4SfGrn(SfGrnVo sfGrnVo,GrnVo vo,String remark,POF_DocStatus docStatus){
    	PubO2oFlowVo pof = new PubO2oFlowVo();
    	pof.setRemark(remark);
    	pof.setBussinessMode(vo.getAllocType());
    	pof.setDocStatus(docStatus);
    	pof.setDataTypeNo(O2OBillConstant.POF_DataTypeNo.NEW_ERP_GRN);
    	
    	pof.setAmount(vo.getTtlVal());
    	pof.setQty(vo.getTtlQty().longValue());
    	String bizType = POF_BIZTYPE.O2O.name();
    	pof.setBizType(bizType);
    	pof.setDataNo(sfGrnVo.getGrnNum());
    	pof.setDataType(PubO2OFlowType.B2B_NGRN.name());
    	Long seqId = pubO2oFlowMapper.getO2oSeqId();
    	pof.setO2oSeqid(seqId);
    	pof.setBatchNo(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_BATCH_NO)));
    	pof.setInnerNo(vo.getPubB2cDocCode());
    	pof.setShopid(vo.getWarehId());
    	pof.setUnitId(vo.getUnitId());
    	pof.setVendeeid(vo.getDispUnitId());
    	pof.setWarehid(vo.getDelivWarehId());
    	pof.setDataFlowSeqid(SoaBillUtils.toObject(vo.getExtraParams().get(BASE_EXTRA.DATA_FLOW_SEQID),Integer.class));
    	pubO2oFlowMapper.save(pof);
    }
}
